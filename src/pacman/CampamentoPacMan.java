package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class CampamentoPacMan extends Arc {

    private final Timeline cronograma;

    public CampamentoPacMan(final Laberinto laberinto) {

        cronograma = new Timeline();
        cronograma.setCycleCount(1);

        KeyFrame kf1
                = new KeyFrame(Duration.millis(600),
                        new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        laberinto.pacMan.setVisible(false);

                        for (Fantasma g : laberinto.fantasmas) {
                            g.ocultar();
                        }

                        setVisible(true);
                    }

                },
                        new KeyValue(startAngleProperty(), 90),
                        new KeyValue(lengthProperty(), 360)
                );

        KeyFrame kf2
                = new KeyFrame(Duration.millis(1800),
                        new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        setVisible(false);
                        laberinto.iniciarNuevaVida();
                    }

                },
                        new KeyValue(startAngleProperty(), 270),
                        new KeyValue(lengthProperty(), 0)
                );

        cronograma.getKeyFrames().addAll(kf1, kf2);
    }

    public void pausa() {
        cronograma.pause();
    }

    public void iniciar() {
        cronograma.play();
    }

    public boolean estaCorriendo() {
        return cronograma.getStatus() == Animation.Status.RUNNING;
    }

    public boolean estaPauseado() {
        return cronograma.getStatus() == Animation.Status.PAUSED;
    }

    public void iniciarAnimacion(int x, int y) {

        setStartAngle(90);
        setCenterX(x);
        setCenterY(y);

        cronograma.playFromStart();
    }

}
