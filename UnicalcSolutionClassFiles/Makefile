TEST_CLASSES= QuantityTester.class ASTTester.class UnicalcTester.java
test:
	make -C ../ testcompile 
	cp ../*Tester.class .
	java -cp .:/junit-4.10.jar org.junit.runner.JUnitCore QuantityTester
	java -cp .:/junit-4.10.jar org.junit.runner.JUnitCore ASTTester
	java -cp .:/junit-4.10.jar org.junit.runner.JUnitCore UnicalcTester
