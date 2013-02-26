/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citylife;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import namegenerator.NameGenerator;

/**
 *
 * @author Jens
 */
public class Persoon {

    private String voornaam;
    private String achternaam;
    private char geslacht;
    private Date geboortedatum;
    private Date sterftedatum;
    private int IQ;
    private Persoon partner;
    private boolean intSport;
    private boolean intGezondheid;
    private boolean intPolitiek;
    private boolean intWetenschap;
    private boolean intGeld;
    private boolean intMuziek;
    private boolean intAuto;
    private boolean intReizen;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public Persoon() {
        Random random = new Random();
        try {
            NameGenerator ngVN = new NameGenerator("Roman.txt");
            NameGenerator ngAN = new NameGenerator("Elven.txt");
            voornaam = ngVN.compose(random.nextInt(4) + 1);
            achternaam = ngAN.compose(random.nextInt(4) + 1);
        } catch (IOException ex) {
            Logger.getLogger(Persoon.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (Math.abs(random.nextDouble()) < 0.5) {
            geslacht = 'M';
        } else {
            geslacht = 'V';
        }
        int geb = Math.abs(random.nextInt());

        int jaar = 0, maand = 0, dag = 0;
        try {
            jaar = Integer.parseInt(Integer.toString(geb).substring(4, 6));
            maand = Integer.parseInt(Integer.toString(geb).substring(2, 4));
            dag = Integer.parseInt(Integer.toString(geb).substring(0, 2));
        } catch (StringIndexOutOfBoundsException e) {
            //System.out.println(e);
        }
        geboortedatum = new Date(jaar, maand, dag);
        sterftedatum = null;

        IQ = testIQ();

        partner = null;

        intSport = random.nextBoolean();
        intGezondheid = random.nextBoolean();
        intPolitiek = random.nextBoolean();
        intWetenschap = random.nextBoolean();
        intGeld = random.nextBoolean();
        intMuziek = random.nextBoolean();
        intAuto = random.nextBoolean();
        intReizen = random.nextBoolean();
    }

    public void koppel(Persoon p) {
        partner = p;
        if (p.getPartner() == null) {
            p.koppel(this);
        }
    }

    public void dagOuder() {
        if (!isDood()){            
            sterf();
        }        
    }
    
    public boolean isDood(){
        boolean dood = false;
        if (sterftedatum != null) {
            dood = true;           
        }
        return dood;
    }
    
    private void sterf(){
        int leeftijd = getAge();
        int kans;
        if (leeftijd < 1) {
            kans = 177 * 365;
        } else if (leeftijd < 4) {
            kans = 4386 * 365;
        } else if (leeftijd < 14) {
            kans = 8333 * 365;
        } else if (leeftijd < 24) {
            kans = 1908 * 365;
        } else if (leeftijd < 34) {
            kans = 1215 * 365;
        } else if (leeftijd < 44) {
            kans = 663 * 365;
        } else if (leeftijd < 54) {
            kans = 279 * 365;
        } else if (leeftijd < 64) {
            kans = 112 * 365;
        } else if (leeftijd < 74) {
            kans = 42 * 365;
        } else if (leeftijd < 84) {
            kans = 15 * 365;
        } else if (leeftijd < 100){
            kans = 6 * 365;
        } else {
            kans = 2 * 365;
        }
             
        Random random = new Random();
        if (random.nextInt(kans)  == 0){            
            sterftedatum = CityLife.day.getTime();
        }
    }

    public int getAge() {
        int currentYear = CityLife.day.getTime().getYear();
        int birthYear = geboortedatum.getYear();

        return currentYear - birthYear;
    }
    
     public int getAge(Date sterftedatum) {
        int currentYear = sterftedatum.getYear();
        int birthYear = geboortedatum.getYear();

        return currentYear - birthYear;
    }

    public Persoon getPartner() {
        return partner;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public Date getSterftedatum() {
        return sterftedatum;
    }
    
    

    public String getNaam() {
        return achternaam + " " + voornaam;
    }

    public int getIQ() {
        return IQ;
    }

    public char getGeslacht() {
        return geslacht;
    }
    
    

    private int testIQ() {
        int iIQ = 0;
        int min;
        int max;
        Random random = new Random();
        double i = random.nextDouble() * 100;
        if (i < 2.2) {
            min = 50;
            max = 65;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        } else if (i < 8.9) {
            min = 66;
            max = 79;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        } else if (i < 25) {
            min = 80;
            max = 90;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        } else if (i < 75) {
            min = 91;
            max = 110;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        } else if (i < 91.1) {
            min = 111;
            max = 119;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        } else if (i < 97.8) {
            min = 120;
            max = 127;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        } else if (i <= 100) {
            min = 128;
            max = 140;
            iIQ = min + (int) (Math.random() * ((max - min) + 1));
        }
        return iIQ;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nNaam: ").append(achternaam).append(" ").append(voornaam);
        sb.append("\nGeboortedatum: ").append(sdf.format(geboortedatum));
        if (sterftedatum != null) {
            sb.append("\nSterftedatum: ").append(sdf.format(sterftedatum)).append(" op ").append(getAge(sterftedatum)).append("-jarige leeftijd.");
            
        }
        sb.append("\nGeslacht: ").append(geslacht);
        sb.append("\nIQ : ").append(IQ);
        if (partner != null) {
            sb.append("\nPartner: ").append(partner.getNaam());
        }
        sb.append("\nInteresses:");
        sb.append("\n\tSport: ").append(intSport).append("\tGezondheid: ").append(intGezondheid);
        sb.append("\n\tMuziek: ").append(intMuziek).append("\tWetenschap: ").append(intWetenschap);
        sb.append("\n\tGeld: ").append(intGeld).append("\tPolitiek: ").append(intPolitiek);
        sb.append("\n\tAuto: ").append(intAuto).append("\tReizen: ").append(intReizen);
        sb.append("\n");
        return sb.toString();
    }
}
