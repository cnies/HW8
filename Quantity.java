/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and A11347379
	 Section B00 and Section B00
	 May 22nd, 2014
 */
import java.util.Collections;
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
	public static final List<String> empty = Collections.<String>emptyList();

	////////////////////CONSTRUCTORS/////////////////////
	/*
		 Constructor containing values for the quantity, and the units in the
		 numerator or denominator
	 */
	public Quantity(double value, List<String> unitsNum, List<String> unitsDen){
		this.quantity = value;
		this.theUnits = new HashMap<String, Integer>();
		for (String unit: unitsNum){
			adjustExponentsBy(unit, 1);
		}
		for (String unit: unitsDen){
			adjustExponentsBy(unit, -1);
		}

	}


	/*
		 Deep-Copy constructor of another Quantity
	 */
	public Quantity(Quantity toCopy){
		this.quantity = toCopy.quantity;
		this.theUnits = new HashMap<String, Integer>();
		this.theUnits.putAll(toCopy.theUnits);
	}

	/*
		 Default no-arg constructor. Defaults to a value of 1.0 and
		 no units
	 */
	public Quantity(){
		this(1.0, empty, empty);
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
		if (!(toCompare instanceof Quantity)){
			return false;
		}
		if (toCompare == null)
			throw new NullPointerException();
		return this.toString().equals(toCompare.toString());
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
		if (toMult == null)
			throw new IllegalArgumentException();
		Quantity toReturn = new Quantity(this);
		double newValue = this.quantity*toMult.quantity;
		toReturn.quantity = newValue;
		for (String unit: toMult.theUnits.keySet()){
			toReturn.adjustExponentsBy(unit, toMult.theUnits.get(unit));
		}
		return toReturn;
	}

	/*
		 Divides this by another Quantity
	 */
	public Quantity div(Quantity toDiv){
		if (toDiv == null)
			throw new IllegalArgumentException();
		Quantity toReturn = new Quantity(this);
		double newValue = this.quantity/toDiv.quantity;
		toReturn.quantity = newValue;
		for (String unit: toDiv.theUnits.keySet()){
			toReturn.adjustExponentsBy(unit, -toDiv.theUnits.get(unit));
		}
		return toReturn;
	}

	/*
		 Raise this Quantity to an integer power
	 */
	public Quantity pow(int exponent){
    if (exponent == 0){
      return new Quantity();
    }
		Quantity toReturn = new Quantity(this);
		toReturn.quantity = java.lang.Math.pow(quantity, (double) exponent);
		for (String unit: toReturn.theUnits.keySet()){
			toReturn.multiplyExponentsBy(unit, exponent);
		}	
		return toReturn;
	}

	/*
		 Add another Quantity of the same units to this
	 */
	public Quantity add(Quantity toAdd){
		if (!(this.theUnits.equals(toAdd.theUnits)) 
				|| toAdd == null)
			throw new IllegalArgumentException();
		double newValue = this.quantity + toAdd.quantity;
		Quantity toReturn = new Quantity(this);
		toReturn.quantity = newValue;
		return toReturn;
	}

	/*
		 Find the difference between this and a Quantity of the same units
	 */
	public Quantity sub(Quantity toSubtract){
		if (!(this.theUnits.equals(toSubtract.theUnits)) 
				|| toSubtract == null)
			throw new IllegalArgumentException();
		double newValue = this.quantity - toSubtract.quantity;
		Quantity toReturn = new Quantity(this);
		toReturn.quantity = newValue;
		return toReturn;
	}

	/*
		 Find and return the negation of this Quantity
	 */
	public Quantity negate(){
		Quantity toReturn = new Quantity(this);
		toReturn.quantity = -1*toReturn.quantity;
		return toReturn;
	}

	//Uses a database to return a Quantity that represents one of the input unit
	//IE One Hour --> 3600 Seconds, or km ---> 1000m
	public static Quantity normalizedUnit(String unit, Map<String, Quantity> database){
		//Base case. If it's not in the list, return 1.0 <whatever the unit was>
		if (!database.containsKey(unit)){
			return new Quantity(1.0, Arrays.asList(unit), empty);
		}

		//If the unit conversion IS listed:
		else {
			//Get the conversion factor
			Quantity conversion = new Quantity(database.get(unit));
			//For each unit IN the conversion factor:
			for (String theUnit: conversion.theUnits.keySet()){
				//Get the correct factor used to cancel units out
				Quantity factor = new Quantity(1.0, Arrays.asList(theUnit), empty);
				//Divide the next conversion factor by factor if it exists
				factor = normalizedUnit(theUnit, database).div(factor);
				//Multiply the new conversion factor, including any powers
				conversion = conversion.mul(factor.pow(conversion.theUnits.get(theUnit)));
			}
			return conversion;
		}
	}

	//Uses a database to return a copy of this, with the units converted to the
	//primitive units as indicated by the database
	public Quantity normalize(Map<String, Quantity> database){
		Quantity toReturn = new Quantity(this);
		for (String unit: toReturn.theUnits.keySet()){
			Quantity factor = new Quantity(1.0, Arrays.asList(unit), empty);
			Quantity conversion = Quantity.normalizedUnit(unit, database).div(factor);
			toReturn = toReturn.mul(conversion.pow(toReturn.theUnits.get(unit)));
		}
		return toReturn;
	}




	///////////////////HELPER METHODS////////////////////
	//Simple method to shift the exponents in for the specified by delta.
	private void adjustExponentsBy(String unit, int delta){
		Integer oldVal = theUnits.get(unit);
		if (oldVal == null){
			theUnits.put(unit, delta);
			return;
		}
		oldVal = oldVal+delta;
		theUnits.put(unit, oldVal);
		if (oldVal == 0){
			theUnits.remove(unit);
		}
	}

	private void multiplyExponentsBy(String unit, int exp){
		Integer oldVal = theUnits.get(unit);
		Integer newVal = oldVal*exp;
		theUnits.put(unit, newVal);
	}

	//private get

	////////////////PUBLIC TESTER METHODS///////////////

}
