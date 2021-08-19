/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String a[]){
        long Tinicio, Tfinal, Tiempo;
        Tinicio = System.currentTimeMillis();
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences=hblv.checkHost("204.24.34.55",100);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        Tfinal = System.currentTimeMillis();
        Tiempo = Tfinal-Tinicio;
        System.out.println("Tiempo de ejecucion (milisegundos): "+ Tiempo);
    }
    
}
