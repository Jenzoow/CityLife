/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Map.Ground;
import Map.Map;
import utils.Functions;
import utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Jens
 */
public class ColorGrid extends JPanel {
    JLabel[][] myLabels;
    Ground[][] xyMap;

    public ColorGrid(Map map, int cellWidth) {
        xyMap = map.getXyMap();
        myLabels = new JLabel[map.getHeight()][map.getWidth()];
        MyMouseListener myListener = new MyMouseListener(this);
        Dimension labelPrefSize = new Dimension(cellWidth, cellWidth);
        setLayout(new GridLayout(map.getHeight(), map.getWidth()));
        for (int row = 0; row < xyMap.length; row++) {
            for (int col = 0; col < xyMap[row].length; col++) {
                JLabel myLabel;
                myLabel = new JLabel();
                String path = xyMap[row][col].getImagePath();
                ImageIcon icon = new ImageIcon(getClass().getResource(path));

                myLabel.setIcon(icon);
                myLabel.setOpaque(true);
                myLabel.addMouseListener(myListener);
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

    public void refresh(){
        for (int i = 0; i < myLabels.length; i++){
            for (int j = 0; j < myLabels[i].length; j++){
                String path = xyMap[i][j].getImagePath();
                ImageIcon icon = new ImageIcon(getClass().getResource(path));
                Image image = icon.getImage();
                if (xyMap[i][j].getBuildDirection() != null) {
                    switch (xyMap[i][j].getBuildDirection()) {
                        case RIGHT:

                            break;
                        case DOWN:
                            image = ImageUtils.rotateImage(image, 90);
                            break;
                        case LEFT:
                            image = ImageUtils.rotateImage(image, 180);
                            break;
                        case UP:
                            image = ImageUtils.rotateImage(image, 270);
                            break;
                    }
                }
                icon = new ImageIcon(image);
                myLabels[i][j].setIcon(icon);
            }
        }
    }
}
