package culim.ai.components;

import core.game.StateObservation;
import culim.ai.AIUtils;
import tools.Vector2d;

/**
 * The state for a q-learning system for the GVGAI competition.
 * @author culim
 *
 */
public class QLearningState
{
	public StateObservation stateObs;
	public Vector2d avatarPosition;
	public double meanNpcDistance;
	public double meanResourceDistance;
	
	public QLearningState(StateObservation stateObs)
	{
		this.stateObs = stateObs;
		this.avatarPosition = stateObs.getAvatarPosition();
		this.meanNpcDistance = AIUtils.getMeanNpcSquareDistance(stateObs);
		this.meanResourceDistance = AIUtils.getMeanResourceSquareDistance(stateObs);
	}
	
	public boolean isGoal()
	{
		return stateObs.isGameOver();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		QLearningState state;
		if (obj instanceof QLearningState)
		{
			state = (QLearningState) obj;
			return this.avatarPosition.equals(state.avatarPosition) &&
					this.meanNpcDistance == state.meanNpcDistance;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return 0;
	}
	
	@Override
	public String toString()
	{
		return String.format("QState(%s,\t%s)", this.avatarPosition, this.meanNpcDistance);
	}

}
