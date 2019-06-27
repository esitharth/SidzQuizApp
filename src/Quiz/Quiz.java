package Quiz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.io.Serializable;
import com.thoughtworks.xstream.XStream;

public class Quiz extends QuestionBank implements Serializable{
	
	Quiz(Scanner S) throws IOException {
		super(S);
		try {
			this.Q = readfromFileQ();	
		}
		catch(Exception e) {
			
		}
		finally {
			if(this.Q==null)
			{
				this.Q = createQuiz(S);	
			}
		}
	}
	String Title = new String();	
	List<String> Topics = new ArrayList<String>();
	List<Questions> questions = new ArrayList<Questions>();
	String difficulty = "low";
	float Score = 0;
	String filenameQ = "/home/sitharth/eclipse-workspace/SidzQuizApp/Files/Quiz.of";
	Quiz Q;
	
	Quiz createQuiz(Scanner S) throws IOException
	{
		System.out.println("Enter the Title of the Quiz: ");
		this.Title = S.nextLine();
		System.out.println("Enter the Topic to be included(comma seperated):");
		this.Topics = Arrays.asList(S.nextLine().split("\\s*,\\s*"));
		System.out.println("Enter the difficulty level(low, medium, high): ");
		this.difficulty = S.nextLine();
		if(super.QuestionPool.isEmpty())
		{
			this.createQuestionPool(S);
		}
		this.questions.clear() ;
		selectQuestions(super.QuestionPool, this);
		this.writeToFileQ();
		return this;
	}
	void selectQuestions(List<Questions> Qs, Quiz Q)
	{
		
		for (Questions q : Qs)
		{
			if(Q.Topics.contains(q.Topic) && q.difficulty.equals(Q.difficulty))
			{
				Q.questions.add(q);
			}
		}
		
	}
	void runQuiz(Quiz Q, Scanner S)
	{
		for(Questions q : Q.questions)
		{
			q.Response = q.displayQuestion(q,S);
			Score += q.evaluateQuestion(q.Response);
		}
		System.out.println("Score: "+ Score);
		
	}
	void writeToFileQ() throws IOException //XML file write for Quiz
	{
		FileWriter out = new FileWriter(this.filenameQ);
        XStream xs = new XStream();
        xs.toXML(this, out);
        out.close();
		
	}
	Quiz readfromFileQ() throws IOException //XML file read for Quiz
	{	
		FileReader in = new FileReader(this.filenameQ);
		XStream xs = new XStream();
		Quiz Q = (Quiz)xs.fromXML(in);
		if(Q != null)
		{
			return Q;
		}
		else
		{
			return null;
		}
		
	}
}
