import java.util.ArrayList;
/** Class OpTree - A simple parse tree to support minimath
 * @author: Philip Papadopoulos
 * @version 20 May 2014
*/
public class OpTree
{
	private String op;
	private Double dval;
	private OpTree left;
	private OpTree right;
	private static int total = 0;  // Class-wide: track # of nodes created
	private int me; // when this node was created
	
	private static final boolean DEBUG=false;
	
	/** Constructor - an infix op with a left and right subtree 
 	* @param theOp  operation to store in this node
 	* @param l left subtree 
 	* @param r right subtree 
 	*/
	public OpTree(String theOp, OpTree l, OpTree r)
	{
		me = ++total;
		if (DEBUG) System.out.println("OpTree with left and right " + me);
		op = theOp;
		dval = null;
		left = l;
		right = r;
	}
	/** Constructor - A terminal Op 
 	* @param theOp  operation to store in this node
 	*/
	public OpTree(String theOp)
	{
		me = ++total;
		if (DEBUG) System.out.println("Terminal Op " + me);
		op = theOp;
		dval = null;
		left = null;
		right = null;
	}
	/** Constructor - A Terminal Double 
 	* @param d  Double to store in this node
 	*/
	public OpTree(Double d)
	{
		me = ++total;
		if (DEBUG) System.out.println("Terminal Double " + me);
		op = null;
		dval = d;
		left = null;
		right = null;
	}
	/** Constructor - Empty Tree
 	*/
	public OpTree()
	{
		me = ++total;
		if (DEBUG) System.out.println("Empty Subtree" + me);
		op = null;
		dval = null;
		left = null;
		right = null;
	}
	/** does node represent an empty tree 
 	 * @return true if empty, false otherwise
 	 */
	public boolean isEmpty() {
		return (op == null && dval == null);
	}
	
	/** postOrder printing of this tree */
	private ArrayList<String> postOrder()
	{
		ArrayList<String> rlist = new ArrayList<String>();
		postOrder(this,rlist);
		return rlist;
	}
	/** postOrder printing of tree t, recursive helper
 	 * @param t root of subtree to postOrder 
 	 * @param rlist List of ops/doubles in post order */
	private void postOrder(OpTree t, ArrayList<String> rlist)
	{
		if (t == null) return;
		postOrder(t.left, rlist);
		postOrder(t.right, rlist);
		rlist.add(rlist.size(),(t.op != null? t.op : t.dval.toString()));
	}
	/** convert the postOrder visit to a string */
	public String toString()
	{
		return postOrder().toString();
	}
}
// vim:ts=4:sw=4:tw=78:
