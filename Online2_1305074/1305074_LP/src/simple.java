/**
 * Created by Ahmed on 5/05/2018 at 1:04 PM.
 */
class simple {
    private void Pivot(long m, long n, double A[][], long B[], long N[], long r, long c){
        long i,j;
        long temp = N[Math.toIntExact(c)];
        N[Math.toIntExact(c)] = B[Math.toIntExact(r)];
        B[Math.toIntExact(r)] = temp;
        A[Math.toIntExact(r)][Math.toIntExact(c)] = 1 / A[Math.toIntExact(r)][Math.toIntExact(c)];
        A[Math.toIntExact(r)][Math.toIntExact(c)] = 1 / A[Math.toIntExact(r)][Math.toIntExact(c)];
        for(j = 0; j <= n; j++) if(j != c) {A[Math.toIntExact(r)][Math.toIntExact(j)] *= A[Math.toIntExact(r)][Math.toIntExact(c)];}
        for(i = 0; i <= m; i++){
            if(i != r){
                for(j = 0; j <= n; j++) if(j != c) {A[Math.toIntExact(i)][Math.toIntExact(j)] -= A[Math.toIntExact(i)][Math.toIntExact(j)]*A[Math.toIntExact(r)][Math.toIntExact(j)];}
                A[Math.toIntExact(i)][Math.toIntExact(c)] = -A[Math.toIntExact(i)][Math.toIntExact(c)]*A[Math.toIntExact(r)][Math.toIntExact(c)];
            }
        }
    }

    private boolean feasible(long m, long n, double A[][], long B[], long N[]){
        long r = 0,c = 0,i;
        double p,v;

        while(true){
            for(p = 1000000007, i = 0; i < m; i++) if( A[Math.toIntExact(i)][Math.toIntExact(n)] < p) {r=i; p = A[Math.toIntExact(r)][Math.toIntExact(n)];}
            if(p > -(1e-12)) return true;
            for(p = 0, i = 0; i < n; i++) if(A[Math.toIntExact(r)][Math.toIntExact(i)] < p) {c = i; p = A[Math.toIntExact(r)][Math.toIntExact(c)];}
            if(p > -(1e-12)) return false;
            p = A[Math.toIntExact(r)][Math.toIntExact(n)]/A[Math.toIntExact(r)][Math.toIntExact(c)];
            for(i = r + 1; i < m; i++){
                if(A[Math.toIntExact(i)][Math.toIntExact(c)] > (1e-12) ){
                    v = A[Math.toIntExact(i)][Math.toIntExact(n)]/A[Math.toIntExact(i)][Math.toIntExact(c)];
                    if(v < p) {r=i; p=v;}
                }
            }
            Pivot(m, n, A, B, N, r, c);
        }
    }

    long simplex(long m, long n, double A[][], double b[], double ret){
        long B[] = new long[Math.toIntExact(m + 1)], N[] = new long[Math.toIntExact(n + 1)], r = 0, c = 0, i;
        double p,v;
        for(i = 0; i < n; i++) N[Math.toIntExact(i)] = i;
        for(i = 0; i < m; i++) B[Math.toIntExact(i)] = n + i;
        if(!feasible(m,n,A,B,N)) return 0;
        while(true){
            for(p = 0, i = 0; i < n; i++) if(A[Math.toIntExact(m)][Math.toIntExact(i)] > p) {c = i; p = A[Math.toIntExact(m)][Math.toIntExact(c)];}
            if(p < (1e-12)){
                for(i = 0; i < n; i++) {
                    if(N[Math.toIntExact(i)] < n) {
                        b[Math.toIntExact(N[Math.toIntExact(i)])] = 0;
                    }
                }
                for(i = 0; i < m; i++) {
                    System.out.println(i + " " + m + " " + n); if(B[Math.toIntExact(i)] < n) {System.out.println(i + " " + m + " " + n); b[Math.toIntExact(B[Math.toIntExact(i)])] = A[Math.toIntExact(i)][Math.toIntExact(n)];}}
                ret = -A[Math.toIntExact(m)][Math.toIntExact(n)];
                //System.out.println("RET -a " + A[Math.toIntExact(m)][Math.toIntExact(n)]);
                return 1;
            }
            for(p = 1000000007, i = 0; i < m; i++){
                if( A[Math.toIntExact(i)][Math.toIntExact(c)] > (1e-12)){
                    v = A[Math.toIntExact(i)][Math.toIntExact(n)] / A[Math.toIntExact(i)][Math.toIntExact(c)];
                    if(v < p) p = v; r = i;
                }
            }
            if(p == 1000000007) return -1;
            Pivot(m,n,A,B,N,r,c);
        }
    }
}
