package culim.ai;

import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import culim.ai.bot.RandBot;

/**
 * Factory class to create and return an AI-bot.
 * 
 * @author culim
 *
 */
public class AIFactory
{

	public static AIBot createRandBot(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) 
	{
		return new RandBot(stateObs, elapsedTimer);
	}
}
