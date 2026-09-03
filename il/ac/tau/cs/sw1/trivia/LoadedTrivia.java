package il.ac.tau.cs.sw1.trivia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoadedTrivia {
	
	private List<LoadedQuestion> panels;
	private int currQues = 0;
	public LoadedTrivia (String file) {
		Set<LoadedQuestion> tmp = new HashSet<LoadedQuestion>();
		panels = new ArrayList<LoadedQuestion>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			for(String line; (line = br.readLine()) != null; ) {
		        tmp.add(new LoadedQuestion(line));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panels.addAll(tmp);
		Collections.shuffle(panels);
	}
	
	public LoadedQuestion getQuestion() {
		if (currQues < panels.size()) {
			return panels.get(currQues++);
		}
		else {
			return null;
		}
	}
}
