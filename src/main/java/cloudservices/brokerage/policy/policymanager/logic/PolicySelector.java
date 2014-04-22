/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.policymanager.logic;

import cloudservices.brokerage.policy.policycommons.model.entities.Policy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class PolicySelector {

    private final List<Policy> sortedPolicies;
    private final Iterator<Policy> iterator;

    public PolicySelector(Set<Policy> applicablePolicies) {
        this.sortedPolicies = new ArrayList<>(applicablePolicies);
        this.iterator = this.sortedPolicies.iterator();
        this.sort();
    }

    public final void sort() {
        Collections.sort(this.sortedPolicies);
    }

    public Iterator<Policy> getIterator() {
        return iterator;
    }
    
}
