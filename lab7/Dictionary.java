class Dictionary implements DictionaryInterface {
	private class Node{
		String key;
		String value;
		Node left;
		Node right;
		
		Node(String k, String v){
			key = k;
			value = v;
			left = null;
			right = null;
		}
	}
	
	private Node root;
	private int numItems;
	
	private Node findKey(Node R, String k){
		if(R == null || k.compareTo(R.key) == 0) return R;
		if(k.compareTo(R.key) < 0) return findKey(R.left, k);
		else return findKey(R.right, k);
	}
	
	private Node findParent(Node N, Node R){
			Node P = null;
			if(N != R){
			P = R;
			while(P.right != N && P.left != N){
				String nKey = N.key;
				if(nKey.compareTo(P.key) < 0) P = P.left;
				else P = P.right;
			}
			}
			return P;
			
	}
	
	private Node findLeftmost(Node R){
		Node L = R;
		if (L != null){
			for( ; L.left != null; L = L.left);
			}
		return L;
		
	}
	
	private void printInOrder(Node R){
		if(R != null){
			printInOrder(R.left);
			System.out.println(R.key + " " + R.value);
			printInOrder(R.right);
		}
	}
	
	private void deleteAll(Node N){
		if(N != null){
			deleteAll(N.left);
			deleteAll(N.right);
			N = null;
		}
	}
	
	public Dictionary(){
		root = null;
		numItems = 0;
	}
	
	public boolean isEmpty(){
		return(numItems == 0);
	}
	
	public int size(){
		return(numItems);
	}
	
	public String lookup(String k){
		Node N = findKey(root, k);
		if(N != null) return N.value;
		else return null;
	}
	
	public void insert(String k, String v) throws DuplicateKeyException{
		Node N = new Node(k, v);
		if(findKey(root, k) != null){
			throw new DuplicateKeyException("Dictionary error: insert() called with existing key");
		}
		Node P = null, F = root;
		while(F != null){
			P = F;
			if(F.key.compareToIgnoreCase(k) > 0) F = F.left;
			else F = F.right; 
		}
		
		if(P == null) root = N;
		else if(P.key.compareToIgnoreCase(k) > 0) P.left = N;
		else P.right = N;
		numItems++;
	}
	
	public void delete(String k) throws KeyNotFoundException{
		Node N = findKey(root, k);
		Node P, S;
		if(N == null){
			throw new KeyNotFoundException("Dictionary Error: cannot delete non-existing key");
		}
		if(N.left == null && N.right == null){
			if(N == root) root = null;
			else{
				P = findParent(N, root);
				if(P.right == N) P.right = null;
				else P.left = null;
			}
		}
		else if(N.right == null){
			if(N == root) root = N.left;
			else{
				P = findParent(N, root);
				if(P.right == N) P.right = N.left;
				else P.left = N.left;
			}
		}
		else if(N.left == null){
			if(N == root) root = N.right;
			else{
				P = findParent(N, root);
				if(P.right == N) P.right = N.right;
				else P.left = N.right;
			}
		}
		else{
			S = findLeftmost(N.right);
			N.key = S.key;
			N.value = S.value;
			P = findParent(S, N);
			if(P.right == S) P.right = S.right;
			else P.left = S.right;
		}
		numItems--;
	}
	
	public void makeEmpty(){
		deleteAll(root);
		root = null;
		numItems = 0;
	}
	
	public String toString(){
		printInOrder(root);
		return " ";
	}
	
}

