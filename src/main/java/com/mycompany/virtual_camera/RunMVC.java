package com.mycompany.virtual_camera;

import javax.swing.SwingUtilities;

/**
 *
 * @author Paweł Mac
 */
public class RunMVC implements Runnable {
    
    public RunMVC() {
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new RunMVC());
    }
}
