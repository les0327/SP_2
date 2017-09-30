package lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static lab2.Graph.Signal.*;


public class Graph {

    public static void main(String[] args) {

        Signal[] signals = new Signal[] {cfr, cfr, dlm, dlm, dlm, dlm, dlm, ltr, cfr, cfr};

        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        Node node6 = new Node("6");
        Node node7 = new Node("7");
        Node node8 = new Node("8");
        Node node9 = new Node("9");

        node2.set(node3, null, null);
        node3.set(node4, node3, cfr);
        node4.set(node5, null, null);
        node5.set(node6, node7, dlm);
        node6.set(node7, null, null);
        node7.set(node8, null, null);
        node8.set(node9, null, null);
        node9.set(null, node4, ltr);

        System.out.println(outGraph(node2, signals));
    }

    public static String outGraph(Node node, Signal[] signals) {

        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(signals)).append('\n');
        for (Signal signal : signals) {
            if (node.next == null) {
                if (node.signal != signal) {
                    return sb.toString();
                }
            }

            sb.append(signal).append(": ").append(node.name);
            if (node.signal != null) {
                if (node.signal == signal)
                    node = node.nextSignal;
                else
                    node = node.next;

            } else  {
                node = node.next;
            }
            sb.append(" -> ").append(node.name).append('\n');
        }

        return sb.toString();
    }


    @AllArgsConstructor
    public static class Node {
        @Getter @Setter private String name;
        @Getter @Setter private Node next;
        @Getter @Setter private Node nextSignal;
        @Getter @Setter private Signal signal;

        public Node(String name) {
            this.name = name;
        }

        public void set(Node next, Node nextSignal, Signal signal) {
            setNext(next);
            setNextSignal(nextSignal);
            setSignal(signal);
        }
    }

    public enum Signal {
        dlm, cfr, ltr;
    }
}
