/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citylife;

import java.util.Calendar;

/**
 *
 * @author Jens
 */
public class Start {
    int AANTALPERSONEN = 20;

    public Start() {
        initPersonen(AANTALPERSONEN);
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
        for (Persoon p : CityLife.Bevolking){
            if (!p.isDood()){
                p.dagOuder();
            }
        }

    }

    public  void nextDay(int aantalDagen) {
        for (int i = 0; i < aantalDagen; i++) {
            nextDay();
        }
    }
}
