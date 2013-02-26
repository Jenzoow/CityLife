package GUI;

import GUI.ColorGrid;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class MyMouseListener extends MouseAdapter {
    private ColorGrid colorGrid;

    public MyMouseListener(ColorGrid colorGrid) {
        this.colorGrid = colorGrid;
    }

    @Override
    public void mousePressed(MouseEvent e) {
            colorGrid.labelPressed((JLabel)e.getSource());
    }
}