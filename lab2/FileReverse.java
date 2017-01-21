import java.io.*;
import java.util.Scanner;
class FileReverse{
	public static void main(String[] args) throws IOException{
		int lineNumber = 0;
		if(args.length < 2){
			System.out.println("usage: <input file> <output file>");
			System.exit(1);
		}
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));

		while(in.hasNextLine()){
			lineNumber ++;
			String line = in.nextLine().trim() + " ";
			String[] token = line.split("\\s+");
			int n = token.length;
			for(int i = 0; i < n; i++){
				String reverseWord = stringReverse(token[i], token[i].length());
				out.println(reverseWord);
			}
		}

		in.close();
		out.close();
	}

	/*

	static String reverseToken(String s){
		char[] word = s.toCharArray();
		String reverseWord = "";
		for(int j = word.length - 1; i > -1; i--){
			reverseWord += char[i];
		}

		return reverseWord;
	}

        */



	static String Reverse(String s){
		if(s.length() <= 1 || s == null){
			return s;
		}
		return Reverse(s.substring(1)) + s.charAt(0);
	}

	static String stringReverse(String s, int n){
		if(n > 0){
			String word = s.substring(0, n);
			return Reverse(word);
		}
		else{
			return s.substring(0, 0);
		}

	}


}
