package hr.fer.ppj.lab1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Execution class for lexical analysis of the programming language PJ.
 * @author Fani
 *
 */
public class LeksickiAnalizator {

	public static void main(String[] args) {
		
		ArrayList<String> lines = new ArrayList<>();
		Lekser tokenizator;
		
		try(Scanner sc = new Scanner(System.in)){
			
			while(sc.hasNext()) 
				lines.add(sc.nextLine());
			
			int i = 1;
			for (String string : lines) {
				tokenizator = new Lekser(string, i);
				
				while(tokenizator.nextToken().getType() != TokenType.EOF
						&& tokenizator.getToken().getType() != TokenType.COMMENT) {
					System.out.println(tokenizator.getToken());
				}
				
				i++;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
