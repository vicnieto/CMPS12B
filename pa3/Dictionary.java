public class Dictionary implements DictionaryInterface {
	private class Node {
		String key;
		String value;
		Node next;

		Node(String k, String val){
			key = k;
			value = val;
			next = null;
		}
	}

	private Node head;
	private int numPairs;

	private Node findKey(String k){
		Node N = head;
		while(N != null){
			if(N.key == k) return N;
			N = N.next;
		}
		return null;
	}

	private Node findPrevNode(String k){
		Node P = head;
		Node C = head.next;
		while(C != null){
			if(C.key == k) return P;
			C = C.next;
			P = P.next;
		}
		return null;
	}

	public Dictionary(){
		head = null;
		numPairs = 0;
	}

	public boolean isEmpty(){
		return(numPairs == 0);
	}

	public int size(){
		return(numPairs);
	}

	public void insert(String key, String value) throws DuplicateKeyException{
		if(lookup(key) != null){
			throw new DuplicateKeyException("Dictionary Error: insert() called with existing key");
		}
		Node N = new Node(key, value);
		N.next = head;
		head = N;
		numPairs++;
	}


	public String lookup(String k){
		Node A = findKey(k);
		if(A == null) return null;
		return A.value;
	}

	public void delete(String key) throws KeyNotFoundException{
		if(lookup(key) == null){
			throw new KeyNotFoundException("Dictionary Error: delete() called with non-existing key");
		}
		Node A = findKey(key);
		Node Pre = head;
		if(Pre == A){
			head = head.next;
			A = null;
			numPairs--;
		}
		else{
			for( ; Pre.next != A; Pre = Pre.next){};
			Pre.next = A.next;
			A.next = null;
			numPairs--;
		}
	}


	public void makeEmpty(){
		head = null;
		numPairs = 0;
	}

	public String toString(){
		Node N = head;
		StringBuffer sb = new StringBuffer();
		sb = reversePairs(N, sb);
		return new String(sb);
	}

	public StringBuffer reversePairs(Node N, StringBuffer sb){
		if(N != null){
			reversePairs(N.next, sb);
			sb.append(N.key).append(" ").append(N.value).append("\n");
		}
		return sb;
	}	

}
