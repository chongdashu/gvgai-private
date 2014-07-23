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
	public static final boolean DEBUG = false;
	
	public static void log(String s)
	{
		if (DEBUG)
		{
			System.out.println(s);
		}
	}
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
	
	@SuppressWarnings("unchecked")
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
	 * Calculates the average square distance of all  to the avatar.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanObservationSquareDistance(ArrayList<Observation>[] observationPositionsList)
	{
		double sumSquareDistance = 0;
		int count = 0;
		
		if (observationPositionsList == null)
		{
			return 0;
		}
		
		for (ArrayList<Observation> observationPositions : observationPositionsList)
		{
			for (Observation observation : observationPositions)
			{
				sumSquareDistance += observation.sqDist;
				count++;
			}
		}
		
		if (count == 0)
		{
			return 0;
		}
		
		
		return sumSquareDistance/count;
	}
	
	/**
	 * Calculates the distance of the closest NPC.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanClosestNPCDistance(StateObservation stateObs)
	{
		double sumSquareDistance = 0;
		int count = 0;
		
		ArrayList<Observation>[] observationPositionsList = stateObs.getNPCPositions();
		if (observationPositionsList == null)
		{
			return 0;
		}
		
		for (ArrayList<Observation> observationPositions : observationPositionsList )
		{
			if (observationPositions.size() > 0)
			{
				sumSquareDistance += observationPositions.get(0).sqDist;
				count++;
			}
		}
		
		if (count == 0)
		{
			return 0;
		}
		
		return sumSquareDistance/count;
	}
	
	/**
	 * Calculates the average square distance of all the resources to the avatar.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanResourceSquareDistance(StateObservation stateObs)
	{
		ArrayList<Observation>[] resourcePositions =  stateObs.getResourcesPositions(stateObs.getAvatarPosition());
		return getMeanObservationSquareDistance(resourcePositions);
	}
	
	/**
	 * Calculates the average square distance of all the NPCs to the avatar.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanNpcSquareDistance(StateObservation stateObs)
	{
		ArrayList<Observation>[] npcPositions =  stateObs.getNPCPositions(stateObs.getAvatarPosition());
		return getMeanObservationSquareDistance(npcPositions);
	}
	
	/**
	 * Calculates the average square distance of all the <code>Immovable</code> sprites to the avatar.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanImmovableSquareDistance(StateObservation stateObs)
	{
		ArrayList<Observation>[] immovablePositions =  stateObs.getImmovablePositions(stateObs.getAvatarPosition());
		return getMeanObservationSquareDistance(immovablePositions);
	}
	
	/**
	 * Calculates the average square distance of all the <code>Movable</code> sprites to the avatar.
	 * 
	 * @param stateObs the {@link StateObservation} object
	 * @return the calculated average square distance from the avatar to all NPCs.
	 */
	public static double getMeanMovableSquareDistance(StateObservation stateObs)
	{
		ArrayList<Observation>[] movablePositions =  stateObs.getMovablePositions(stateObs.getAvatarPosition());
		return getMeanObservationSquareDistance(movablePositions);
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
	
}
