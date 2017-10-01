package lab2;

import lombok.Getter;
import lombok.Setter;

class LexemeTree {

    @Getter @Setter LexemeNode head;
     void printLexemeTree() {
        printLexeme(head);
    }

    private void printLexeme(LexemeNode node){

        if (node.getLeftNode() != null){
            printLexeme(node.getLeftNode());
        }

        System.out.print(node.getName() + " ");

        if (node.getRightNode() != null){
            printLexeme(node.getRightNode());
        }

    }
}
