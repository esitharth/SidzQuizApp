package Quiz;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


//import javax.print.DocFlavor.URL;

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
	
	String filenameQ = this.getClass().getResource("/files/Quiz.of").getPath();
			//"/home/sitharth/eclipse-workspace/SidzQuizApp/Files/Quiz.of";
	//"../Files/Quiz.of"; 
	String filenameTxT = this.getClass().getResource("/files/QuizResult.txt").getPath();
			//"/home/sitharth/eclipse-workspace/SidzQuizApp/Files/QuizResult.txt";
	Quiz Q;
	//FileWriter writer;
	Quiz createQuiz(Scanner S) throws IOException
	{
		System.out.println("Enter the Title of the Quiz: ");
		this.Title = S.nextLine();
		System.out.println("Enter the Topics to be included(comma seperated): ");
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
	void runQuiz(Quiz Q, Scanner S) throws IOException
	{
		
		for(Questions q : Q.questions)
		{
			q = q.displayQuestion(q,S);
			
			Q.Score += q.evaluateQuestion(q.Response);
		}
		System.out.println("Score: "+ Q.Score);
		Q.writeToFileTxtnPDF(Q);
	}
	void writeToFileTxtnPDF(Quiz Q) throws IOException
	{
		int j=1;
		String fileName = this.getClass().getResource("/files/QuizResult.txt").getPath();
		PrintWriter writer = new PrintWriter(fileName,"UTF-8");
		//"/home/sitharth/eclipse-workspace/SidzQuizApp/src/files/QuizResult.txt","UTF-8");
		//this.getClass().getResource().getPath()
		writer.println("Quiz Results");
		writer.println("-------------");
		writer.println("\n");
		for(Questions q : Q.questions)
		{
			writer.println(j++ +". " + q.Question);
			if(q.MCQ)
			{
				writer.println(q.Options);
				writer.println("Given response: "+q.Response);
				writer.println("Correct response: "+q.CheckResponse);
				
			}
			else
			{
				writer.println("Given response: " +q.Response);
				writer.println("Key words checked for: " +q.CheckResponse);
				
			}
			writer.println("Score :" +q.Mark);
			writer.println("---------------------------------");
		}
		
		writer.println("Total Score :" +Q.Score);
		writer.println("---------------------------------");
		writer.flush();
		writer.close(); //files are located at /SidzQuizApp/target/classes/files/
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
		in.close();
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
