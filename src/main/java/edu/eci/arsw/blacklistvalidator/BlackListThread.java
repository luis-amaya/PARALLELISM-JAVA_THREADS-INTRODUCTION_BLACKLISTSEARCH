package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class BlackListThread extends Thread{
    private String ipAdress;
    private int occurrencesCount;
    private int checkedListCount;
    private int alarmCount;
    private int firstServer;
    private int lastServer;
    private HostBlacklistsDataSourceFacade skds;

    private LinkedList<Integer> servers = new LinkedList<>();

    public LinkedList<Integer> getServers(){
        return servers;
    }

    public int getOcurrencesCount(){
        return occurrencesCount;
    }

    public BlackListThread(String ipAdress, int occurrencesCount, int checkedListCount, int alarmCount, int firstServer, int lastServer, HostBlacklistsDataSourceFacade skds){
        this.ipAdress = ipAdress;
        this.occurrencesCount = occurrencesCount;
        this.checkedListCount = checkedListCount;
        this.alarmCount = alarmCount;
        this.firstServer = firstServer;
        this.lastServer = lastServer;
        this.skds = skds;
    }

    public void run(){
        for(int i=firstServer; i<lastServer && occurrencesCount < alarmCount; i++){
            if(skds.isInBlackListServer(i, ipAdress)){
                servers.add(i);
                occurrencesCount++;
            }
        }
    }
}
