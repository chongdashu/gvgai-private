package culim.ai.components;

import java.util.HashMap;

import core.game.StateObservation;

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
	
	public static class QState extends Object
	{
		public float n;
		
		public QState()
		{
			
		}

		public QState(float n)
		{
			this.n = n;
		}
		
		public QState(StateObservation stateObservation)
		{
			
		}
		
		
		public QState act(QAction prevAction)
		{
			return new QState();
			
		}
		
		public float reward()
		{
			return 0.0f;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof QState)
			{
				QState qstate = (QState)(obj);
				return this.n == qstate.n;
			}
			
			return false;
		}
		

		
	}

	public static class QAction extends Object
	{
		public int m;
		
		public QAction(int m)
		{
			this.m = m;
		}
		
		public boolean equals(Object obj)
		{

			if (obj instanceof QAction)
			{
				QAction qAction = (QAction)(obj);
				return this.m == qAction.m;
			}
			
			return false;
		}
	}

	public static class QStateActionPair extends Object
	{
		public QState state;
		public QAction action;
		
		public QStateActionPair(QState state, QAction action)
		{
			this.state = state;
			this.action = action;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof QStateActionPair)
			{
				QStateActionPair qPair = (QStateActionPair)(obj);
				return this.state.equals(qPair.state) && this.action.equals(qPair.action);
			}
			
			return false;
		}
		
		@Override 
		public int hashCode()
		{
			return 0;
		}
	}

}


