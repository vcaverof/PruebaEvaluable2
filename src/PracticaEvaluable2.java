import java.io.IOException;
import java.util.Random;

import com.sun.jna.*;
import com.sun.jna.platform.win32.User32;

//java -cp ".;libs/jna.jar;libs/jnaplatform.jar" PracticaEvaluable2

public class PracticaEvaluable2 {

    //Variables de los botones que vamos a utilizar
    public static final int VK_UP = 0x26;   // Tecla flecha arriba
    public static final int VK_DOWN = 0x28; // Tecla flecha abajo
    public static final int VK_LEFT = 0x25; // Tecla flecha izquierda
    public static final int VK_RIGHT = 0x27; // Tecla flecha derecha
    public static final int VK_RETURN = 0x0D; //Tecla Enter
    public static final int TECLA_1 = 0x31;
    public static final int TECLA_2 = 0x32;
    public static final int TECLA_3 = 0x33;
    public static final int TECLA_4 = 0x34;
    public static final int TECLA_5 = 0x35;
    public static final int TECLA_6 = 0x36;
    public static final int TECLA_7 = 0x37;
    public static final int TECLA_8 = 0x38;
    public static final int TECLA_9 = 0x39;
    public static final int TECLA_N = 0x4E;
    public static final int TECLA_R = 0x52;
    public static final int TECLA_U = 0x55;
    public static final int TECLA_S = 0x53;


    private static final int TAM = 8;
    private static Random rand = new Random();
    private static int golpes = 0;
    private static int nivel = 1;
    private static int filaPosicion = 1;
    private static int columnaPosicion = 1;
    private static int[][] tablero = new int[TAM][TAM];
    private static int[][] tableroCopia = new int[TAM][TAM];


    // Variables para mantener el estado de las teclas
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean enterPressed = false;
    private static boolean tecla1Pressed = false;
    private static boolean tecla2Pressed = false;
    private static boolean tecla3Pressed = false;
    private static boolean tecla4Pressed = false;
    private static boolean tecla5Pressed = false;
    private static boolean tecla6Pressed = false;
    private static boolean tecla7Pressed = false;
    private static boolean tecla8Pressed = false;
    private static boolean tecla9Pressed = false;
    private static boolean teclaNPressed = false;
    private static boolean teclaRPressed = false;
    private static boolean teclaUPressed = false;
    private static boolean teclaSPressed = false;


    //Arrays para guardar los golpes y usarlos al utilizar deshacer
    private int[] golpesFilas = new int[0];
    private int[] golpesColumnas = new int[0];

    public static void main(String[] args) {

        String opcion = "A";
        generarTablero(nivel); //Generar tablero lleno de 0

        do {
            //Limpiar pantalla antes de iniciar el juego (para cmd)
            borrar();


            //Mostrar interfaz
            System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S )");
            System.out.println();

            mostrarTablero(filaPosicion, columnaPosicion); //Mostrar el tablero generado, con la posición designada seleccionada (1,1)

            System.out.print("Nivel de juego ( L ): " + nivel);
            System.out.println("\t\tNuevo nivel");
            System.out.println("Golpes: " + golpes);
            System.out.println("Instrucciones: ");
            System.out.println("\tMueve el cursor a un botón del tablero (con las flechas)");
            System.out.println("\tPulse return");
            System.out.println("\tpara decrementar el valor de ese botón en 1,");
            System.out.println("\ty también a los valores de sus 4 vecinos.");
            System.out.println("Objetivo:");
            System.out.println("\tDejar todos los botones en 'O'");

            // Bucle para detectar las pulsaciones de teclas
            while (true) {
                // Verifica si la tecla de flecha arriba es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_UP) & 0x8000) != 0) {
                    if (!upPressed) {
                        moverArriba(filaPosicion);
                        upPressed = true; //Mover los corchetes hacia arriba
                        break;
                    }
                } else {
                    upPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                }

                // Verifica si la tecla de flecha abajo es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_DOWN) & 0x8000) != 0) {
                    if (!downPressed) {
                        moverAbajo(filaPosicion); //Mover los corchetes hacia abajo
                        downPressed = true;
                        break;
                    }
                } else {
                    downPressed = false;
                }

