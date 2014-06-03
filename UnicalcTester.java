/*
	 CSE 12 Homework 8
	 Christopher Nies and Hasan Abu-Amara
	 A11393577 and A11347379
	 Section B00 and Section B00
	 May 22nd, 2014
 */
import junit.framework.TestCase;
public class UnicalcTester extends TestCase{
	private static final Tokenizer tok = new Tokenizer();
  private Unicalc calculator;
	private String worksForR1 = "zorkmid^2";
  private String worksForR2 = "500";
	private String worksForQ1 = "zorkmid";
  private String worksForQ2 = "12345";
  private String worksForQ3 = worksForQ2 + worksForQ1;
	private String worksForK1 = "-5";
  private String worksForK2 = "--(5*9*(0 + 3)^2)";
	private String worksForP1 = "2*(4 + 5)";
  private String worksForP2 = "2/(4*3)";
	private String worksForE1 = "5 + 2";
	private String worksForL1 = "5 ampere";
  private String worksForL2 = "2^2 + 2(6 - 8) + 20";
	private String worksForS1 = "def zorkmid 5 ampere";
  private String worksForS2 = "5 + 8*3 + 3 - 0";
  private String worksForS3 = "# 50 ampere * 120 second";
  private String error1 = "5^(6*7+-5)";
  private String error2 = "def # 1 ohm";
  private String error3 = "7*8 +";

	protected void setUp(){
    calculator = new Unicalc();
	}

  protected void tearDown()
  {
    calculator = null;
  }

  public void testSError1()
  {
    calculator.tokenize(error1);
    try
    {
      AST treeGenerated = calculator.S();
      fail("We made an AST tree of something that should be an error.");
    }
    catch (ParseError e)
    {
      //Should happen!
    }
  }

  public void testSError3()
  {
    calculator.tokenize(error3);
    try
    {
      AST treeGenerated = calculator.S();
      fail("We made an AST of something that is incomplete");
    }
    catch (ParseError e)
    {
      //Should happen!
    }
  }

	public void testSDefinition()
  {
    calculator.tokenize(worksForS1);
    AST treeGenerated = calculator.S();
    assertEquals("Define(zorkmid,Product(Value(5.0),Value(1.0 ampere)))",
                  treeGenerated.toString());
  }

  public void testSEquation()
  {
    calculator.tokenize(worksForS2);
    AST treeGenerated = calculator.S();
    assertEquals("Sum(Value(5.0),Sum(Product(Value(8.0),Value(3.0)),Difference("+
                "Value(3.0),Value(0.0))))", treeGenerated.toString());
  }

  public void testSNormalize()
  {
    calculator.tokenize(worksForS3);
    AST treeGenerated = calculator.S();
    assertEquals("Normalize(Product(Product(Value(50.0),Value(1.0 ampere))," +
                 "Product(Value(120.0),Value(1.0 second))))", 
                 treeGenerated.toString());
  }

  public void testSError2()
  {
    calculator.tokenize(error2);
    try
    {
      AST treeGenerated = calculator.S();
      fail("Managed to redefine # as a unit.");
    }
    catch (ParseError e)
    {
      //Should happen!
    }
  }

  public void testLDefition()
  {
    calculator.tokenize(worksForL1);
    AST treeGenerated = calculator.L();
    assertEquals("Product(Value(5.0),Value(1.0 ampere))", treeGenerated.toString());
  }

  public void testLEquation()
  {
    calculator.tokenize(worksForL2);
    AST treeGenerated = calculator.L();
    assertEquals("Sum(Power(Value(2.0),2),Sum(Product(Value(2.0)," + 
                  "Difference(Value(6.0),Value(8.0))),Value(20.0)))",
                  treeGenerated.toString());
  }

  public void testLNormalize()
  {
    calculator.tokenize(worksForS3);
    AST treeGenerated = calculator.L();
    assertEquals("Normalize(Product(Product(Value(50.0),Value(1.0 ampere))," +
                 "Product(Value(120.0),Value(1.0 second))))",
                 treeGenerated.toString());
  }

  public void testESimple()
  {
    calculator.tokenize(worksForE1);
    AST treeGenerated = calculator.E();
    assertEquals("Sum(Value(5.0),Value(2.0))", treeGenerated.toString());
  }

  public void testEComplex()
  {
    calculator.tokenize(worksForS2);
    AST treeGenerated = calculator.E();
    assertEquals("Sum(Value(5.0),Sum(Product(Value(8.0),Value(3.0)),Difference("+
                "Value(3.0),Value(0.0))))", treeGenerated.toString());
    
    calculator.tokenize(worksForL2);
    treeGenerated = calculator.E();
    assertEquals("Sum(Power(Value(2.0),2),Sum(Product(Value(2.0)," + 
                  "Difference(Value(6.0),Value(8.0))),Value(20.0)))",
                  treeGenerated.toString());
  }

  public void testPProduct()
  {
    calculator.tokenize(worksForP1);
    AST treeGenerated = calculator.P();
    assertEquals("Product(Value(2.0),Sum(Value(4.0),Value(5.0)))",
                  treeGenerated.toString());
  }

  public void testPDivision()
  {
    calculator.tokenize(worksForP2);
    AST treeGenerated = calculator.P();
    assertEquals("Quotient(Value(2.0),Product(Value(4.0),Value(3.0)))",
                  treeGenerated.toString());
  }

  public void testKNegation()
  {
    calculator.tokenize(worksForK1);
    AST treeGenerated = calculator.K();
    assertEquals("Negation(Value(5.0))", treeGenerated.toString());

    calculator.tokenize(worksForK2);
    treeGenerated = calculator.K();
    assertEquals("Negation(Negation(Product(Value(5.0),Product(Value(9.0)," +
                "Power(Sum(Value(0.0),Value(3.0)),2)))))",
                treeGenerated.toString());
  }

  public void testQWord()
  {
    calculator.tokenize(worksForQ1);
    AST treeGenerated = calculator.Q();
    assertEquals("Value(1.0 zorkmid)", treeGenerated.toString());
  }

  public void testQNumber()
  {
    calculator.tokenize(worksForQ2);
    AST treeGenerated = calculator.Q();
    assertEquals("Value(12345.0)", treeGenerated.toString());
  }

  public void testQUnit()
  {
    calculator.tokenize(worksForQ3);
    AST treeGenerated = calculator.Q();
    assertEquals("Product(Value(12345.0),Value(1.0 zorkmid))",
                  treeGenerated.toString());
  }

  public void testRUnit()
  {
    calculator.tokenize(worksForR1);
    AST treeGenerated = calculator.R();
    assertEquals("Power(Value(1.0 zorkmid),2)", treeGenerated.toString());
  }

  public void testRValue()
  {
    calculator.tokenize(worksForR2);
    AST treeGenerated = calculator.R();
    assertEquals("Value(500.0)", treeGenerated.toString());
  }
}
