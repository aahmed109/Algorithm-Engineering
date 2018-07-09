import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.pow;

/**
 * Created by Ahmed on 29/04/2018 at 4:49 PM.
 */
public class approxLP {
    private static double [][] A;
    private static int [] X;
    private static int [] ind;
    private static int [] covers;
    private static ArrayList<Integer> union(ArrayList<Integer> universal, ArrayList<Integer> toUnion){
        for (Integer aToUnion : toUnion) {
            int X = aToUnion;
            if (!universal.contains(X)) {
                universal.add(X);
            }
        }
        return universal;
    }
    private static void fileReader(String filename) throws IOException {
        String line;
        int T, N, M;
        double finalWeight = 0.0;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            T = Integer.parseInt(bufferedReader.readLine());

            for(int i = 0; i < T; i ++){
                N = Integer.parseInt(bufferedReader.readLine());
                ArrayList<Integer> universal = new ArrayList<>(N);
                M = Integer.parseInt(bufferedReader.readLine());
                ArrayList<ArrayList> subsets = new ArrayList<>();
                for(int j = 0; j < M; j++) {
                    line = bufferedReader.readLine();
                    String[] s = line.split(" ");
                    ArrayList arrayList1 = new ArrayList();
                    subsets.add(arrayList1);
                    ArrayList arrayList = subsets.get(j);
                    arrayList.add(Double.parseDouble(s[0]));
                    arrayList.add(1, j);
                    ArrayList<Integer> toUnion = new ArrayList<>();
                    for(int k = 0; k < 2; k++){
                        arrayList.add(Integer.parseInt(s[k + 1]));
                        toUnion.add(Integer.parseInt(s[k + 1]));
                    }
                    universal = union(universal, toUnion);
                    for (Integer anUniversal : universal) {
                        System.out.print(anUniversal);
                    }
                    System.out.println();
                }
                ind  = new int[subsets.size()];
                covers = new int[subsets.size()];
                long startTime = System.nanoTime();
                A = new double[universal.size() + 1][subsets.size() + 1];
                X = new int[subsets.size()];
                for(double[] arr : A){
                    Arrays.fill(arr,0);
                }

                for(int j = 0; j < universal.size() - 1; j++){
                    for(int k = 0; k < subsets.size() - 1; k++){
                        for(int l = 2; l < subsets.get(k).size(); l++){
                            if((int) subsets.get(k).get(l) == j){
                                A[j][k] = -1;
                            }
                        }
                    }
                }

                for(int j = 0; j < A[0].length - 1; j++){
                    A[universal.size()][j] = -(double) subsets.get(j).get(0);
                }
                //System.out.println("Printing THE ARRAY");
                for(int j = 0; j < A.length; j++){
                    for(int k = 0; k < A[j].length; k++){
                        System.out.print(A[j][k] + " ");
                    }
                    System.out.println();
                }
                //System.out.println("Printed");
                simple simple = new simple();
                double b[] = new double[subsets.size() + 1];
                Arrays.fill(b, 0);
                double ret = 0.0;
                long nn = simple.simplex(N, M, A, b, ret);
                System.out.println(nn);
                System.out.println(ret);
                for (int j = 0; j < b.length; j++) {
                    if (b[j] != 0.0) System.out.print(b[j] + " ");
                }
                System.out.println();

                System.out.println("Run Time = " + (System.nanoTime() - startTime)/1000000000.0 + " sec");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filename = "src/1305074_test.txt";
        try{
            fileReader(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
