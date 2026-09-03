package il.ac.tau.cs.sw1.trivia;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LoadedTrivia {
    private final List<LoadedQuestion> panels;
    private int currQues = 0;

    public LoadedTrivia(String file) {
        Set<LoadedQuestion> uniqueQuestions = new LinkedHashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    uniqueQuestions.add(new LoadedQuestion(line));
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("failed loading trivia file: " + file, e);
        }
        panels = new ArrayList<>(uniqueQuestions);
        Collections.shuffle(panels);
    }

    public LoadedQuestion getQuestion() {
        if (currQues < panels.size()) {
            return panels.get(currQues++);
        }
        return null;
    }
}
