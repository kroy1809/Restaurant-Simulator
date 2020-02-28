# Java Makefile

# Project Structure
# Makefile
# README
# /src
#	/*.java
# /target
# 	/*.class
# /input
# 	*.txt
# /output
# 	*.txt

# set the entry of the java application
ENTRY_POINT = App

# set your source file name
SOURCE_FILES = \
Diner.java \
Order.java \
Cook.java \
App.java

# set file path related to the makefie location
TARGET = target
SOURCE = src

# set your java compiler:
JAVAC = javac

OUT = output.txt

# set compiler option
ENCODING = -encoding UTF-8
JFLAGS = $(ENCODING)

vpath %.class $(TARGET)
vpath %.java $(SOURCE)

# show help message by default:
Default:
	@echo "make init: build the target directory."
	@echo "make build: compile the source file."
	@echo "make clean: clear classes generated."
	@echo "make rebuild: rebuild project."
	@echo "make run: run the application at the entry point."

build: $(SOURCE_FILES:.java=.class)

%.class: %.java
	$(JAVAC) -d $(TARGET) $(SOURCE)/*.java

rebuild: clean build

.PHONY: init clean run

init:
	mkdir -pv $(TARGET)

clean:
	rm -frv $(TARGET)/*

run:
	java -classpath $(TARGET) $(ENTRY_POINT) $(FILE) $(OUT)
