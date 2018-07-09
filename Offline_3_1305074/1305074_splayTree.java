import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ahmed on 9/06/2018 at 11:42 AM.
 */
public class splayTree {
    static long startTime = System.nanoTime();
    static double insertTime = 0;
    static double searchTime = 0;
    static double deleteTime = 0;
    static int insertCount = 0;
    static int deleteCount = 0;
    static int searchCount = 0;

    private static boolean notFound = false;
    private static Node<Integer> root = null;
    private static int itemCount = 0;
    private static ArrayList<Node<Integer>> splays = new ArrayList<>();

    private static Node<Integer> searchGreatGrandParent(Node<Integer> node)
    {
        int num = node.data;

        Node<Integer> temp = root;

        while(temp.data != null){
            if(temp.data.equals(num)){
                return null;
            }
            if(temp.right != null && temp.right.data.equals(num)){
                return temp;
            }
            if(temp.left != null && temp.left.data.equals(num)){
                return temp;
            }
            if(temp.data.compareTo(num) < 0){
                temp = temp.right;
            }
            else{
                if(temp.data.compareTo(num) > 0){
                    temp = temp.left;
                }
            }
        }
        return null;
    }

    private static void searchRotators(Node<Integer> node)
    {
        int num = node.data;

        Node<Integer> temp = root;

        while(temp != null){
            if(temp.data.equals(num)){
                splays.add(temp);
                break;
            }

            if(temp.right != null && temp.right.data.compareTo(num) == 0){
                splays.add(temp);
                splays.add(temp.right);
                break;
            }
            else if(temp.left != null && temp.left.data.compareTo(num) == 0){
                splays.add(temp);
                splays.add(temp.left);
                break;
            }
            else if(temp.right != null && temp.right.right != null && temp.right.right.data.compareTo(num) == 0){
                splays.add(temp);
                splays.add(temp.right);
                splays.add(temp.right.right);
                break;
            }
            else if(temp.right != null && temp.right.left != null && temp.right.left.data.compareTo(num) == 0){
                splays.add(temp);
                splays.add(temp.right);
                splays.add(temp.right.left);
                break;
            }

            else if(temp.left != null && temp.left.left != null && temp.left.left.data.compareTo(num) == 0){
                splays.add(temp);
                splays.add(temp.left);
                splays.add(temp.left.left);
                break;
            }
            else if(temp.left != null && temp.left.right != null && temp.left.right.data.compareTo(num) == 0){
                splays.add(temp);
                splays.add(temp.left);
                splays.add(temp.left.right);
                break;
            }
            else if(temp.data.compareTo(num) < 0){
                temp = temp.right;
            }
            else{
                if(temp.data.compareTo(num) > 0){
                    temp = temp.left;
                }
            }
        }
    }

    private static void singleRotation(Node<Integer> node1, Node<Integer> node2){
        if(node1.right != null && node1.right.equals(node2)){
            node1.right = node2.left;
            node2.left = node1;
        }
        else if(node1.left != null && node1.left.equals(node2)){
            node1.left = node2.right;
            node2.right = node1;
        }
    }

