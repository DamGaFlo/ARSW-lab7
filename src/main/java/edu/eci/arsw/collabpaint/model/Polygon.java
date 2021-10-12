package edu.eci.arsw.collabpaint.model;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Point> points;
    public Polygon(){
        points=new ArrayList();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
    public int nPoints(){
        return points.size();
    }
}
