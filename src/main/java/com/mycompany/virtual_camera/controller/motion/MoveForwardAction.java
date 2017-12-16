package com.mycompany.virtual_camera.controller.motion;

import com.mycompany.virtual_camera.model.ViewportModel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Paweł Mac
 */
public class MoveForwardAction extends AbstractAction {
    
    ViewportModel viewportModel;
    
    public MoveForwardAction(ViewportModel viewportModel) {
        this.viewportModel = viewportModel;
        this.putValue(NAME, "↑");
        this.putValue(SHORT_DESCRIPTION, "move forward");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.viewportModel.moveForward();
    }
}
