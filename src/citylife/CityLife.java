/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citylife;

import Map.Map;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Jens
 */
public class CityLife {

    static ArrayList<Persoon> Bevolking = new ArrayList<Persoon>();
    static Calendar day;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Start start = new Start();  
        start.nextDay(20000);
        Statistics.leeftijdsTabel();


    }    
}
