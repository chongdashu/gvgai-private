package culim.ai.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import culim.ai.AIUtils;
import culim.ai.bot.QLearningBot;

public class QLearning implements Serializable
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
		gamma = 1;	// 0: immediate rewards, 1: later rewards
		alpha = 0.3;	// 0: easy to unlearn 1: harder to unlearn
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
	public void run(StateObservation stateObs, ElapsedCpuTimer elapsedTimer, int depth)
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
		
		QLearningState currentState;
		StateObservation currentStateObs = stateObs;
		
		for (int i=0; i < depth && isRunPossible(stateObs, elapsedTimer); i++)
		{
			currentState = new QLearningState(stateObs);
			
			// 1. Select random action for currentState.
			ACTIONS action = AIUtils.randomElement(currentStateObs.getAvailableActions());
			
//			AIUtils.log(String.format("randomAction=%s", action));
			
			// 2. Simulate action on currentState to get nextState.
			stateObs.advance(action);
			QLearningState nextState = new QLearningState(stateObs);
			
			// 3. Get maximum Q-value for nextState 
			double qMax = getQMaxForState(nextState);
			
			// 4. Update q-table entry for <currentState, action>
			double currentValue = getQValueForStateAction(currentState, new QLearningAction(action));
			double reward = QLearningReward.getReward(nextState);
			
//			double updatedValue = reward + gamma * qMax;
			double updatedValue = currentValue +  alpha * (reward + gamma * qMax - currentValue);
			
			putQValueForStateAction(currentState, QLearningAction.fromAction(action), updatedValue);
			
			AIUtils.log(String.format("[Reward], qState=%s\taction=%s\treward=%s", currentState, action, reward));
			// System.out.println(String.format("QTable[%s]=%s", currentState, updatedValue));
			
			// 5. Set S = S'
			// Note: stateObs has already been advanced. 
			// currentState = nextState;
		}
		
	}
	
	private boolean isRunPossible(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		return !stateObs.isGameOver() && elapsedTimer.remainingTimeMillis() >= 10;
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
	
	public void putQValueForStateAction(QLearningState state, QLearningAction action, double value)
	{
		getStateActionValueMap(state).put(action, value);
	}
	
	public double getQMaxForState(QLearningState state)
	{
		HashMap<QLearningAction, Double> actionValueMap = getStateActionValueMap(state);
		
		ArrayList<QLearningAction> maxActions = new ArrayList<QLearningAction>();
		double  maxValue = -99999999;
		
		Set<QLearningAction> actionSet = actionValueMap.keySet();
		for (QLearningAction action : actionSet)
		{
			// Get max value
			double currentValue = actionValueMap.get(action);
			if (currentValue >= maxValue)
			{
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
//			if (bestQActions.contains(QLearningAction.USE))
//			{
//				bestAction = QLearningAction.USE;
//			}
//			else
//			{
				bestAction =  AIUtils.randomElement(bestQActions);
//			}
		}
		
		return bestAction;
	}
	
	public ArrayList<QLearningAction> getBestActions(QLearningState state)
	{
		HashMap<QLearningAction, Double> stateQValues = getStateActionValueMap(state);
		double qMax = getQMaxForState(state);
		
		ArrayList<QLearningAction> maxActions = new ArrayList<QLearningAction>();
		
		for (QLearningAction action : stateQValues.keySet())
		{
			double currentValue = stateQValues.get(action);
			if (currentValue == qMax)
			{
				maxActions.add(action);
			}
		}
		
		return maxActions;
	}
}
