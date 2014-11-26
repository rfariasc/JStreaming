JFLAGS = -g -Xlint
JC = javac
RM = rm

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

# Compile
SOURCES = \
	Servidor.java \
	User.java
	
CLASSES = \
        Servidor.class \
        User.class
        
default: classes

classes: $(SOURCES:.java=.class)

clean:
	$(RM) *.class $(JARNAME)

run:
	java Main