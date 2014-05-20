package culim.ai.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import culim.ai.components.QLearning;
import culim.ai.components.QLearning.QAction;
import culim.ai.components.QLearning.QState;
import culim.ai.components.QLearning.QStateActionPair;

public class AITest
{
	public AITest()
	{
		QLearning qLearning = new QLearning();
		
		QState state1 = new QState(5.0f);
		QAction action1 = new QAction(2);
		QStateActionPair pair1 = new QStateActionPair(state1, action1);
		
		QState state2 = new QState(5.0f);
		QAction action2 = new QAction(2);
		QStateActionPair pair2 = new QStateActionPair(state2, action2);
		
		QState state3 = new QState(15.0f);
		QAction action3 = new QAction(2);
		QStateActionPair pair3 = new QStateActionPair(state3, action3);
		
		qLearning.qRewardMap.put(pair1, 10.0f);
		qLearning.qRewardMap.put(pair2, 15.0f);
		qLearning.qRewardMap.put(pair3, 1.0f);
		
		write(state1, "ser/state1.ser");
		QState state4 = read("ser/state1.ser");
		
		write(action1, "ser/action1.ser");
		QAction action4 = read("ser/action1.ser");
		
		write(pair1, "ser/pair1.ser");
		QStateActionPair pair4 = read("ser/pair1.ser");
		
		write(qLearning.qRewardMap, "ser/qRewardMap.ser");
		HashMap<QStateActionPair, Float> qRewardMap1 = read("ser/qRewardMap.ser");
		
		System.out.println(String.format("Test1: state1.equals(state2) | %s", boolString(state1.equals(state2))));
		System.out.println(String.format("Test2: !state1.equals(state3) | %s", boolString(!state1.equals(state3))));
		System.out.println(String.format("Test3: !state2.equals(state3) | %s", boolString(!state2.equals(state3))));
		System.out.println(String.format("Test4: pair1.equals(pair2) | %s", boolString(pair1.equals(pair2))));
		System.out.println(String.format("Test5: qLearning.qRewardMap.get(pair3) == 1.0f) | %s", boolString(qLearning.qRewardMap.get(pair3) == 1.0f)));
		System.out.println(String.format("Test6: qLearning.qRewardMap.get(pair2) == 15.0f) | %s", boolString(qLearning.qRewardMap.get(pair2) == 15.0f)));
		System.out.println(String.format("Test7: qLearning.qRewardMap.get(pair1) == 15.0f) | %s", boolString(qLearning.qRewardMap.get(pair1) == 15.0f)));
		System.out.println(String.format("Test8: state1.equals(state4) | %s", boolString(state1.equals(state4))));
		System.out.println(String.format("Test9: action1.equals(action4) | %s", boolString(action1.equals(action4))));
		System.out.println(String.format("Test10: pair1.equals(pair4) | %s", boolString(pair1.equals(pair4))));
		System.out.println(String.format("Test11: qRewardMap1.get(pair4) == 15.0f | %s", boolString(qLearning.qRewardMap.get(pair4) == 15.0f)));
		
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
	
	public static String boolString(boolean pass)
	{
		return pass ? "Passed!" : "Failed.";
	}

	public static void main(String[] args)
	{
		new AITest();
	}

}
