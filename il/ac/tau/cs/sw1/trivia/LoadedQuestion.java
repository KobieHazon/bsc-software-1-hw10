package il.ac.tau.cs.sw1.trivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoadedQuestion {
	
	private String question;
	private Set<String> answers;
	private String correct;
	public LoadedQuestion(String line) {
		String[] parts = new String[5];
		answers = new HashSet<String>();
		parts = line.split("\\t");
		question = parts[0];
		for (int i = 0; i < 4; i++) {
			answers.add(parts[i+1]);
		}
		correct = parts[1];
	}
	
	public List<String> getRandomAnswers() {
		List<String> ans = new ArrayList<String>();
		ans.addAll(answers);
		Collections.sort(ans);
		return ans;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	@Override
	public int hashCode() {
		int sum = 0;
		for (String answer: answers) {
			sum += answer.hashCode();
		}
		sum += question.hashCode();
		return sum;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LoadedQuestion) {
			LoadedQuestion tmp = (LoadedQuestion) obj;
			return this.question.equals(tmp.question) && this.answers.equals(tmp.answers);
		}
		return true;
	}
	
	public boolean isCorrect(String s) {
		return s.equals(this.correct);
	}

}
