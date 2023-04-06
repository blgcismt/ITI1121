import java.util.NoSuchElementException;

public class OrderedList implements OrderedStructure {

    // Implementation of the doubly linked nodes (nested-class)

    private static class Node {

      	private Comparable value;
      	private Node previous;
      	private Node next;

      	private Node ( Comparable value, Node previous, Node next ) {
      	    this.value = value;
      	    this.previous = previous;
      	    this.next = next;
      	}
    }

    // Instance variables

    private Node head;

    // Representation of the empty list.

    public OrderedList() {
        head = new Node(null, null, null);
        head.previous = head;
        head.next = head;
    }

    // Calculates the size of the list

    public int size() {
        int size = 0;
        Node current = head.next;
        while (current != head) {
            size++;
            current = current.next;
        }
        return size;
    }

    
    public Object get( int pos ) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException( "pos: " + pos );
        }

        Node current = head.next;

        for (int i=0; i<pos; i++) {
            current = current.next;
        }

        return current.value;
    }

    // Adding an element while preserving the order

    public boolean add( Comparable o ) {
            if (o == null) {
                throw new IllegalArgumentException( "o is null" );
            }
    
            Node current = head.next;
    
            while (current != head && current.value.compareTo( o ) < 0) {
                current = current.next;
            }
    
            Node newNode = new Node(o, current.previous, current);
            current.previous.next = newNode;
            current.previous = newNode;
    
            return true;
    }

    //Removes one item from the position pos.

    public void remove( int pos ) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException( "pos: " + pos );
        }

        Node current = head.next;

        for (int i=0; i<pos; i++) {
            current = current.next;
        }

        current.previous.next = current.next;
        current.next.previous = current.previous;
    }

    // Knowing that both lists store their elements in increasing
    // order, both lists can be traversed simultaneously.

    public void merge( OrderedList other ) {
        
        if (other == null) {
            throw new IllegalArgumentException( "other is null" );
        }

        Node current = head.next;
        Node otherCurrent = other.head.next;

        while (current != head && otherCurrent != other.head) {
            if (current.value.compareTo( otherCurrent.value ) > 0) {
                Node newNode = new Node(otherCurrent.value, current.previous, current);
                current.previous.next = newNode;
                current.previous = newNode;
                otherCurrent = otherCurrent.next;
            } else {
                current = current.next;
            }
        }

        while (otherCurrent != other.head) {
            Node newNode = new Node(otherCurrent.value, head.previous, head);
            head.previous.next = newNode;
            head.previous = newNode;
            otherCurrent = otherCurrent.next;
        }
    }
}
