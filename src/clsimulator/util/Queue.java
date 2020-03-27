package clsimulator.util;


public class Queue<T> {
	private T[] data;
	private int front;
	private int rear;
	private int numItems;
	private int maxCount;
	
	public Queue() {
		data = (T[]) new Object[100];
		front = -1;
		rear = -1;
		maxCount = 100;
		numItems = 0;
	}
	
	public Queue(int maxCount) {
		data = (T[]) new Object[maxCount];
		front = -1;
		rear = -1;
		this.maxCount = maxCount;
		numItems = 0;
	}
	
	public boolean empty() {
		return numItems == 0;
	}
	
	public void insert(T t) {
		if(numItems == maxCount) {
			System.out.println("queue is full");
		}
		else {
			rear = (rear + 1) % maxCount;
			data[rear] = t;
			numItems++;
			if(numItems == 1) {
				front =rear;
			}
		}
	}
	
	public T remove() {
		if(empty()) {
			System.out.println("queue is empty!!");	
			return null;
		}
		
		else {
			T newItem = data[front];
			numItems--;
			front = (front+1) % maxCount;
			if(numItems == 0) {
				front = rear = -1;
			}
			return newItem;
		}
	}
	
	public T peak() {
		if(empty()) {
			return null;
		}
		
		else {
			return data[front];
		}
	}
	
	public int size() {
		return numItems;
	}
	
	
}
