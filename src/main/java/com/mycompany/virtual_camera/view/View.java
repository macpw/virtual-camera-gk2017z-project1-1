package com.mycompany.virtual_camera.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Paweł Mac
 */
public class View {
    
    public View() {
        this.createAndShowGui();
    }
    
    private void createAndShowGui() {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(150, 100));
        
        JPanel mainJPanel = new JPanel();
        jFrame.add(mainJPanel);
        this.addComponetnsToPane(mainJPanel);
        
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    
    private void addComponetnsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    }
    
    public static void main(String[] args) {
        new View();
    }
}