    private static void doubleRotation()
    {
        Node<Integer> greatGrandParent = searchGreatGrandParent(splays.get(splays.size() - 3));
        if((splays.get(splays.size() - 3).right != null && splays.get(splays.size() - 2).right != null && splays.get(splays.size() - 3).right.equals(splays.get(splays.size() - 2)) && splays.get(splays.size() - 2).right.equals(splays.get(splays.size() - 1))) || (splays.get(splays.size() - 3).left != null && splays.get(splays.size() - 2).left!= null && splays.get(splays.size() - 3).left.equals(splays.get(splays.size() - 2)) && splays.get(splays.size() - 2).left.equals(splays.get(splays.size() - 1)))){ //right zig-zig and maybe left zig-zig as well
            singleRotation(splays.get(splays.size() - 3), splays.get(splays.size() - 2));

            if(splays.get(splays.size() - 3).equals(root)) {
                root = splays.get(splays.size() - 2);
            }
            singleRotation(splays.get(splays.size() - 2), splays.get(splays.size() - 1));
            if(splays.get(splays.size() - 2).equals(root)) {
                root = splays.get(splays.size() - 1);
            }
        }

        else if(splays.get(splays.size() - 3).right != null && splays.get(splays.size() - 2).left != null && splays.get(splays.size() - 3).right.equals(splays.get(splays.size() - 2)) && splays.get(splays.size() - 2).left.equals(splays.get(splays.size() - 1))){ //right-left zig-zag
            singleRotation(splays.get(splays.size() - 2), splays.get(splays.size() - 1));
            splays.get(splays.size() - 3).right = splays.get(splays.size() - 1);
            singleRotation(splays.get(splays.size() - 3), splays.get(splays.size() - 1));
            if(splays.get(splays.size() - 3).equals(root)){
                root = splays.get(splays.size() - 1);
            }
        }

        if(splays.get(splays.size() - 3).left != null && splays.get(splays.size() - 2).right != null && splays.get(splays.size() - 3).left.equals(splays.get(splays.size() - 2)) && splays.get(splays.size() - 2).right.equals(splays.get(splays.size() - 1))){ //left-right zig-zag
            singleRotation(splays.get(splays.size() - 2), splays.get(splays.size() - 1));
            splays.get(splays.size() - 3).left = splays.get(splays.size() - 1);
            singleRotation(splays.get(splays.size() - 3), splays.get(splays.size() - 1));
            if(splays.get(splays.size() - 3).equals(root)){
                root = splays.get(splays.size() - 1);
            }
        }
        if(greatGrandParent != null) {
            if (greatGrandParent.right != null && greatGrandParent.right.equals(splays.get(splays.size() - 3))) {
                greatGrandParent.right = splays.get(splays.size() - 1);
            } else if (greatGrandParent.left != null && greatGrandParent.left.equals(splays.get(splays.size() - 3))) {
                greatGrandParent.left = splays.get(splays.size() - 1);
            }
        }
    }

    private static void splay(Node<Integer> node, boolean delete)
    {
        int tempSize = splays.size();
        searchRotators(node);
        Node<Integer> rightHolder = null;
        if(delete && splays.get(tempSize).equals(root)){
            rightHolder = splays.get(tempSize).right;
            splays.remove(splays.get(tempSize));
        }

        if(splays.size() == tempSize){
            System.out.println("***Item Not Found***");
            notFound = true;
        }

        else if(splays.size() == tempSize + 3) {
            doubleRotation();
            Node<Integer> tempRoot = root;
            if (splays.get(splays.size() - 1).data.compareTo(tempRoot.data) != 0) {
                Node<Integer> n = splays.get(splays.size() - 1);
                splays.clear();
                //System.out.println("after double rotation");
                //BTreePrinter.printNode(root);
                splay(n, delete);

            }

        }

        else if(delete && splays.size() == tempSize + 1){
            splays.get(splays.size() - 1).right = rightHolder;
            root = splays.get(splays.size() - 1);
        }
        else if(splays.size() == tempSize + 2){
            singleRotation(splays.get(splays.size() - 2), splays.get(splays.size() - 1));
            Node<Integer> tempRoot = root;
            if (splays.get(splays.size() - 1).data.compareTo(tempRoot.data) != 0) {
                if(delete){
                    splays.get(splays.size() - 1).right = rightHolder;
                }
                root = splays.get(splays.size() - 1);
            }
            //System.out.println("after single rotation");
            //BTreePrinter.printNode(root);
        }
    }

    private static void split(int num)
    {
        Node<Integer> temp = root;
        Node<Integer> found = null;
        while(temp != null){
            if(temp.data.equals(num)){
                break;
            }
            if(temp.data.compareTo(num) < 0){
                if(temp.right == null) found = temp;
                temp = temp.right;
            }
            else{
                if(temp.data.compareTo(num) > 0){
                    if(temp.left == null) {
                        found = temp;
                    }
                    temp = temp.left;
                }
            }
        }

        if(temp != null){
            splay(temp, false);
        }
        else{
            splay(found, false);
        }
    }

    private static void insert(int num)
    {
        itemCount++;
        if(itemCount == 1) {
            root = new Node<>(num);
        }
        else{
            split(num);
            Node<Integer> tempRoot = root;
            Node<Integer> newNode = new Node<>(num);

            if(newNode.data.compareTo(tempRoot.data) < 0) {
                newNode.right = tempRoot;
                newNode.left = tempRoot.left;
                tempRoot.left = null;
            }
            else {
                newNode.left = tempRoot;
                newNode.right = tempRoot.right;
                tempRoot.right = null;
            }

            root = newNode;

        }

        splays.clear();
        //BTreePrinter.printNode(root);
    }

