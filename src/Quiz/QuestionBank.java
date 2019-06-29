package Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;

import java.lang.reflect.Type;

public class QuestionBank extends Questions {
	
	List<Questions> QuestionPool = new ArrayList<Questions>();
	String filenameQB = this.getClass().getResource("/files/QuestionBank.of").getPath();
	XStream xStream = new XStream();
	
	void createQuestionPool(Scanner S) throws IOException
	{	
		System.out.println("Enter the number of questions to be entered(in integers): ");
		String noQ = S.nextLine();
		for(int i = 0; i< Integer.parseInt(noQ); i++)
		{
			Questions newQuestion = new Questions();
			QuestionPool.add(newQuestion.getQuestion(S));
		}
		this.writeToFileQB();
	}
	void searchQuestions(List<Questions> Qs, Scanner S)
	{
		//List<Questions> searchResult = new ArrayList<Questions>();
		System.out.println("Enter the Topic under which to search the Questions: ");
		String Topic = S.nextLine();
		int i=1;
		System.out.println("--------------------------------------------------------");
		System.out.println("Questions under the topic: ");
		
		for (Questions q : Qs)
		{
			if(q.Topic.equals(Topic) )
			{
				System.out.println(i++ + ". "+ q.Question);
				//searchResult.add(q);
			}
		}
		if(i==1)
			System.out.println("Question Pool has no questions under the searched topic.");
			System.out.println("--------------------------------------------------------");
	}
	void displayAllQuestions(List<Questions> Qs, Scanner S)
	{
		int i=1;
		System.out.println("Question List:");
		for (Questions q : Qs)
		{
			System.out.println(i++ + ". "+ q.Question);
			char a = 'a';
			if(q.MCQ)
			{
				for(String option : q.Options) 
				{
					System.out.println(a++ + ". " + option);
				}
			}
			else
			{
				System.out.println("Keywords to check: "+ q.CheckResponse);
			}
			System.out.println("Topic: "+ q.Topic);
			System.out.println("Difficulty: "+ q.difficulty);
			System.out.println("----------------------------");
		}
		System.out.println("Question List ends.");
	}
	void updateQuestions(List<Questions> Qs, Scanner S)
	{
		Questions P = new Questions();
		displayAllQuestions(Qs, S);
		System.out.println("Enter the numbers of questions seperated by comma to be updated(integers): ");
		List<String> QNumbers = Arrays.asList(S.nextLine().split("\\s*,\\s*"));
		int index;
		for(int j=0; j<QNumbers.size(); j++)
		{
			index = Integer.parseInt(QNumbers.get(j));
			System.out.println("Given Question: "+ Qs.get(index-1).Question);
			System.out.println("Enter the new Question: ");
			P.Question = S.nextLine(); 
			System.out.println("Enter the difficulty level: ");
			P.difficulty = S.nextLine();
			if(Qs.get(index-1).MCQ)
			{
				char k = 'a';
				while(k!='e')
				{
					System.out.println("Enter option "+k+":");				
					P.Options.add(S.nextLine());
					k++;
				}
				System.out.println("Enter the correct option/s (a|b|c|d): ");	
				P.CheckResponse = S.nextLine();
			}
			else
			{
				System.out.println("Enter the keywords to check in response (seperated by '|' without spaces between): ");
				P.CheckResponse = S.nextLine();
			}
			
			Qs.add(index-1, P);
		}
	}
	void deleteQuestions(List<Questions> Qs, Scanner S)
	{
		System.out.println("Enter the numbers of questions seperated by comma to be deleted(integers): ");
		List<String> QNumbers = Arrays.asList(S.nextLine().split("\\s*,\\s*"));
		int index;
		for(int j=0; j<QNumbers.size(); j++)
		{
			index = Integer.parseInt(QNumbers.get(j));
			Qs.remove(index-1);
		}
		System.out.println("The mentioned Question(s) have been removed from the list");
		System.out.println("---------------------");
	}
	List<Questions> readfromFileQB() throws IOException //XML File Read for the QuestionBank
	{	
		FileReader in = new FileReader(this.filenameQB);
		XStream xs = new XStream();
		QuestionBank QB = (QuestionBank)xs.fromXML(in);
		in.close();
		if(QB != null)
		{
			return QB.QuestionPool;
		}
		else
		{
			return null;
		}
		
	}
	void writeToFileQB() throws IOException //XML File Write for the QuestionBank
	{
		
		FileWriter out = new FileWriter(this.filenameQB);
        XStream xs = new XStream();
        xs.toXML(this, out);
        out.close();

	}
	QuestionBank(Scanner S) throws IOException 
	{
		try {	
			this.QuestionPool = readfromFileQB();
		}
		catch(Exception e){
			
			
		}
		finally
		{
			if(this.QuestionPool == null)
			{
				createQuestionPool(S);
			}
		}			
	}	
}
