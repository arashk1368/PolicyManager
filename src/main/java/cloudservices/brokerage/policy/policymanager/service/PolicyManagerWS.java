/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.policymanager.service;

import cloudservices.brokerage.commons.utils.file_utils.ResourceFileUtil;
import cloudservices.brokerage.commons.utils.logging.LoggerSetup;
import cloudservices.brokerage.policy.policycommons.model.DAO.BaseDAO;
import cloudservices.brokerage.policy.policycommons.model.DAO.DAOException;
import cloudservices.brokerage.policy.policycommons.model.entities.Policy;
import cloudservices.brokerage.policy.policycommons.model.entities.Service;
import cloudservices.brokerage.policy.policycommons.model.entities.State;
import cloudservices.brokerage.policy.policymanager.logic.PolicyApplier;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@WebService(serviceName = "PolicyManagerWS")
public class PolicyManagerWS {

    private final static Logger LOGGER = Logger.getLogger(PolicyManagerWS.class
            .getName());

    private void setupLoggers() throws IOException {
        LoggerSetup.setup(ResourceFileUtil.getResourcePath("log.txt"), ResourceFileUtil.getResourcePath("log.html"));
        LoggerSetup.log4jSetup(ResourceFileUtil.getResourcePath("log4j.properties"),
                ResourceFileUtil.getResourcePath("hibernate.log"));
    }

    @WebMethod(operationName = "applyPolicy")
    public Service applyPolicy(@WebParam(name = "applicablePolicies") Set<Policy> applicablePolicies,
            @WebParam(name = "currentServiceLevel") String currentServiceLevel,
            @WebParam(name = "currentState") State currentState,
            @WebParam(name = "nextState") State nextState,
            @WebParam(name = "initialState") State initialState,
            @WebParam(name = "goalState") State goalState)
            throws IOException, DAOException {
        setupLoggers();

        if (applicablePolicies == null) {
            throw new IllegalArgumentException("Applicable Policies can not be null");
        } else if (applicablePolicies.isEmpty()) {
            Service temp=new Service();
            temp.setServicesStr(currentServiceLevel);
            return temp;
        }
        if (currentServiceLevel == null) {
            throw new IllegalArgumentException("Current Service Level can not be null");
        }
        if (currentState != null) {
            throw new IllegalArgumentException("Current state can not be null");
        }
        if (nextState != null) {
            throw new IllegalArgumentException("Next state can not be null");
        }
        if (initialState == null) {
            throw new IllegalArgumentException("Initial state can not be null");
        }
        if (goalState == null) {
            throw new IllegalArgumentException("Goal state can not be null");
        }
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            BaseDAO.openSession(configuration);
            PolicyApplier policyApplier = new PolicyApplier(initialState, goalState);
            return policyApplier.apply(applicablePolicies, currentServiceLevel, currentState, nextState);
        } finally {
            BaseDAO.closeSession();
        }
    }
}
