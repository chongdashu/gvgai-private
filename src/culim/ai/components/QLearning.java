package culim.ai.components;

import java.util.ArrayList;
import java.util.HashMap;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import culim.ai.AIUtils;
import culim.ai.bot.QLearningBot;

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
		
		qTable = new HashMap<QLearningState, HashMap<QLearningAction, Double>>();
		gamma = 0.5;
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
	 * @param stateObs the {@link StateObservation} object. 
	 * @param elapsedTimer the {@link ElapsedCpuTimer} object.
	 * @param depth the depth of the search
	 */
	public void run(QLearningState state, ElapsedCpuTimer elapsedTimer, int depth)
	{
		/**
		 * 1. Select random initial state S.
		 * 2. Do while goal hasn't been reached
		 * 	a) Select on among all possible actions for current state S, an action A.
		 *  b) Simulate A on S to get us to S'
		 *  c) Get maximum Q-value for (S', A'), for all possible actions A' (qMax) 
		 *  d) Compute Q(S,A) = R(S,A) + gamma + Qmax
		 *  e) Set S = S'
		 */
		
		QLearningState currentState = new QLearningState(state.stateObs.copy());
		
		for (int i=0; i < depth && isRunPossible(currentState, elapsedTimer); i++)
		{
			// 1. Select random action for currentState.
			ACTIONS action = AIUtils.randomElement(currentState.stateObs.getAvailableActions());
			
			// 2. Simulate action on currentState to get nextState.
			QLearningState nextState = new QLearningState(currentState.stateObs.copy());
			nextState.stateObs.advance(action);
			
			// 3. Get maximum Q-value for nextState 
			double qMax = getQMaxForState(nextState);
			
			// 4. Update q-table entry for <currentState, action>
			double currentValue = getQValueForStateAction(state, new QLearningAction(action));
			double reward = QLearningReward.getReward(nextState);
			double updatedValue = reward + gamma * qMax;
			
			// 5. Set S = S'
			currentState = nextState;
		}
		
	}
	
	private boolean isRunPossible(QLearningState state, ElapsedCpuTimer elapsedTimer)
	{
		return !state.isGoal() && elapsedTimer.remainingTimeMillis() >= 10;
	}
	
	public HashMap<QLearningAction, Double> getStateActionValueMap(QLearningState state)
	{
		HashMap<QLearningAction, Double> actionValueMapping = qTable.get(state);
		if (actionValueMapping == null)
		{
			// This state doesn't yet exist in the q-table.
			// Create and initialize a new HashMap<Action,Value> for this state.
			actionValueMapping = new HashMap<QLearningAction, Double>();
			ACTIONS[] allActions = ACTIONS.values();
			for (ACTIONS action : allActions)
			{
				actionValueMapping.put(new QLearningAction(action), 0d);
				qTable.put(state, actionValueMapping);
			}
		}
		
		return actionValueMapping;
	}
	
	public double getQValueForStateAction(QLearningState state, QLearningAction action)
	{
		HashMap<QLearningAction, Double> stateQValues = getStateActionValueMap(state);
		return stateQValues.get(action);
	}
	
	public double getQMaxForState(QLearningState state)
	{
		HashMap<QLearningAction, Double> stateQValues = getStateActionValueMap(state);
		
		ArrayList<QLearningAction> maxActions = new ArrayList<QLearningAction>();
		double  maxValue = -99999999;
		
		for (QLearningAction action : stateQValues.keySet())
		{
			double currentValue = stateQValues.get(action);
			if (currentValue > maxValue)
			{
				maxActions.add(action);
				maxValue = currentValue;
			}
		}
		
		return maxValue;
	}

	/**
	 * Returns the {@link QLearningAction} with the highest value in the {@link QLearning #qTable}.
	 * 
	 * @param state the state as the index to the q-table
	 * @return the action with the higest value for the given state
	 */
	public QLearningAction getBestAction(QLearningState state)
	{
		ArrayList<QLearningAction> bestQActions = getBestActions(state);
		
		if (bestQActions.contains(QLearningAction.NIL))
		{
			bestQActions.remove(QLearningAction.NIL);
		}
		
		if (bestQActions.contains(QLearningAction.ESCAPE))
		{
			bestQActions.remove(QLearningAction.ESCAPE);
		}
		
		QLearningAction bestAction = QLearningAction.NIL;
		
		if (bestQActions.size() > 0)
		{
			bestAction =  AIUtils.randomElement(bestQActions);
		}
		
		return bestAction;
	}
	
	public ArrayList<QLearningAction> getBestActions(QLearningState state)
	{
		HashMap<QLearningAction, Double> stateQValues = getStateActionValueMap(state);
		
		ArrayList<QLearningAction> maxActions = new ArrayList<QLearningAction>();
		double  maxValue = -99999999;
		
		for (QLearningAction action : stateQValues.keySet())
		{
			double currentValue = stateQValues.get(action);
			if (currentValue > maxValue)
			{
				maxActions.add(action);
				maxValue = currentValue;
			}
		}
		
		return maxActions;
	}
}
