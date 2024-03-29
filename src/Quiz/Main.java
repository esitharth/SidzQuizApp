package Quiz;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException
	{
		Scanner S = new Scanner(System.in);
		Quiz Qz = new Quiz(S);
		User u = new User(S);
		String loginGate;
		do
		{
			System.out.println("-----------------------");
			System.out.println("SidzQuizApp \n"
					+ "For Admin Login press 'A' \n"
					+ "For Student Login press 'S' \n"
					+ "To Quit press 'Q'"); 			// for now the admin details are hardcoded
			System.out.println("-----------------------");
			System.out.println("Your input: ");
			loginGate = S.nextLine();
			if(loginGate.contains("S") | loginGate.contains("s")) // to enter Student portal
			{
				u.getStudentDetail(S);
				Qz.runQuiz(Qz.Q, S);
				
			}
			else if(loginGate.contains("A") | loginGate.contains("a")) // to enter Admin portal
			{
				u.getAdminDetail(S);
				if(u.checkAdmin(u))
				{
					String response;
					do
					{
						System.out.println("-----------------------");
						System.out.println("What do you want to do?");
						System.out.println("a. Create Question Pool");
						System.out.println("b. Create Quiz");
						System.out.println("c. Display Questions");
						System.out.println("d. Search Questions");
						System.out.println("e. Update Questions");
						System.out.println("f. Delete Questions");
						System.out.println("g. Quit");
						System.out.println("-----------------------");
						System.out.println("Enter your response: ");
						response = S.nextLine();
						
						switch(response)
						{
							case "a":
								Qz.createQuestionPool(S);
								break;
							case "b":
								Qz.createQuiz(S);
								break;
							case "c":
								Qz.displayAllQuestions(Qz.QuestionPool, S);
								break;
							case "d":
								Qz.searchQuestions(Qz.QuestionPool, S);
								break;
							case "e":
								Qz.updateQuestions(Qz.QuestionPool, S);
								Qz.writeToFileQB();
								break;
							case "f":
								Qz.deleteQuestions(Qz.QuestionPool, S);
								Qz.writeToFileQB();
						}
					}while(!response.contains("g"));							
				}
				else
				{
					System.out.println("Admin credentials mismatch!");
				}
			}
			else if(loginGate.contains("Q") | loginGate.contains("q"))
			{
				System.out.println("Have a nice day!");
				break;
			}
		}while(!(loginGate.contains("S") | loginGate.contains("s")|loginGate.contains("S") | loginGate.contains("s")));	
		S.close();
	}	
}
