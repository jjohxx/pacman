package pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class Punto extends Parent {

  public BooleanProperty deberiaPararLaAnimacion;
  public int tipoPunto;
  private IntegerProperty radio;
  private int radioAnimacion;
  private int delta;
  private Timeline cronograma;

  public Punto(int x, int y, int tipoPunto) {

    this.deberiaPararLaAnimacion = new SimpleBooleanProperty(false);
    this.tipoPunto = tipoPunto;

    if (tipoPunto == DatosDelLaberinto.PUNTO_MAGICO) {
      this.radio = new SimpleIntegerProperty(5);
    }
    else {
      this.radio = new SimpleIntegerProperty(1);
    }

    delta = -1;
    radioAnimacion = 3;

    Circle circulo = new Circle(x, y, this.radio.intValue(), Color.YELLOW);
    circulo.radiusProperty().bind(this.radio);

    getChildren().add(circulo);
  }

  private void crearCronograma() {
    cronograma = new Timeline();
    cronograma.setCycleCount(Timeline.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.seconds(0.25), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        hacerUnaMarca();
      }
    });

    cronograma.getKeyFrames().add(kf);
  }

  public void playCronograma() {
    if (cronograma == null) {
      crearCronograma();
    }

    cronograma.play();
  }

  public final void hacerUnaMarca() {

    if (!isVisible() || deberiaPararLaAnimacion.get()) {
      return;
    }
    radioAnimacion += delta;
    int x1 = Math.abs(radioAnimacion) + 3;

    if (x1 > 5) {
      delta = -delta;
    }
    this.radio.set(x1);
  }
}
