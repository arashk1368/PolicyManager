/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.policymanager.logic;

import cloudservices.brokerage.policy.policycommons.model.entities.Service;
import cloudservices.brokerage.policy.policycommons.model.entities.State;
import java.util.Set;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class RepairChecker {

    private final State goalState;
    private final State initialState;

    public RepairChecker(State initialState, State goalState) {
        this.initialState = initialState;
        this.goalState = goalState;
    }

    /**
     *
     * This method is not supported yet and just returns true.
     */
    public boolean checkRepair(Set<Service> actions, State currentState, State nextState) {
        return true;
    }
}
