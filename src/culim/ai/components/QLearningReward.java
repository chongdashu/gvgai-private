package culim.ai.components;

import culim.ai.AIUtils;
import ontology.Types.WINNER;

public class QLearningReward
{
	public static double getReward(QLearningState state)
	{
		// Get the reward for a given q-learning state.
		return AIUtils.getGenericReward(state.stateObs);
	}

}
