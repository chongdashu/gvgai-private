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
	
	public QLearningAction(ACTIONS action)
	{
		this.action = action;
	}
	
	public ACTIONS value()
	{
		return this.action;
	}
}
