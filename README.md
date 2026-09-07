# BSc Software 1 - Homework 10

A historical archive of my CS BSc coursework.

## Contents

This homework contains a Java trivia application and riddle exercises:

- `il.ac.tau.cs.sw1.trivia` - trivia question loading, loaded-question model, and SWT GUI source.
- `src/riddles` - equality, hashing, comparison, and collection-behavior exercises.
- `src/enumRiddles` - enum state-transition exercises.
- `resources/trivia/trivia.txt` - recovered trivia data file, moved from the submission root into a resource folder.

## Provenance

- Era: CS BSc.
- Last recovered work: June 2018.
- Original handout status: the exact matching Homework 10 handout was not recovered.
- Maintenance changes: the current version adds repeatable local validation and hardens the loaded-question/parser model. The SWT GUI source is retained but treated as optional because SWT is not available in the local validation environment.

## Tech Stack

- Java 8 language features, validated with Java 11 or newer.
- SWT for the optional desktop GUI.
- Plain `javac` and `java`; no external dependencies for the core/parser/riddle validation.
- `make` for repeatable compile, test, optional GUI compile, and cleanup commands.

## Run Core Validation

```bash
make test
```

To remove generated files:

```bash
make clean
```

## Compile The Optional GUI

The GUI depends on SWT. When an SWT JAR is available locally:

```bash
make compile-swt SWT_JAR=/path/to/swt.jar
```

## Repository layout

- `src/`: Java source packages (`il/`, `riddles/`, and `enumRiddles/`), preserving their package names.
- `resources/`: supplied trivia data.
- `tests/`: focused automated checks.
- `assignment/`: recovered assignment/provenance context.
- `build/`: ignored compiler output.

The root-level `make test` and `make compile-swt SWT_JAR=...` commands use the new source root. No package names or algorithms changed.
