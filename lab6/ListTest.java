class ListTest {
	public static void main(String[] args) {
		List<String> foods = new List<String>();
		List<String> foods2 = new List<String>();
		foods.add(1, "pizza");
		foods.add(2, "Ham");
		foods.add(3, "Pasta");
		System.out.println(foods);
		foods.remove(2);
		System.out.println(foods.size());
		System.out.println(foods.isEmpty());
		foods.add(2, "Macaroni");
		foods.add(5, "Chicken");
		System.out.println(foods);
		System.out.println(foods.equals(foods2));
		
	}
}
