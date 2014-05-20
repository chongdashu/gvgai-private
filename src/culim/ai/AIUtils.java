package culim.ai;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AIUtils
{	
	public static void write(Object obj, String filename)
	{
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream(filename);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(obj);
	         out.close();
	         fileOut.close();
	         System.out.println(String.format("Serialized data is saved in %s", filename));
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public static <T> T read(String filename)
	{
		T ret = null;
		  try
		  {
		     FileInputStream fileIn = new FileInputStream(filename);
		     ObjectInputStream in = new ObjectInputStream(fileIn);
		     ret = (T) in.readObject();
		     in.close();
		     fileIn.close();
		     return ret;
		  }catch(IOException i)
		  {
		     i.printStackTrace();
		     return ret;
		  }catch(ClassNotFoundException c)
		  {
		     System.out.println("Employee class not found");
		     c.printStackTrace();
		     return ret;
		  }
	}

}
