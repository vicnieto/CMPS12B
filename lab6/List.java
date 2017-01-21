@SuppressWarnings("overrides")
public class List<T> implements ListInterface<T>{
	private class Node{
		T item;
		Node next;
	
	Node(T t){
		item = t;
		next = null;
	}
	}


	
	private Node head;
	private int numItems;
	
	public List(){
		head = null;
		numItems = 0;
	}
	
	private Node find(int index){
		Node N = head;
		for(int i = 1; i < index; i++) N = N.next;
		return N;
	}
	
	public boolean isEmpty(){
		return(numItems == 0);
	}
	
	public int size(){
		return(numItems);
	}
	
	public T get(int index) throws ListIndexOutOfBoundsException{
		if( index<1 || index>numItems ){
			   throw new ListIndexOutOfBoundsException(
				"List Error: get() called on invalid index: " + index);
			}
			
		Node N = find(index);
		return N.item;
	}
	
	public void add(int index, T newItem) throws ListIndexOutOfBoundsException{
		if( index<1 || index>(numItems+1) ){
			   throw new ListIndexOutOfBoundsException(
				"List Error: add() called on invalid index: " + index);
			}
		if(index == 1){
			Node N = new Node(newItem);
			N.next = head;
			head = N;
		}
		else{
			Node P = find(index - 1);
			Node C = P.next;
			P.next = new Node(newItem);
			P = P.next;
			P.next = C;
		}
		numItems++;
	}
	
	public void remove(int index) throws ListIndexOutOfBoundsException{
		if( index<1 || index>numItems ){
			   throw new ListIndexOutOfBoundsException(
				"IntegerList Error: remove() called on invalid index: " + index);
		}
		if(index == 1){
			Node N = head;
			head = head.next;
			N.next = null;
		}
		else{
			Node P = find(index - 1);
			Node N = P.next;
			P.next = N.next;
			N.next = null;
		}
		numItems--;
	}
	
	public void removeAll(){
		head = null;
		numItems = 0;
	}
	
	public String toString(){
		String result = "";
		for(Node N = head; N != null; N = N.next) result += N.item.toString() + " ";
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object rhs){
		boolean equals = false;
		List<T> H = null;
		Node N = null;
		Node X = null;
		if(this.getClass() == rhs.getClass()){
			H = (List<T>)rhs;
			equals = (this.numItems == H.numItems);
			N = this.head;
			X = H.head;
			while(equals && N != null){
				equals = (N.item == X.item);
				N = N.next;
				X = X.next;
			}
		}
		return equals;
	}
	
	
}
