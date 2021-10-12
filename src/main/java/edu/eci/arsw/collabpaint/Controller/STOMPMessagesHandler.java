package edu.eci.arsw.collabpaint.Controller;

import edu.eci.arsw.collabpaint.model.Point;
import edu.eci.arsw.collabpaint.model.Polygon;
import edu.eci.arsw.collabpaint.persistence.PointStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    private PointStock pointStock;

    @MessageMapping("/newpoint{numdibujo}")
    public void handlePointEvent(Point pt, @DestinationVariable String numdibujo) throws Exception {
        System.out.println("Nuevo punto recibido en el servidor!:"+pt);
        Polygon polygon = pointStock.addPoint(Integer.parseInt(numdibujo),pt);
        msgt.convertAndSend("/topic/newpoint"+numdibujo, pt);
        if(polygon.nPoints()>=3){
            msgt.convertAndSend("/topic/newpolygon"+numdibujo, polygon);
        }
    }
}