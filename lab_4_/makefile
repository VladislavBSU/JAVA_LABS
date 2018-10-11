# makefile z4_2

.SUFFIXES: .class .java

PACK=z4_2
TARG=test
JC=javac -g
JM=java -ea -jar
JR=jar -cfe

OBJ=$(PACK)\Vector.class \
    $(PACK)\Main.class

.java.class:
	$(JC) $<

run: build
	$(JM) $(TARG).jar

build: 	$(OBJ)
	$(JR) $(TARG).jar $(PACK).test $(OBJ)

clean:
	for %%f in ($(OBJ)) do del %%f
	del $(TARG).jar

$(PACK)\Matrix.class: $(PACK)\Vector.java makefile

$(PACK)\test.class: $(PACK)\Main.java makefile

# eof makefile z4_2
