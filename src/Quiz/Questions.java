package Quiz;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.*;

public class Questions implements Serializable {
	boolean MCQ;
	String Question;
	String Topic;
	List<String> Options = new ArrayList<String>();
	String Response;
	String CheckResponse;
	float Mark = 0;
	int TotalQuestions;
	String difficulty;
	
	Questions getQuestion(Scanner S) {
		System.out.println("Enter Question: ");
		Question = S.nextLine();
		System.out.println("Under which topics does this question come: ");
		Topic = S.nextLine();
		System.out.println("Enter difficulty level(low, medium, high): ");
		difficulty = S.nextLine();
		System.out.println("Is this Question an MCQ? (y/n):");
		char mcq = S.nextLine().trim().charAt(0);
		if(mcq == 'y')
			MCQ = true;
		else
			MCQ = false;
		if(MCQ)
		{
			char j = 'a';
			while(j!='e')
			{
				System.out.println("Enter option "+j+":");				
				Options.add(S.nextLine());
				j++;
			}
			System.out.println("Enter the correct option/s (a|b|c|d): ");	
			CheckResponse = S.nextLine();
		}
		else
		{
			System.out.println("Enter the keywords to check in response (seperated by '|' without spaces between): ");
			CheckResponse = S.nextLine();
		}
		//S.close();
		return this;
		
	}
	
	float evaluateQuestion(String Response) //Scoring function 
	{
		if(MCQ)
		{
			List<String> list1 = new java.util.ArrayList<>(Arrays.asList(CheckResponse.split("\\|")));
			int correctResponses = list1.size();
			List<String> list2 = new java.util.ArrayList<>(Arrays.asList(Response.split("\\|")));
			if(list1.equals(list2))
			{
				Mark = 1;
				return Mark;
			}
			else //enables multi-option analysis and scoring
			{
				int givenResponses = list2.size();	
				list2.retainAll(list1);
				int givenCorrectResponses = list2.size();
				if(givenResponses >= givenCorrectResponses) 
				{
					Mark = (float)givenCorrectResponses/givenResponses;
				}
				else if(givenResponses <= givenCorrectResponses)
				{
					Mark = (float)givenResponses/givenCorrectResponses;
				}
				return Mark;
			}
		}
		else //scoring function on key word analysis
		{
			List<String> listofKeywords = new java.util.ArrayList<>(Arrays.asList(CheckResponse.split("\\|")));
			List<String> listofWords = new java.util.ArrayList<>(Arrays.asList(Response.split(" ")));
			
				listofWords.retainAll(listofKeywords);
				int givenResponses = listofWords.size();
				int correctResponses = listofKeywords.size();
				Mark = givenResponses/correctResponses;
				return Mark;
			
		}
	}
	Questions displayQuestion(Questions q, Scanner P)
	{
		//String response = new String();
		System.out.println(q.Question);
		if(MCQ)
		{
			char i='a';
			for (String option : Options)
			{
				System.out.print(i+". ");
				System.out.println(option);
				i++;
			}
			System.out.println("Enter your response(for multiple responses seperate option with '|' without spaces between):");
			q.Response = P.nextLine();
			return q;
		}
		else
		{
			System.out.println("Your response: ");
			q.Response = P.nextLine();
			return q;
		}
		
	}
	Questions()
	{
		
	}

}




