package il.ac.tau.cs.sw1.trivia;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Label startupMessageLabel;
	private Font boldFont;
	private String lastAnswer;
	private LoadedTrivia trivia;
	private LoadedQuestion curQuestion;
	private SaveWheel passListener;
	private SaveWheel fiftyListener;
	
	// Currently visible UI elements.
	Label instructionLabel;
	Label questionLabel;
	private List<Button> answerButtons = new LinkedList<>();
	private List<SelectionListener> answerListeners = new LinkedList<>();
	private Button passButton;
	private Button fiftyFiftyButton;
	private int score;
	private int numQues;
	private int wrongQues;
	
	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3,
				monitor_bounds.height / 4));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);

		// create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE
				| SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));

		// "Browse" button
		final Button browseButton = new Button(fileSelection, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				//Do Nothing
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				filePathField.setText(GUIUtils.getFilePathFromFileDialog(shell));
			}
			
		});
		
		// "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");
		playButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// Do Nothing
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				trivia = new LoadedTrivia(filePathField.getText());
				curQuestion = trivia.getQuestion();
				lastAnswer = "";
				score = 0;
				numQues = 0;
				wrongQues = 0;
				scoreLabel.setText(String.valueOf(score));
				updateQuestionPanel(curQuestion.getQuestion(), curQuestion.getRandomAnswers());
			}
			
		});
	}

	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game
	 * starts. See the updateQuestionPanel for creating the question and answer
	 * buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		// Initially, only displays a message
		startupMessageLabel = new Label(questionPanel, SWT.NONE);
		startupMessageLabel.setText("No question to display, yet.");
		startupMessageLabel.setLayoutData(GUIUtils.createFillGridData(2));
		this.passListener = new SaveWheel("Pass");
		this.fiftyListener = new SaveWheel("");
	}

	/**
	 * Serves to display the question and answer buttons
	 */
	private void updateQuestionPanel(String question, List<String> answers) {
		// Save current list of answers.
		this.answerButtons = new LinkedList<>();
		this.answerListeners = new LinkedList<>();
		// clear the question panel
		Control[] children = questionPanel.getChildren();
		for (Control control : children) {
			control.dispose();
		}

		// create the instruction label
		instructionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		instructionLabel.setText(lastAnswer + "Answer the following question:");
		instructionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the question label
		questionLabel = new Label(questionPanel, SWT.CENTER | SWT.WRAP);
		questionLabel.setText(question);
		questionLabel.setFont(boldFont);
		questionLabel.setLayoutData(GUIUtils.createFillGridData(2));

		// create the answer buttons
		for (int i = 0; i < 4; i++) {
			Button answerButton = new Button(questionPanel, SWT.PUSH | SWT.WRAP);
			answerButton.setText(answers.get(i));
			GridData answerLayoutData = GUIUtils.createFillGridData(1);
			answerLayoutData.verticalAlignment = SWT.FILL;
			answerButton.setLayoutData(answerLayoutData);
			SelectionListener tmp = new answerListener(answerButton);
			answerButton.addSelectionListener(tmp);
			answerListeners.add(tmp);
			answerButtons.add(answerButton);
		}
		

		// create the "Pass" button to skip a question
		passButton = new Button(questionPanel, SWT.PUSH);
		passButton.setText("Pass");
		GridData data = new GridData(GridData.END, GridData.CENTER, true,
				false);
		data.horizontalSpan = 1;
		passButton.setLayoutData(data);
		passButton.addSelectionListener(passListener);
		if (passListener.used && score <= 0)
			passButton.setEnabled(false);
		
		// create the "50-50" button to show fewer answer options
		fiftyFiftyButton = new Button(questionPanel, SWT.PUSH);
		fiftyFiftyButton.setText("50-50");
		data = new GridData(GridData.BEGINNING, GridData.CENTER, true,
				false);
		data.horizontalSpan = 1;
		fiftyFiftyButton.setLayoutData(data);
		fiftyFiftyButton.addSelectionListener(fiftyListener);
		if (fiftyListener.used && score <= 0)
			fiftyFiftyButton.setEnabled(false);
		
		// two operations to make the new widgets display properly
		questionPanel.pack();
		questionPanel.getParent().layout();
	}
	
	private void GameOver() {
		for (int i = 0; i < 4; i++) {
			this.answerButtons.get(i).setEnabled(false);
		}
		this.passButton.setEnabled(false);
		this.fiftyFiftyButton.setEnabled(false);
	}

	private void UpdateScore() {
		scoreLabel.setText(String.valueOf(score));
	}
	
	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}
	
	public class SaveWheel implements SelectionListener {
		
		private boolean used;
		private boolean isPass;
		
		public SaveWheel(String s) {
			if (s.equals("Pass"))
				isPass = true;
			else
				isPass = false;
			used = false;
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (!used || score > 0) {
				if (used)
					score--;
				used = true;
				if (isPass) {
					lastAnswer = "";
					curQuestion = trivia.getQuestion();
					if (curQuestion == null) {
						GUIUtils.showInfoDialog(shell,"GAME OVER", "Your final score is " + score + " after " + numQues + " questions.");
						GameOver();
					}
					else {
						UpdateScore();
						updateQuestionPanel(curQuestion.getQuestion(), curQuestion.getRandomAnswers());
					}
				}
				else {
					UpdateScore();
					updateQuestionPanel(curQuestion.getQuestion(), curQuestion.getRandomAnswers());
					List<Button> tmp = new LinkedList<>();
					for (Button button: answerButtons)
						tmp.add(button);
					Collections.shuffle(tmp);
					tmp.get(0).setEnabled(false);
					tmp.get(1).setEnabled(false);
				}
			}
			
			
		}
		
	}
	
	public class answerListener implements SelectionListener {
		private Button button;
		public answerListener(Button button) {
			this.button = button;
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if (curQuestion.isCorrect(button.getText())) {
				numQues++;
				score += 3;
				UpdateScore();
				lastAnswer = "Correct! ";
				wrongQues = 0;
				curQuestion = trivia.getQuestion();
				if (curQuestion == null) {
					GUIUtils.showInfoDialog(shell,"GAME OVER", "Your final score is " + score + " after " + numQues + " questions.");
					GameOver();
				}
				else {
					updateQuestionPanel(curQuestion.getQuestion(), curQuestion.getRandomAnswers());
				}
			}
			else {
				score -= 2;
				UpdateScore();
				lastAnswer = "Wrong... ";
				numQues++;
				wrongQues++;
				curQuestion = trivia.getQuestion();
				if (wrongQues >= MAX_ERRORS || curQuestion == null) {
					GUIUtils.showInfoDialog(shell,"GAME OVER", "Your final score is " + score + " after " + numQues + " questions.");
					GameOver();
				}
				else {
					updateQuestionPanel(curQuestion.getQuestion(), curQuestion.getRandomAnswers());
				}
			}
		}
	}
}
