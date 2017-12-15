package com.mycompany.virtual_camera.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Paweł Mac
 */
public final class MotionControlJPanel extends JPanel {
    
    private final JLabel stepJLabel = new JLabel("step:");
    private final JTextField stepTextField = new JTextField(1);
    
    private final JButton moveForwardJButton  = new JButton("↑");
    private final JButton moveBackwardJButton = new JButton("↓");
    private final JButton moveLeftJButton     = new JButton("←");
    private final JButton moveRightJButton    = new JButton("→");
    private final JButton moveUpwardJButton   = new JButton("↥");
    private final JButton moveDownwardJButton = new JButton("↧");
    
    public MotionControlJPanel() {
        this.moveForwardJButton .setToolTipText("move forward");
        this.moveBackwardJButton.setToolTipText("move backward");
        this.moveLeftJButton    .setToolTipText("move left");
        this.moveRightJButton   .setToolTipText("move right");
        this.moveUpwardJButton  .setToolTipText("move upward");
        this.moveDownwardJButton.setToolTipText("move downward");
        
        this.setBorder(new TitledBorder("Motion Control"));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.add(stepJLabel, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(stepTextField, gbc);
        // back to default
        gbc.gridwidth = 1;// Default
        gbc.fill = GridBagConstraints.NONE;// Default
        gbc.gridy = 1;
        gbc.gridx = 1;
        this.add(moveForwardJButton, gbc);
        gbc.gridy = 2;
        this.add(moveBackwardJButton, gbc);
        gbc.gridx = 0;
        this.add(moveLeftJButton, gbc);
        gbc.gridx = 2;
        this.add(moveRightJButton, gbc);
        gbc.gridy = 1;
        gbc.gridx = 3;
        this.add(moveUpwardJButton, gbc);
        gbc.gridy = 2;
        this.add(moveDownwardJButton, gbc);
    }
    
    // Getters
    
    public JTextField getStepTextField() {
        return stepTextField;
    }
    
    public JButton getMoveForwardJButton() {
        return moveForwardJButton;
    }
    
    public JButton getMoveBackwardJButton() {
        return moveBackwardJButton;
    }
    
    public JButton getMoveLeftJButton() {
        return moveLeftJButton;
    }
    
    public JButton getMoveRightJButton() {
        return moveRightJButton;
    }
    
    public JButton getMoveUpwardJButton() {
        return moveUpwardJButton;
    }
    
    public JButton getMoveDownwardJButton() {
        return moveDownwardJButton;
    }
    
    // Test
    public static void main(String[] args) {
        JFrame jFrame = new JFrame(MotionControlJPanel.class.getSimpleName() + " -- test");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(160, 120));
        
        JPanel mainJPanel = new JPanel();
        jFrame.add(mainJPanel);
        MotionControlJPanel motionControlJPanel = new MotionControlJPanel();
        mainJPanel.add(motionControlJPanel);
        
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
