package culim.ai.components;

import java.io.Serializable;
import java.util.TreeSet;

import core.game.Event;
import core.game.StateObservation;
import culim.ai.AIUtils;
import tools.Vector2d;

/**
 * The state for a q-learning system for the GVGAI competition.
 * @author culim
 *
 */
public class QLearningState implements Serializable
{
	public Vector2d avatarPosition;
	public double meanNpcDistance;
	public double meanResourceDistance;
	public boolean isGameOver;
	public int worldWidth;
	public int worldHeight;
	public int blockSize;
	public int gridWidth;
	public int gridHeight;
	public TreeSet<Event> eventsHistory;
	public double meanMovableDistances;
	public double meanImmovableDistances;
	public double winScore;
	public double gameScore;
	public double meanClosestNPCDistance;
	public double genericReward;
	public int meanClosestNPCManhattan;
	public Vector2d nearestNPCGridPosition;
	public Vector2d avatarGridPosition;
	public int nRemainingNPCs;
	
	public QLearningState(StateObservation stateObs)
	{
		this.isGameOver = stateObs.isGameOver();
		this.avatarPosition = stateObs.getAvatarPosition();
		this.avatarGridPosition = AIUtils.getGridPosition(stateObs, stateObs.getAvatarPosition());
		this.nearestNPCGridPosition = AIUtils.getNearestNPCGridPosition(stateObs);
		this.meanNpcDistance = AIUtils.getMeanNpcSquareDistance(stateObs);
		this.meanResourceDistance = AIUtils.getMeanResourceSquareDistance(stateObs);
		this.meanMovableDistances = AIUtils.getMeanMovableSquareDistance(stateObs);
		this.meanImmovableDistances = AIUtils.getMeanImmovableSquareDistance(stateObs);
		this.meanClosestNPCDistance = AIUtils.getMeanClosestNPCDistance(stateObs);
		this.meanClosestNPCManhattan = AIUtils.getNearestNPCManhattanDistance(stateObs);
		this.blockSize = stateObs.getBlockSize();
		this.worldWidth = stateObs.getWorldDimension().width;
		this.worldHeight = stateObs.getWorldDimension().height;
		this.gridWidth = this.worldWidth/this.blockSize;
		this.gridHeight = this.worldHeight/this.blockSize;
		this.winScore = QLearningReward.getGameOverScore(stateObs);
		this.gameScore = QLearningReward.getGameScore(stateObs);
		this.genericReward = QLearningReward.getGenericReward(stateObs);
		this.nRemainingNPCs = AIUtils.getRemainingNPCs(stateObs);
		// this.eventsHistory = stateObs.getEventsHistory();
	}
	
	public boolean isGoal()
	{
		return isGameOver;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		QLearningState state;
		if (obj instanceof QLearningState)
		{
			state = (QLearningState) obj;
			return 	this.avatarPosition.equals(state.avatarPosition) &&
					this.meanNpcDistance == state.meanNpcDistance && 
					this.meanMovableDistances == state.meanMovableDistances &&
					this.meanImmovableDistances == state.meanImmovableDistances &&
					this.winScore == state.winScore &&
					this.gameScore == state.gameScore &&
					this.genericReward == state.genericReward &&
					this.meanClosestNPCDistance == state.meanClosestNPCDistance &&
					this.meanClosestNPCManhattan == state.meanClosestNPCManhattan && 
					this.nearestNPCGridPosition.equals(state.nearestNPCGridPosition) &&
					this.nRemainingNPCs == state.nRemainingNPCs; 
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
		return String.format("QState A[%s]\tNPC[%s]\tnRemainingNPCs=%s)", this.avatarGridPosition, this.nearestNPCGridPosition, this.nRemainingNPCs );
	}

}
