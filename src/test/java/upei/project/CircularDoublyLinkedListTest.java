package upei.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircularDoublyLinkedListTest {
    private final Land newYork = new Land("New York", 100, 10, (byte) 1);
    private final Land toronto = new Land("Toronto", 100, 10, (byte) 1);
    private final Land vancouver = new Land("Vancouver", 100, 10, (byte) 1);
    private final Land montreal = new Land("Montreal", 100, 10, (byte) 1);

    /**
     * Test the add method of the CircularDoublyLinkedList class
     */
    @Test
    public void addAndGetTileTest_emptyList_NewYorkAndMontreal(){
        CircularDoublyLinkedList<Land> list = new CircularDoublyLinkedList<Land>();
        list.add(newYork);
        list.add(toronto);
        list.add(vancouver);
        list.add(montreal);

        assertEquals(list.get(0), newYork, "Test addAndGetTileTest: Expected: New York, Received: "+list.get(0));
        assertEquals(list.get(3), montreal, "Test addAndGetTileTest: Expected: Montreal, Received: "+list.get(3));
    }

    /**
     * Test the getHead method
     */
    @Test
    public void getHeadTest_emptyList_NewYork(){
        CircularDoublyLinkedList<Land> list = new CircularDoublyLinkedList<Land>();
        list.add(newYork);
        list.add(toronto);
        list.add(vancouver);
        list.add(montreal);

        assertEquals(list.getHead().getData(), newYork, "Test getHeadTest: Expected: New York, Received: "+list.getHead().getData());
    }

    @Test
    public void removeTest_emptyList_TorontoRemoved(){
        CircularDoublyLinkedList<Land> list = new CircularDoublyLinkedList<Land>();
        list.add(newYork);
        list.add(toronto);
        list.add(vancouver);
        list.add(montreal);

        list.remove(toronto);
        assertEquals(list.get(1), vancouver, "Test removeTest: Expected: Vancouver, Received: "+list.get(1));
        assertEquals(list.get(0), newYork, "Test removeTest: Expected: New York, Received: "+list.get(0));
    }

    /**
     * Test the circular property of the list
     */
    @Test
    public void pointerLoopTest_emptyList_vancouver(){
        CircularDoublyLinkedList<Land> list = new CircularDoublyLinkedList<Land>();
        list.add(newYork);
        list.add(toronto);
        list.add(vancouver);
        list.add(montreal);
        Node<Land> pointer = list.getHead();
        for (int i = 0; i < 10; i++) {
            pointer = pointer.getNext();
        }

        assertEquals(pointer.getData(), vancouver, "Test circularTest: Expected: Vancouver, Received: "+pointer.getData().getName());
    }

    /**
     * Test the getNext method of the Node class
     */
    @Test
    public void pointerGetNextGetDataTest(){
        CircularDoublyLinkedList<Land> list = new CircularDoublyLinkedList<Land>();
        list.add(newYork);
        list.add(toronto);
        list.add(vancouver);
        Node<Land> pointer = list.getHead();
        assertEquals(pointer.getNext().getData(), toronto, "Test circularTest: Expected: Toronto, Received: "+ pointer.getNext().getData().getName());
        pointer = pointer.getNext().getNext();
        assertEquals(pointer.getNext().getData(), newYork, "Test circularTest: Expected: New York, Received: "+ pointer.getNext().getData().getName());
    }
}
