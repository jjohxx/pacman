package pacman;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class MuroRectangulo extends Parent {

  public MuroRectangulo(float x1, float y1, float x2, float y2) {
    Rectangle rectangulo = new Rectangle();
    rectangulo.setX(DatosDelLaberinto.calcGridXFloat(x1));
    rectangulo.setY(DatosDelLaberinto.calcGridYFloat(y1));
    rectangulo.setWidth(DatosDelLaberinto.calcGridXFloat(x2) - DatosDelLaberinto.calcGridXFloat(x1));
    rectangulo.setHeight(DatosDelLaberinto.calcGridYFloat(y2) - DatosDelLaberinto.calcGridYFloat(y1));
    rectangulo.setStrokeWidth(DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    rectangulo.setStroke(Color.BLUE);
    rectangulo.setArcWidth(12);
    rectangulo.setArcHeight(12);
    rectangulo.setCache(true);

    getChildren().add(rectangulo);
    DatosDelLaberinto.setBloqueDatoLaberinto(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2));
  }

}
