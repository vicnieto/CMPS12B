import java.util.ArrayList;
import java.util.*;
class Recursion{
	public static void main(String[] args) {
		int[] A = {-1, 2, 6, 12, 9, 2, -5, -2, 8, 5, 7};
		int[] B = new int[A.length];
		int[] C = new int[A.length];
		int minIndex = minArrayIndex(A, 0, A.length-1);
		int maxIndex = maxArrayIndex(A, 0, A.length-1);

		for(int x: A) System.out.print(x+" ");
		System.out.println();

		System.out.println( "minIndex = " + minIndex );
		System.out.println( "maxIndex = " + maxIndex );
		reverseArray1(A, A.length, B);
		for(int x: B) System.out.print(x+" ");
		System.out.println();

		reverseArray2(A, A.length, C);
		for(int x: C) System.out.print(x+" ");
		System.out.println();

		reverseArray3(A, 0, A.length-1);
		for(int x: A) System.out.print(x+" ");
		System.out.println();
	}

	static void reverseArray1(int[] x, int n, int[] y){
		if(n > 0){
			y[y.length - n] = x[n - 1];
			reverseArray1(x, n-1, y);
		}
		return;
	}

	static void reverseArray2(int[] x, int n, int[] y){
		if(n > 0){
			reverseArray2(x, n-1, y);
			y[n - 1] = x[x.length - n];
		}
		return;
	}

	static void reverseArray3(int[] x, int i, int j){
		int temp;
		if(i < j){
			temp = x[i];
			x[i] = x[j];
			x[j] = temp;
			reverseArray3(x, i + 1, j - 1);
		}
		return;
	}


	static int maxArray(int[] x, int p, int r){
		if(r == p){ 
			return x[p];
		} 
		else{
			int q = (p + r) / 2;
			int left = maxArray(x, p, q);
			int right = maxArray(x, q + 1, r);
			return Math.max(left, right);
		}
	}

	static int maxArrayIndex(int[] x, int p, int r){
		int maxInt = maxArray(x, p, r);
		int maxIndex = getIndex(x, maxInt, "max");
		return maxIndex;
	}

	static int minArray(int[] x, int p, int r){
		if(r == p){ 
			return x[p];
		} 
		else{
			int q = (p + r) / 2;
			int left = minArray(x, p, q);
			int right = minArray(x, q + 1, r);
			return Math.min(left, right);
		}
	}



	static int minArrayIndex(int[] x, int p, int r){
		int minInt = minArray(x, p, r);
		int minIndex = getIndex(x, minInt, "min");
		return minIndex;
	}

	static int getIndex(int[] x, int element, String max_min){
		if(max_min == "max"){
			for(int i = 0; i < x.length; i++){
				if(x[i] == element) 
					return i;
			}
		}
		else if(max_min == "min"){
			for(int i = 0; i < x.length; i++){
				if(x[i] == element) 
					return i;
			}
		}
		return -1;
	}

	static void printArray(int[] h){
		System.out.print("[ ");
		for(int i = 0; i < h.length; i++){
			System.out.print(h[i] + " ");
		}
		System.out.print("]");
	}
}
