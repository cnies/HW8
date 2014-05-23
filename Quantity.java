/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and
	 Section B00 and Section B00
	 May 22nd, 2014
 */
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;
public class Quantity{

	///////////////////////FIELDS////////////////////////
	private double quantity; /*The main value of the measured quantity*/
	private Map<String, Integer> theUnits; /*The Units with their exponents*/

	////////////////////CONSTRUCTORS/////////////////////
	/*
		 Constructor containing values for the quantity, and the units in the
		 numerator or denominator
	 */
	public Quantity(double value, List<String> unitsNum, List<String> unitsDen){
	}

	/*
		 Deep-Copy constructor of another Quantity
	 */
	public Quantity(Quantity toCopy){
	}

	/*
		 Default no-arg constructor. Defaults to a value of 1.0 and
		 no units
	 */
	public Quantity(){
	}





	///////////////////PUBLIC METHODS////////////////////

	//Provided for us. Good thing too!
	public String toString() {
		double valueOfTheQuantity = this.quantity;
		Map<String,Integer> mapOfTheQuantity = this.theUnits;
		// Ensure we get the units in order
		TreeSet<String> orderedUnits =new TreeSet<String>(mapOfTheQuantity.keySet());
		StringBuffer unitsString = new StringBuffer();
		for (String key : orderedUnits) {
			int expt = mapOfTheQuantity.get(key);	
			unitsString.append(" " + key);
			if (expt != 1)
				unitsString.append("^" + expt);
		}
		// Used to convert doubles to a string with a
		// fixed maximum number of decimal places.
		DecimalFormat df = new DecimalFormat("0.0####");
		// Put it all together and return
		return df.format(valueOfTheQuantity)
			+ unitsString.toString();
	}

	//Returns true if this Quantity is the same as another. Use Java's
	//"instanceof" to make sure the object is a Quantity
	public boolean equals(Object toCompare){
		return false;
	}

	//Returns a hashcode for this Quantity. Pretty easy, since all we
	//need to do is get the hash code for the String
	public int hashCode(){
		return this.toString().hashCode();
	}

	//All of these are operations on the Quantities, all of them throw
	//IllegalArgumentExceptions if the arguments are null. Add and Subtract
	//also throw this exception if they are not of the same units
	/*
		 Used for the multiplication of this by another Quantity
	 */
	public Quantity mul(Quantity toMult){
		return (Quantity) null;
	}

	/*
		 Divides this by another Quantity
	 */
	public Quantity div(Quantity toDiv){
		return (Quantity) null;
	}

	/*
		 Raise this Quantity to an integer power
	 */
	public Quantity pow(int exponent){
		return (Quantity) null;
	}

	/*
		 Add another Quantity of the same units to this
	 */
	public Quantity add(Quantity toAdd){
		return (Quantity) null;
	}

	/*
		 Find the difference between this and a Quantity of the same units
	 */
	public Quantity sub(Quantity toSubtract){
		return (Quantity) null;
	}

	/*
		 Find and return the negation of this Quantity
	 */
	public Quantity negate(){
		return (Quantity) null;
	}

	//Uses a database to return a Quantity that represents one of the input unit
	//IE One Hour --> 3600 Seconds, or km ---> 1000m
	public static Quantity normalizeUnit(String unit, Map<String, Quantity> database){
		return (Quantity) null;
	}

	//Uses a database to return a copy of this, with the units converted to the
	//primitive units as indicated by the database
	public static Quantity normalize(Map<String, Quantity> database){
		return (Quantity) null;
	}




	///////////////////HELPER METHODS////////////////////
	//Simple method to shift the exponents in for the specified by delta.
	private void adjustExponentsBy(int delta, String unit){
	}

}
