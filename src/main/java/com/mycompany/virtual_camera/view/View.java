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
    
    private final ViewportJPanel viewportJPanel;
    private final DistanceJPanel distanceJPanel;
    private final MotionControlJPanel motionControlJPanel;
    private final RotationControlJPanel rotationControlJPanel;
    
    public View(int width, int height) {
        viewportJPanel = new ViewportJPanel(width, height);
        this.distanceJPanel = new DistanceJPanel();
        this.motionControlJPanel = new MotionControlJPanel();
        this.rotationControlJPanel = new RotationControlJPanel();
        this.createAndShowGui();
    }
    
    // Getters
    
    public ViewportJPanel getViewportJPanel() {
        return viewportJPanel;
    }
    
    public DistanceJPanel getDistanceJPanel() {
        return distanceJPanel;
    }
    
    public MotionControlJPanel getMotionControlJPanel() {
        return motionControlJPanel;
    }
    
    public RotationControlJPanel getRotationControlJPanel() {
        return rotationControlJPanel;
    }
    
    // Methods
    
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
        JPanel controlJPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        pane.add(viewportJPanel, gbc);
        pane.add(controlJPanel, gbc);
        
        gbc.gridx = GridBagConstraints.RELATIVE;// Default
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlJPanel.add(distanceJPanel, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        controlJPanel.add(motionControlJPanel, gbc);
        controlJPanel.add(rotationControlJPanel, gbc);
    }
    
    // Test View
    public static void main(String[] args) {
        new View(600, 400);
    }
}
