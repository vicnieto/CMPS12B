class QueueTest {
	public static void main(String[] args) {
		Queue listx = new Queue();
		Queue listy = new Queue();
		listx.enqueue("first");
		listx.enqueue("why");
		listx.enqueue("you");
		listx.enqueue("so");
		listx.enqueue("itchy");
		//System.out.println(listx.peek());
		System.out.println(listx.length());
		listx.dequeue();
		// listx.dequeue();
		System.out.println(listx.toString());
		listy = listx.makeCopy();
		System.out.println(listy);
	}
}
