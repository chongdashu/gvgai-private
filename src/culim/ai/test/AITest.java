package culim.ai.test;

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
		
		System.out.println(String.format("Test1: state1.equals(state2) | %s", boolString(state1.equals(state2))));
		System.out.println(String.format("Test2: !state1.equals(state3) | %s", boolString(!state1.equals(state3))));
		System.out.println(String.format("Test3: !state2.equals(state3) | %s", boolString(!state2.equals(state3))));
		System.out.println(String.format("Test4: pair1.equals(pair2) | %s", boolString(pair1.equals(pair2))));
		System.out.println(String.format("Test5: qLearning.qRewardMap.get(pair3) == 1.0f) | %s", boolString(qLearning.qRewardMap.get(pair3) == 1.0f)));
		System.out.println(String.format("Test6: qLearning.qRewardMap.get(pair2) == 15.0f) | %s", boolString(qLearning.qRewardMap.get(pair2) == 15.0f)));
		System.out.println(String.format("Test7: qLearning.qRewardMap.get(pair1) == 15.0f) | %s", boolString(qLearning.qRewardMap.get(pair1) == 15.0f)));
		
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
