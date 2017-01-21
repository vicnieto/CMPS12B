public class DictionaryTest {
	public static void main(String[] args) {
		Dictionary A = new Dictionary();
		A.insert("pizza", "pepperoni");
		A.insert("pasta", "tomatoe sauce");
		A.insert("Orange Chicken", "sugar");
		A.insert("1", "21");
		A.delete("pasta");
		A.insert("trap", "life");
		A.delete("trap");
		System.out.println(A.isEmpty());
		x = A.lookup("pasta");
		System.out.print(x);
		System.out.print(A.size());
		System.out.print(A.toString());
	}
}
