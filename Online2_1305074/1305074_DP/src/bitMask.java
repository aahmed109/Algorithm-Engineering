import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.*;

/**
 * Created by Ahmed on 29/04/2018 at 2:44 PM.
 */
public class bitMask {
    private static double globalMin = Double.MAX_VALUE;
    private static double memo[][];
    private static ArrayList<Integer> arrayList = new ArrayList<>();
    private static ArrayList result = new ArrayList();

    private static double min_value(int current, int bitmask, double cost, ArrayList<Integer> uni, ArrayList<ArrayList> subs, ArrayList arrayList){

        if(current == uni.size() && bitmask ==  pow(2, subs.size()) - 1) {
            double weight  = 0.0;
            cost = round(cost * 10.0) / 10.0;
            memo[current][bitmask] = cost;
            for(int i = 0; i < arrayList.size(); i++){
                if((int) arrayList.get(i) == 1){
                    weight += (double) subs.get(i).get(0);
                }
            }
            if(weight < globalMin){
                globalMin = weight;
                result.clear();
                for(int i = 0; i < arrayList.size(); i++){
                    if((int) arrayList.get(i) == 1){
                        result.add(subs.get(i).get(1));
                    }
                }
            }
            return cost;
        }
        else if(current == uni.size() && bitmask !=  pow(2, subs.size()) - 1) {
            memo[current][bitmask] = Double.MAX_VALUE;
            return Double.MAX_VALUE;
        }
        else{
            //if(current > 0) System.out.println(arrayList.get(current - 1));
            arrayList.set(current, 0);
            double ans = min_value(current + 1, bitmask, cost, uni, subs, arrayList);
            arrayList.set(current, 1);
            int setPattern = 0;
            for(int i = 0; i < subs.size(); i++){
                if((int) subs.get(i).get(3) == current || (int) subs.get(i).get(4) == current){
                    setPattern |= 1 << i;
                }
            }
            double temp = min_value(current + 1, bitmask | setPattern, cost + (double) subs.get(current).get(0), uni, subs, arrayList);
            ans = min(ans, temp);
            ans = round(ans * 10.0) / 10.0;
            memo[current][bitmask] = ans;

            return memo[current][bitmask];
        }
    }

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

        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            T = Integer.parseInt(bufferedReader.readLine());

            for(int i = 0; i < T; i ++) {
                N = Integer.parseInt(bufferedReader.readLine());
                ArrayList<Integer> universal = new ArrayList<>(N);
                M = Integer.parseInt(bufferedReader.readLine());
                ArrayList<ArrayList> subsets = new ArrayList<>();
                for (int j = 0; j < M; j++) {
                    line = bufferedReader.readLine();
                    String[] s = line.split(" ");
                    ArrayList arrayList1 = new ArrayList();
                    subsets.add(arrayList1);
                    ArrayList arrayList = subsets.get(j);
                    arrayList.add(Double.parseDouble(s[0]));
                    arrayList.add(1, j);
                    ArrayList<Integer> toUnion = new ArrayList<>();
                    for (int k = 0; k < 2; k++) {
                        arrayList.add(Integer.parseInt(s[k + 1]));
                        toUnion.add(Integer.parseInt(s[k + 1]));
                    }
                    universal = union(universal, toUnion);
                    for (Integer anUniversal : universal) {
                        System.out.print(anUniversal);
                    }
                    System.out.println();
                }

                for (int l = 0; l < subsets.size(); l++) {
                    int setPattern = 0;
                    for (int m = 2; m < subsets.get(l).size(); m++) {
                        int temp = (int) subsets.get(l).get(m);
                        setPattern |= 1 << temp;
                    }
                    subsets.get(l).add(2, setPattern);
                }
                memo = new double[universal.size() + 1][(int) (pow(2, subsets.size()))];
                for (double[] arr : memo) {
                    Arrays.fill(arr, -1);
                }
                long startTime = System.nanoTime();
                arrayList = new ArrayList<>(Collections.nCopies(subsets.size(), -1));
                double s = min_value(0, 0, 0.0, universal, subsets, arrayList);
                System.out.println(s);
                for (Object aResult : result) {
                    System.out.println(aResult);
                }
                result.clear();
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

