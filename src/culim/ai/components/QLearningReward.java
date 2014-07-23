package culim.ai.components;

import core.game.StateObservation;
import culim.ai.AIUtils;
import ontology.Types.WINNER;

public class QLearningReward
{
	
	public static double getReward(QLearningState state)
	{
		float width =  state.gridWidth;
		float height =  state.gridHeight;
		double max = (width*height)*(width*height);
		
		// Get the reward for a given q-learning state.
		double reward = 
						+ 1.0 *state.winScore
						+ 0.5 * state.gameScore
						+ 0.15 * (1-state.meanNpcDistance/max)
						+ 0.45 * (state.meanMovableDistances/max)
						+ 0.30 * (state.meanClosestNPCDistance)
						;
						
		
		return reward;
		
	}
	
	public static double getGameScore(StateObservation stateObs)
	{
		return stateObs.getGameScore()/25;
	}
	
	public static double getGameOverScore(StateObservation stateObs)
	{
		double gameWinnerScore = 0;
		WINNER gameWinner = stateObs.getGameWinner();
		
		if (gameWinner == WINNER.PLAYER_WINS)
		{
			gameWinnerScore = 5;
		}
		else if (gameWinner == WINNER.NO_WINNER)
		{
			gameWinnerScore = 0;
		}
		else if (gameWinner == WINNER.PLAYER_LOSES)
		{
			gameWinnerScore = -5; 
		}
		else if (gameWinner == WINNER.PLAYER_DISQ)
		{
			gameWinnerScore = -10;
		}
		
		return gameWinnerScore;
	}

}
