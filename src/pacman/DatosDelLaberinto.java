package pacman;

/**
 *
 * @author Jhonatan Mamani Mendoza
 * @author Jorge Ocampo Cerezo
 */
public final class DatosDelLaberinto {

    public static final int BLOQUE = 1;
    public static final int VACIO = 0;
    public static final int REJILLA = 16;
    public static final int TAMANIO_CUADRO_X = 29;
    public static final int TAMANIO_CUADRO_Y = 31;
    public static final int TRAZO_DE_LA_CUADRICULA = 2;
    public static final int PUNTO_MAGICO = 3;
    public static final int PUNTO_NORMAL = 2;

    private static final Object[][] PUNTEROS_DEL_PUNTO = new Object[TAMANIO_CUADRO_X + 1][TAMANIO_CUADRO_Y + 1];
    private static final int[][] LABERINTO_DATOS = new int[TAMANIO_CUADRO_X + 1][TAMANIO_CUADRO_Y + 1];
    private static final int X_OFFSET = REJILLA * 2;
    private static final int Y_OFFSET = REJILLA * 2;

    private static int puntoTotal = 0;

    private DatosDelLaberinto() {
    }

    ;

  private static int hacerRango(int a, char coordenada) {

        if (a < 0) {
            return 0;
        } else if ((coordenada == 'X') && (a > TAMANIO_CUADRO_X)) {
            return TAMANIO_CUADRO_X;
        } else if ((coordenada == 'Y') && (a > TAMANIO_CUADRO_Y)) {
            return TAMANIO_CUADRO_Y;
        }

        return a;
    }

    public static void setBloqueDatoLaberinto(int x1, int y1, int x2, int y2) {
        x1 = hacerRango(x1, 'X');
        y1 = hacerRango(y1, 'Y');
        x2 = hacerRango(x2, 'X');
        y2 = hacerRango(y2, 'Y');

        for (int i = x1; i <= x2; i++) {
            LABERINTO_DATOS[i][y1] = BLOQUE;
            LABERINTO_DATOS[i][y2] = BLOQUE;
        }

        for (int i = y1; i <= y2; i++) {
            LABERINTO_DATOS[x1][i] = BLOQUE;
            LABERINTO_DATOS[x2][i] = BLOQUE;
        }

    }

    public static int calcGridX(int x) {
        return REJILLA * x + X_OFFSET;
    }

    public static float calcGridXFloat(final float x) {
        return REJILLA * x + X_OFFSET;
    }

    public static int calcGridY(int y) {
        return REJILLA * y + Y_OFFSET;
    }

    public static float calcGridYFloat(final float y) {
        return REJILLA * y + Y_OFFSET;
    }

    public static int getDatos(int x, int y) {
        return LABERINTO_DATOS[x][y];
    }

    public static void setDatos(int x, int y, int value) {
        LABERINTO_DATOS[x][y] = value;

        if ((value == PUNTO_MAGICO) || (value == PUNTO_NORMAL)) {
            puntoTotal++;
        }
    }

    public static Object getPunto(int x, int y) {
        return PUNTEROS_DEL_PUNTO[x][y];
    }

    public static void setDot(int x, int y, Object dot) {
        PUNTEROS_DEL_PUNTO[x][y] = dot;
    }

    public static int getPuntoTotal() {
        return puntoTotal;
    }

    public static void imprimirDatos() {
        for (int i = 0; i <= TAMANIO_CUADRO_Y; i++) {
            for (int j = 0; j <= TAMANIO_CUADRO_X; j++) {
                System.out.print(LABERINTO_DATOS[j][i] + " ");
            }
            System.out.println("");
        }
    }

    public static void imprimirPuntos() {
        for (int i = 0; i <= TAMANIO_CUADRO_Y; i++) {
            for (int j = 0; j <= TAMANIO_CUADRO_X; j++) {
                if (null != PUNTEROS_DEL_PUNTO[j][i]) {
                    System.out.print(((Punto) PUNTEROS_DEL_PUNTO[j][i]).tipoPunto + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
    }

}
