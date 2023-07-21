package hr.fer.ppj.lab2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Execution class for syntax analysis of the programming language PJ.
 * @author Fani
 *
 */
public class SintaksniAnalizator {
	
	public static void main(String[] args) {
		/**
		 * The output of a lexical analysis. Tokens to be syntactically analyzed.
		 */
		ArrayList<String> lines = new ArrayList<>();
		
		
		//scanning input
		try(Scanner sc = new Scanner(System.in)){
			
			while(sc.hasNext()) 
				lines.add(sc.nextLine());
			
			lines.add("EOF");
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//parsing input
		Parser p = new Parser(lines);
		var tree = p.parse().getTree();
		
		//printing output
		for (String string : tree) {
			System.out.println(string);
		}

	}

}
