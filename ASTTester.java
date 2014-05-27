/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and A11347379
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
		AST pro = new Product(a, v);
		Quantity result = value1.mul(value2);
		assertEquals(result, pro.eval(db));
	}

	public void testQuotient(){
		AST quo = new Quotient(a,v);
		Quantity result = value1.div(value2);
		assertEquals(result, quo.eval(db));
	}

	public void testSum(){
		AST sum = new Sum(a, v);
		try{
			sum.eval(db);
		}
		catch (IllegalArgumentException e){
			//pass
		}
		Quantity p = new Quantity(1.0, Arrays.asList("s"), emp);
		AST pro = new Product(a,new Value(p) );
		sum = new Sum(pro, v);
		Quantity result = value2.add(p.mul(value1));
		assertEquals(result, sum.eval(db));
	}

	public void testDifference(){
		AST diff= new Difference(a, v);
		try{
			diff.eval(db);
		}
		catch (IllegalArgumentException e){
			//pass
		}
		Quantity p = new Quantity(1.0, Arrays.asList("s"), emp);
		AST pro = new Product(a,new Value(p) );
		diff = new Difference(v, pro);
		Quantity result = value2.sub(p.mul(value1));
		assertEquals(result, diff.eval(db));
	}

	public void testPower(){
		AST power = new Power(v, 3);
		Quantity result = value2.pow(3);
		assertEquals(result, power.eval(db));
	}

	public void testNegation(){
		AST neg = new Negation(a);
		Quantity result = value1.negate();
		assertEquals(result, neg.eval(db));
	}

	public void testNormalize(){
		Quantity force = new Quantity(-5, Arrays.asList("N"), emp);
		AST forceV = new Value(force);
		AST quo = new Quotient(forceV, a);
		AST norm = new Normalize(quo);
		Quantity result = new Quantity(-1.0, Arrays.asList("kg"), emp);
		assertEquals(result, norm.eval(db));
	}

	public void testDefine(){
		String newUnit = "twilight_sparkle";
		Quantity forceOfT = new Quantity(1500, Arrays.asList("N"), emp);
		Quantity distOfT = new Quantity(500, Arrays.asList("m"), emp);
		AST aTwi = new Product(new Value(forceOfT), new Value(distOfT));
		AST norm = new Normalize(aTwi);
		AST define = new Define(newUnit, norm);
		define.eval(db);
		assertTrue(db.containsKey(newUnit));
		assertTrue(db.containsValue(forceOfT.mul(distOfT).normalize(db)));
		Quantity result = new Quantity(750000, Arrays.asList("kg", "meter", "meter")
				, Arrays.asList("second", "second"));
		Quantity oneTwi = new Quantity(1.0, Arrays.asList("twilight_sparkle"), emp);
		assertEquals(result, oneTwi.normalize(db));
	}




}
