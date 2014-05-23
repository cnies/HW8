JC = javac

.SUFFIXES:.java .class

.java.class:
	$(JC) -cp /:.:./Pre-HW:/junit-4.10.jar $*.java

PRE_CLASSES = ./Pre-HW/OpTree.class ./Pre-HW/Tokenizer.class ./Pre-HW/MyParser.class
TEST_CLASSES= QuantityTester.class ASTTester.class UnicalcTester.class
MAIN_CLASSES= Quantity.class AST.class Unicalc.class

prerun: $(PRE_CLASSES)
	java -cp ./Pre-HW MyParser

test: $(MAIN_CLASSES) $(TEST_CLASSES)
	java -cp .:/junit-4.10.jar org.junit.runner.JUnitCore $(TEST_CLASSES)
run: $(MAIN_CLASSES)
	java Unicalc
clean:
	rm -f *.class
testcompile: $(TEST_CLASSES)
testpremade:
	make -C UnicalcSolutionClassFiles/ test
