package com.mycompany.virtual_camera.controller;

import com.mycompany.virtual_camera.model.ViewportModel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Paweł Mac
 */
public class ChangeListenerForFocalDistanceJSlider implements ChangeListener {
    
    private final ViewportModel viewportModel;
    private final JLabel distanceJLabel;
    
    public ChangeListenerForFocalDistanceJSlider(ViewportModel viewportModel, JLabel distanceJLabel) {
        this.viewportModel = viewportModel;
        this.distanceJLabel = distanceJLabel;
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider sourceJSlider = (JSlider)e.getSource();
        if (!sourceJSlider.getValueIsAdjusting()) {
            int value = sourceJSlider.getValue();
            viewportModel.setDistanceBetweenObserverAndViewport((double)value);
            distanceJLabel.setText(Integer.toString(value));
        }
    }
}
