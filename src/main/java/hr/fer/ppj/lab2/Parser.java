package hr.fer.ppj.lab2;

import java.util.ArrayList;

/**
 * Class used for syntax analysis of the programming language PJ.
 * @author Fani
 *
 */
public class Parser {
	
	/**
	 * Index of the currently inspected token.
	 */
	int currentIndex;
	
	/**
	 * The list of uniform characters given by a tokenizer.
	 */
	ArrayList<String> uniform;
	
	/**
	 * 	The generative tree of this program.
	 */
	ArrayList<String> tree;
	
	/**
	 * Currently inspected input token.
	 */
	String input;
	
	int spaces = 0;
	
	/**
	 * Creates a new Parser for given list of tokens.
	 * @param inputLines given list of tokens to be parsed.
	 */
	public Parser(ArrayList<String> inputLines) {
		currentIndex = 0;
		uniform = inputLines;
		tree =  new ArrayList<String>();
	}
	
	/**
	 * Executes the parsing of the program.
	 */
	public Parser parse() {
		next();
		programStart();	
		return this;
	}
	
	/**
	 * Returns the generative tree of this program represented by a list of strings 
	 * which should be printed using end of line between each string.
	 * @return list of strings representing the generative tree.
	 */
	public ArrayList<String> getTree() {
		return tree;
	}
	
	private void programStart() {
		tree.add("<program>");
		
		if(input.contains("IDN") || input.contains("KR_ZA") || input.equals("EOF"))
			commandList();
		else {
			error("in programStart");
		}
	}
	
	private void commandList() {
		spaces++;
		tree.add(make(spaces) + "<lista_naredbi>");
		
		if(input.contains("IDN") || input.contains("KR_ZA")) {
			command();
			commandList();
		} else if(input.contains("KR_AZ") || input.equals("EOF")) {
			tree.add(make(++spaces) + "$");
			spaces--;
			//if(!input.equals("EOF")) next();
		} else {
			error("in commandList");
		}
		spaces--;
	}
	
	private void command() {
		spaces++;
		tree.add(make(spaces) + "<naredba>");
		
		if(input.contains("IDN")) {
			assignCommand();
			
		} else if(input.contains("KR_ZA")) {
			forLoop();
		} else {
			error("in command");
		}
		spaces--;
	}
	
	private void assignCommand() {
		spaces++;
		tree.add(make(spaces) + "<naredba_pridruzivanja>");
		
		spaces++;
		tree.add(make(spaces) + input);
		next();
		
		if(!input.contains("OP_PRIDRUZI")) 
			error("in assignCommand");
		
		
		tree.add(make(spaces) + input);
		next();
		spaces--;
		
		if(input.contains("IDN")  || 
		   input.contains("BROJ") || 
		   input.contains("OP_PLUS") ||
		   input.contains("OP_MINUS") ||
		   input.contains("L_ZAGRADA")) {
			E();
		} else {
			error("in assignCommand idn");
		}
		spaces--;
	}
	
	private void forLoop() {
		spaces++;
		tree.add(make(spaces) + "<za_petlja>");
		
		//KR_ZA
		if(!input.contains("KR_ZA")) 
			error("in forLoop");
		
		tree.add(make(++spaces) + input);
		next();
		
		//IDN
		if(!input.contains("IDN")) error("in forLoop idn");
		
		tree.add(make(spaces) + input);
		next();
		
		//KR_OD
		if(!input.contains("KR_OD")) error("in forLoop OD");
		
		tree.add(make(spaces) + input);
		spaces--;
		next();
		
		// <E>
		E();
		
		//KR_DO
		if(!input.contains("KR_DO")) error("in forLoop");
		
		tree.add(make(++spaces) + input);
		spaces--;
		next();
		
		// <E>
		E();
		
		// <lista_naredbi>
		commandList();
		
		if(!input.contains("KR_AZ")) {
			error("in forLoop");
		}
		tree.add(make(++spaces) + input);
		spaces--;
		next();
		spaces--;
	}
	
