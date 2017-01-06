package pacman;

import javafx.animation.Animation;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public class PacMan extends ObjectoEnMovimiento {

    public int contarPuntoComido;
    public SimpleIntegerProperty puntaje;
    private static final int[] GRADO_DE_ROTACION = new int[]{0, 90, 180, 270};
    private int bufferDelTeclado;
    private final SimpleIntegerProperty actualDireccion;
    private Laberinto laberinto;
    public PacMan(Laberinto laberinto, int x, int y) {

        this.laberinto = laberinto;
        this.x = x;
        this.y = y;

        Image imagenPorDefecto = new Image(getClass().getResourceAsStream("images/izq1.png"));
        imagenes = new Image[]{imagenPorDefecto,
            new Image(getClass().getResourceAsStream("images/izq2.png")),
            imagenPorDefecto,
            new Image(getClass().getResourceAsStream("images/round.png"))
        };

        contarPuntoComido = 0;
        puntaje = new SimpleIntegerProperty(0);
        actualDireccion = new SimpleIntegerProperty(MOVER_IZQUIERDA);

        imagenX = new SimpleIntegerProperty(DatosDelLaberinto.calcGridX(x));
        imagenY = new SimpleIntegerProperty(DatosDelLaberinto.calcGridX(y));

        direccionY = -1;
        direccionX = 0;

        ImageView imagenPacman = new ImageView(imagenPorDefecto);
        imagenPacman.xProperty().bind(imagenX.add(-13));
        imagenPacman.yProperty().bind(imagenY.add(-13));
        imagenPacman.imageProperty().bind(imagenBindear);
        IntegerBinding rotacion = new IntegerBinding() {

            {
                super.bind(actualDireccion);
            }

            @Override
            protected int computeValue() {
                return GRADO_DE_ROTACION[actualDireccion.get()];
            }
        };
        imagenPacman.rotateProperty().bind(rotacion);

        bufferDelTeclado = -1;

        getChildren().add(imagenPacman);
    }

    private void moverHorizontalmente() {

        contadorMovimientos++;

        if (contadorMovimientos < PASO_DE_ANIMACION) {
            imagenX.set(imagenX.get() + (direccionY * VELOCIDAD_DE_MOVIMIENTO));
        } else {
            contadorMovimientos = 0;
            x += direccionY;

            imagenX.set(DatosDelLaberinto.calcGridX(x));

            int siguienteX = direccionY + x;

            if ((y == 14) && (siguienteX <= 1 || siguienteX >= 28)) {
                if ((siguienteX < -1) && (direccionY < 0)) {
                    x = DatosDelLaberinto.TAMANIO_CUADRO_X;
                    imagenX.set(DatosDelLaberinto.calcGridX(x));
                } else {
                    if ((siguienteX > 30) && (direccionY > 0)) {
                        x = 0;
                        imagenX.set(DatosDelLaberinto.calcGridX(x));
                    }
                }
            } else if (DatosDelLaberinto.getDatos(siguienteX, y) == DatosDelLaberinto.BLOQUE) {
                estado = PARADO;
            }
        }
    }

    private void moverVerticalmente() {

        contadorMovimientos++;

        if (contadorMovimientos < PASO_DE_ANIMACION) {
            imagenY.set(imagenY.get() + (direccionX * VELOCIDAD_DE_MOVIMIENTO));
        } else {
            contadorMovimientos = 0;
            y += direccionX;
            imagenY.set(DatosDelLaberinto.calcGridX(y));
            int sigienteY = direccionX + y;
            if (DatosDelLaberinto.getDatos(x, sigienteY) == DatosDelLaberinto.BLOQUE) {
                estado = PARADO;
            }
        }
    }

    private void moverDerecha() {

        if (actualDireccion.get() == MOVER_DERECHA) {
            return;
        }

        int siguienteX = x + 1;

        if (siguienteX >= DatosDelLaberinto.TAMANIO_CUADRO_X) {
            return;
        }

        if (DatosDelLaberinto.getDatos(siguienteX, y) == DatosDelLaberinto.BLOQUE) {
            return;
        }

        direccionY = 1;
        direccionX = 0;

        bufferDelTeclado = -1;
        actualDireccion.set(MOVER_DERECHA);
        estado = MOVIENDO;
    }

    private void moverIzquierda() {

        if (actualDireccion.get() == MOVER_IZQUIERDA) {
            return;
        }

        int siguienteX = x - 1;

        if (siguienteX <= 1) {
            return;
        }

        if (DatosDelLaberinto.getDatos(siguienteX, y) == DatosDelLaberinto.BLOQUE) {
            return;
        }

        direccionY = -1;
        direccionX = 0;

        bufferDelTeclado = -1;
        actualDireccion.set(MOVER_IZQUIERDA);
        estado = MOVIENDO;
    }

    private void moverArriba() {

        if (actualDireccion.get() == MOVER_ARRIBA) {
            return;
        }

        int siguienteY = y - 1;

        if (siguienteY <= 1) {
            return;
        }

        if (DatosDelLaberinto.getDatos(x, siguienteY) == DatosDelLaberinto.BLOQUE) {
            return;
        }

        direccionY = 0;
        direccionX = -1;

        bufferDelTeclado = -1;
        actualDireccion.set(MOVER_ARRIBA);
        estado = MOVIENDO;
    }

    private void moverAbajo() {

        if (actualDireccion.get() == MOVER_ABAJO) {
            return;
        }

        int siguienteY = y + 1;

        if (siguienteY >= DatosDelLaberinto.TAMANIO_CUADRO_Y) {
            return;
        }

        if (DatosDelLaberinto.getDatos(x, siguienteY) == DatosDelLaberinto.BLOQUE) {
            return;
        }

        direccionY = 0;
        direccionX = 1;

        bufferDelTeclado = -1;
        actualDireccion.set(MOVER_ABAJO);

        estado = MOVIENDO;
    }

    private void manejadorDeTecladoDeEntrada() {

        if (bufferDelTeclado < 0) {
            return;
        }

        if (bufferDelTeclado == MOVER_IZQUIERDA) {
            moverIzquierda();
        } else if (bufferDelTeclado == MOVER_DERECHA) {
            moverDerecha();
        } else if (bufferDelTeclado == MOVER_ARRIBA) {
            moverArriba();
        } else if (bufferDelTeclado == MOVER_ABAJO) {
            moverAbajo();
        }

    }

    public void setBufferDeTeclado(int k) {
        bufferDelTeclado = k;
    }

    private void actualizarPuntaje() {
        if (y != 14 || (x > 0 && x < DatosDelLaberinto.TAMANIO_CUADRO_X)) {
            Punto punto = (Punto) DatosDelLaberinto.getPunto(x, y);

            if (punto != null && punto.isVisible()) {
                puntaje.set(puntaje.get() + 10);
                punto.setVisible(false);
                contarPuntoComido++;

                if (puntaje.get() >= 10000) {
                    laberinto.agregarVida();
                }

                if (punto.tipoPunto == DatosDelLaberinto.PUNTO_MAGICO) {
                    laberinto.hacerFantasmaHueco();
                }

                if (contarPuntoComido >= DatosDelLaberinto.getPuntoTotal()) {
                    laberinto.iniciarNuevoNivel();
                }
            }
        }
    }

    public void ocultar() {
        setVisible(false);
        tiempo.stop();
    }

    @Override
    public void caminarEnElPaso() {
        if (laberinto.juegoPauseado.get()) {

            if (tiempo.getStatus() != Animation.Status.PAUSED) {
                tiempo.pause();
            }
            return;
        }
        if (imagenActual.get() == 0) {
            manejadorDeTecladoDeEntrada();
        }

        if (estado == MOVIENDO) {

            if (direccionY != 0) {
                moverHorizontalmente();
            }

            if (direccionX != 0) {
                moverVerticalmente();
            }

            if (imagenActual.get() < PASO_DE_ANIMACION - 1) {
                imagenActual.set(imagenActual.get() + 1);
            } else {
                imagenActual.set(0);
                actualizarPuntaje();
            }
        }
        laberinto.pacmanEncuentraAFantasma();
    }

    public void resetearEstado() {
        estado = MOVIENDO;
        actualDireccion.set(MOVER_IZQUIERDA);
        direccionY = -1;
        direccionX = 0;

        bufferDelTeclado = -1;
        imagenActual.set(0);
        contadorMovimientos = 0;

        x = 15;
        y = 24;

        imagenX.set(DatosDelLaberinto.calcGridX(x));
        imagenY.set(DatosDelLaberinto.calcGridY(y));

        setVisible(true);
        iniciar();
    }

}