                // Verifica si la tecla de flecha izquierda es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_LEFT) & 0x8000) != 0) {
                    if (!leftPressed) {
                        moverIzquierda(columnaPosicion); //Mover los corchetes a la izquierda
                        leftPressed = true;
                        break;
                    }
                } else {
                    leftPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_RIGHT) & 0x8000) != 0) {
                    if (!rightPressed) {
                        moverDerecha(columnaPosicion); //Mover los corchetes a la derecha
                        rightPressed = true;
                        break;
                    }
                } else {
                    rightPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_RETURN) & 0x8000) != 0) {
                    if (!enterPressed) {

                        //Generar el golpe al pulsar ENTER, en la casilla que tengamos seleccionada (entre corchetes)
                        decrementar(filaPosicion, columnaPosicion);
                        decrementar(filaPosicion + 1, columnaPosicion);
                        decrementar(filaPosicion - 1, columnaPosicion);
                        decrementar(filaPosicion, columnaPosicion - 1);
                        decrementar(filaPosicion, columnaPosicion + 1);

                        enterPressed = true;
                        break;
                    }
                } else {
                    enterPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_S) & 0x8000) != 0) {
                    if (!teclaSPressed) {
                        teclaSPressed = true;
                        opcion = "S"; //Cambiar el valor de opcion a S para salir del bucle y finalizar el juego
                        break;
                    }
                } else {
                    teclaSPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_N) & 0x8000) != 0) {
                    if (!teclaNPressed) {
                        teclaNPressed = true;
                        generarTablero(nivel); //Generar un nuevo tablero en el nivel que estamos
                        break;
                    }
                } else {
                    teclaNPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_U) & 0x8000) != 0) {
                    if (!teclaUPressed) {
                        teclaUPressed = true;
                        generarTablero(nivel); //Generar un nuevo tablero en el nivel que estamos
                        break;
                    }
                } else {
                    teclaUPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_R) & 0x8000) != 0) {
                    if (!teclaRPressed) {
                        teclaRPressed = true;
                        generarTablero(nivel); //Generar un nuevo tablero en el nivel que estamos
                        break;
                    }
                } else {
                    teclaRPressed = false;
                }


            }
        } while (!opcion.equals("S"));

    }

    public static void generarTablero(int nivel) {
        //Inicializar el tablero a 0 y mostrarlo
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tablero[i][j] = 0;
            }
        }

        golpear(nivel);

        tableroCopia = tablero;

    }

    public static void mostrarTablero(int filaPosicion, int columnaPosicion) {
        //Mostrar el tablero con los corchetes que seleccionan una posición
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                if (i == filaPosicion && j == columnaPosicion) {
                    System.out.print("[" + tablero[i][j] + "]");
                } else {
                    System.out.print(" " + tablero[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void copiarTableroConCorchetes() {
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tablero[i][j] = tableroCopia[j][i];
            }
        }
    }

    public static void golpear(int nivel) {
        Random rand = new Random();

        for (int i = 1; i < nivel * 3; i++) {
            int fila = rand.nextInt(1, 7);
            int columna = rand.nextInt(1, 7);

            aumentar(fila, columna);
            aumentar(fila + 1, columna);
            aumentar(fila - 1, columna);
            aumentar(fila, columna - 1);
            aumentar(fila, columna + 1);

        }
    }

    public static void aumentar(int fila, int columna) {
        tablero[fila][columna]++;
        if (tablero[fila][columna] > 3) {
            tablero[fila][columna] = 0;
        }
    }

    public static void decrementar(int fila, int columna) {
        tablero[fila][columna]--;
        if (tablero[fila][columna] < 0) {
            tablero[fila][columna] = 3;
        }
    }

    public static void moverArriba(int filas) {
        if (filas == 1) {
            filaPosicion = 6;
        } else {
            filaPosicion--;
        }
    }

    public static void moverAbajo(int filas) {
        if (filas == 6) {
            filaPosicion = 1;
        } else {
            filaPosicion++;
        }
    }

    public static void moverIzquierda(int columnas) {
        if (columnas == 1) {
            columnaPosicion = 6;
        } else {
            columnaPosicion--;
        }
    }

    public static void moverDerecha(int columnas) {
        if (columnas == 6) {
            columnaPosicion = 1;
        } else {
            columnaPosicion++;
        }
    }

    public static void obtenerGolpesNivel() {
        int niveles = nivel;
        switch (niveles) {
            case 1 -> {
                int valor = 3;
            }
            case 2 -> {
                int valor = 6;
            }
            case 3 -> {
                int valor = 9;
            }
            case 4 -> {
                int valor = 12;
            }
            case 5 -> {
                int valor = 15;
            }
            case 6 -> {
                int valor = 18;
            }
            case 7 -> {
                int valor = 21;
            }
            case 8 -> {
                int valor = 24;
            }
            case 9 -> {
                int valor = 27;
            }
        }
    }

    private static void borrar() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        Kernel32 INSTANCE = (Kernel32) Native.load("user32", User32.class);

        // Definir la función de Windows que lee un carácter de la consola
        boolean GetAsyncKeyState(int vKey);
    }

}