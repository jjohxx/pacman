package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public abstract class ObjectoEnMovimiento extends Parent {
  protected static final int PASO_DE_ANIMACION = 4;
  protected static final int VELOCIDAD_DE_MOVIMIENTO = DatosDelLaberinto.REJILLA / PASO_DE_ANIMACION;

  protected static final int MOVIENDO = 1;
  protected static final int PARADO = 0;

  protected static final int MOVER_IZQUIERDA = 0;
  protected static final int MOVER_ARRIBA = 1;
  protected static final int MOVER_DERECHA = 2;
  protected static final int MOVER_ABAJO = 3;

  protected Laberinto laberinto;
  protected int estado;

  protected IntegerProperty imagenActual;
  protected Image[] imagenes;
  protected ObjectBinding imagenBindear;
  protected int contadorMovimientos;

  protected int x;
  protected int y;

  public IntegerProperty imagenX;
  public IntegerProperty imagenY;

  protected int direccionY;
  protected int direccionX;

  protected Timeline tiempo;

  public ObjectoEnMovimiento() {
    imagenActual = new SimpleIntegerProperty(0);

    imagenBindear = new ObjectBinding() {

      {
        super.bind(imagenActual);
      }

      @Override
      protected Image computeValue() {
        return imagenes[imagenActual.get()];
      }
    };

    contadorMovimientos = 0;
    direccionY = 0;
    direccionX = 0;
    tiempo = crearCronograma();
  }

  public abstract void caminarEnElPaso();
  private Timeline crearCronograma() {
    tiempo = new Timeline();
    tiempo.setCycleCount(Timeline.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.millis(45), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        caminarEnElPaso();
      }

    });
    tiempo.getKeyFrames().add(kf);

    return tiempo;
  }

  public void parar() {
    tiempo.stop();
  }

  public void pausear() {
    tiempo.pause();
  }

  public void iniciar() {
    tiempo.play();
  }

  public boolean estaCorriendo() {
    return tiempo.getStatus() == Animation.Status.RUNNING;
  }

  public boolean estaPauseado() {
    return tiempo.getStatus() == Animation.Status.PAUSED;
  }

}
