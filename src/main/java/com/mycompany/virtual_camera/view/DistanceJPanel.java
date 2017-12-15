package com.mycompany.virtual_camera.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Paweł Mac
 */
public final class DistanceJPanel extends JPanel {
    
    private static final int MINIMUM_WIDTH = 400;// JSlider minimum width
    private static final int MINIMUM_DISTANCE = 10;// mimimum value on JSlider
    
    private final JLabel  distanceJLabel;
    private final JSlider distanceJSlider;
    
    public DistanceJPanel() {
        this.distanceJLabel = new JLabel("distance");
        this.distanceJSlider = new JSlider();
        this.distanceJSlider.setMinimum(0);
        this.distanceJSlider.setMaximum(2000);
        this.distanceJSlider.setMajorTickSpacing(200);
        this.distanceJSlider.setMinorTickSpacing(50);
        this.distanceJSlider.setPaintTicks(true);
        this.distanceJSlider.setPaintLabels(true);
        this.distanceJSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JSlider)e.getSource()).getValue() < MINIMUM_DISTANCE) {
                    ((JSlider)e.getSource()).setValue(MINIMUM_DISTANCE);
                }
            }
        });
        this.distanceJSlider.setMinimumSize(
                new Dimension(MINIMUM_WIDTH, distanceJSlider.getMinimumSize().height)
        );
        this.distanceJSlider.setPreferredSize(
                new Dimension(MINIMUM_WIDTH, distanceJSlider.getPreferredSize().height)
        );
        
        this.setBorder(new TitledBorder("Distance between observer and viewport"));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        this.add(distanceJLabel, gbc);
        gbc.weightx = 1.0d;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(distanceJSlider, gbc);
    }
    
    // Getters
    
    public JLabel getDistanceJLabel() {
        return distanceJLabel;
    }
    
    public JSlider getDistanceJSlider() {
        return distanceJSlider;
    }
    
    // Test
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(160, 120));
        
        JPanel mainJPanel = new JPanel();
        jFrame.add(mainJPanel);
        
        DistanceJPanel distanceJPanel = new DistanceJPanel();
        //distanceJPanel.setPreferredSize(new Dimension(600, 150));
        mainJPanel.add(distanceJPanel);
        
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
