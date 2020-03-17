package movietitles;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
public class MovieTitles {

    class MovieNode {

        // The left and right side of this node
        MovieNode leftSide, rightSide;
        
        // The data in the node
        String title, year;

        // Constructor
        public MovieNode(String title, String year) {
            this.title = title;
            this.year = year;
            leftSide = null;
            rightSide = null;
        }

        public int compareTo(Object o) {
            return this.title.compareTo(((MovieNode)o).title);
        }
        
        @Override
        public String toString() {
            return title + " " + year;
        }

        @Override
        public boolean equals(Object o) {
            return this.title.equals(((MovieNode)o).title);
        }

    }

    // head of the tree
    private MovieNode root;

    public MovieTitles() {
        // initial tree has no node
        root = null;
    }

    public void add(String name, String year) {

        // this creates a new node with data
        MovieNode node = new MovieNode(name, year);

        // if root is null then it is an empty tree
        // so new node will be the root
        if(root == null) {
            root = node;
        } else {
            addToSide(root, node);
        }

    }

    // Recursion
    private void addToSide(MovieNode localRoot, MovieNode node) {
        
        // if node is less than the localRoot then the node will be on the left of the localRoot
        if(node.compareTo(localRoot) < 0) {
            if(localRoot.leftSide == null) {
                localRoot.leftSide = node;
            } else {
                // if localRoot already has a left side then it will recurse on that side
                addToSide(localRoot.leftSide, node);
            }
        // if node is greater than the localRoot then node will be on the right of the localRoot
        } else if(node.compareTo(localRoot) > 0) {
            if(localRoot.rightSide == null) {
                localRoot.rightSide = node;
            } else {
                // if localRoot already has a right child then recurse on that child
                addToSide(localRoot.rightSide, node);
            }
        } else {
            return;
        }
    }

    public MovieNode getRoot() {
        return root;
    }

    public LinkedList<MovieNode> subset(String start, String end) {
        LinkedList<MovieNode> list = new LinkedList<>();
        MovieNode startNode = new MovieNode(start, "");
        MovieNode endNode = new MovieNode(end, "");
        addToSubset(list, startNode, endNode, root);
        return list;
    }

    // Recursion method and linkedList 
    private LinkedList<MovieNode> addToSubset(LinkedList<MovieNode> list, MovieNode startNode, MovieNode endNode, MovieNode currentNode) {
        // If currentNode is null then do nothing
        if(currentNode == null) {
        // if currentNode is within the boundary of start and end node
        } else if(currentNode.compareTo(startNode) >= 0 && currentNode.compareTo(endNode) <= 0) {
            // add left side
            addToSubset(list, startNode, endNode, currentNode.leftSide);
            // add itself to the list
            list.add(currentNode);
            // add right side and nodes
            addToSubset(list, startNode, endNode, currentNode.rightSide);
        }
        return list;
    }

    // Recursion Method
    public void printTree(MovieNode node) {
        if(node == null) return;
        
        // print left side
        printTree(node.leftSide);
        System.out.println(node);
        // print right side
        printTree(node.rightSide);
    }

    public static void main(String[] args) throws IOException {
        MovieTitles tree = new MovieTitles();
        // Reads csv file 
        File file = new File("movies.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        String line = reader.readLine();
        while(line != null) {
            String name;
            String date;
            if(line.lastIndexOf("(") == -1) {
                name = line.substring(line.indexOf(",") + 1, line.lastIndexOf(","));
                date = "Not Available";
            } else {
                name = line.substring(line.indexOf(",") + 1, line.lastIndexOf("(") - 1).replace("\"", "");
                date = line.substring(line.lastIndexOf("("), line.lastIndexOf(")") + 1);
            }
            tree.add(name, date);
            line = reader.readLine();
        }
        reader.close();
        LinkedList<MovieNode> list = tree.subset("Toy Story", "White Squall");
        for(MovieNode e : list) {
            System.out.println(e);
        }
    }
}
