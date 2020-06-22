package insertsort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by liuxiyang
 */

public class Link {
    private Node head = null; // 头节点

    public Link() {
        this.head = new Node(0);
    }

    //  链表节点
    static class Node {
        Node next = null;// 节点的引用，指向下一个节点
        int data;// 节点的对象，即内容

        public Node(int data) {
            this.data = data;
        }
    }


    public void addNode(int d) {
        Node newNode = new Node(d);// 实例化一个节点
        if (head == null) {
            head.next = newNode;
            return;
        }
        Node tmp = head;
        while (tmp.next != null) {
            if (tmp.next.data > newNode.data) {
                newNode.next = tmp.next;
                tmp.next = newNode;
                return;
            }
            tmp = tmp.next;
        }
        tmp.next = newNode;
    }

    public void printList() {
        try {
            String pathName = "src/main/java/insertsort/results-online.txt";
            File file = new File(pathName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(pathName);
            Node tmp = head;
            while (tmp.next != null) {
                fileWriter.write(tmp.next.data + "\n");
                tmp = tmp.next;
            }
            System.out.println("Finish saving results");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
