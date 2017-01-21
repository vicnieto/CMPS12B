public class Queue implements QueueInterface {
	private class Node{
		Object item;
		Node next;
		
		Node(Object x){
			item = x;
			next = null;
		}
	}
	
	private Node front;
	private Node back;
	private int numItems;
	
	private Node findElem(Object x){
		Node N = front;
		while(N != null){
			if(N.item == x) return N;
			N = N.next;
		}
		return null;
	}
	
	public Queue(){
		front = null;
		back = null;
		numItems = 0;
	}
	
	public boolean isEmpty(){
		return(numItems == 0);
	}
	
	public int length(){
		return numItems;
	}
	
	public void enqueue(Object newItem){
		Node N = new Node(newItem), X;
		if(numItems == 0){
			front = N;
			back = N;
		}
		else{
			X = front;
			while(X.next != null) X = X.next;
			X.next = N;
			back = N;
		}
		numItems++;
	}
	
	public Object dequeue() throws QueueEmptyException{
		if( numItems==0 ){
			   throw new QueueEmptyException("cannot dequeue() empty queue");
			}
		Object returnValue = front.item;
		if(numItems == 1){
			front = null;
			back = null;
		}
		else{ front = front.next; }
	numItems--;
	return returnValue;
	
	}
	
	public Object peek() throws QueueEmptyException{
		if( numItems==0 ){
			   throw new QueueEmptyException("cannot peek() empty queue");
		}
		return front.item;
	}
	
	public void dequeueAll() throws QueueEmptyException{
		if( numItems==0 ){
			   throw new QueueEmptyException("cannot dequeueAll() empty queue");
		}
		front = null;
		back = null;
		numItems = 0;
	}
	
	public Queue makeCopy(){
		Queue copy = new Queue();
		Node N = front;
		while(N != null){
			copy.enqueue(N.item);
			N = N.next;
		}
		return copy; 
	}
	
	public String toString(){
		String s = "";
		Node N = front;
		for( ;N!=null; N = N.next){
			s += N.item + " ";
		}
		return s;
	}
}


