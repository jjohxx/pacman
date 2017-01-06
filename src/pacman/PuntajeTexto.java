package pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class PuntajeTexto extends Parent {
  private static final Font PUNTAJE_FUENTE = new Font(11);
  private static final Color PUNTAJE_COLOR_LLENADO = Color.YELLOW;
  private static final int MOSTRAR_TIEMPO = 2;
  private final Text texto;

  private Timeline cronograma;

  public PuntajeTexto(String s, boolean esVisible) { 
    texto = new Text(s);
    texto.setFont(PUNTAJE_FUENTE);
    texto.setFill(PUNTAJE_COLOR_LLENADO);
    crearCronograma();
    getChildren().add(texto);
    setVisible(esVisible);
  }

  private void crearCronograma() {
    cronograma = new Timeline();
    cronograma.setCycleCount(1);
    KeyFrame kf = new KeyFrame(Duration.seconds(MOSTRAR_TIEMPO), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        setVisible(false);
      }
    });
    cronograma.getKeyFrames().add(kf);
  }

  public void mostrarTexto() {
    setVisible(true);
    cronograma.playFromStart();
  }

  public void setX(int x) {
    texto.setX(x);
  }

  public void setY(int y) {
    texto.setY(y);
  }
}
