package com.mycompany.virtual_camera.controller;

import com.mycompany.virtual_camera.model.ViewportModel;
import com.mycompany.virtual_camera.view.View;

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
    }
}
