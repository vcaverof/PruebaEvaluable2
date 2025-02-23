import java.io.IOException;
import java.util.Random;

import com.sun.jna.*;
import com.sun.jna.platform.win32.User32;

// java -cp ".;libs/jna.jar;libs/jna-platform.jar" Main

public class Main {

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
    public static final int TECLA_L = 0x4C;
    public static final int TAM = 8;

    private static Random rand = new Random();
    private static int golpes = 0;
    private static int nivel = 5;
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
    private static boolean teclaLPressed = false;


    //Arrays para guardar los golpes y usarlos al utilizar deshacer
    private static int[] golpesFilas = new int[100];
    private static int[] golpesColumnas = new int[100];

    public static void main(String[] args) {

        String opcion = "A";
        generarTablero(); //Generar tablero lleno de 0

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

                        golpes++;

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
                        generarTablero(); //Generar un nuevo tablero en el nivel que estamos
                        golpes = 0;
                        tableroCopia = tablero;
                        break;
                    }
                } else {
                    teclaNPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_U) & 0x8000) != 0) {
                    if (!teclaUPressed) {
                        if (golpes > 0) {
                            deshacerGolpe();
                            golpes--;
                            if (golpes == 0) {
                                golpesFilas = new int[100];
                                golpesColumnas = new int[100];
                            }
                        }
                        teclaUPressed = true;
                        break;
                    }
                } else {
                    teclaUPressed = false;
                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_R) & 0x8000) != 0) {
                    if (!teclaRPressed) {

                        recuperarTablero();
                        teclaRPressed = true;
                        break;
                    }
                } else {
                    teclaRPressed = false;

                }

                // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada

                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_L) & 0x8000) != 0) {
                    if (!teclaLPressed) {
                        System.out.print("Nivel de juego ( L ): " + nivel + "\t\tNuevo nivel: ");
                        while (true) {
                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_1) & 0x8000) != 0) {
                                if (!tecla1Pressed) {
                                    nivel = 1;
                                    tecla1Pressed = true;
                                    break;
                                }
                            } else {
                                teclaRPressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_2) & 0x8000) != 0) {
                                if (!tecla2Pressed) {
                                    nivel = 2;
                                    tecla1Pressed = true;

                                    break;
                                }
                            } else {
                                tecla1Pressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_3) & 0x8000) != 0) {
                                if (!tecla3Pressed) {
                                    nivel = 3;
                                    tecla3Pressed = true;

                                    break;
                                }
                            } else {
                                tecla3Pressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_4) & 0x8000) != 0) {
                                if (!tecla4Pressed) {
                                    nivel = 4;
                                    tecla4Pressed = true;
                                    break;
                                }
                            } else {
                                tecla4Pressed = false;
                            }


                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_5) & 0x8000) != 0) {
                                if (!tecla5Pressed) {
                                    nivel = 5;
                                    tecla5Pressed = true;
                                    break;
                                }
                            } else {
                                tecla5Pressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_6) & 0x8000) != 0) {
                                if (!tecla6Pressed) {
                                    nivel = 6;
                                    tecla6Pressed = true;
                                    break;
                                }
                            } else {
                                tecla6Pressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_7) & 0x8000) != 0) {
                                if (!tecla7Pressed) {
                                    nivel = 7;
                                    tecla7Pressed = true;
                                    break;
                                }
                            } else {
                                tecla7Pressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_8) & 0x8000) != 0) {
                                if (!tecla8Pressed) {
                                    nivel = 8;
                                    tecla8Pressed = true;
                                    break;
                                }
                            } else {
                                tecla8Pressed = false;
                            }

                            // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_9) & 0x8000) != 0) {
                                if (!tecla9Pressed) {
                                    nivel = 9;
                                    tecla9Pressed = true;
                                    break;
                                }
                            } else {
                                tecla9Pressed = false;
                            }
                        }

                        generarTablero();
                        teclaLPressed = true;
                        break;
                    }
                } else {
                    teclaLPressed = false;

                }
            }

            //Para poner un límite de golpes al juego
            //if (golpes > nivel * 3) {
            //System.out.println("Has perdido");
            // break;
            //}

            if (comprobarGanador()) {
                int obtenerGolpesEsperados = obtenerGolpesNivel();
                System.out.println("Enhorabuena, has ganado");

                if (obtenerGolpesEsperados == golpes) {
                    System.out.println("Perfecto. Hecho en: " + golpes + " golpes");
                } else if (obtenerGolpesEsperados < golpes) {
                    System.out.println("Extraordinariamente bien. Hecho en: " + golpes + " golpes");
                } else {
                    System.out.println("Hecho en: " + golpes + " golpes");
                }

                System.out.println("Nivel alcanzado: " + nivel);
                System.out.println("Presiona enter para continuar con un nuevo tablero ");

                while (true) {
                    // Verifica si la tecla de flecha derecha es presionada y aún no ha sido registrada
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_RETURN) & 0x8000) != 0) {
                        if (!enterPressed) {

                            generarTablero();
                            golpes = 0;

                            enterPressed = true;
                            break;
                        }
                    } else {
                        enterPressed = false;
                    }
                }
            }

        } while (!opcion.equals("S"));
    }

    public static void generarTablero() {
        //Inicializar el tablero a 0 y mostrarlo
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tablero[i][j] = 0;
            }
        }

        golpear();

        copiarTablero();

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

    public static void copiarTablero() {
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tableroCopia[i][j] = tablero[i][j];
            }
        }
    }

    public static void recuperarTablero() {
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tablero[i][j] = tableroCopia[i][j];
            }
        }
    }

    public static void golpear() {
        for (int i = 0; i < nivel * 3; i++) {
            int fila = rand.nextInt(1, TAM - 1);
            int columna = rand.nextInt(1, TAM - 1);

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
        golpesFilas[golpes] = filaPosicion;
        golpesColumnas[golpes] = columnaPosicion;

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

    public static int obtenerGolpesNivel() {
        int golpesNivel = 0;
        switch (nivel) {
            case 1 -> {
                golpesNivel = 3;
            }
            case 2 -> {
                golpesNivel = 6;
            }
            case 3 -> {
                golpesNivel = 9;
            }
            case 4 -> {
                golpesNivel = 12;
            }
            case 5 -> {
                golpesNivel = 15;
            }
            case 6 -> {
                golpesNivel = 18;
            }
            case 7 -> {
                golpesNivel = 21;
            }
            case 8 -> {
                golpesNivel = 24;
            }
            case 9 -> {
                golpesNivel = 27;
            }
        }
        return golpesNivel;
    }

    public static void deshacerGolpe() {
        if (golpes > 0) {
            int fila = golpesFilas[golpes - 1];
            int columna = golpesColumnas[golpes - 1];

            aumentar(fila, columna);
            aumentar(fila + 1, columna);
            aumentar(fila - 1, columna);
            aumentar(fila, columna - 1);
            aumentar(fila, columna + 1);
        }
    }

    public static boolean comprobarGanador() {
        boolean esta = true;
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                if (tablero[i][j] != 0) {
                    esta = false;
                    break;
                }
            }
        }
        return esta;
    }

    public static void borrar() {
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
