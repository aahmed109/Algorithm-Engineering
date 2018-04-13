import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Ahmed on 14/04/2018 at 3:26 AM.
 */
public class twoApprox {
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
        nodes sourceNode = new nodes(nodeArray.getNodes()[sourceNum].getX(), nodeArray.getNodes()[sourceNum].getY());

    }
}
