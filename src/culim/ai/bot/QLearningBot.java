package culim.ai.bot;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import culim.ai.AIBot;
import culim.ai.components.QLearning;

public class QLearningBot extends AIBot
{
	QLearning qLearning;

	public QLearningBot(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		super(stateObs, elapsedTimer);
		qLearning = new QLearning();
	}

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		return null;
	}

	@Override
	public void teardown()
	{
		super.teardown();
	}
	
	

}
