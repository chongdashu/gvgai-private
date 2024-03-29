package controllers.human;

import java.util.TreeSet;

import ontology.Types;
import tools.ElapsedCpuTimer;
import tools.Utils;
import tools.Vector2d;
import core.game.Event;
import core.game.Game;
import core.game.StateObservation;
import core.player.AbstractPlayer;

/**
 * Created by diego on 06/02/14.
 */
public class Agent extends AbstractPlayer
{

    /**
     * Public constructor with state observation and time due.
     * @param so state observation of the current game.
     * @param elapsedTimer Timer for the controller creation.
     */
    public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer)
    {
    }


    /**
     * Picks an action. This function is called every game step to request an
     * action from the player.
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     * @return An action for the current state
     */
    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer)
    {
        Vector2d move = Utils.processMovementActionKeys(Game.ki.getMask());
        boolean useOn = Utils.processUseKey(Game.ki.getMask());

        //In the keycontroller, move has preference.
        Types.ACTIONS action = Types.ACTIONS.fromVector(move);
        
        Vector2d avatarPosition = stateObs.getAvatarPosition();
        TreeSet<Event> history = stateObs.getEventsHistory();
        
        System.out.println(String.format("[culim.Agent], avatarPosition=%s", avatarPosition));
        System.out.println(String.format("[culim.Agent], validActions=%s", stateObs.getAvailableActions()));

        if(action == Types.ACTIONS.ACTION_NIL && useOn)
            action = Types.ACTIONS.ACTION_USE;

        return action;
    }
}