	private void E() {
		spaces++;
		tree.add(make(spaces) + "<E>");
		
		if(input.contains("IDN")      ||
		   input.contains("BROJ")     ||
		   input.contains("OP_PLUS")  ||
		   input.contains("OP_MINUS") ||
		   input.contains("L_ZAGRADA")) {
			T();
			listE();
		} else {
			error("in E");
		}
		spaces--;
	}
	
	private void T() {
		spaces++;
		tree.add(make(spaces) + "<T>");
		
		if(input.contains("IDN")      ||
		   input.contains("BROJ")     ||
		   input.contains("OP_PLUS")  ||
		   input.contains("OP_MINUS") ||
		   input.contains("L_ZAGRADA")) {
			P();
			listT();
		} else {
			error("in T");
		}
		spaces--;
	}
	
	private void P() {
		spaces++;
		tree.add(make(spaces) + "<P>");
		
		if(input.contains("OP_PLUS")) {
			tree.add(make(++spaces) + input);
			spaces--;
			next();
			P();
			
		} else if(input.contains("OP_MINUS")) {
			tree.add(make(++spaces) + input);
			spaces--;
			next();
			P();
			
		} else if(input.contains("L_ZAGRADA")) {
			tree.add(make(++spaces) + input);
			spaces--;
			next();
			E();
			if(!input.contains("D_ZAGRADA"))
				error("in P");
			tree.add(make(++spaces) + input);
			spaces--;
			next();
			
		} else if(input.contains("IDN") || input.contains("BROJ")) {
			spaces++;
			tree.add(make(spaces) + input);
			next();
			spaces--;
			
		} else {
			error("in P");
		} 
		spaces--;
	}
	
	private void listE() {
		spaces++;
		tree.add(make(spaces) + "<E_lista>");
		
		if(input.contains("OP_PLUS")) {
			tree.add(make(++spaces) + input);
			next();
			spaces--;
			E();
		} else if(input.contains("OP_MINUS")) {
			tree.add(make(++spaces) + input);
			next();
			spaces--;
			E();
		} else if(input.contains("IDN")       ||
				  input.contains("BROJ")	  ||
				  input.contains("KR_ZA")     ||
				  input.contains("KR_DO")     ||
				  input.contains("KR_AZ")     ||
				  input.contains("D_ZAGRADA") ||
				  input.equals("EOF")) {
			
			tree.add(make(++spaces) + "$");
			spaces--;
			//if(!input.equals("EOF")) next();
		} else {
			error("in listE");
		}
		spaces--;
	}
	
	private void listT() {
		spaces++;
		tree.add(make(spaces) + "<T_lista>");
		
		if(input.contains("OP_PUTA")) {
			tree.add(make(++spaces) + input);
			next();
			spaces--;
			T();
		} else if(input.contains("OP_DIJELI")) {
			tree.add(make(++spaces) + input);
			next();
			spaces--;
			T();
		} else if(input.contains("IDN")    ||
				input.contains("KR_ZA")    ||
				input.contains("KR_DO")    ||
				input.contains("KR_AZ")    ||
				input.contains("OP_PLUS")  ||
				input.contains("OP_MINUS") ||
				input.contains("D_ZAGRADA")||
				input.equals("EOF")
				){
			tree.add(make(++spaces) + "$");
			spaces--;

		} else {
			error("in listT");
		}
		spaces--;
	}
	
	/**
	 * Prints an error message and exits the program.
	 */
	private void error(String additionalMessage) {
		input = input.equals("EOF") ? "kraj" : input;
		
		System.out.println("err " + input);
		/*
		 * Used for debugging:
		 * 
		 * System.err.println(additionalMessage);
		 *
		 * for (String string : tree) 
		 *	System.err.println(string);
		 */
		System.exit(1);
	}
	
	/**
	 * Moves the "pointer" to the next input token.
	 */
	private void next() {
		input = uniform.get(currentIndex++);
	}
	
	/**
	 * Returns a string consisting of the specified number of spaces.
	 * @param numOfSpaces the specified number of spaces
	 * @return a string consisting of the specified number of spaces.
	 */
	private String make(int numOfSpaces) {
		String result = "";
		for(int i = 0; i < numOfSpaces; i++) 
			result += " ";
		return result;
	}
}
