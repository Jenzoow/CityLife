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

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Jens
 */
public class ColorGrid extends JPanel {
    JLabel[][] myLabels;

    public ColorGrid(Map map, int cellWidth) {
        Ground[][] xyMap = map.getXyMap();
        myLabels = new JLabel[map.getHeight()][map.getWidth()];
        MyMouseListener myListener = new MyMouseListener(this);
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
                myLabel.addMouseListener(myListener);
                myLabel.setIcon(icon);
                myLabel.setPreferredSize(labelPrefSize);
                add(myLabel);
                myLabels[row][col] = myLabel;
            }
        }
    }

    public void labelPressed(JLabel label) {
        for (int row = 0; row < myLabels.length; row++) {
            for (int col = 0; col < myLabels[row].length; col++) {
                if (label == myLabels[row][col]) {
                    showMessageDialog(this, "Clicked grid: [" + row + "," + col + "]");
                }
            }
        }
    }
}
