package culim.ai;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import ontology.Types.WINNER;
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

	/**
	 * Returns a random {@link Integer} between the numbers <code>low</code> (inclusive)
	 * and <code>high</code> (exclusive).
	 * 
	 * @param low lowest value (inclusive)
	 * @param high highest value (exclusive)
	 * @return random {@link Integer} between the numbers <code>low</code> (inclusive)
	 */
	public static int randomInt(int low, int high)
	{
		return (int) (low + high*Math.random()); 
	}
	
	/**
	 * Returns a random element from an {@link ArrayList}
	 * @param arrayList list of elements
	 * @return randomly selected element from the list
	 */
	public static <T> T randomElement(ArrayList<T> arrayList)
	{
		T element = null;
		
		if (arrayList != null)
		{
			if (!arrayList.isEmpty())
			{
				element = arrayList.get(randomInt(0, arrayList.size()));
			}
		}
		
		return element;
	}
	
	public static double getGenericReward(StateObservation stateObs)
	{
		// Winner
		if (stateObs.isGameOver())
		{
			double gameWinnerScore = 0;
			WINNER gameWinner = stateObs.getGameWinner();
			
			if (gameWinner == WINNER.PLAYER_WINS)
			{
				gameWinnerScore = 5000;
			}
			else if (gameWinner == WINNER.NO_WINNER)
			{
				gameWinnerScore = 0;
			}
			else if (gameWinner == WINNER.PLAYER_LOSES)
			{
				gameWinnerScore = -1000; 
			}
			else if (gameWinner == WINNER.PLAYER_DISQ)
			{
				gameWinnerScore = -9999;
			}
			
			return gameWinnerScore;
		}
		else
		{
			return stateObs.getGameScore();
		}
	}

}
