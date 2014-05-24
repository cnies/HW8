/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and
	 Section B00 and Section B00
	 May 22nd, 2014
 */
import junit.framework.TestCase;
import java.util.*;

public class ASTTester extends TestCase{
	private Quantity value1;
	private Quantity value2;
	private AST a; private AST v;
	private static final Map<String, Quantity> db  = QuantityDB.getDB();
	private static final List<String> emp = new LinkedList<String>();

	protected void setUp(){
		value1 = new Quantity(5.0, Arrays.asList("m"), Arrays.asList("s", "s"));
		value2 = new Quantity(2.5, Arrays.asList("m"), Arrays.asList("s"));
		a = new Value(value1);
		v = new Value(value2);
	}

	public void testValue(){
		assertEquals(value1, a.eval(db));
		assertEquals(value2, v.eval(db));
	}

	public void testProduct(){

	}

	public void testQuotient(){
	}

	public void testSum(){
	}

	public void testDifference(){
	}

	public void testPower(){
	}

	public void testNegation(){
	}

	public void testNormalize(){
	}

	public void testDefine(){
	}




}
