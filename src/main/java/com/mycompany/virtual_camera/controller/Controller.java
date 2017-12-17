package com.mycompany.virtual_camera.controller;

import com.mycompany.virtual_camera.controller.motion.MoveBackwardAction;
import com.mycompany.virtual_camera.controller.motion.MoveDownwardAction;
import com.mycompany.virtual_camera.controller.motion.MoveForwardAction;
import com.mycompany.virtual_camera.controller.motion.MoveLeftAction;
import com.mycompany.virtual_camera.controller.motion.MoveRightAction;
import com.mycompany.virtual_camera.controller.motion.MoveUpwardAction;
import com.mycompany.virtual_camera.model.ViewportModel;
import com.mycompany.virtual_camera.view.View;
import javax.swing.JButton;

/**
 *
 * @author Paweł Mac
 */
public class Controller {
    
    private final ViewportModel viewportModel;
    private final View view;
    
    public Controller(ViewportModel viewportModel, View view) {
        this.viewportModel = viewportModel;
        this.view = view;
        this.addListenersToMotionButtons();
    }
    
    private void addListenersToMotionButtons() {
        JButton moveForwardJButton  = view.getMotionControlJPanel().getMoveForwardJButton();
        JButton moveBackwardJButton = view.getMotionControlJPanel().getMoveBackwardJButton();
        JButton moveLeftJButton     = view.getMotionControlJPanel().getMoveLeftJButton();
        JButton moveRightJButton    = view.getMotionControlJPanel().getMoveRightJButton();
        JButton moveUpwardJButton   = view.getMotionControlJPanel().getMoveUpwardJButton();
        JButton moveDownwardJButton = view.getMotionControlJPanel().getMoveDownwardJButton();
        
        MoveForwardAction  moveForwardAction  = new MoveForwardAction(viewportModel);
        MoveBackwardAction moveBackwardAction = new MoveBackwardAction(viewportModel);
        MoveLeftAction     moveLeftAction     = new MoveLeftAction(viewportModel);
        MoveRightAction    moveRightAction    = new MoveRightAction(viewportModel);
        MoveUpwardAction   moveUpwardAction   = new MoveUpwardAction(viewportModel);
        MoveDownwardAction moveDownwardAction = new MoveDownwardAction(viewportModel);
        
        moveForwardJButton .addActionListener(moveForwardAction);
        moveBackwardJButton.addActionListener(moveBackwardAction);
        moveLeftJButton    .addActionListener(moveLeftAction);
        moveRightJButton   .addActionListener(moveRightAction);
        moveUpwardJButton  .addActionListener(moveUpwardAction);
        moveDownwardJButton.addActionListener(moveDownwardAction);
    }
}
