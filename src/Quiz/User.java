package Quiz;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;

public class User extends UserBase{
	User(Scanner S) throws IOException {
		//super.UB = super.readfromFileUB();
//		if(this.UB.UserList.isEmpty())
//		{
//			if(this.knowStudentorAdmin())
//			{
//				this.getStudentDetail(S);
//				super.addUser(this);
//				super.writeToFileUB();
//			}
//			else
//			{
//				this.getAdminDetail(S);
//				super.addUser(this);
//				super.writeToFileUB();
//			}
//		}
	}

	String Name;
	String Id;
	String Password;
	Boolean Type; //True for Student, False for Admin
	String filenameUB = "/home/sitharth/eclipse-workspace/SidzQuizApp/Files/UserBase.of";
	
	void takeQuiz(Quiz Q)
	{	Scanner S = new Scanner(System.in);
		Q.runQuiz(Q, S);		
	}
	boolean knowStudentorAdmin()
	{
		Scanner S = new Scanner(System.in);
		System.out.println("Are you a student?(Enter y/n): ");
		String in = S.nextLine();
		if(in.contentEquals("y") | in.contentEquals("Y") )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	void getStudentDetail(Scanner S)
	{
		System.out.println("Enter Name: ");
		this.Name = S.nextLine();
		System.out.println("Enter Id: ");
		this.Id = S.nextLine();		
		this.Type = true;
	}
	
	void getAdminDetail(Scanner S) throws IOException
	{
		System.out.println("Enter Name: ");
		this.Name = S.nextLine();
		System.out.println("Enter Id: ");
		this.Id = S.nextLine();
		System.out.println("Enter Password: ");
		this.Password = S.nextLine();
		this.Type = false;
		super.addUser(this);
		super.writeToFileUB();
	}
	
	boolean checkAdmin(User u) // for now the admin details are hardcoded
	{
		if(u.Name.equals("Admin") && u.Id.equals("AAA") && u.Password.contentEquals("ValidateAdmin"))
		{
			return true;			
		}
		else
		{
			return false;
		}
	}
	
	
}
