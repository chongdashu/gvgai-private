package culim.ai.components;

import java.util.HashMap;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import culim.ai.AIBot;
import culim.ai.bot.QLearningBot;
import culim.ai.components.LegacyQLearning.QState;

public class QLearning
{
	public HashMap<QLearningState, HashMap<QLearningAction, Double>> qTable;
	public double gamma;	// discount
	public double alpha;	// step size
	
	public QLearning()
	{
		/**
		 * Algorithm
		 * ---------
		 * 1. Set the gamma parameter
		 * 2. Set environment rewards in matrix R
		 * 3. Initialize qTable to all zeroes
		 */
	}
	
	/**
	 * Called directly by the method {@link QLearningBot #act(StateObservation, ElapsedCpuTimer)}.
	 * This is when the {@link QLearningBot} is given a period of time to decide on its next action.
	 * <p>
	 * Here we try to update the {@link QLearning #qTable} as much as possible. We then
	 * try to return the best possible move by looking at the qTable.
	 * <p>
	 * e.g.,
	 * The chosen best action would be done by looking at {@link QLearning #qTable}[state] and
	 * returning the {@link QLearningAction} that gives the highest reward score.
	 * 
	 * @param stateObs the given state 
	 * @param remainingTimeMsec remaining time in milliseconds
	 */
	public void run(QLearningState state, long remainingTimeMsec)
	{
		/**
		 * 1. Select random initial state S.
		 * 2. Do while goal hasn't been reached
		 * 	a) Select on among all possible actions for current state S, an action A.
		 *  b) Simulate A on S to get us to S'
		 *  c) Get maximum Q-value for (S', A) (Qmax)
		 *  d) Compute Q(S,A) = R(S,A) + gamma + Qmax
		 *  e) Set S = S'
		 */
	}
	
	/**
	 * Returns the {@link QLearningAction} with the highest value in the {@link QLearning #qTable}.
	 * 
	 * @param state the state as the index to the q-table
	 * @return the action with the higest value for the given state
	 */
	public QLearningAction getBestAction(QLearningState state)
	{
		return null;
	}
}
