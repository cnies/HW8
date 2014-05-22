import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.util.Scanner;

/**An extension of the Parser class provided in class by the professor. This
version is a little bit more advanced in that it implements the capability for
the entirety of PEMDAS, rather than just addition and multilication. Uses the
OpTree class provided by the professor, as well as his Tokenizer
*/
public class MyParser{
	private String[] tokens;/*The array containing the tokens to be parsed*/
	private int nextTokenIndex;/*Current location within the token array*/

	//Empty
	public MyParser(){
		this.tokens = new String[0];
		this.nextTokenIndex=0;
	}

	//Fairly obvious, simply returns the next token and increments through the
	//list of tokens
	public String popNextToken(){
		if (nextTokenIndex<tokens.length){
			return tokens[nextTokenIndex++];
		}
		return null;
	}

	//Same as the one above, only it does not increment through the list
	public String peekNextToken(){
		if (nextTokenIndex<tokens.length)
			return tokens[nextTokenIndex];
		return null;
	}

	//Returns true if we are out of tokens to look at
	public boolean noMoreTokens(){
		return (this.peekNextToken() == null);
	}


	//Returns an entire parse tree based on an array of tokens
	public OpTree parse(String tokens[]) throws ParseException{
		this.tokens = tokens;
		this.nextTokenIndex = 0;
		OpTree toReturn = this.Expression();
		//If there is anything left in the list, uh oh something went wrong
		if (!noMoreTokens())
			throw new ParseException("Error at "+(nextTokenIndex), nextTokenIndex);
		return toReturn;
	}

	/*Processes an "Expression" which is either the sum of two "Terms", the
	difference between two Terms, or just a Term by itself. IE
	E ::= T+T | T-T | T
	*/
	public OpTree Expression() throws ParseException{
		//If this function is called, but the token list is empty, throw Exception.
		//This should never happen in a correctly formatted list
		if (noMoreTokens())
			throw new ParseException("Parse error at token "+nextTokenIndex,
			nextTokenIndex);

		//Every Expression starts with a term
		OpTree left = Term();

		//If this first Term was the only thing left, return it 
		if (noMoreTokens())
			return left;

		//If the list is not empty, and the next operation is not recognized
		//return the Term. There is nothing to be done here
		if (!"+".equals(peekNextToken()) && !"-".equals(peekNextToken()))
			return left;

		//POST-CONDITION: The next token in the list is a '+' or a '-'
		//Get the operator, get the Term on the right, then return this new tree
		String oper = popNextToken();
		OpTree right = Term();
		return new OpTree(oper, left, right);
	}


	/*Processes a "Term", which is either the product of two "Factors", the
	quotient of two "Factors" or just a Factor by itself
	T ::= F*F | F/F | F
	*/
	public OpTree Term() throws ParseException{
		//Should NEVER be empty at this point in a correctly formatted list
		if (noMoreTokens())
			throw new ParseException("Parse error at token" +nextTokenIndex
															,nextTokenIndex);

		//All Terms start with a Factor
		OpTree left = Factor();
		
		//If the Factor we just found was the only thing left, return it
		if (noMoreTokens())
			return left;

		//If the list is not empty, and the next operation is not recognized
		//return the Factor we just found.
		if (!"*".equals(peekNextToken()) && !"/".equals(peekNextToken()))
			return left;

		//POST-CONDITION: The next token in the list is either a '*' or a '/'
		//Get the operator, get the Factor on the right, return this new tree
		String oper = popNextToken();
		OpTree right = Factor();
		return new OpTree(oper, left, right);
	}


	/* Processes a "Factor", which is either an "Expression" within a 
	set of paranthesis, a Factor raised to the power of another Factor,
	or a "Number" by itself.
	F ::= N | (E) | F^F
	*/
	public OpTree Factor() throws ParseException{
		//Should NEVER be empty at this point in a correctly formatted list
		if (noMoreTokens())
			throw new ParseException("Parse error at token "+nextTokenIndex,
			nextTokenIndex);

		//Check to see if the Factor starts with a '('
		if (!"(".equals(peekNextToken())){
			//If not, it is either a Number, or a Number^Factor
			OpTree left = Number();
			//If it is just a Number, return it
			if (!"^".equals(peekNextToken()))
				return left;
			//If it is a Number^Factor, make a recursive call
			String carrot = popNextToken();
			OpTree right = Factor();
			//Return the new Operation tree
			return new OpTree(carrot, left, right);
		}

		//If Factor starts with a '(' then it is either
		//an (Expression) or an (Expression)^Factor

		//Pop out the opening '('
		String leftParen = popNextToken();
		//Process the inside as an Expression (recursive)
		OpTree tree = Expression();
		//Pop the closing ')', which needs to be there
		String rightParen = popNextToken();

		//If the closing ')' is not there, then it is not properly formatted and
		//should throw an exeption
		if (rightParen == null || !rightParen.equals(")"))
			throw new ParseException("Needs closed paranthesis at "+nextTokenIndex,
															nextTokenIndex);
		//If there is not a '^' after the closing parenthesis, we are done
		if (!"^".equals(peekNextToken()))
			return tree;

		//If there is a '^', then pop it, process the right side using a recursive
		//call to Factor(), then return the new tree if all goes well
		String oper = popNextToken();
		OpTree right = Factor();
		return new OpTree(oper, tree, right); //WE ARE DONE :D
	}


	/* Processes a "Number", which is exactly what it sounds like. It is a Double.
	N ::= <any double>
	*/
	public OpTree Number() throws ParseException{
		//Should NEVER be empty at this point in a correctly formatted list
		if (noMoreTokens()) 
			throw new ParseException("Parse error at token "+nextTokenIndex,
			nextTokenIndex);

		//Takes out the single token
		String token = popNextToken();

		//If it's a number, great! Return a terminal node to the tree
  	try{
			return new OpTree(new Double(token));
		}

		//If there is a problem (token is not a number) throw an exception, because
		//that should never ever happen
		catch (Exception e){
			throw new ParseException("Token at "+(nextTokenIndex-1)+" is not a number", 
															nextTokenIndex);
		}
	}


	//Same as the main method in the original "Parser" class. It prompts users to 
	//enter the input, and then returns the post-order traversal of the tree.
	public static void main(String[] args)
	{
		Tokenizer tokenizer = new Tokenizer();
		MyParser parser = new MyParser();
		Scanner s = new Scanner( System.in );

		while (true)
		{
			String line = s.nextLine();
			List<String> tokenList = Tokenizer.tokenize( line );
			if (tokenList == null) break;   // no lines left…

			System.out.println("Tokens: " + tokenList);
			String[] tokens = new String[tokenList.size()];
			for (int i = 0; i< tokenList.size(); i++)
				tokens[i] = tokenList.get(i);
			try{
			OpTree parseTree = parser.parse((String[]) tokens);
			System.out.println("Parse tree: " + parseTree);
			}
			catch (ParseException e){
				System.err.println("Error: "+e.getMessage());
			}
		}
	}
  
}
