/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.policymanager.logic;

import cloudservices.brokerage.policy.policycommons.model.DAO.DAOException;
import cloudservices.brokerage.policy.policycommons.model.DAO.PolicyDAO;
import cloudservices.brokerage.policy.policycommons.model.entities.Policy;
import cloudservices.brokerage.policy.policycommons.model.entities.PolicyService;
import cloudservices.brokerage.policy.policycommons.model.entities.Service;
import cloudservices.brokerage.policy.policycommons.model.entities.State;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class PolicyApplier {
    
    private final PolicyDAO policyDAO;
    private final RepairChecker repairChecker;
    
    public PolicyApplier(State initialState, State goalState) {
        this.policyDAO = new PolicyDAO();
        this.repairChecker = new RepairChecker(initialState, goalState);
    }
    
    public Service apply(Set<Policy> applicablePolicies,
            String currentServiceLevel, State currentState, State nextState)
            throws DAOException {
        Service applied = new Service();
        applied.setServicesStr(currentServiceLevel);
        PolicySelector policySelector = new PolicySelector(applicablePolicies);
        for (Iterator<Policy> it = policySelector.getIterator(); it.hasNext();) {
            Policy policy = it.next();
            policyDAO.load(policy, policy.getId());
            Set<Service> actions = new HashSet<>();
            for (PolicyService ps : policy.getPolicyServices()) {
                actions.add(ps.getService());
            }
            if (repairChecker.checkRepair(actions, currentState, nextState)) {
                applied = new Service();
                applied.addServiceLevel(actions);
                break;
            }
        }
        return applied;
    }
}
