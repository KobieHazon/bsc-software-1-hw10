JAVAC ?= javac
JAVA ?= java
BUILD_DIR := build
CORE_SOURCES := $(shell find src tests -name '*.java' ! -name 'TriviaGUI.java' ! -name 'GUIUtils.java' ! -name 'TriviaMain.java')
GUI_SOURCES := $(shell find src -name '*.java')

.PHONY: compile compile-swt test clean

compile:
	mkdir -p $(BUILD_DIR)
	$(JAVAC) -encoding UTF-8 -Xlint:all -Werror -d $(BUILD_DIR) $(CORE_SOURCES)

compile-swt:
	@test -n "$(SWT_JAR)" || (echo "Set SWT_JAR=/path/to/swt.jar to compile the GUI" && exit 1)
	mkdir -p $(BUILD_DIR)
	$(JAVAC) -encoding UTF-8 -cp "$(SWT_JAR)" -d $(BUILD_DIR) $(GUI_SOURCES)

test: compile
	$(JAVA) -cp $(BUILD_DIR) RunHw10Checks
	$(JAVA) -cp $(BUILD_DIR) riddles.Riddle >/tmp/bsc-software-1-hw10-riddle-smoke.txt
	$(JAVA) -cp $(BUILD_DIR) enumRiddles.DayTest >/tmp/bsc-software-1-hw10-day-smoke.txt
	$(JAVA) -cp $(BUILD_DIR) enumRiddles.TLightTest >/tmp/bsc-software-1-hw10-tlight-smoke.txt

clean:
	rm -rf $(BUILD_DIR)
