package culim.ai.components;

import java.util.HashMap;

public class QLearning
{
	public HashMap<QStateActionPair, Float> qRewardMap;
	public float discount;
	public float stepSize;
	
	public QState prevState;
	public QAction prevAction;
	
	public QLearning()
	{
		qRewardMap = new HashMap<QStateActionPair,Float>();
		discount = 0.9f;
		stepSize = 0.2f;
	}
	
	public void init(QState state, QAction action)
	{
		prevState = state;
		prevAction = action;
	}
	
	public void step()
	{
		// 1. Select an action a.
		// 2. Act out the action a.
		// 3. Calculate reward r.
		// 4. Get next state s'
		// 5. Update hash-table
		
		QState nextState = prevState.act(prevAction);
		float reward = nextState.reward();
		
	}

}

class QState
{

	public QState act(QAction prevAction)
	{
		return new QState();
		
	}
	
	public float reward()
	{
		return 0.0f;
	}
	
}

class QAction
{
	
}

class QStateActionPair
{
	public QState state;
	public QAction action;
	
	public QStateActionPair(QState state, QAction action)
	{
		this.state = state;
		this.action = action;
	}
}
