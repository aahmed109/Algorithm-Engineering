import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Ahmed on 13/04/2018 at 12:49 AM.
 */
public class branchAndBound {
    private static double finalResult = 9999;
    private static nodeArray finalNodes;
    private static boolean[] visited;
    private static double firstMin(nodeArray nodeArray, int n){
        double min = 9999;
        nodes inspect = nodeArray.getNodes()[n];
        for(int k = 0; k < nodeArray.getLen(); k++){
            double dist =  nodeArray.distance(inspect, nodeArray.getNodes()[k]);
            if(min > dist && n != k){
                min = dist;
            }
        }
        //System.out.println(min);
        return min;
    }

    private static double secondMin(nodeArray nodeArray, int n){
        double first = 9999;
        double second = 9999;
        nodes inspect = nodeArray.getNodes()[n];
        for(int i = 0; i < nodeArray.getLen(); i++){
            double dist =  nodeArray.distance(inspect, nodeArray.getNodes()[i]);
            if(n == i){
                continue;
            }
            if(dist <= first){
                second = first;
                first = dist;
            }
            else if(dist <= second && dist > first){
                second = dist;
            }
        }
        //System.out.println(second);
        return second;
    }
    private static nodeArray fileReader(String filename) {
        String line;
        nodes[] graphNode = new nodes[100];
        nodeArray nodeArray = null;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                graphNode[i] = new nodes(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1]));
                i += 1;
            }
            nodes[] realGraphNode = new nodes[i];
            System.arraycopy(graphNode, 0, realGraphNode, 0, i);

            nodeArray = new nodeArray(i, realGraphNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodeArray;
    }

    private static<T> int find(T[] a, T target){
        return Arrays.asList(a).indexOf(target);
    }

    private static void TSPRec(nodeArray nodeArray, int currentBound, int currentWeight, int level, nodeArray currentPath){
        if(level == nodeArray.getLen()){
            if(nodeArray.distance(currentPath.getNodes()[level - 1], currentPath.getNodes()[0]) > 0.0009){
                double currentResult = currentWeight + currentPath.distance(currentPath.getNodes()[level - 1], currentPath.getNodes()[0]);
                if(currentResult < finalResult){
                    //nodes[] nodeArray1 = nodeArray.getNodes().clone();
                    //System.arraycopy(nodeArray1, 0, finalNodes.getNodes(), 0, nodeArray.getLen());
                    System.arraycopy(nodeArray.getNodes(), 0, finalNodes.getNodes(), 0, nodeArray.getLen());
                    finalNodes.getNodes()[nodeArray.getLen()] = nodeArray.getNodes()[0];
                    finalResult = currentResult;
                }
            }
            return;
        }

        for(int i = 0; i < nodeArray.getLen(); i++){
            if(!visited[i] && nodeArray.distance(nodeArray.getNodes()[i], currentPath.getNodes()[level - 1]) > 0.0009){
                //System.out.println(nodeArray.distance(nodeArray.getNodes()[i], currentPath.getNodes()[level - 1]));
                int temp = currentBound;
                currentWeight += nodeArray.distance(nodeArray.getNodes()[i], currentPath.getNodes()[level - 1]);
                if(level == 1){
                    currentBound -= (firstMin(nodeArray, level - 1) + firstMin(nodeArray, i)) / 2;
                }
                else {
                    currentBound -= (secondMin(nodeArray, level - 1) + firstMin(nodeArray, i)) / 2;
                }

                if(finalResult > currentBound + currentWeight){
                    currentPath.getNodes()[level] = nodeArray.getNodes()[i];
                    //System.out.println(i);
                    visited[i] = true;

                    TSPRec(nodeArray, currentBound, currentWeight, level + 1, currentPath);
                }
                //else System.out.println("into else for once");

                currentWeight -= nodeArray.distance(nodeArray.getNodes()[i], currentPath.getNodes()[level - 1]);
                currentBound = temp;

                Arrays.fill(visited, false);
                for(int ii = 0; ii < level; ii++){
                    int index = find(nodeArray.getNodes(), currentPath.getNodes()[ii]);
                    visited[index] = true;     //fixing necessary
                }
            }
        }
    }
    public static void main(String[] args) {
        //String filename = "src/branchAndBound.txt";
        String filename = "src/exactExponent.txt";
        nodeArray nodeArray = null;
        nodeArray = fileReader(filename);
        Scanner scanner = new Scanner(System.in);
        int sourceNum = 0;
        assert nodeArray != null;
        while ((sourceNum = scanner.nextInt()) >= nodeArray.getLen()) {
            System.out.println("wrong value");
        }
        long startTime = System.nanoTime();
        int size = nodeArray.getLen();
        finalNodes = new nodeArray(size + 1);
        //System.out.println(finalNodes.getLen());
        visited = new boolean[size];
        Arrays.fill(visited, false);
        nodes sourceNode = new nodes(nodeArray.getNodes()[sourceNum].getX(), nodeArray.getNodes()[sourceNum].getY());
        nodes[] path = new nodes[size + 1];

        nodeArray currentPath = new nodeArray(size + 1);

        int currentBound = 0;

        for(int i = 0; i < size; i++){
            currentBound += (firstMin(nodeArray,i) + secondMin(nodeArray,i));
        }

        currentBound = (int) (currentBound * 0.5);
        visited[0] = true;
        currentPath.getNodes()[0] = nodeArray.getNodes()[0];

        TSPRec(nodeArray, currentBound, 0, 1, currentPath);

        System.out.print("FINAL RESULT: ");
        for(int ii = 0; ii < finalNodes.getLen() - 1; ii++) {
            System.out.print("(" + finalNodes.getNodes()[ii].getX() + ", " +  finalNodes.getNodes()[ii].getY() + "), ");
        }
        System.out.println("(" + finalNodes.getNodes()[finalNodes.getLen() - 1].getX() + ", " + finalNodes.getNodes()[finalNodes.getLen() - 1].getY() + ")");
        System.out.println("COST = " + finalResult);

        System.out.println("Run Time = " + (System.nanoTime() - startTime)/1000000000.0 + " sec");
    }
}
