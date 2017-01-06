package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class MuroLineaNegra extends Line {

  public MuroLineaNegra(float x1, float y1, float x2, float y2) {
    init(x1, y1, x2, y2);
    setStrokeWidth(DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA + 1);
    setStroke(Color.BLACK);
  }

  public MuroLineaNegra(Color lineaDeColor, float x1, float y1, float x2, float y2) {
    init(x1, y1, x2, y2);
    
    setStrokeWidth(DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    setStroke(lineaDeColor);
  }

  private void init(float x1, float y1, float x2, float y2) {
    setCache(true);
    if (x1 == x2) { // LINEA VERTICAL
      setStartX(DatosDelLaberinto.calcGridXFloat(x1));
      setStartY(DatosDelLaberinto.calcGridYFloat(y1) + DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
      setEndX(DatosDelLaberinto.calcGridXFloat(x2));
      setEndY(DatosDelLaberinto.calcGridYFloat(y2) - DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    }
    else { // LINEA HORIZONTAL
      setStartX(DatosDelLaberinto.calcGridXFloat(x1) + DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
      setStartY(DatosDelLaberinto.calcGridYFloat(y1));
      setEndX(DatosDelLaberinto.calcGridXFloat(x2) - DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
      setEndY(DatosDelLaberinto.calcGridYFloat(y2));
    }
  }
}
