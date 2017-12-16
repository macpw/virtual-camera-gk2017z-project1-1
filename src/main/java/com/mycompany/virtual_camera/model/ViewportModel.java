package com.mycompany.virtual_camera.model;

import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Paweł Mac
 */
public class ViewportModel {
    
    private Set<Point3D> point3DsSet;
    private Set<Edge3D> edge3DsSet;
    private double distanceBetweenObserverAndViewport;
    private final int viewportWidth;
    private final int viewportHeight;
    
    private Map<Edge3D, Line2DHolder> edge3DToLine2DHolderMap;
    
    private RealMatrix perspectiveTransformationMatrix;
    
    public ViewportModel(Set<Point3D> point3DsSet, Set<Edge3D> edge3DsSet, double distanceBetweenObserverAndViewport, int viewportWidth, int viewportHeight) {
        this.point3DsSet = point3DsSet;
        this.edge3DsSet = edge3DsSet;
        this.distanceBetweenObserverAndViewport = distanceBetweenObserverAndViewport;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        
        this.edge3DToLine2DHolderMap = new HashMap<>();
        this.initEdge3DToLine2DHolderMap();
    }
    
    // Getters and Setters
    
    public double getDistanceBetweenObserverAndViewport() {
        return distanceBetweenObserverAndViewport;
    }
    
    public void setDistanceBetweenObserverAndViewport(double distanceBetweenObserverAndViewport) {
        this.distanceBetweenObserverAndViewport = distanceBetweenObserverAndViewport;
        //TODO
    }
    
    // Getters
    
    public int getViewportWidth() {
        return viewportWidth;
    }
    
    public int getViewportHeight() {
        return viewportHeight;
    }
    
    public Collection<Line2DHolder> getCollectionOfLine2DHolder() {
        return edge3DToLine2DHolderMap.values();
    }
    
    // Methods
    
    private void initEdge3DToLine2DHolderMap() {
        for (Edge3D edge3D : edge3DsSet) {
            Line2D line2D = new Line2D.Double(0.0d, 0.0d, 0.0d, 0.0d);
            Line2DHolder line2DHolder = new Line2DHolder(line2D);
            this.calculateLine2DOnViewport(edge3D, line2DHolder);
            this.edge3DToLine2DHolderMap.put(edge3D, line2DHolder);
        }
    }
    
    private void calculateLine2DOnViewport(Edge3D edge3D, Line2DHolder line2DHolder) {
        double x1 = (edge3D.getFirst() .getX() * distanceBetweenObserverAndViewport) / edge3D.getFirst() .getZ();
        double y1 = (edge3D.getFirst() .getY() * distanceBetweenObserverAndViewport) / edge3D.getFirst() .getZ();
        double x2 = (edge3D.getSecond().getX() * distanceBetweenObserverAndViewport) / edge3D.getSecond().getZ();
        double y2 = (edge3D.getSecond().getY() * distanceBetweenObserverAndViewport) / edge3D.getSecond().getZ();
        x1 =  x1 + (viewportWidth  / 2.0d);
        y1 = -y1 + (viewportHeight / 2.0d);
        x2 =  x2 + (viewportWidth  / 2.0d);
        y2 = -y2 + (viewportHeight / 2.0d);
        line2DHolder.getLine2D().setLine(x1, y1, x2, y2);
        
        if (edge3D.getFirst().getZ() > distanceBetweenObserverAndViewport) {
            line2DHolder.setFirstInFrontOfViewport(true);
        } else {
            line2DHolder.setFirstInFrontOfViewport(false);
        }
        
        if (edge3D.getSecond().getZ() > distanceBetweenObserverAndViewport) {
            line2DHolder.setSecondInFrontOfViewport(true);
        } else {
            line2DHolder.setSecondInFrontOfViewport(false);
        }
    }
}
