package edu.eci.arsw.collabpaint.persistence;

import edu.eci.arsw.collabpaint.model.Point;
import edu.eci.arsw.collabpaint.model.Polygon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PointStock {
    private ConcurrentHashMap<Integer, Polygon> points = new ConcurrentHashMap<>();

    public Polygon addPoint(int number,Point point){
        points.putIfAbsent(number,new Polygon());
        return insertar(number,point);

    }

    private Polygon insertar(int number,Point point){
        Polygon puntero = points.get(number);
        synchronized(puntero){
            puntero.getPoints().add(point);
        }
        return puntero;

    }
    private Polygon getPoints(int number){
        return points.get(number);
    }
}
