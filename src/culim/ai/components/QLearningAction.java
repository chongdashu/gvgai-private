package culim.ai.components;

import ontology.Types.ACTIONS;

/**
 * The action for a q-learning system for the GVGAI competition.
 * @author culim
 *
 */
public class QLearningAction
{
	public ACTIONS action;
	
	public static final QLearningAction UP = new QLearningAction(ACTIONS.ACTION_UP);
	public static final QLearningAction DOWN = new QLearningAction(ACTIONS.ACTION_DOWN);
	public static final QLearningAction LEFT = new QLearningAction(ACTIONS.ACTION_LEFT);
	public static final QLearningAction RIGHT = new QLearningAction(ACTIONS.ACTION_RIGHT);
	public static final QLearningAction USE = new QLearningAction(ACTIONS.ACTION_USE);
	public static final QLearningAction ESCAPE = new QLearningAction(ACTIONS.ACTION_ESCAPE);
	public static final QLearningAction NIL = new QLearningAction(ACTIONS.ACTION_UP);
	
	public QLearningAction(ACTIONS action)
	{
		this.action = action;
	}
	
	public ACTIONS value()
	{
		return this.action;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof QLearningAction)
		{
			QLearningAction qAction = (QLearningAction) obj; 
			return this.action == qAction.action;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return 1;
	}
}
