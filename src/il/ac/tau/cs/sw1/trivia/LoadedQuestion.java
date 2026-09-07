package il.ac.tau.cs.sw1.trivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LoadedQuestion {
    private static final int EXPECTED_COLUMNS = 5;

    private final String question;
    private final Set<String> answers;
    private final String correct;

    public LoadedQuestion(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length != EXPECTED_COLUMNS) {
            throw new IllegalArgumentException("question line must contain question and four answers");
        }
        question = requireNonBlank(parts[0], "question");
        answers = new HashSet<>();
        for (int i = 1; i < parts.length; i++) {
            answers.add(requireNonBlank(parts[i], "answer"));
        }
        if (answers.size() != 4) {
            throw new IllegalArgumentException("question line must contain four distinct answers");
        }
        correct = parts[1];
    }

    public List<String> getRandomAnswers() {
        List<String> sortedAnswers = new ArrayList<>(answers);
        Collections.sort(sortedAnswers);
        return sortedAnswers;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answers, correct);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LoadedQuestion)) {
            return false;
        }
        LoadedQuestion other = (LoadedQuestion) obj;
        return question.equals(other.question) && answers.equals(other.answers) && correct.equals(other.correct);
    }

    public boolean isCorrect(String answer) {
        return correct.equals(answer);
    }

    private String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
