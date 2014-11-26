JFLAGS = -g -Xlint
JC = javac
RM = rm

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

# Compile
SOURCES = \
	BoolObj.java \
	Client_GUI.java \
	KillProcessThread.java \
	Main.java \
	Receiver.java \
	SourceSelectionGUI.java \
	StreamBackend.java \
	StreamGobbler.java \
	Transmitter.java \
	User.java

CLASSES = \
        BoolObj.class \
        Client_GUI.class \
        KillProcessThread.class \
        Main.class \
        Receiver.class \
        SourceSelectionGUI.class \
        StreamBackend.class \
        StreamGobbler.class \
       	Transmitter.class \
        User.class
        
default: classes

classes: $(SOURCES:.java=.class)

clean:
	$(RM) *.class $(JARNAME)

run:
	java Main