    private static Node<Integer> findLeftBest(Node<Integer> node){
        if(node == null){
            return null;
        }
        Node<Integer> res = node;
        Node<Integer> lRes = findLeftBest(node.left);
        Node<Integer> rRes = findLeftBest(node.right);

        if(lRes != null && lRes.data.compareTo(res.data) > 0) res = lRes;
        if(rRes != null && rRes.data.compareTo(res.data) > 0) res = rRes;

        return res;
    }

    private static void search(int num)
    {
        splay(new Node<>(num), false);
        if(notFound) notFound = false;
        //BTreePrinter.printNode(root);
    }

    private static void delete(int num)
    {
        if(itemCount == 0){
            System.out.println("Empty Tree");
        }
        else {
            splay(new Node<>(num), false);
            if(!notFound) {
                Node<Integer> leftBest = findLeftBest(root.left);
                if(leftBest != null) {
                    splay(leftBest, true);
                }
                else root = root.right;
                itemCount--;
                splays.clear();
            }
            else {
                notFound = false;
            }
            //BTreePrinter.printNode(root);
        }

    }

    private static void randomFunction(int N) {
        ArrayList<Integer> insertedList = new ArrayList<>();
        Random random = new Random();
//        long startTime = System.nanoTime();
//        double insertTime = 0;
//        double searchTime = 0;
//        double deleteTime = 0;
//        int insertCount = 0;
//        int deleteCount = 0;
//        int searchCount = 0;
        for (int i = 0; i < N; i++) {
            int s = random.nextInt(3) + 1;
            //System.out.println(s);
            if (s == 1) {
                insertCount++;
                int p = random.nextInt();
                insertedList.add(p);
                long insertStartTime = System.nanoTime();
                insert(p);
                insertTime += (System.nanoTime() - insertStartTime) / 1e+9;
                //System.out.println("Item Number-" + itemCount + ", Added Item-" + p + ", Running Time-" + (System.nanoTime() - insertStartTime) / 1e+9);
            } else if (s == 2) {
                searchCount++;
                long searchStartTime = System.nanoTime();
                if (Double.compare((double) i / N * 100.0, 50.0) < 0) {
                    search(random.nextInt());
                } else {
                    int p = insertedList.get(random.nextInt(insertedList.size()));

                    search(p);
                    //System.out.println("Search Item-" + p + ", Running Time-" + (System.nanoTime() - searchStartTime) / 1e+9);
                }
                searchTime += (System.nanoTime() - searchStartTime) / 1e+9;
            } else if (s == 3) {
                deleteCount++;
                long deleteStartTime = System.nanoTime();
                if (Double.compare((double) i / N * 100.0, 50.0) < 0) {
                    delete(random.nextInt());
                } else {
                    int p = insertedList.get(random.nextInt(insertedList.size()));

                    delete(p);
                    //System.out.println("Item Number-" + itemCount + ", Delete Item-" + p + ", Running Time-" + (System.nanoTime() - deleteStartTime) / 1e+9);
                }
                deleteTime += (System.nanoTime() - deleteStartTime) / 1e+9;
            }
        }
        System.out.println(insertTime + " " + searchTime + " " + deleteTime);
        System.out.println("operations " + insertCount + " " + searchCount + " " + deleteCount);
        System.out.println("averages " + insertTime/insertCount + " " + searchTime/searchCount + " " + deleteTime/deleteCount);
    }

    private static void fileRead(){
        String line;
        try{
            FileReader fileReader = new FileReader("src/1305074_testCase_2.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(!(line = bufferedReader.readLine()).equals("##")){
                String command = line.split(" ")[0];
                switch (command.toLowerCase()) {
                    case "insert":
                        insert(Integer.parseInt(line.split(" ")[1]));
                        break;
                    case "search":
                        search(Integer.parseInt(line.split(" ")[1]));
                        break;
                    case "delete":
                        delete(Integer.parseInt(line.split(" ")[1]));
                        break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        //Scanner scanner = new Scanner(System.in);
        /*for(int i = 0; i < 7; i++) {
            insert(scanner.nextInt());
        }
        System.out.println("new splay");
        splay(new Node<>(652), false);
        BTreePrinter.printNode(root);*/

        /*int N = 1004;

        randomFunction(N);*/
        fileRead();

        //System.out.println(itemCount);
        System.out.println((System.nanoTime() - startTime) * 1e-9);
    }
}
/*
1
3
45
63
5
23
98
88
123
50
2
85
31
455
65

5626
56
853
85
456
78
4458
652
760
450
*/