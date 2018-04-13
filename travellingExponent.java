import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Ahmed on 12/04/2018 at 12:26 PM.
 */

class nodeArray{
    private int len = 1;
    private nodes[] nodes = new nodes[len];
    private int cost = 0;
    nodeArray(int len, nodes[] nodes){
        this.len = len;
        this.nodes = nodes;
        cost = measureCost();
    }

    nodeArray(){
        for(int i = 0; i < this.len; i++){
            nodes[i] = new nodes(9999,9999);
        }
    }

    nodeArray(int len){
        this.nodes = new nodes[len];
        this.len = len;
        for(int i = 0; i < len; i++){
            nodes[i] = new nodes(9999,9999);
        }
    }

    void setLen(int len){
        nodes = new nodes[len];
        this.len = len;
    }
    int getLen(){
        return len;
    }

    nodes[] getNodes(){
        return nodes;
    }
    private int absolute(int n){
        if(n < 0) return -n;
        return n;
    }
    int distance(nodes node1, nodes node2){
        return (absolute(node1.getX() - node2.getX()) + absolute(node1.getY() - node2.getY()));
    }
    private int measureCost(){
        int c = 0;
        for(int i = 0; i < len - 1; i++){
            c += distance(nodes[i], nodes[i + 1]);
        }
        return c;
    }

    int getCost(){
        return cost;
    }

}

class nodes{
    private int X = 9999;
    private int Y = 9999;

    nodes(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    int getX(){
        return X;
    }

    int getY(){
        return Y;
    }
}

public class travellingExponent {
    private static int minValue = 9999;
    private static nodeArray minNode = new nodeArray();
    private static nodeArray finalize(nodeArray nodeArray, nodes sourceNode){
        nodes[] q = nodeArray.getNodes();
        nodes[] temp = new nodes[q.length + 2];
        temp[0] = temp[temp.length - 1] = sourceNode;
        System.arraycopy(q, 0, temp, 1, temp.length - 1 - 1);
        return new nodeArray(temp.length, temp);
    }

    private static void permute(nodeArray nodeArray, int num, nodes source){
        int i;
        if(num == nodeArray.getLen()){
            nodeArray finalCost = finalize(nodeArray, source);

            if(minValue > finalCost.getCost()){
                minValue = finalCost.getCost();
                minNode = finalCost;
            }
        }
        else{
            for(i = num; i < nodeArray.getLen(); i++){
                nodes temp = nodeArray.getNodes()[num];
                nodeArray.getNodes()[num] = nodeArray.getNodes()[i];
                nodeArray.getNodes()[i] = temp;

                permute(nodeArray, num + 1, source);

                temp = nodeArray.getNodes()[num];
                nodeArray.getNodes()[num] = nodeArray.getNodes()[i];
                nodeArray.getNodes()[i] = temp;
            }
        }
    }

    private static nodeArray fileReader(String filename) throws IOException {
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
        String filename = "src/exactExponent.txt";
        //String filename = "src/branchAndBound.txt";
        nodeArray nodeArray = null;
        try {
            nodeArray = fileReader(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        int sourceNum;
        assert nodeArray != null;
        while ((sourceNum = scanner.nextInt()) >= nodeArray.getLen()) {
            System.out.println("wrong value");
        }
        long startTime = System.nanoTime();
        nodes sourceNode = new nodes(nodeArray.getNodes()[sourceNum].getX(), nodeArray.getNodes()[sourceNum].getY());

        nodeArray moveNodeArray = new nodeArray();
        moveNodeArray.setLen(nodeArray.getLen() - 1);
        System.arraycopy(nodeArray.getNodes(), 1, moveNodeArray.getNodes(), 0, nodeArray.getLen() - 1);

        permute(moveNodeArray, 0, sourceNode);

        System.out.print("FINAL RESULT: ");
        for(int ii = 0; ii < minNode.getLen() - 1; ii++) {
            System.out.print("(" + minNode.getNodes()[ii].getX() + ", " +  minNode.getNodes()[ii].getY() + "), ");
        }
        System.out.println("(" + minNode.getNodes()[minNode.getLen() - 1].getX() + ", " + minNode.getNodes()[minNode.getLen() - 1].getY() + ")");
        System.out.println("COST = " + minValue);

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime/1000000000.0);
    }
}
