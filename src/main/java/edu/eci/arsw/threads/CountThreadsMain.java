/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import edu.eci.arsw.threads.CountThread;
/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    private static Thread thread1, thread2, thread3;
    public static void main(String a[]){
        thread1 = new Thread(new CountThread(0, 99));
        thread2 = new Thread(new CountThread(99, 199));
        thread3 = new Thread(new CountThread(200, 299));

        run();
    }
    
    public static void start(){
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static void run(){
        thread1.run();
        thread2.run();
        thread3.run();
    }
}
