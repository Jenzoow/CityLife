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
    static Map map;
    private JFrame frame;
    ColorGrid mainPanel;

    public MapGUI() {
        map = new Map();
        frame = new JFrame("CityLife");
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new ColorGrid(map, CELLWIDTH);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void refresh(){
        mainPanel.refresh();
    }

    public static Map getMap() {
        return map;
    }
}
