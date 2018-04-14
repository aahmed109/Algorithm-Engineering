import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ahmed on 14/04/2018 at 3:26 AM.
 */
public class twoApprox {
    private static boolean[] visited;
    private static int[] preOrders;
    private static boolean[] visitedEdge;
    private static boolean allSame(boolean[] array){
        boolean flag = true;
        boolean first = array[0];
        for(int i = 1; i < array.length && flag; i++)
        {
            if (array[i] != first) flag = false;
        }
        return flag;
    }
    private static int[] find(weirdClass[] weirdClasses, int n){
        int[] num = new int[weirdClasses.length];
        Arrays.fill(num, 9999);
        int j = 0;
        for(int i = 1; i < weirdClasses.length; i++){
            if(weirdClasses[i].parent == n){
                num[j++] = weirdClasses[i].child;
            }
        }

        return num;
    }
    private static void preOrder(weirdClass[] weirdClasses, int numPoints, int s, int maxInd){
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        LinkedList<Integer>[] integerLinkedList = new LinkedList[numPoints];
        for(int i = 0; i < weirdClasses.length; i++){
            integerLinkedList[i] = new LinkedList<>();
            int[] num = find(weirdClasses, i);
            for(int j = 0; j < num.length; j++){
                if(num[j] == 9999) break;
                integerLinkedList[i].add(num[j]);
            }
        }

        int[] num = find(weirdClasses, weirdClasses.length - 1);
        for(int j = 0; j < num.length; j++){
            if(num[j] == 9999) break;
            integerLinkedList[weirdClasses.length - 1].add(num[j]);
        }

        while (!stack.empty()){
            s = stack.peek();
            //System.out.print("printing peek: ");
            //System.out.println(s);
            stack.pop();

            if(!visited[s]){
                visited[s] = true;
                preOrders[maxInd++] = s;
            }

            Iterator<Integer> integerIterator = integerLinkedList[s].iterator();

            while (integerIterator.hasNext()){
                int j = integerIterator.next();
                if(!visited[j]) stack.push(j);
            }

        }
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

    public static void main(String[] args) {
        String filename = "src/testCase.txt";
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

        MST mst = new MST(size);
        double[][] graph = new double[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(i==j){
                    graph[i][j] = 0.0;
                }
                else{
                    graph[i][j] = graph[j][i] = nodeArray.distance(nodeArray.getNodes()[i], nodeArray.getNodes()[j]);
                }
            }
        }
        weirdClass[] weirdClasses;
        weirdClasses = mst.primMST(graph);
        System.out.println("printing child");

        visited = new boolean[size];
        visitedEdge = new boolean[weirdClasses.length];
        Arrays.fill(visited, false);
        Arrays.fill(visitedEdge, false);

        preOrders = new int[size + 1];
        preOrder(weirdClasses, size, 0, 0);
        preOrders[size] = preOrders[0];


        System.out.print("FINAL RESULT: ");
        double cost = 0.0;
        for(int i = 0; i < preOrders.length - 1; i ++){
            if(i > 0){
                nodes nodes1 = new nodes(nodeArray.getNodes()[preOrders[i]].getX(), nodeArray.getNodes()[preOrders[i]].getY());
                cost += nodeArray.distance(nodes1, new nodes(nodeArray.getNodes()[preOrders[i-1]].getX(), nodeArray.getNodes()[preOrders[i-1]].getY()));
            }
            int x = nodeArray.getNodes()[preOrders[i]].getX();
            int y = nodeArray.getNodes()[preOrders[i]].getY();
            System.out.print("(" + x + ", " + y + "), ");
        }
        System.out.println("(" + nodeArray.getNodes()[preOrders[preOrders.length - 1]].getX() + ", " + nodeArray.getNodes()[preOrders[preOrders.length - 1]].getY() + ")");
        cost += nodeArray.distance(nodeArray.getNodes()[preOrders[preOrders.length - 1]], nodeArray.getNodes()[preOrders[preOrders.length - 2]]);
        System.out.println("COST = " + cost);
        System.out.println((System.nanoTime() - startTime)/1000000000.0);
    }
}

