package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class BlackListThreadValidator extends Thread{
    private final String ipAddress;
    private final HostBlacklistsDataSourceFacade skds;
    private static final int BLACK_LIST_ALARM_COUNT = 5;
    private int checkedListCount;
    private final int firstServer, lastServer;
    private int occurrencesCount;
    private final LinkedList<Integer> blackListOccurrences;

    public BlackListThreadValidator(String ipAddress, int firstServer, int lastServer, HostBlacklistsDataSourceFacade skds){
        this.ipAddress = ipAddress;
        this.skds = skds;
        this.firstServer = firstServer;
        this.lastServer = lastServer;
        occurrencesCount = 0;
        checkedListCount = 0;
        blackListOccurrences = new LinkedList<>();
    }

    public void run(){
        for (int i = firstServer; i< lastServer && occurrencesCount < BLACK_LIST_ALARM_COUNT; i++){
            if(skds.isInBlackListServer(i, ipAddress)){
                blackListOccurrences.add(i);
                occurrencesCount++;
            } else{
                checkedListCount++;
            }
        }
    }

    public LinkedList<Integer> getServers(){
        return blackListOccurrences;
    }

    public int getOcurrencesCount(){
        return occurrencesCount;
    }

    public int getCheckedListCount(){
        return checkedListCount;
    }
}
