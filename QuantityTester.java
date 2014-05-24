/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and
	 Section B00 and Section B00
	 May 22nd, 2014
 */
import junit.framework.TestCase;
import java.util.*;
public class QuantityTester extends TestCase{
	private Quantity accel;
	private Quantity oneSec;
	private Quantity power;
	private Quantity resistance;
	private Quantity current;
	private Quantity voltage;

	public void setUp(){
		accel = new Quantity(-9.8, Arrays.asList("m"), Arrays.asList("s", "s"));
		oneSec = new Quantity(1.0, Arrays.asList("s"), empty());
		resistance = new Quantity(10, Arrays.asList("megohm"), empty());
		current = new Quantity (1.5, Arrays.asList("A"), empty());
	}
	public void testConstructor(){
		String toCheck = accel.toString();
		assertTrue(toCheck.contains("-9.8 m s^-2"));
	}

	public void testCopy(){
		Quantity accelCopy = new Quantity(accel);
		assertEquals(accel, accelCopy);
	}

	public void testNoArg(){
		Quantity simple = new Quantity();
		assertEquals(new Quantity(1, empty(), empty()), simple);
	}

	public void testEquals(){
		Quantity toCompare = new Quantity(9.8, Arrays.asList("m"),
												 Arrays.asList("s", "s"));
		String notAQuantity = "Not a quantity";
		assertTrue(!accel.equals(toCompare));
		assertTrue(!accel.equals(notAQuantity));
		assertTrue(accel.equals(new Quantity(accel)));
	}

	public void testMul(){
		Quantity currentCopy = new Quantity(current);
		Quantity resistanceCopy = new Quantity(resistance);
		voltage = current.mul(resistance);
		assertEquals(new Quantity(15, Arrays.asList("A", "megohm"), empty()), voltage);
		assertEquals(current, currentCopy);
		assertEquals(resistance, resistanceCopy);
	}

	public void testDiv(){
		Quantity accelCopy = new Quantity(accel);
		Quantity secCopy = new Quantity(oneSec);
		Quantity jerk = accel.div(oneSec);
		Quantity currentCopy = new Quantity(current);
		assertEquals(new Quantity(-9.8, Arrays.asList("m"),
									 Arrays.asList("s", "s", "s")), jerk);
		assertEquals(accelCopy, accel);
		assertEquals(secCopy, oneSec);
		Quantity one = current.div(currentCopy);
		assertEquals(new Quantity(1.0, empty(), empty()), one);

	}

	public void testPow(){
		Quantity currentCopy = new Quantity(current);
		Quantity resistanceCopy = new Quantity(resistance);
		Quantity power = (current.pow(2)).mul(resistance);
		assertEquals(new Quantity(22.5, Arrays.asList("A", "A", "megohm"), empty()),
									power);
	}

	public void testAdd(){
		Quantity newAccel = new Quantity(4.9, Arrays.asList("m"),
		 Arrays.asList("s","s"));
		Quantity newCurrent = new Quantity(.75, Arrays.asList("A"), empty());
		Quantity addAccel = accel.add(newAccel);
		Quantity addCurrent = current.add(newCurrent);
		assertEquals(new Quantity(-4.9, Arrays.asList("m"),
															 Arrays.asList("s", "s")), addAccel);
		assertEquals(new Quantity(2.25, Arrays.asList("A"),
															 empty()), addCurrent);
		try{
			newAccel.add(newCurrent);
		}
		catch (IllegalArgumentException e){
			//pass
		}
	}

	public void testSub(){
		Quantity newAccel = new Quantity(4.9, Arrays.asList("m"),
				Arrays.asList("s","s"));
		Quantity newCurrent = new Quantity(.75, Arrays.asList("A"), empty());
		Quantity addAccel = accel.sub(newAccel);
		Quantity addCurrent = current.sub(newCurrent);
		assertEquals(new Quantity(-14.7, Arrays.asList("m"),
					Arrays.asList("s", "s")), addAccel);
		assertEquals(new Quantity(.75, Arrays.asList("A"),
					empty()), addCurrent);
		try{
			newAccel.sub(newCurrent);
		}
		catch (IllegalArgumentException e){
			//pass
		}
	}
	

	public void testNegate(){
		Quantity negated = accel.negate();
		assertEquals(new Quantity(9.8, Arrays.asList("m"),
					Arrays.asList("s", "s")), negated);

	}

	public void testNormalizedUnit(){
		Map<String, Quantity> db = QuantityDB.getDB();
		Quantity amps = Quantity.normalizedUnit("A", db); 
		Quantity mps = Quantity.normalizedUnit("kph", db);
		assertEquals(new Quantity(1.0, Arrays.asList("ampere"), empty()), amps); 
		assertEquals(new Quantity(.27778, Arrays.asList("meter"),
					Arrays.asList("second")), mps);
		assertEquals(new Quantity(3600000, Arrays.asList("kg", "meter", "meter"),
					Arrays.asList("second", "second")),
				Quantity.normalizedUnit("kwh", db));
		assertEquals(new Quantity(1.0, Arrays.asList("smoot"), empty()),
				Quantity.normalizedUnit("smoot", db));
	}

	public void testNormalize(){
		Map<String, Quantity> db = QuantityDB.getDB();
		Quantity mph = new Quantity(10, Arrays.asList("mph"), empty());
		Quantity toCompare = new Quantity(4.4704, Arrays.asList("m"),
				Arrays.asList("s"));
		assertEquals(mph.normalize(db), toCompare.normalize(db));
		Quantity kph = new Quantity(60, Arrays.asList("kph"), empty());
		toCompare = new Quantity(999.99996, Arrays.asList("m"), Arrays.asList("min"));
		assertEquals(kph.normalize(db), toCompare.normalize(db));
	}

	public void testHash(){
		Quantity simple = new Quantity(1, empty(), empty());
		assertTrue(!(simple.hashCode() == oneSec.hashCode()));
		assertEquals(accel.hashCode(), new Quantity(accel).hashCode());
		Quantity diff = new Quantity(-9.7, Arrays.asList("m"), Arrays.asList("s", "s"));
		assertTrue(accel.hashCode() != diff.hashCode());
	}




	private List<String> empty(){
		return Collections.<String>emptyList();
	}
}
