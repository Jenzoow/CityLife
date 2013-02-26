/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package citylife;

import java.util.ArrayList;

/**
 *
 * @author Jens
 */
public class Statistics {
        static ArrayList<Persoon> Bevolking = new ArrayList<Persoon>(CityLife.Bevolking);
    
        public static void leeftijdsTabel() {
        int[] freq = new int[120];
        for (int i = 0; i < freq.length; i++) {
            freq[i] = 0;
        }

        for (Persoon p : Bevolking) {
            int geboortejaar = p.getGeboortedatum().getYear();
            freq[geboortejaar] += 1;
        }

        for (int i = 0; i < freq.length; i++) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            int aantal = 0;
            sb.append(i).append(": ");
            for (int j = 0; j < freq[i]; j++) {
                aantal++;
                sb2.append("|");
            }
            sb.append(aantal).append(sb2);
            System.out.println(sb);
        }
    }

    public static void intelligence() {
        int[] freq = new int[7];
        double a = 0;
        for (int i = 0; i < freq.length; i++) {
            freq[i] = 0;
        }

        for (Persoon p : Bevolking) {
            if (p.getIQ() < 66) {
                freq[0] += 1;
            } else if (p.getIQ() < 80) {
                freq[1] += 1;
            } else if (p.getIQ() < 91) {
                freq[2] += 1;
            } else if (p.getIQ() < 111) {
                freq[3] += 1;
            } else if (p.getIQ() < 120) {
                freq[4] += 1;
            } else if (p.getIQ() < 128) {
                freq[5] += 1;
            } else if (p.getIQ() >= 128) {
                freq[6] += 1;
            }
            a++;
        }

        for (int i = 0; i < freq.length; i++) {
            double perc = (freq[i] / a) * 100;
            System.out.println(freq[i] + " " + perc);
        }
    }
    
    public static void printDoden(){
        int a = 0;
        for (Persoon p: Bevolking){
            if (p.isDood()){
                a++;
                System.out.println(p);
            }
        }
        System.out.println("Aantal doden : " + a);
    }
    
    public static void aantalMannen(){
        int a = 0;
        for (Persoon p: Bevolking){
            if (p.getGeslacht() == 'M'){
                a++;
            }
        }
        System.out.println(a);
    }
    
     public static void printBevolking() {
        StringBuilder sb = new StringBuilder();
        for (Persoon p : Bevolking) {
            sb.append(p);
        }
        System.out.println(sb);
    }
     
     public static void sterfteLeeftijd(){
         int[] freq = new int[150];
         for (int i : freq){
             i = 0;
         }
         
         for (Persoon p: Bevolking){
             if (p.isDood()){
                 int leeftijd = p.getAge(p.getSterftedatum());
                 freq[leeftijd]++;
             }
         }
         
         for (int i = 0; i < freq.length; i++){             
             if (freq[i] != 0){
                 System.out.println(i + " " + freq[i]);
             }             
         }
     }
     
    
    
}
