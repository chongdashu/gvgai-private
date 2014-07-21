package culim.ai.bot;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;
import core.game.StateObservation;
import culim.ai.AIBot;
import culim.ai.AIUtils;
import culim.ai.components.LegacyQLearning;
import culim.ai.components.QLearning;
import culim.ai.components.QLearningAction;
import culim.ai.components.QLearningState;

public class QLearningBot extends AIBot
{
	LegacyQLearning legacyLearning;
	QLearning qLearning;

	public QLearningBot(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
		legacyLearning = new LegacyQLearning();
		qLearning = new QLearning();
	}

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		// Aim:
		// ----
		// To update the agent's Q-table, for which given a state `s', we are able to
		// select the best action 'a' based on its value in the Q-table.
		// e.g., 	Given state s1:
		//			If Q[s1,a1]=10, Q[s1,a2]=20, Q[s1,a3]=30,
		//			Then select action `a3' in order to maximize chance to reaching goal.
		
		// Actions:
		// --------
		// See ACTIONS
		// 1) UP
		// 2) DOWN
		// 3) LEF 
		// 4) RIGHT
		// 5) USE
		// 6) ESCAPE
		// 7) NIL
		
		QLearningState state = createState(stateObs);
		while (elapsedTimer.remainingTimeMillis() > 10)
		{
			qLearning.run(state, elapsedTimer, 5);
		}
		QLearningAction action = qLearning.getBestAction(state);
		return getAction(action);
	}
	
	public static ACTIONS getAction(QLearningAction action)
	{
		return action.value();
	}
	
	public static QLearningState createState(StateObservation stateObs)
	{
		return new QLearningState(stateObs);
	}
}
