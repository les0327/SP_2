package lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;

public class Graph {

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
