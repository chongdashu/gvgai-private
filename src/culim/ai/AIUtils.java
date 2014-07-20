package culim.ai;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;

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
	
	/**
	 * Calculates the average square distance of all the NPCs to the avatar.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanNpcSquareDistance(StateObservation stateObs)
	{
		ArrayList<Observation>[] npcPositions =  stateObs.getNPCPositions();
		Observation obs = null;
		
		double sumSquareDistance = 0;
		int count = 0;
		
		for (ArrayList<Observation> npcs : npcPositions)
		{
			for (Observation observation : npcs)
			{
				sumSquareDistance += observation.sqDist;
				count++;
			}
		}
		
		return sumSquareDistance/count;
	}

}
