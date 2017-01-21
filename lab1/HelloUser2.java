//victor; vnieto; 12M; HelloUser2.java is a program that asks for the users 
//name and then says Hello your_name. Makefile makes an executable file for
//this program.

import java.util.Scanner;
class HelloUser2{
	public static void main(String[] args){
		System.out.print("What's your name?: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.next();
		System.out.println("Hello " + name);
	}
}
