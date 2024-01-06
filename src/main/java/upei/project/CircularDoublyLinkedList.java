package upei.project;

public class CircularDoublyLinkedList<T>{
    private Node<T> head;
    private int size;

    public CircularDoublyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Add an object to the end of the list
     * @param obj The object to be added
     */
    public void add(T obj) {
        Node<T> newNode = new Node<T>(obj);
        if (head == null) {
            head = newNode;
            head.setNext(head);
            head.setPrev(head);
        } else {
            Node<T> temp = head;
            while (temp.getNext() != head) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
            newNode.setPrev(temp);
            newNode.setNext(head);
            head.setPrev(newNode);
        }
        size++;
    }

    /**
     * Add multiple objects to the end of the list
     * @param objs The objects to be added
     */
    public void add(T... objs){
        for (int i = 0; i < objs.length; i++) {
            add(objs[i]);
        }
    }

    /**
     * Remove an object from the list
     * @param obj The object to be removed
     */
    public void remove(T obj) {
        Node<T> temp = head;
        if (head.getData() == obj) {
            head = head.getNext();
            head.setPrev(temp.getPrev());
            temp.getPrev().setNext(head);
        } else {
            while (temp.getNext() != head) {
                if (temp.getData() == obj) {
                    temp.getPrev().setNext(temp.getNext());
                    temp.getNext().setPrev(temp.getPrev());
                    break;
                }
                temp = temp.getNext();
            }
        }
        size--;
    }

    /**
     * Get an object from the list based on the index
     * @param index The index of the object to be retrieved
     */
    public T get(int index) {
        Node<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getData();
    }

    /**
     * Get the head of the list
     * @return The head of the list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Get the size of the list
     */
    public int getSize() {
        return size;
    }
}
