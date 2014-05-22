// file:    Parser.java in miniMath.zip
// purpose: Parser
// author: Z. Dodds
//
/** class: Parser
* purpose: to convert from an array of Strings (tokens) 
*          to a valid parse tree, as an OpenList
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Parser
{
	/** tokens is the array of tokens for this Parser object
	*/
	private String[] tokens;        // this object's tokens
	private int nextTokenIndex;     // the next token to look at


	/** constructorL
	*/
	public Parser()
	{
		this.tokens = new String[0];  // empty array so far!
		this.nextTokenIndex = 0;
	}
	/** return next token AND ADVANCE
	*/
	public String popNextToken()
	{
		if (nextTokenIndex < tokens.length)
			return tokens[nextTokenIndex++];
		return null;
	}

	/** return next token AND DO NOT ADVANCE
	*/
	public String peekNextToken()
	{
		if (nextTokenIndex < tokens.length)
			return tokens[nextTokenIndex];
		return null;
	}

	/** do we have any more tokens to go?
	*/
	public boolean noMoreTokens()
	{
		return (this.peekNextToken() == null);
	}
	/** method parse
	* resets the tokens and nextTokenIndex and then parses
	* using recursive descent, starting with the start symbol...
	* output: an OpTree representing the resulting parse tree
	*/
	public OpTree parse(String[] tokens)
	{
		/*
		*       S -> P + S | P
		*       P -> N * P | N
		*       N -> parses to a Double
		*/
		this.tokens = tokens;
		this.nextTokenIndex = 0;
		return this.S();         // "All expressions are S's"
	}
	/** method S
	* parses an S nonterminal in our grammar using recursive descent
	*/
	public OpTree S()
	{
		if (noMoreTokens())
			return new OpTree();     		// Null Tree

		OpTree leftTree = P();          // "All S's start with P's"

		if (noMoreTokens())               // that's all, folks!
			return leftTree;  

		if (!"+".equals(peekNextToken())) // unrecognized token 
			return leftTree;                //   for THIS rule

		// so the next token WAS a "+" - first we'll get rid of it
		String operator = popNextToken(); // gone, but not forgotten
		OpTree rightTree = S();         // get the right-hand side

		return new OpTree( operator, leftTree, rightTree );
	}
	/** method P **/
	public OpTree P()
	{
		if (noMoreTokens()) return new OpTree();

		OpTree tree = N();   // "All P's start with N"

		if (noMoreTokens() || !"*".equals(peekNextToken()))
			return tree;    // finished, at least with THIS rule

		// the next token WAS a "*" - first get rid of it
		String operator = popNextToken();
		OpTree rightTree = P();  // get the right-hand side

		return new OpTree( operator, tree, rightTree );
	}

	/** method N **/
	public OpTree N()
	{
		if (noMoreTokens()) return new OpTree();

		String token = popNextToken();

		try {
			return new OpTree(new Double(token));
		} 
		catch (Exception e) {
			return new OpTree(token);  // just include the String
		}
	}


	/** main - for testing **/
	public static void main(String[] args)
	{
		Tokenizer tokenizer = new Tokenizer();
		Parser parser = new Parser();
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

			OpTree parseTree = parser.parse((String[]) tokens);
			System.out.println("Parse tree: " + parseTree);
		}
	}
}

// vim:ts=4:sw=4:tw=78:
