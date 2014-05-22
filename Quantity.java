/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and
	 Section B00 and Section B00
	 May 22nd, 2014
 */
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





	///////////////////PUBLIC METHODS////////////////////

	//Provided for us
	public String toString() {
		double valueOfTheQuantity = this.quantity;
		Map<String,Integer> mapOfTheQuantity = this.theUnits;
		// Ensure we get the units in order TreeSet<String> orderedUnits =
		new TreeSet<String>(mapOfTheQuantity.keySet());
		StringBuffer unitsString = new StringBuffer();
		for (String key : orderedUnits) {
			int expt = mapOfTheQuantity.get(key);
		}
		unitsString.append(" " + key); if (expt != 1)
			unitsString.append("^" + expt);
		// Used to convert doubles to a string with a
		// fixed maximum number of decimal places.
		DecimalFormat df = new DecimalFormat("0.0####");
		// Put it all together and return
		return df.format(valueOfTheQuantity)
			+ unitsString.toString();
	}





	///////////////////HELPER METHODS////////////////////
}
