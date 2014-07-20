package culim.ai.components;

import tools.Vector2d;

/**
 * The state for a q-learning system for the GVGAI competition.
 * @author culim
 *
 */
public class QLearningState
{
	public Vector2d avatarPosition;
	public double meanNpcDistance;
	
	public QLearningState(Vector2d avatarPosition, double meanNpcDistance)
	{
		this.avatarPosition = avatarPosition.copy();
		this.meanNpcDistance = meanNpcDistance;
	}

}
