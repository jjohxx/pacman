package pacman;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class MuroRectanguloNegro extends Parent {

  public MuroRectanguloNegro(float x1, float y1, float x2, float y2) {

    Rectangle rectangulo = new Rectangle();
    rectangulo.setX(DatosDelLaberinto.calcGridXFloat(x1) + DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    rectangulo.setY(DatosDelLaberinto.calcGridYFloat(y1) + DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    rectangulo.setWidth(DatosDelLaberinto.REJILLA * (x2 - x1) - DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA * 2);
    rectangulo.setHeight(DatosDelLaberinto.REJILLA * (y2 - y1) - DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA * 2);
    rectangulo.setStrokeWidth(DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    rectangulo.setStroke(Color.BLACK);
    rectangulo.setArcWidth(3);
    rectangulo.setArcHeight(3);
    rectangulo.setCache(true);
    getChildren().add(rectangulo); 
  }

}
