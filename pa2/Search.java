import java.io.*;
import java.util.*;
class Search {
	public static void main(String[] args) throws IOException{
		if(args.length < 2){
			System.out.println("Usage: Search <input file> <target word(s)>");
			System.exit(1);
		}
		Scanner in = new Scanner(new File(args[0]));
		in.useDelimiter("\\Z"); 
		String s = in.next();  
		in.close();
		String[] lines = s.split("\n");  
		int lineCount = lines.length;  

		ArrayList<String> lineCopy = arrayCopy(lines);

		mergeSort(lines, 0, lineCount - 1);

		for(int j = 1; j < args.length; j++){
			String result = searchTarget(lines, 0, lineCount - 1, args[j]);
			if(result == "found"){ 
				ArrayList<Integer> targetLine = getIndice(lineCopy, args[j]);
				System.out.println(args[j] + " found in line(s): ");
				for(Integer num : targetLine) System.out.print(num + " ");
				System.out.println();
			}
			else System.out.println(args[j] + " not found in file " + args[0]);
		}
	}


	public static ArrayList<Integer> getIndice(ArrayList<String> lineCopy, String word){
		ArrayList<Integer> sentenceNumber = new ArrayList<Integer>();
		while(lineCopy.contains(word)){
			int index = lineCopy.indexOf(word) + 1;
			sentenceNumber.add(index);
			lineCopy.remove(word);
			lineCopy.add(index - 1, " ");
		}
		return sentenceNumber;

	}




	public static void mergeSort(String[] A, int p, int r){
		int q;
		if(p < r) {
			q = (p+r)/2;

			mergeSort(A, p, q);
			mergeSort(A, q+1, r);
			merge(A, p, q, r);
		}
	}

	static ArrayList<String> arrayCopy(String[] lines){
		ArrayList<String> lineCopy = new ArrayList<String>();
		for(String word : lines) lineCopy.add(word);
		return lineCopy;
	}


	public static void merge(String[] A, int p, int q, int r){
		int n1 = q-p+1;
		int n2 = r-q;
		String[] L = new String[n1];
		String[] R = new String[n2];
		int i, j, k;

		for(i=0; i<n1; i++){
			L[i] = A[p+i];
		}
		for(j=0; j<n2; j++){ 
			R[j] = A[q+j+1];
		}

		i = 0; j = 0;
		for(k=p; k<=r; k++){
			if( i<n1 && j<n2 ){
				if( L[i].compareTo(R[j]) < 0){
					A[k] = L[i];
					i++;
				}else{
					A[k] = R[j];
					j++;
				}
			}else if( i<n1 ){
				A[k] = L[i];
				i++;
			}else{ // j<n2
				A[k] = R[j];
				j++;
			}
		}
	}

	static String searchTarget(String[] A, int p, int r,  String target){
		int q;
		if(p > r) {
			return "not";
		}else{
			q = (p+r)/2;
			if(target.compareTo(A[q]) == 0){
				return "found";
			}else if(target.compareTo(A[q]) < 0){
				return searchTarget(A, p, q-1, target);
			}else{ // target > A[q]
				return searchTarget(A, q+1, r, target);
			}
		}
	}
}
