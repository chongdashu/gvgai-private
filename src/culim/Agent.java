/**
 * 
 */
package culim;

import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import culim.ai.AIBot;
import culim.ai.AIFactory;

/**
 * @author culim
 *
 */
public class Agent extends AbstractPlayer {
	public AIBot bot;
	
	public Agent(StateObservation stateObservation, ElapsedCpuTimer timer) {
		bot = AIFactory.createRandBot(stateObservation, timer);
	}

	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
	{
		return bot.act(stateObs, elapsedTimer);
	}


}
