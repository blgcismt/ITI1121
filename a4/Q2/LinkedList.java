import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {

    private static class Node<T> {

        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> head;
    private int size;

    public LinkedList() {
        head = new Node<E>(null, null, null); // dummy node!
        head.prev = head.next = head;
        size = 0;
    }

    private class LinkedListIterator implements Iterator<E> {

        private Node<E> current = head; 

        private int indexTracker = -1; // -1 because the first call to next() will increment it to 0

        public boolean hasNext() {
            return (current.next != head);
        }
       
        public E next() {
            if (!hasNext()) { // if there is no next element
                throw new NoSuchElementException();
            }
            current = current.next; // move to the next element
            indexTracker += 1; // increment the index tracker
            return current.value; // return the value of the current element
        }

        
        public int nextIndex() {
            int returnValue = indexTracker + 1; // the next index is the current index + 1
            return  returnValue; // return the next index
        }
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    public int size() {
        return size;
    }

    public E get(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        Node<E> p = head.next;

        for (int i = 0; i < index; i++) {
            p = p.next;
        }

        return p.value;
    }

    public void addFirst(E elem) {

        if (elem == null) {
            throw new NullPointerException();
        }

        Node<E> second = head.next;

        head.next = new Node<E>(elem, head, second);
        second.prev = head.next;

        size++;
    }

    public void add(E elem) {

        if (elem == null) {
            throw new NullPointerException();
        }

        Node<E> before = head.prev, after = head;

        before.next = new Node<E>(elem, before, after);
        after.prev = before.next;

        size++;
    }

    public Iterator<E> iterator(int nextIndex) {
        if (nextIndex < 0 || nextIndex > size) { // if the index is out of bounds
            throw new IndexOutOfBoundsException(Integer.toString(nextIndex)); // throw an exception
        }
        
        LinkedListIterator returnIterator = new LinkedListIterator(); // create a new iterator

        while (returnIterator.nextIndex() < nextIndex) { // while the next index is less than the index we want
            returnIterator.next(); // move to the next element
        }

        return returnIterator; // return the iterator
    }

    public Iterator<E> iterator(Iterator<E> other) {
        if (other == null) { // if the iterator is null
            throw new NullPointerException(); // throw an exception 
        }
        
        if (((LinkedListIterator) other).nextIndex() < 0 || ((LinkedListIterator) other).nextIndex() > size) { // if the index is out of bounds
            throw new IndexOutOfBoundsException(Integer.toString(((LinkedListIterator) other).nextIndex())); // throw an exception 
        }

        LinkedListIterator returnIterator = new LinkedListIterator(); // create a new iterator

        while (returnIterator.nextIndex() < ((LinkedListIterator) other).nextIndex()) { // while the next index is less than the index we want
            returnIterator.next(); // move to the next element
        }

        return returnIterator; // return the iterator
    }
    
}
