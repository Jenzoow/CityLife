/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Map.Map;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author Jens
 */
public class MapGUI {
    static int CELLWIDTH = 32;

    private static void createAndShowGui() {
        
        ColorGrid mainPanel;
        mainPanel = new ColorGrid(new Map(), CELLWIDTH);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        JFrame frame = new JFrame("CityLife");
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGui();
    }
}
