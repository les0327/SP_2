package lab2;

import static lab2.Graph.Node;
import static lab2.Graph.Signal.*;

public class Main {

    public static void main(String[] args) {
        showPart1();
        System.out.println('\n');
        showPart2();
    }

    private static void showPart1() {
        LexemeTree tree = new LexemeTree();

        LexemeNode nodeN = new LexemeNode(null, "n", null);
        LexemeNode node1 = new LexemeNode(null, "1", null);
        LexemeNode node2 = new LexemeNode(nodeN, "-", node1);
        LexemeNode node3 = new LexemeNode(nodeN, ":=", node2);
        LexemeNode node0 = new LexemeNode(null, "0", null);
        LexemeNode node4 = new LexemeNode(nodeN, "=", node0);
        LexemeNode nodeB = new LexemeNode(null, "b", null);
        LexemeNode node9 = new LexemeNode(null, ";", null);
        LexemeNode node10 = new LexemeNode(null, ")", node9);
        LexemeNode node5 = new LexemeNode(nodeN, "]", node10);
        LexemeNode nodeA = new LexemeNode(null, "a", null);
        LexemeNode node6 = new LexemeNode(nodeA, "[", node5);
        LexemeNode node7 = new LexemeNode(nodeB, "!=", node6);
        LexemeNode node8 = new LexemeNode(node4, "or", node7);
        LexemeNode node11 = new LexemeNode(node3, "until", null);
        LexemeNode node12 = new LexemeNode(node11, "(", node8);
        LexemeNode node13 = new LexemeNode(null, "repeat", node12);

        tree.setHead(node13);

        tree.printLexemeTree();
    }

    private static void showPart2() {
        Graph.Signal[] signals = new Graph.Signal[] {ltr, cfr, cfr, dlm, cfr, dlm, ltr, ltr, ltr, dlm};

        Node node2 = new Graph.Node("2");
        Node node3 = new Graph.Node("3");
        Node node4 = new Graph.Node("4");
        Node node5 = new Graph.Node("5");
        Node node6 = new Graph.Node("6");
        Node node7 = new Graph.Node("7");
        Node node8 = new Graph.Node("8");
        Node node9 = new Graph.Node("9");

        node2.set(node3, null, null);
        node3.set(node4, node3, cfr);
        node4.set(node5, null, null);
        node5.set(node6, node7, dlm);
        node6.set(node7, null, null);
        node7.set(node8, null, null);
        node8.set(node9, null, null);
        node9.set(null, node4, ltr);

        System.out.println(Graph.outGraph(node2, signals));
    }

}
