package com.mycompany.virtual_camera.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author Paweł Mac
 */
public class KeyBinding {
    
    private static final int DELAY = 50;
    private final JComponent jComponent;
    private final javax.swing.Timer timer;
    private final Map<KeyStroke, Boolean> keyStrokeToKeyPressedMap = new HashMap<>();
    private final Map<KeyStroke, Action>  keyStrokeToActionMap     = new HashMap<>();
    private int keyPressedCounter = 0;
    private final Set<KeyStroke> withoutModifiersKeyStrokesSet   = new HashSet<>();
    private final Set<KeyStroke> ctrlModifierKeyStrokesSet       = new HashSet<>();
    private final Set<KeyStroke> shiftModifierKeyStrokesSet      = new HashSet<>();
    private final Set<KeyStroke> ctrlShiftModifiersKeyStrokesSet = new HashSet<>();
    private KeyStroke lastPressedKeyStroke;
    private Robot robot;
    private boolean pressKeyAgain = false;
    
    public KeyBinding(JComponent jComponent, int delay) {
        this.jComponent = jComponent;
        this.timer = new Timer(delay, new ActionForTimer());
        this.timer.setInitialDelay(0);
        
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(KeyBinding.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.modifiersPressedAndReleased();
    }
    
    public KeyBinding(JComponent jComponent) {
        this(jComponent, DELAY);
    }
    
    public void bindKeyWithAction(int keyCode, int modifiers, Action action) {
        InputMap   inputMap = jComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = jComponent.getActionMap();
        
        KeyStroke  pressedKeyStroke = KeyStroke.getKeyStroke(keyCode, modifiers, false);
        KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(keyCode, modifiers, true);
        
        inputMap.put(pressedKeyStroke, pressedKeyStroke.toString());
        actionMap.put(pressedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!keyStrokeToKeyPressedMap.get(pressedKeyStroke)) {
                    keyPressedCounter++;
                    if (keyPressedCounter == 1) {
                        timer.start();
                    }
                }
                // key is pressed
                keyStrokeToKeyPressedMap.replace(pressedKeyStroke, true);
                lastPressedKeyStroke = pressedKeyStroke;
            }
        });
        
        inputMap.put(releasedKeyStroke, releasedKeyStroke.toString());
        actionMap.put(releasedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keyStrokeToKeyPressedMap.get(pressedKeyStroke)) {
                    keyPressedCounter--;
                    if (keyPressedCounter == 0) {
                        timer.stop();
                    }
                }
                // key is released
                keyStrokeToKeyPressedMap.replace(pressedKeyStroke, false);
                
                // press key again (by robot) for fluent animation (in ActionForTimer)
                if (lastPressedKeyStroke.equals(pressedKeyStroke) && keyPressedCounter > 0) {
                    pressKeyAgain = true;
                }
            }
        });
        
        keyStrokeToKeyPressedMap.put(pressedKeyStroke, Boolean.FALSE);
        keyStrokeToActionMap.put(pressedKeyStroke, action);
        switch (modifiers) {
            case 0:
                withoutModifiersKeyStrokesSet.add(pressedKeyStroke);
                break;
            case InputEvent.CTRL_DOWN_MASK:
                ctrlModifierKeyStrokesSet.add(pressedKeyStroke);
                break;
            case InputEvent.SHIFT_DOWN_MASK:
                shiftModifierKeyStrokesSet.add(pressedKeyStroke);
                break;
            case InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK:
                ctrlShiftModifiersKeyStrokesSet.add(pressedKeyStroke);
                break;
        }
    }
    
    // necessery when release modifier first
    private void modifiersPressedAndReleased() {
        InputMap inputMap = jComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = jComponent.getActionMap();
        
        KeyStroke ctrlReleasedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, 0, true);
        inputMap.put(ctrlReleasedKeyStroke, ctrlReleasedKeyStroke.toString());
        actionMap.put(ctrlReleasedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : ctrlModifierKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        KeyStroke ctrlPressedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, InputEvent.CTRL_DOWN_MASK, false);
        inputMap.put(ctrlPressedKeyStroke, ctrlPressedKeyStroke.toString());
        actionMap.put(ctrlPressedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : withoutModifiersKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        
        KeyStroke shiftReleasedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true);
        inputMap.put(shiftReleasedKeyStroke, shiftReleasedKeyStroke.toString());
        actionMap.put(shiftReleasedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : shiftModifierKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        KeyStroke shiftPressedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK, false);
        inputMap.put(shiftPressedKeyStroke, shiftPressedKeyStroke.toString());
        actionMap.put(shiftPressedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : withoutModifiersKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        
        // shift ctrl pressed, then ctrl released
        KeyStroke shiftCtrlReleasedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, InputEvent.SHIFT_DOWN_MASK, true);
        inputMap.put(shiftCtrlReleasedKeyStroke, shiftCtrlReleasedKeyStroke.toString());
        actionMap.put(shiftCtrlReleasedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : ctrlShiftModifiersKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        // shift pressed, then shift ctrl pressed
        KeyStroke shiftCtrlPressedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_CONTROL, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK, false);
        inputMap.put(shiftCtrlPressedKeyStroke, shiftCtrlPressedKeyStroke.toString());
        actionMap.put(shiftCtrlPressedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : shiftModifierKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        
        // ctrl shift pressed, then shift released
        KeyStroke ctrlShiftReleasedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.CTRL_DOWN_MASK, true);
        inputMap.put(ctrlShiftReleasedKeyStroke, ctrlShiftReleasedKeyStroke.toString());
        actionMap.put(ctrlShiftReleasedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : ctrlShiftModifiersKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
        // ctrl pressed, then ctrl shift pressed
        KeyStroke ctrlShiftPressedKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK, false);
        inputMap.put(ctrlShiftPressedKeyStroke, ctrlShiftPressedKeyStroke.toString());
        actionMap.put(ctrlShiftPressedKeyStroke.toString(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (KeyStroke keyStroke : ctrlModifierKeyStrokesSet) {
                    if (keyStrokeToKeyPressedMap.get(keyStroke)) {
                        keyStrokeToKeyPressedMap.replace(keyStroke, false);
                        keyPressedCounter--;
                    }
                }
            }
        });
    }
    
    private class ActionForTimer implements ActionListener {
        
        public ActionForTimer() {
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Map.Entry<KeyStroke, Boolean> entry : keyStrokeToKeyPressedMap.entrySet()) {
                KeyStroke keyStroke = entry.getKey();
                Boolean isKeyPressed = entry.getValue();
                if (isKeyPressed) {
                    Action action = keyStrokeToActionMap.get(keyStroke);
                    action.actionPerformed(e);
                    if (pressKeyAgain) {
                        robot.keyPress(keyStroke.getKeyCode());
                    }
                }
            }
            pressKeyAgain = false;
        }
    }
}
