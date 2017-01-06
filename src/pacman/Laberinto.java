package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */

public class Laberinto extends Parent {

  public static final boolean DEBUG = false;

  private int contadorFantasmeComido;

  public BooleanProperty juegoPauseado;

  private static final PuntajeTexto[] PUNTAJE_TEXTO = {
    new PuntajeTexto("200", false) 
    ,
    new PuntajeTexto("400", false) 
    ,
    new PuntajeTexto("800", false) 
    ,
    new PuntajeTexto("1600", false) 
  };

  public PacMan pacMan;

  public final Fantasma[] fantasmas;

  private final CampamentoPacMan campamentoPacman;
  private static final Image IMAGEN_PACMAN = new Image(Laberinto.class.getResourceAsStream("images/izq1.png"));
  private final SimpleIntegerProperty nivel;
  private boolean agregarVidaFlag;
  private final SimpleIntegerProperty contadorDeVidas;
  public BooleanProperty esperarParaIniciar;

  private final Group mensaje;
 private final BooleanProperty ulimoResultadoDeJuego;
 private final Text resultaadoDelJuegoTexto;
 private int contadorFlashing;

 private final Timeline cronogramaFlashing;
  private final Group grupo;
  public Laberinto() {

    setFocused(true);

    juegoPauseado = new SimpleBooleanProperty(false);

    pacMan = new PacMan(this, 15, 24);

    final Fantasma fantasmaRojo = new Fantasma(
            new Image(getClass().getResourceAsStream("images/fantasmarojo1.png")),
            new Image(getClass().getResourceAsStream("images/fantasmarojo2.png")),
            this,
            pacMan,
            15, // x
            14, // y
            0,  // x 
            -1, // y 
            1); // 

    final Fantasma fantasmaRosado = new Fantasma(
            new Image(getClass().getResourceAsStream("images/fantasmarosado1.png")),
            new Image(getClass().getResourceAsStream("images/fantasmarosado2.png")),
            this,
            pacMan,
            14,
            15,
            1,  // x 
            0,  // y 
            5); //
    final Fantasma fantasmaCyan = new Fantasma(
            new Image(getClass().getResourceAsStream("images/fantasmacyan1.png")),
            new Image(getClass().getResourceAsStream("images/fantasmacyan2.png")),
            this,
            pacMan,
            12,
            15,
            1,   // x
            0,   // y
            20); // t

    final Fantasma fantasmaNaranja = new Fantasma(
            new Image(getClass().getResourceAsStream("images/fantasmanaranja1.png")),
            new Image(getClass().getResourceAsStream("images/fantasmanaranja2.png")),
            this,
            pacMan,
            16,
            15,
            1,   // 
            0,   // 
            30); // 
    fantasmas = new Fantasma[] {fantasmaRojo, fantasmaRosado, fantasmaCyan, fantasmaNaranja};

    campamentoPacman = new CampamentoPacMan(this);
    campamentoPacman.setCenterX(0);
    campamentoPacman.setCenterY(0);
    campamentoPacman.setRadiusX(13);
    campamentoPacman.setRadiusY(13);
    campamentoPacman.setStartAngle(90);
    campamentoPacman.setLength(360);
    campamentoPacman.setType(ArcType.ROUND);
    campamentoPacman.setFill(Color.YELLOW);
    campamentoPacman.setVisible(false);

    contadorDeVidas = new SimpleIntegerProperty(2);

    final ImageView vidasImagen1 = new ImageView(IMAGEN_PACMAN);
    vidasImagen1.setX(DatosDelLaberinto.calcGridX(18));
    vidasImagen1.setY(DatosDelLaberinto.calcGridYFloat(DatosDelLaberinto.TAMANIO_CUADRO_Y + 0.8f));
    vidasImagen1.visibleProperty().bind(contadorDeVidas.greaterThan(0));
    vidasImagen1.setCache(true);
    final ImageView vidasImagen2 = new ImageView(IMAGEN_PACMAN);
    vidasImagen2.setX(DatosDelLaberinto.calcGridX(16));
    vidasImagen2.setY(DatosDelLaberinto.calcGridYFloat(DatosDelLaberinto.TAMANIO_CUADRO_Y + 0.8f));
    vidasImagen2.visibleProperty().bind(contadorDeVidas.greaterThan(1));
    vidasImagen2.setCache(true);
    final ImageView vidasImagen3 = new ImageView(IMAGEN_PACMAN);
    vidasImagen3.setX(DatosDelLaberinto.calcGridX(14));
    vidasImagen3.setY(DatosDelLaberinto.calcGridYFloat(DatosDelLaberinto.TAMANIO_CUADRO_Y + 0.8f));
    vidasImagen3.visibleProperty().bind(contadorDeVidas.greaterThan(2));
    vidasImagen3.setCache(true);
    final ImageView[] vidasImagenes = new ImageView[] {vidasImagen1, vidasImagen2, vidasImagen3};

    nivel = new SimpleIntegerProperty(1);
    agregarVidaFlag = true;
    esperarParaIniciar = new SimpleBooleanProperty(true);

    mensaje = new Group();
    final Rectangle rectanguloMensaje = new Rectangle(DatosDelLaberinto.calcGridX(5),
            DatosDelLaberinto.calcGridYFloat(17.5f),
            DatosDelLaberinto.REJILLA * 19,
            DatosDelLaberinto.REJILLA * 5);
    rectanguloMensaje.setStroke(Color.RED);
    rectanguloMensaje.setStrokeWidth(5);
    rectanguloMensaje.setFill(Color.CYAN);
    rectanguloMensaje.setOpacity(0.75);
    rectanguloMensaje.setArcWidth(25);
    rectanguloMensaje.setArcHeight(25);

    final StringBinding mensajebinding = new StringBinding() {

            {
                super.bind(juegoPauseado);
            }

            @Override
            protected String computeValue() {
                if (juegoPauseado.get()) {
                    return " PRESIONA LA TECLA 'P' \n PARA TERMINAR";
                } else {
                    return "PRESIONA CUALQUIER TECLA \n PARA INICIAR!";
                }
            }
        };

    final Text mensajeDeTexto = new Text(DatosDelLaberinto.calcGridX(6),
            DatosDelLaberinto.calcGridYFloat(20.5f),
            "CUALQUIER TECLA \n PARA INICIAR!");
    mensajeDeTexto.textProperty().bind(mensajebinding);
    mensajeDeTexto.setFont(new Font(18));
    mensajeDeTexto.setFill(Color.RED);
    mensaje.getChildren().add(rectanguloMensaje);
    mensaje.getChildren().add(mensajeDeTexto);

    ulimoResultadoDeJuego = new SimpleBooleanProperty(false);

    final StringBinding lastGameResultBinding = new StringBinding() {

        {
            super.bind(ulimoResultadoDeJuego);
        }

        @Override
        protected String computeValue() {
            if (ulimoResultadoDeJuego.get()) {
                return "  GANASTE ";
            } else {
                return "PERDISTE   ";
            }
        }
    };

    resultaadoDelJuegoTexto = new Text(DatosDelLaberinto.calcGridX(11),
            DatosDelLaberinto.calcGridY(11) + 8,
            " GANASTE ");
    resultaadoDelJuegoTexto.textProperty().bind(lastGameResultBinding);
    resultaadoDelJuegoTexto.setFont(new Font(20));
    resultaadoDelJuegoTexto.setFill(Color.RED);
    resultaadoDelJuegoTexto.setVisible(false);

    contadorFlashing = 0;

    cronogramaFlashing = new Timeline();
    cronogramaFlashing.setCycleCount(5);
    final KeyFrame kf = new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
         resultaadoDelJuegoTexto.setVisible(!resultaadoDelJuegoTexto.isVisible());
         if (++contadorFlashing == 5) {
           mensaje.setVisible(true);
           esperarParaIniciar.set(true);
         }
      }

    });
    cronogramaFlashing.getKeyFrames().add(kf);

    grupo = new Group();

    final Rectangle fondoNegro = new Rectangle(0, 0,
            DatosDelLaberinto.calcGridX(DatosDelLaberinto.TAMANIO_CUADRO_X + 2),
            DatosDelLaberinto.calcGridY(DatosDelLaberinto.TAMANIO_CUADRO_Y + 3));
    fondoNegro.setFill(Color.BLACK);
    fondoNegro.setCache(true);
    grupo.getChildren().add(fondoNegro);

    grupo.getChildren().add(new MuroRectangulo(0, 0, DatosDelLaberinto.TAMANIO_CUADRO_X, DatosDelLaberinto.TAMANIO_CUADRO_Y));

    grupo.getChildren().add(new MuroRectangulo(14, -0.5f, 15, 4));
    grupo.getChildren().add(new MuroRectanguloNegro(13.8f, -1, 15.3f, 0));

    grupo.getChildren().add(new MuroRectangulo(2, 2, 5, 4)); 
    grupo.getChildren().add(new MuroRectangulo(7, 2, 12, 4)); 
    grupo.getChildren().add(new MuroRectangulo(17, 2, 22, 4));
    grupo.getChildren().add(new MuroRectangulo(24, 2, 27, 4));
    grupo.getChildren().add(new MuroRectangulo(2, 6, 5, 7)); 

    grupo.getChildren().add(new MuroRectangulo(14, 6, 15, 10));
    grupo.getChildren().add(new MuroRectangulo(10, 6, 19, 7));
    grupo.getChildren().add(new MuroLineaNegra(14.05f, 7, 14.95f, 7));

    grupo.getChildren().add(new MuroRectangulo(7.5f, 9, 12, 10));
    grupo.getChildren().add(new MuroRectangulo(7, 6, 8, 13));
    grupo.getChildren().add(new MuroLineaNegra(8, 9, 8, 10));

    grupo.getChildren().add(new MuroRectangulo(17, 9, 21.5f, 10));
    grupo.getChildren().add(new MuroRectangulo(21, 6, 22, 13));
    grupo.getChildren().add(new MuroLineaNegra(21, 9, 21, 10));

    grupo.getChildren().add(new MuroRectangulo(24, 6, 27, 7)); 

    grupo.getChildren().add(new MuroRectangulo(10, 12, 19, 17));
    grupo.getChildren().add(new MuroRectangulo(10.5f, 12.5f, 18.5f, 16.5f));
    final Rectangle cageRect = new Rectangle(DatosDelLaberinto.calcGridX(13),
            DatosDelLaberinto.calcGridY(12),
            DatosDelLaberinto.REJILLA * 3,
            DatosDelLaberinto.REJILLA / 2);
    cageRect.setStroke(Color.GREY);
    cageRect.setFill(Color.GREY);
    cageRect.setCache(true);
    grupo.getChildren().add(cageRect);

    grupo.getChildren().add(new MuroRectangulo(7, 15, 8, 20));

    grupo.getChildren().add(new MuroRectangulo(21, 15, 22, 20));

    grupo.getChildren().add(new MuroRectangulo(14, 19, 15, 23));
    grupo.getChildren().add(new MuroRectangulo(10, 19, 19, 20));
    grupo.getChildren().add(new MuroLineaNegra(14.05f, 20, 14.95f, 20));

    grupo.getChildren().add(new MuroRectangulo(4, 22, 5, 26));
    grupo.getChildren().add(new MuroRectangulo(2, 22, 5, 23));
    grupo.getChildren().add(new MuroRectanguloNegro(4, 22.05f, 5, 23.2f));

    grupo.getChildren().add(new MuroRectangulo(7, 22, 12, 23)); 

    grupo.getChildren().add(new MuroRectangulo(24, 22, 25, 26));
    grupo.getChildren().add(new MuroRectangulo(24, 22, 27, 23));
    grupo.getChildren().add(new MuroRectanguloNegro(24, 22.05f, 25, 23.2f));

    grupo.getChildren().add(new MuroRectangulo(17, 22, 22, 23)); 

    grupo.getChildren().add(new MuroRectangulo(-1, 25, 2, 26));
    grupo.getChildren().add(new MuroRectangulo(27, 25, DatosDelLaberinto.TAMANIO_CUADRO_X + 1, 26)); 

    grupo.getChildren().add(new MuroRectangulo(7, 25, 8, 29));
    grupo.getChildren().add(new MuroRectangulo(2, 28, 12, 29));
    grupo.getChildren().add(new MuroLineaNegra(7.05f, 28, 7.95f, 28));

    grupo.getChildren().add(new MuroRectangulo(14, 25, 15, 29));
    grupo.getChildren().add(new MuroRectangulo(10, 25, 19, 26));
    grupo.getChildren().add(new MuroLineaNegra(14.05f, 26, 14.95f, 26));

    grupo.getChildren().add(new MuroRectangulo(21, 25, 22, 29));
    grupo.getChildren().add(new MuroRectangulo(17, 28, 27, 29));
    grupo.getChildren().add(new MuroLineaNegra(21.05f, 28, 21.95f, 28));

    final Rectangle outerWall = new Rectangle(DatosDelLaberinto.calcGridXFloat(-0.5f),
            DatosDelLaberinto.calcGridYFloat(-0.5f),
            (DatosDelLaberinto.TAMANIO_CUADRO_X + 1) * DatosDelLaberinto.REJILLA,
            (DatosDelLaberinto.TAMANIO_CUADRO_Y + 1) * DatosDelLaberinto.REJILLA);
    outerWall.setStrokeWidth(DatosDelLaberinto.TRAZO_DE_LA_CUADRICULA);
    outerWall.setStroke(Color.BLUE);
    outerWall.setFill(null);
    outerWall.setArcWidth(12);
    outerWall.setArcHeight(12);
    outerWall.setCache(true);
    grupo.getChildren().add(outerWall);

    grupo.getChildren().add(new MuroRectangulo(-1, 9, 5, 13)); 
    grupo.getChildren().add(new MuroRectangulo(-1, 9.5f, 4.5f, 12.5f)); 
    grupo.getChildren().add(new MuroRectangulo(-1, 15, 5, 20)); 
    grupo.getChildren().add(new MuroRectangulo(-1, 15.5f, 4.5f, 19.5f)); 

    grupo.getChildren().add(new MuroRectangulo(DatosDelLaberinto.TAMANIO_CUADRO_X - 5, 9, DatosDelLaberinto.TAMANIO_CUADRO_X + 1, 13)); 
    grupo.getChildren().add(new MuroRectangulo(DatosDelLaberinto.TAMANIO_CUADRO_X - 4.5f, 9.5f, DatosDelLaberinto.TAMANIO_CUADRO_X + 1, 12.5f)); 
    grupo.getChildren().add(new MuroRectangulo(DatosDelLaberinto.TAMANIO_CUADRO_X - 5, 15, DatosDelLaberinto.TAMANIO_CUADRO_X + 1, 20)); 
    grupo.getChildren().add(new MuroRectangulo(DatosDelLaberinto.TAMANIO_CUADRO_X - 4.5f, 15.5f, DatosDelLaberinto.TAMANIO_CUADRO_X + 1, 19.5f)); 

    grupo.getChildren().add(new MuroRectanguloNegro(-2, 8, -0.5f, DatosDelLaberinto.TAMANIO_CUADRO_Y)); 
    grupo.getChildren().add(new MuroRectanguloNegro(-0.5f, 8, 0, 9.5f)); 
    grupo.getChildren().add(new MuroRectanguloNegro(-0.5f, 19.5f, 0, DatosDelLaberinto.TAMANIO_CUADRO_Y)); 

    grupo.getChildren().add(new MuroRectanguloNegro(DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, 8, DatosDelLaberinto.TAMANIO_CUADRO_X + 2, DatosDelLaberinto.TAMANIO_CUADRO_Y)); 
    grupo.getChildren().add(new MuroRectanguloNegro(DatosDelLaberinto.TAMANIO_CUADRO_X, 8, DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, 9.5f));
    grupo.getChildren().add(new MuroRectanguloNegro(DatosDelLaberinto.TAMANIO_CUADRO_X, 19.5f, DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, DatosDelLaberinto.TAMANIO_CUADRO_Y)); 

    grupo.getChildren().add(new MuroRectanguloNegro(-1, 13, 1, 15)); 
    grupo.getChildren().add(new MuroRectanguloNegro(DatosDelLaberinto.TAMANIO_CUADRO_X - 1, 13, DatosDelLaberinto.TAMANIO_CUADRO_X + 1, 15)); 

    grupo.getChildren().add(new MuroLineaNegra(Color.BLUE, -0.5f, 9, -0.5f, 9.5f));
    grupo.getChildren().add(new MuroLineaNegra(Color.BLUE, -0.5f, 19.5f, -0.5f, 20));
    grupo.getChildren().add(new MuroLineaNegra(Color.BLUE, DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, 9, DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, 9.5f));
    grupo.getChildren().add(new MuroLineaNegra(Color.BLUE, DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, 19.5f, DatosDelLaberinto.TAMANIO_CUADRO_X + 0.5f, 20));

    final Text puntajeDeTexto = new Text(DatosDelLaberinto.calcGridX(0),
            DatosDelLaberinto.calcGridY(DatosDelLaberinto.TAMANIO_CUADRO_Y + 2),
            "PUNTAJE: " + pacMan.puntaje);
    puntajeDeTexto.textProperty().bind(pacMan.puntaje.asString("PUNTAJE: %1d  "));
    puntajeDeTexto.setFont(new Font(20));
    puntajeDeTexto.setFill(Color.YELLOW);
    puntajeDeTexto.setCache(true);
    grupo.getChildren().add(puntajeDeTexto);

    grupo.getChildren().addAll(PUNTAJE_TEXTO);
    grupo.getChildren().add(campamentoPacman);
    grupo.getChildren().addAll(vidasImagenes);
    grupo.getChildren().add(resultaadoDelJuegoTexto);

    final Text nivelDeTexto = new Text(DatosDelLaberinto.calcGridX(22),
            DatosDelLaberinto.calcGridY(DatosDelLaberinto.TAMANIO_CUADRO_Y + 2),
            "NIVEL: " + nivel);
    nivelDeTexto.textProperty().bind(nivel.asString("NIVEL: %1d "));
    nivelDeTexto.setFont(new Font(20));
    nivelDeTexto.setFill(Color.YELLOW);
    nivelDeTexto.setCache(true);
    grupo.getChildren().add(nivelDeTexto);
    grupo.setFocusTraversable(true); 
    grupo.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override public void handle(KeyEvent ke) {
        onKeyPressed(ke);
      }
    });


    colocarPuntoHorizontalmente(2,12,1);
    colocarPuntoHorizontalmente(17,27,1);
    
    colocarPuntoHorizontalmente(2,27,5);

    colocarPuntoHorizontalmente(2,5,8);
    colocarPuntoHorizontalmente(24,27,8);

    colocarPuntoHorizontalmente(10,13,8);
    colocarPuntoHorizontalmente(16,19,8);

    colocarPuntoHorizontalmente(2,12,21);
    colocarPuntoHorizontalmente(17,27,21);

    colocarPuntoHorizontalmente(2,2,24);
    colocarPuntoHorizontalmente(27,27,24);

    colocarPuntoHorizontalmente(7,12,24);
    colocarPuntoHorizontalmente(17,22,24);

    colocarPuntoHorizontalmente(2,5,27);
    colocarPuntoHorizontalmente(24,27,27);

    colocarPuntoHorizontalmente(10,12,27);
    colocarPuntoHorizontalmente(17,19,27);

    colocarPuntoHorizontalmente(2,27,30); 


    colocarPuntoVerticalmente(1,1,8);
    colocarPuntoVerticalmente(28,1,8);

    colocarPuntoVerticalmente(1,21,24);
    colocarPuntoVerticalmente(28,21,24);

    colocarPuntoVerticalmente(1,27,30);
    colocarPuntoVerticalmente(28,27,30);
    
    colocarPuntoVerticalmente(3,24,27);
    colocarPuntoVerticalmente(26,24,27);

    colocarPuntoVerticalmente(6,1,27);
    colocarPuntoVerticalmente(23,1,27);

    colocarPuntoVerticalmente(9,5,8);
    colocarPuntoVerticalmente(20,5,8);

    colocarPuntoVerticalmente(9,24,27);
    colocarPuntoVerticalmente(20,24,27);

    colocarPuntoVerticalmente(13,1,4);
    colocarPuntoVerticalmente(16,1,4);

    colocarPuntoVerticalmente(13,21,24);
    colocarPuntoVerticalmente(16,21,24);

    colocarPuntoVerticalmente(13,27,30);
    colocarPuntoVerticalmente(16,27,30);

    grupo.getChildren().add(pacMan);

    grupo.getChildren().addAll(fantasmas);

    grupo.getChildren().add(new MuroRectanguloNegro(-2, 13, -0.5f, 15));
    grupo.getChildren().add(new MuroRectanguloNegro(29.5f, 13, 31, 15));

    grupo.getChildren().add(mensaje);


    getChildren().add(grupo);

    if (DEBUG) {
      DatosDelLaberinto.imprimirDatos();
      DatosDelLaberinto.imprimirPuntos();
    }
  }


  public void onKeyPressed(KeyEvent e) {
    if (esperarParaIniciar.get()) {
      esperarParaIniciar.set(false);
      iniciarNuevoJuego();
      return;
    }

    if (e.getCode() == KeyCode.P) {
      if (juegoPauseado.get()) {
        finalDelJuego();
      } else {
        pausearJuego();
      }

      return;
    }

    if (juegoPauseado.get()) {
      return;
    }

    if (e.getCode() == KeyCode.DOWN) {
      pacMan.setBufferDeTeclado(ObjectoEnMovimiento.MOVER_ABAJO);
    } else if (e.getCode() == KeyCode.UP) {
      pacMan.setBufferDeTeclado(ObjectoEnMovimiento.MOVER_ARRIBA);
    } else if (e.getCode() == KeyCode.RIGHT) {
      pacMan.setBufferDeTeclado(ObjectoEnMovimiento.MOVER_DERECHA);
    } else if (e.getCode() == KeyCode.LEFT) {
      pacMan.setBufferDeTeclado(ObjectoEnMovimiento.MOVER_IZQUIERDA);
    }

  }


  public final Punto crearPunto(int x1, int y1, int tipo) {
    Punto d = new Punto(DatosDelLaberinto.calcGridX(x1), DatosDelLaberinto.calcGridY(y1), tipo);

    if (tipo == DatosDelLaberinto.PUNTO_MAGICO) {
      d.playCronograma();

      d.deberiaPararLaAnimacion.bind(juegoPauseado.or(esperarParaIniciar)); 
    }
    DatosDelLaberinto.setDatos(x1, y1, tipo);

    DatosDelLaberinto.setDot(x1, y1, d);
    return d;
  }

  public final void colocarPuntoHorizontalmente(int x1, int x2, int y) {

    Punto punto;
    for (int x = x1; x <= x2; x++) {
      if (DatosDelLaberinto.getDatos(x, y) == DatosDelLaberinto.VACIO) {
        int tipoPunto;
        if ((x == 28 || x == 1) && (y == 3 || y == 24)) {
          tipoPunto = DatosDelLaberinto.PUNTO_MAGICO;
        } else {
          tipoPunto = DatosDelLaberinto.PUNTO_NORMAL;
        }

        punto = crearPunto(x, y, tipoPunto);
        grupo.getChildren().add(punto);
      }
      else {
        if (DEBUG) {
          System.out.println("!! PELIGRO intente colocar en otros puntos, esta ocupado. (" + x + ", " + y + ")");
        }
      }
    }
  }

  public final void colocarPuntoVerticalmente(int x, int y1, int y2) {
    Punto punto;
    for (int y = y1; y <= y2; y++) {
      if (DatosDelLaberinto.getDatos(x, y) == DatosDelLaberinto.VACIO) {
        int tipoPunto;

        if ( (x == 28 || x == 1) && (y == 3 || y == 24) ) {
          tipoPunto = DatosDelLaberinto.PUNTO_MAGICO;
        }
        else {
          tipoPunto = DatosDelLaberinto.PUNTO_NORMAL;
        }

        punto = crearPunto(x, y, tipoPunto);
        grupo.getChildren().add(punto);
      }
      else {
        if (DEBUG) {
          System.out.println("!! PELIGRO intente colocar en otros puntos, esta ocupado. (" + x + ", " + y + ")");
        }
      }
    }
  }


  public void hacerFantasmaHueco() {

    contadorFantasmeComido = 0;

    for (Fantasma g : fantasmas) {
      g.cambiarAHuecoFantasma();
    }
  }

  public boolean hasMet(Fantasma g) {

    int distanciaMantenida = 22;

    int x1 = g.imagenX.get();
    int x2 = pacMan.imagenX.get();

    int diffX = Math.abs(x1 - x2);

    if (diffX >= distanciaMantenida) {
      return false;
    }

    int y1 = g.imagenY.get();
    int y2 = pacMan.imagenY.get();
    int diffY = Math.abs(y1 - y2);

    if (diffY >= distanciaMantenida) {
      return false;
    }
    if (diffY * diffY + diffX * diffX <= distanciaMantenida * distanciaMantenida) {
      return true;
    }

    return false;
  }

  public void pacmanEncuentraAFantasma() {

    for (Fantasma g : fantasmas) {
      if (hasMet(g)) {
        if (g.esHueco) {
          pacmanComeFantasma(g);
        }
        else {
          for (Fantasma fantasma : fantasmas) {
            fantasma.parar();
          }
          pacMan.parar();

          campamentoPacman.iniciarAnimacion(pacMan.imagenX.get(), pacMan.imagenY.get());
          break;
        }
      }
    }
  }

  public void pacmanComeFantasma(Fantasma g) {
    contadorFantasmeComido++;

    int multiplicador = 1;
    for (int i = 1; i <= contadorFantasmeComido; i++) {
      multiplicador += multiplicador;
    }

    pacMan.puntaje.set(pacMan.puntaje.get() + multiplicador * 100);
    if ( agregarVidaFlag && (pacMan.puntaje.get() >= 10000) ) {
      agregarVida();
    }

    PuntajeTexto st = PUNTAJE_TEXTO[contadorFantasmeComido - 1];
    st.setX(g.imagenX.get() - 10);
    st.setY(g.imagenY.get());

    g.parar();
    g.resetearEstado();
    g.contadorDeLaTrampa = -10;

    st.mostrarTexto();
  }

  public void finalDelJuego() {

    if (!juegoPauseado.get()) {
      return;
    }

    mensaje.setVisible(false);

    for (Fantasma g : fantasmas) {
      if (g.estaPauseado()) {
        g.iniciar();
      }
    }

    if (pacMan.estaPauseado()) {
      pacMan.iniciar();
    }

    if (campamentoPacman.estaPauseado()) {
      campamentoPacman.iniciar();
    }

    if (cronogramaFlashing.getStatus() == Animation.Status.PAUSED) {
      cronogramaFlashing.play();
    }

    juegoPauseado.set(false);

  }

  public void pausearJuego() {

    if ( esperarParaIniciar.get() || juegoPauseado.get() ) {
      return;
    }

    mensaje.setVisible(true);

    for (Fantasma g : fantasmas) {
      if (g.estaCorriendo()) {
        g.pausear();
      }
    }

    if (pacMan.estaCorriendo()) {
      pacMan.pausear();
    }

    if (campamentoPacman.estaCorriendo()) {
      campamentoPacman.pausa();
    }

    if (cronogramaFlashing.getStatus() == Animation.Status.RUNNING) {
      cronogramaFlashing.pause();
    }
    juegoPauseado.set(true);
  }

  public void iniciarNuevoJuego() {

    mensaje.setVisible(false);
    pacMan.resetearEstado();

    resultaadoDelJuegoTexto.setVisible(false);

    if (!ulimoResultadoDeJuego.get()) {
      nivel.set(1);
      agregarVidaFlag = true;
      pacMan.puntaje.set(0);
      pacMan.contarPuntoComido = 0;

      contadorDeVidas.set(2);
    }
    else {
      ulimoResultadoDeJuego.set(false);
      nivel.set(nivel.get() + 1);
    }

    for (int x = 1; x <= DatosDelLaberinto.TAMANIO_CUADRO_X; x++) {
      for (int y = 1; y <= DatosDelLaberinto.TAMANIO_CUADRO_Y; y++) {
        Punto punto = (Punto) DatosDelLaberinto.getPunto(x, y);
        if ( (punto != null) && !punto.isVisible() ) {
          punto.setVisible(true);
        }
      }
    }
    for (Fantasma g : fantasmas) {
      g.resetearEstado();
    }

}

  public void iniciarNuevoNivel() {

    ulimoResultadoDeJuego.set(true);

    pacMan.ocultar();
    pacMan.contarPuntoComido = 0;

    for (Fantasma g : fantasmas) {
      g.ocultar();
    }

    contadorFlashing = 0;
    cronogramaFlashing.playFromStart();
  }

  public void iniciarNuevaVida() {

    if (contadorDeVidas.get() > 0) {
      contadorDeVidas.set(contadorDeVidas.get() - 1);
    }
    else {
      ulimoResultadoDeJuego.set(false);
      contadorFlashing = 0;
      cronogramaFlashing.playFromStart();
      return;
    }

    pacMan.resetearEstado();

    for (Fantasma g : fantasmas) {
      g.resetearEstado();
    }
  }

  public void agregarVida() {

    if (agregarVidaFlag) {
      contadorDeVidas.set(contadorDeVidas.get() + 1);
      agregarVidaFlag = false;
    }
  }

}
