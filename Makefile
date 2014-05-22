JC = javac

.SUFFIXES:.java .class

.java.class:
	$(JC) -cp .:./Pre-HW:/junit-4.10.jar $*.java

PRE_CLASSES = ./Pre-HW/OpTree.class ./Pre-HW/Tokenizer.class ./Pre-HW/MyParser.class

prerun: $(PRE_CLASSES)
	java -cp ./Pre-HW MyParser
