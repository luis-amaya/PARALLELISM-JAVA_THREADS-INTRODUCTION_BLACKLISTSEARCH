/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, Integer numberOfThreads){
        
        int ocurrencesCount=0;
        int checkedListsCount=0;
        int range, firstServer, lastServer;
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        range = skds.getRegisteredServersCount()/numberOfThreads;
        LinkedList<BlackListThreadValidator> blackListThreadValidator = new LinkedList<>();
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
        for(int i=0; i< numberOfThreads; i++){
            firstServer = range*i;
            lastServer = range*(i+1);

            blackListThreadValidator.add(new BlackListThreadValidator(ipaddress, firstServer, lastServer, skds));
        }

        for(BlackListThreadValidator thread:blackListThreadValidator){
            thread.start();
        }

        for(BlackListThreadValidator thread:blackListThreadValidator){
            try {
                thread.join();
                ocurrencesCount += thread.getOcurrencesCount();
                checkedListsCount += thread.getCheckedListCount();

                blackListOcurrences.addAll(thread.getServers());
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
