package hr.fer.ppj.lab1;

/**
 * Class used for lexical analysis of the programming language PJ.
 * Tokenizes the input.
 * @author Fani
 *
 */
public class Lekser {
	/**
	 * Input text which is analysed.
	 */
	private char[] input; 
	
	/**
	 * Current token.
	 */
	private Token token; 
	
	/**
	 * Index of the first yet unprocessed character.
	 */
	private int index;
	
	/**
	 * Index of this line in the whole program.
	 */
	private int line;
	
	/**
	 * Creates a new lexer from given input string and line index.
	 * @param input string of text analysed by the lexer
	 * @param line index of the line in the program
	 * @throws NullPointerException if the input is null
	 */
	public Lekser(String input, int line) {
		if(input == null)
			throw new NullPointerException();
		
		this.input = input.toCharArray();
		this.index  = 0;
		this.line = line;
	}
	
	/**
	 * Generates and returns the next token
	 * @return the next token.
	 */
	public Token nextToken() {
		extractNextToken();
		return token;
	}
	
	/**
	 * Returns the last generated token. Can be called multiple times, no new tokens will be generated.
	 * @return the last generated token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Extracts the next token from the input.
	 */
	private void extractNextToken(){
		// there has already been an EOF
		if(token != null && token.getType() == TokenType.EOF)
			throw new RuntimeException("No tokens available.");
		
		skipBlank();
		
		// no more tokens, EOF
		if(index >= input.length) {
			token = new Token(TokenType.EOF, null, line);
			return;
		}
		
		//comments
		if(isSlash(input[index]) && index + 1 < input.length 
					&& isSlash(input[index + 1])) {
			int start = index;
			index++;
			while(index < input.length) {
				index++;
			}
			
			int end = index;
			String newComment = new String(input, start, end - start);
			token = new Token(TokenType.COMMENT, newComment, line);
			return;
		}
		
		//operators
		if(input[index] == '=') {
			token = new Token(TokenType.OP_PRIDRUZI, "=", line);
			index++;
			return;
		} else if(input[index] == '+') {
			token = new Token(TokenType.OP_PLUS, "+", line);
			index++;
			return;
		} else if(input[index] == '-') {
			token = new Token(TokenType.OP_MINUS, "-", line);
			index++;
			return;
		} else if(input[index] == '*') {
			token = new Token(TokenType.OP_PUTA, "*", line);
			index++;
			return;
		} else if(input[index] == '/') {
			token = new Token(TokenType.OP_DIJELI, "/", line);
			index++;
			return;
		} else if(input[index] == '(') {
			token = new Token(TokenType.L_ZAGRADA, "(", line);
			index++;
			return;
		} else if(input[index] == ')') {
			token = new Token(TokenType.D_ZAGRADA, ")", line);
			index++;
			return;
		}
		
		//keywords
		if(index + 1 < input.length) {
			if(isOD(index) && (input.length == 2 ||
					(index + 2 < input.length && isBlank(index+2) || index - 1 > 0 && isBlank(index-1)))) {
				index = index + 2;
				token = new Token(TokenType.KR_OD, "od", line);
				return;
			} else if(isDO(index) && (input.length == 2 ||
					(index + 2 < input.length && isBlank(index+2) || index - 1 > 0 && isBlank(index-1)))) {
				index = index + 2;
				token = new Token(TokenType.KR_DO, "do", line);
				return;
			} else if(isZA(index)&& (input.length == 2 ||
					(index + 2 < input.length && isBlank(index+2) || index - 1 > 0 && isBlank(index-1)))) {
				index = index + 2;
				token = new Token(TokenType.KR_ZA, "za", line);
				return;
			} else if(isAZ(index)&& (input.length == 2 ||
					(index + 2 < input.length && isBlank(index+2) || index - 1 > 0 && isBlank(index-1)))) {
				index = index + 2;
				token = new Token(TokenType.KR_AZ, "az", line);
				return;
			}
		}
		
		//identificator
		if(Character.isLetter(input[index])){
			int start = index;
			index++;
			while(index < input.length && Character.isLetterOrDigit(input[index])) {
				index++;
			}
			int end = index;
			String newId = new String(input, start, end - start);
			token = new Token(TokenType.IDN, newId, line);
			return;
		}
		
		//numbers
		if(Character.isDigit(input[index])) {
			int start = index;
			index++;
			while(index < input.length && Character.isDigit(input[index])) {
				index++;
			}
			int end = index;
			String newNumber = new String(input, start, end - start);
			token = new Token(TokenType.BROJ, newNumber, line);
			return;
		}
		
	}
	
	/**
	 * Skips all blank(whitespace) characters.
	 */
	private void skipBlank() {
		while(index < input.length) {
			char currChar = input[index];
			if(currChar == '\r' || currChar == '\n' || currChar == '\t'|| currChar == ' ') {
				index++;
				continue;
			}
			break;
		}
	}
	
	private boolean isBlank(int index) {
		return input[index] == ' ' || input[index] == '\r' || input[index] == '\n' || input[index] == '\t';
	}
	private boolean isAZ(int index) {
		return input[index] == 'a' && input[index + 1] == 'z';
	}
	
	private boolean isZA(int index) {
		return input[index] == 'z' && input[index + 1] == 'a';
	}
	
	private boolean isDO(int index) {
		return input[index] == 'd' && input[index + 1] == 'o';
	}
	
	private boolean isOD(int index) {
		return input[index] == 'o' && input[index + 1] == 'd';
	}
	
	private boolean isSlash(char c) {
		return c == '/';
	}

}
