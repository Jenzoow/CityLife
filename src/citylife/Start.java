/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citylife;

import GUI.MapGUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * @author Jens
 */
public class Start {
    int AANTALPERSONEN = 2;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    MapGUI mapGUI;

    public Start() {
        initPersonen(AANTALPERSONEN);
        mapGUI = new MapGUI();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                nextDay();
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void initPersonen(int aantal) {
        CityLife.day = Calendar.getInstance();
        for (int i = 0; i < aantal; i++) {
            Persoon persoon = new Persoon();
            addPersoon(persoon);
        }
    }

    public  void addPersoon(Persoon persoon) {
        CityLife.Bevolking.add(persoon);
    }


    public  void nextDay() {
        CityLife.day.add(Calendar.DATE, 1); // Add day
        System.out.println(sdf.format(CityLife.day.getTime()));
        for (Persoon p : CityLife.Bevolking){
            if (!p.isDood()){
                p.dagOuder();
            }
        }
        MapGUI.getMap().placeHouse();
        mapGUI.refresh();
    }



    public  void nextDay(int aantalDagen) {
        for (int i = 0; i < aantalDagen; i++) {
            nextDay();
        }
    }
}
