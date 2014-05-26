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
	private String worksForR;
	private String worksForQ;
	private String worksForK;
	private String worksForP;
	private String worksForE;
	private String worksForL;
	private String worksForS1 = "def zorkmid 5 ampere";
  private String worksForS2 = "5 + 8*3 + 3 - 0";
  private String worksForS3 = "# 50 ampere * 120 second";

	protected void setUp(){
    calculator = new Unicalc();
	}

  protected void tearDown()
  {
    calculator = null;
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
}
