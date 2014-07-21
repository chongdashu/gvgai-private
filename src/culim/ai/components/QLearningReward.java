package culim.ai.components;

import culim.ai.AIUtils;
import ontology.Types.WINNER;

public class QLearningReward
{
	
	public static double getReward(QLearningState state)
	{
		float width =  state.stateObs.getWorldDimension().width/state.stateObs.getBlockSize();
		float height =  state.stateObs.getWorldDimension().height/state.stateObs.getBlockSize();
		double max = (width*height)*(width*height);
		
		// Get the reward for a given q-learning state.
		return AIUtils.getGenericReward(state.stateObs) + 
				((state.meanNpcDistance/(max)));
	}

}
