package com.mycompany.virtual_camera.controller;

import com.mycompany.virtual_camera.controller.motion.MoveBackwardAction;
import com.mycompany.virtual_camera.controller.motion.MoveDownwardAction;
import com.mycompany.virtual_camera.controller.motion.MoveForwardAction;
import com.mycompany.virtual_camera.controller.motion.MoveLeftAction;
import com.mycompany.virtual_camera.controller.motion.MoveRightAction;
import com.mycompany.virtual_camera.controller.motion.MoveUpwardAction;
import com.mycompany.virtual_camera.controller.motion.StepJTextFieldDocumentListener;
import com.mycompany.virtual_camera.controller.rotation.AngleJTextFieldDocumentListener;
import com.mycompany.virtual_camera.controller.rotation.RotateDownwardAction;
import com.mycompany.virtual_camera.controller.rotation.RotateLeftAction;
import com.mycompany.virtual_camera.controller.rotation.RotateRightAction;
import com.mycompany.virtual_camera.controller.rotation.RotateTiltLeftAction;
import com.mycompany.virtual_camera.controller.rotation.RotateTiltRightAction;
import com.mycompany.virtual_camera.controller.rotation.RotateUpwardAction;
import com.mycompany.virtual_camera.model.ViewportModel;
import com.mycompany.virtual_camera.view.View;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Paweł Mac
 */
public class Controller {
    
    private final ViewportModel viewportModel;
    private final View view;
    private final KeyBinding keyBinding;
    
    public Controller(ViewportModel viewportModel, View view) {
        this.viewportModel = viewportModel;
        this.view = view;
        this.keyBinding = new KeyBinding(view.getViewportJPanel());
        this.addListenerToDistanceJPanel();
        this.addMotionActions();
        this.addRotationActions();
    }
    
    private void addListenerToDistanceJPanel() {
        JLabel distanceJLabel = view.getDistanceJPanel().getDistanceJLabel();
        JSlider distanceJSlider = view.getDistanceJPanel().getDistanceJSlider();
        ChangeListenerForFocalDistanceJSlider changeListenerForDistanceJSlider = new ChangeListenerForFocalDistanceJSlider(viewportModel, distanceJLabel);
        distanceJSlider.addChangeListener(changeListenerForDistanceJSlider);
        distanceJSlider.setValue((int)viewportModel.getDistanceBetweenObserverAndViewport());
        distanceJLabel.setText(Integer.toString((int)viewportModel.getDistanceBetweenObserverAndViewport()));
    }
    
    private void addMotionActions() {
        JTextField stepJTextField = view.getMotionControlJPanel().getStepJTextField();
        stepJTextField.setText(Double.toString(viewportModel.getStep()));
        stepJTextField.setToolTipText("step="+viewportModel.getStep());
        StepJTextFieldDocumentListener stepTextFieldDocumentListener = new StepJTextFieldDocumentListener(stepJTextField, viewportModel);
        stepJTextField.getDocument().addDocumentListener(stepTextFieldDocumentListener);
        
        // get buttons
        JButton moveForwardJButton  = view.getMotionControlJPanel().getMoveForwardJButton();
        JButton moveBackwardJButton = view.getMotionControlJPanel().getMoveBackwardJButton();
        JButton moveLeftJButton     = view.getMotionControlJPanel().getMoveLeftJButton();
        JButton moveRightJButton    = view.getMotionControlJPanel().getMoveRightJButton();
        JButton moveUpwardJButton   = view.getMotionControlJPanel().getMoveUpwardJButton();
        JButton moveDownwardJButton = view.getMotionControlJPanel().getMoveDownwardJButton();
        
        // create actions
        MoveForwardAction  moveForwardAction  = new MoveForwardAction(viewportModel);
        MoveBackwardAction moveBackwardAction = new MoveBackwardAction(viewportModel);
        MoveLeftAction     moveLeftAction     = new MoveLeftAction(viewportModel);
        MoveRightAction    moveRightAction    = new MoveRightAction(viewportModel);
        MoveUpwardAction   moveUpwardAction   = new MoveUpwardAction(viewportModel);
        MoveDownwardAction moveDownwardAction = new MoveDownwardAction(viewportModel);
        
        // add actions
        moveForwardJButton .addActionListener(moveForwardAction);
        moveBackwardJButton.addActionListener(moveBackwardAction);
        moveLeftJButton    .addActionListener(moveLeftAction);
        moveRightJButton   .addActionListener(moveRightAction);
        moveUpwardJButton  .addActionListener(moveUpwardAction);
        moveDownwardJButton.addActionListener(moveDownwardAction);
        
        // key bindings
        moveForwardJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "move forward");
        moveForwardJButton.getActionMap().put("move forward", moveForwardAction);
        
        moveBackwardJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "move backward");
        moveBackwardJButton.getActionMap().put("move backward", moveBackwardAction);
        
        moveLeftJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "move left");
        moveLeftJButton.getActionMap().put("move left", moveLeftAction);
        
        moveRightJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "move right");
        moveRightJButton.getActionMap().put("move right", moveRightAction);
        
        moveUpwardJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), "move upward");
        moveUpwardJButton.getActionMap().put("move upward", moveUpwardAction);
        
        moveDownwardJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK), "move downward");
        moveDownwardJButton.getActionMap().put("move downward", moveDownwardAction);
        
        keyBinding.bindKeyWithAction(KeyEvent.VK_W, 0, moveForwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_S, 0, moveBackwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_A, 0, moveLeftAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_D, 0, moveRightAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_R, 0, moveUpwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_F, 0, moveDownwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK, moveUpwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK, moveDownwardAction);
    }
    
    private void addRotationActions() {
        JTextField angleJTextField = view.getRotationControlJPanel().getAngleJTextField();
        angleJTextField.setText(Double.toString(viewportModel.getAngleInDegrees()));
        angleJTextField.setToolTipText("angle="+viewportModel.getAngleInDegrees());
        AngleJTextFieldDocumentListener angleTextFieldDocumentListener = new AngleJTextFieldDocumentListener(angleJTextField, viewportModel);
        angleJTextField.getDocument().addDocumentListener(angleTextFieldDocumentListener);
        
        // get buttons
        JButton rotateLeftJButton      = view.getRotationControlJPanel().getRotateLeftJButton();
        JButton rotateRightJButton     = view.getRotationControlJPanel().getRotateRightJButton();
        JButton rotateUpwardJButton    = view.getRotationControlJPanel().getRotateUpwardJButton();
        JButton rotateDownwardJButton  = view.getRotationControlJPanel().getRotateDownwardJButton();
        JButton rotateTiltLeftJButton  = view.getRotationControlJPanel().getRotateTiltLeftJButton();
        JButton rotateTiltRightJButton = view.getRotationControlJPanel().getRotateTiltRightJButton();
        
        // create actions
        RotateLeftAction      rotateLeftAction      = new RotateLeftAction(viewportModel);
        RotateRightAction     rotateRightAction     = new RotateRightAction(viewportModel);
        RotateUpwardAction    rotateUpwardAction    = new RotateUpwardAction(viewportModel);
        RotateDownwardAction  rotateDownwardAction  = new RotateDownwardAction(viewportModel);
        RotateTiltLeftAction  rotateTiltLeftAction  = new RotateTiltLeftAction(viewportModel);
        RotateTiltRightAction rotateTiltRightAction = new RotateTiltRightAction(viewportModel);
        
        // add actions
        rotateLeftJButton     .addActionListener(rotateLeftAction);
        rotateRightJButton    .addActionListener(rotateRightAction);
        rotateUpwardJButton   .addActionListener(rotateUpwardAction);
        rotateDownwardJButton .addActionListener(rotateDownwardAction);
        rotateTiltLeftJButton .addActionListener(rotateTiltLeftAction);
        rotateTiltRightJButton.addActionListener(rotateTiltRightAction);
        
        // key bindings
        rotateLeftJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.SHIFT_DOWN_MASK), "rotate left");
        rotateLeftJButton.getActionMap().put("rotate left", rotateLeftAction);
        
        rotateRightJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_DOWN_MASK), "rotate right");
        rotateRightJButton.getActionMap().put("rotate right", rotateRightAction);
        
        rotateUpwardJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.SHIFT_DOWN_MASK), "rotate upward");
        rotateUpwardJButton.getActionMap().put("rotate upward", rotateUpwardAction);
        
        rotateDownwardJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.SHIFT_DOWN_MASK), "rotate downward");
        rotateDownwardJButton.getActionMap().put("rotate downward", rotateDownwardAction);
        
        rotateTiltLeftJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK), "rotate tilt left");
        rotateTiltLeftJButton.getActionMap().put("rotate tilt left", rotateTiltLeftAction);
        
        rotateTiltRightJButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK), "rotate tilt right");
        rotateTiltRightJButton.getActionMap().put("rotate tilt right", rotateTiltRightAction);
        
        keyBinding.bindKeyWithAction(KeyEvent.VK_A, InputEvent.SHIFT_DOWN_MASK, rotateLeftAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_D, InputEvent.SHIFT_DOWN_MASK, rotateRightAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_W, InputEvent.SHIFT_DOWN_MASK, rotateUpwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK, rotateDownwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_A, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK, rotateTiltLeftAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_D, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK, rotateTiltRightAction);
        
        keyBinding.bindKeyWithAction(KeyEvent.VK_J, 0, rotateLeftAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_L, 0, rotateRightAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_I, 0, rotateUpwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_K, 0, rotateDownwardAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_U, 0, rotateTiltLeftAction);
        keyBinding.bindKeyWithAction(KeyEvent.VK_O, 0, rotateTiltRightAction);
    }
}
