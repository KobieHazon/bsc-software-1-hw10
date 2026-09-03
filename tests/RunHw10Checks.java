import il.ac.tau.cs.sw1.trivia.LoadedQuestion;
import il.ac.tau.cs.sw1.trivia.LoadedTrivia;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RunHw10Checks {
    public static void main(String[] args) {
        testLoadedQuestion();
        testLoadedTrivia();
        System.out.println("All HW10 checks passed");
    }

    private static void testLoadedQuestion() {
        LoadedQuestion question = new LoadedQuestion("Question?	Correct	Wrong 1	Wrong 2	Wrong 3");
        checkEquals("Question?", question.getQuestion(), "question text");
        check(question.isCorrect("Correct"), "correct answer is accepted");
        check(!question.isCorrect("Wrong 1"), "wrong answer is rejected");

        List<String> answers = question.getRandomAnswers();
        checkEquals(4, answers.size(), "answer count");
        checkEquals("Correct", answers.get(0), "answers are sorted deterministically");
        check(question.equals(new LoadedQuestion("Question?	Correct	Wrong 1	Wrong 2	Wrong 3")),
                "matching questions are equal");
        check(!question.equals("not a question"), "different object types are not equal");

        boolean malformedFailed = false;
        try {
            new LoadedQuestion("Question?	Only one answer");
        } catch (IllegalArgumentException expected) {
            malformedFailed = true;
        }
        check(malformedFailed, "malformed question line is rejected");
    }

    private static void testLoadedTrivia() {
        LoadedTrivia trivia = new LoadedTrivia("resources/trivia/trivia.txt");
        int count = 0;
        Set<String> seen = new HashSet<>();
        LoadedQuestion question;
        while ((question = trivia.getQuestion()) != null) {
            count++;
            seen.add(question.getQuestion());
        }
        checkEquals(50, count, "recovered trivia file has 50 question entries");
        checkEquals(49, seen.size(), "recovered trivia file has one repeated prompt with different answers");
        checkEquals(null, trivia.getQuestion(), "no question after file is exhausted");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void checkEquals(Object expected, Object actual, String message) {
        if (expected == null) {
            if (actual != null) {
                throw new AssertionError(message + ": expected null but got " + actual);
            }
            return;
        }
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ": expected " + expected + " but got " + actual);
        }
    }
}
