/**
 * @author Marcel Turcotte, Guy-Vincent Jourdan and Mehrdad Sabetzadeh, Ismet Bilgic
 *         (University of Ottawa)
 * 
 * 
 */

public class LinkedQueue<D> implements Queue<D> {

	private static class Elem<T> {
		private T value;
		private Elem<T> next;

		private Elem(T value, Elem<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	private Elem<D> front;
	private Elem<D> rear;

	public LinkedQueue() {
		front = rear = null;
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void enqueue(D newElement) {

		if (newElement == null) {
			throw new NullPointerException("no null object in my queue !");
			// could have been IllegalArgumentException but NPE seems
			// to be the norm
		}

		Elem<D> newElem;
		newElem = new Elem<D>(newElement, null);
		if (isEmpty()) {
			front = newElem;
			rear = newElem;
		} else {
			rear.next = newElem;
			rear = newElem;
		}
	}

	public D dequeue() {

		if (isEmpty()) {
			throw new IllegalStateException("Dequeue method called on an empty queue");
		}

		D returnedValue;
		returnedValue = front.value;

		if (front.next == null) {
			front = rear = null;
		} else {
			front = front.next;
		}

		return returnedValue;
	}

	public D peek() {
		if (isEmpty()) {  
			throw new IllegalStateException("Peek method called on an empty queue"); //  throw an exception if the queue is empty
		}
		return front.value; // return the value of the front element
	}

	public int size() {
		if (isEmpty()) { // if the queue is empty, return 0
			return 0;
		}
		int size = 0; // initialize the size to 0
		if (!isEmpty()) { // if the queue is not empty
			Elem<D> cursor = front; // create a cursor to traverse the queue
			size++; // increment the size
			while (cursor.next != null) { // while the cursor is not at the end of the queue
				cursor = cursor.next; // move the cursor to the next element
				size++; // increment the size
			}
		}
		return size; // return the size
	} 

	public String toString() {

		StringBuffer returnedValue = new StringBuffer("[");

		if (!isEmpty()) {
			Elem<D> cursor = front;
			returnedValue.append(cursor.value);
			while (cursor.next != null) {
				cursor = cursor.next;
				returnedValue.append(", " + cursor.value);
			}
		}

		returnedValue.append("]");
		return returnedValue.toString();

	}
}
