package Quiz;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class UserBase{
	
	UserBase() throws IOException {
				
		super();
		// TODO Auto-generated constructor stub
		//this.UserList = readFile().UserList;
	}
	UserBase UB;
	List<User> UserList = new ArrayList<User>();
	String filenameUB = this.getClass().getResource("/files/UserBase.of").getPath();
	//"/home/sitharth/eclipse-workspace/SidzQuizApp/Files/UserBase.of";
	
	boolean checkUser(User u) 
	{
		for(User v : UserList)
		{
			if(u.equals(v))
			{
				return true;
			}
		}
		return false;
	}
	void addUser(User u) throws IOException
	{
		UserList.add(u);
		//writeToFile();
	}
	void writeToFileUB() throws IOException
	{
		
		FileWriter out = new FileWriter(this.filenameUB);
        XStream xs = new XStream();
        xs.toXML(this, out);
        out.close();
	}
	UserBase readfromFileUB() throws FileNotFoundException
	{
		FileReader in = new FileReader(this.filenameUB);
		XStream xs = new XStream();
		UserBase UB = (UserBase)xs.fromXML(in);
		if(UB != null)
		{
			return UB;
		}
		else
		{
			return null;
		}	
	}

}
