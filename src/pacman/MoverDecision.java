package pacman;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class MoverDecision {

  public int x;
  public int y;

  public int puntaje;

  public void evaluar(PacMan pacMan, boolean eshueco) {
    if (x < 1 || y < 1 || (y >= DatosDelLaberinto.TAMANIO_CUADRO_Y) || (x >= DatosDelLaberinto.TAMANIO_CUADRO_X)) {
      puntaje = -1;
      return;
    }

    int estado = DatosDelLaberinto.getDatos(x, y);
    if (estado == DatosDelLaberinto.BLOQUE) {
      puntaje = -1;
      return;
    }

    int distancia = Math.abs(x - pacMan.x) + Math.abs(y - pacMan.y);

    if (eshueco) {
      puntaje = 500 + distancia; // 
    }
    else {
      puntaje = 500 - distancia; //
    }

  }

}
