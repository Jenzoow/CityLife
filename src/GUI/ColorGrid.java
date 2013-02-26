/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Map.Ground;
import Map.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Jens
 */
public class ColorGrid extends JPanel {

    public ColorGrid(Map map, int cellWidth) {
        Ground[][] xyMap = map.getXyMap();
        Dimension labelPrefSize = new Dimension(cellWidth, cellWidth);
        setLayout(new GridLayout(map.getHeight(), map.getWidth()));
        for (int row = 0; row < xyMap.length; row++) {
            for (int col = 0; col < xyMap[row].length; col++) {
                JLabel myLabel;
                myLabel = new JLabel();
                myLabel.setOpaque(true);
                String path = xyMap[row][col].getImagePath();
                ImageIcon icon = new ImageIcon(getClass().getResource(path));
                icon = new ImageIcon(icon.getImage());
                myLabel.setIcon(icon);
                myLabel.setPreferredSize(labelPrefSize);
                add(myLabel);
            }
        }
    }
}
