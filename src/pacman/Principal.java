package pacman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class Principal extends Application {

  /**
   * @param args Linea de comando en argumentos.
   */
  public static void main(String[] args) {
    Application.launch(Principal.class, args);
  }

  @Override
  public void start(Stage escenarioPrimario) {
    escenarioPrimario.setTitle("PACMAN por Jorge Ocampo y Jhonatan Mamani");
    escenarioPrimario.setWidth(DatosDelLaberinto.calcGridX(DatosDelLaberinto.TAMANIO_CUADRO_X + 2));
    escenarioPrimario.setHeight(DatosDelLaberinto.calcGridY(DatosDelLaberinto.TAMANIO_CUADRO_Y + 4));

    final Group raiz = new Group();
    final Scene escena = new Scene(raiz);
    raiz.getChildren().add(new Laberinto());
    escenarioPrimario.setScene(escena);
    escenarioPrimario.show();
  }
}
