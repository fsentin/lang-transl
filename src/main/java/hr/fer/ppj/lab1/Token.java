package hr.fer.ppj.lab1;

/**
 * A sequence of characters that can be treated as a unit in the grammar of the programming languages. 
 * @author Fani
 *
 */
public class Token {
	/**
	 * The type of the token.
	 */
	private TokenType type;
	
	/**
	 * The value of a token.
	 */
	private Object value;
	
	/**
	 * Index of the line in which the token is located in the program.
	 */
	private int line;
	
	/**
	 * Constructs a new token with specified type and value.
	 * @param type the TokenType enumeration constant of the new token
	 * @param value the value object of the new token
	 * @param the index of the line in which the token is located in the program
	 * @throws NullPointerException if the token type is null
	 */
	public Token(TokenType type, Object value, int line) {
		if(type == null) 
			throw new NullPointerException("Token type can not be null."); 
		this.type = type;
		this.value = value;
		this.line = line;
	}
	
	/**
	 * Retrieves the value of this token.
	 * @return the value of this token.
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Retrieves the type of this token.
	 * @return the type of this token.
	 */
	public TokenType getType() {
		return this.type;
	}
	
	/**
	 * Creates a string representation of this token.
	 * @return String representing this token.
	 */
	@Override
	public String toString() {
		return  type + " " + line + " " + value;
	}
}
