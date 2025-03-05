/*
    Cavero Fernández Víctor
 */

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
    public static final int TECLA_1 = 0x31; //Tecla 1
    public static final int TECLA_2 = 0x32; //Tecla 2
    public static final int TECLA_3 = 0x33; //Tecla 3
    public static final int TECLA_4 = 0x34; //Tecla 4
    public static final int TECLA_5 = 0x35; //Tecla 5
    public static final int TECLA_6 = 0x36; //Tecla 6
    public static final int TECLA_7 = 0x37; //Tecla 7
    public static final int TECLA_8 = 0x38; //Tecla 8
    public static final int TECLA_9 = 0x39; //Tecla 9
    public static final int TECLA_N = 0x4E; //Tecla N
    public static final int TECLA_R = 0x52; //Tecla R
    public static final int TECLA_U = 0x55; //Tecla U
    public static final int TECLA_S = 0x53; //Tecla S
    public static final int TECLA_L = 0x4C; //Tecla L
    public static final int TAM = 8; //Tamaño del tablero (TAMxTAM)
    public static final int GOLPES_NIVEL = 3; //Cantidad de golpes inversos por nivel generados
    public static final int TAM_CASILLAS = 3; //Número máximo que alcanza cada casilla

    //Variables utilizadas en los métodos
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


    //Arrays utilizados al deshacer cada golpe
    private static int[] golpesFilas = new int[0];
    private static int[] golpesColumnas = new int[0];

    public static void main(String[] args) {

        //Inicializar la variable de las teclas en A
        String opcion = "A";
        generarTablero(); //Generar tablero lleno de 0

        //Bucle general de funcionamiento del juego, mostrando la interfaz y el tablero de juego
        do {
            borrar();  //Limpiar pantalla antes de iniciar el juego (para cmd)

            //Mostrar interfaz del juego
            System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S )");
            System.out.println(); //Salto de linea

            mostrarTablero(filaPosicion, columnaPosicion); //Mostrar el tablero generado, con la posición designada seleccionada (1,1)

            //Mostrar interfaz inferior del juego, con las instrucciones
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

                // Verifica si la tecla ENTER es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_RETURN) & 0x8000) != 0) {
                    if (!enterPressed) {

                        golpear();

                        enterPressed = true;
                        break;
                    }
                } else {
                    enterPressed = false;
                }

                // Verifica si la tecla S es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_S) & 0x8000) != 0) {
                    if (!teclaSPressed) {
                        teclaSPressed = true;
                        opcion = "S"; //Cambiar el valor de opcion a S para salir del bucle y finalizar el juego
                        break;
                    }
                } else {
                    teclaSPressed = false;
                }

                // Verifica si la tecla N es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_N) & 0x8000) != 0) {
                    if (!teclaNPressed) {
                        teclaNPressed = true;
                        generarTablero(); //Generar un nuevo tablero en el nivel que estamos
                        golpes = 0;
                        recuperarTablero();
                        break;
                    }
                } else {
                    teclaNPressed = false;
                }

                // Verifica si la tecla U es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_U) & 0x8000) != 0) {
                    if (!teclaUPressed) {
                        if (golpes > 0) {
                            deshacerGolpe();
                            golpes--;
                            if (golpes == 0) {
                                golpesFilas = new int[0];
                                golpesColumnas = new int[0];
                            } else {

                                int[] auxGolepesFilas = new int[golpes];
                                int[] auxGolepesColumnas = new int[golpes];

                                for (int i = 0; i < golpes; i++) {
                                    auxGolepesFilas[i] = golpesFilas[i];
                                    auxGolepesColumnas[i] = golpesColumnas[i];
                                }

                                golpesFilas = auxGolepesFilas;
                                golpesColumnas = auxGolepesColumnas;

                            }
                        }

                        teclaUPressed = true;
                        break;

                    }
                } else {
                    teclaUPressed = false;
                }

                // Verifica si la tecla R es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_R) & 0x8000) != 0) {
                    if (!teclaRPressed) {
                        recuperarTablero();
                        teclaRPressed = true;
                        break;
                    }
                } else {
                    teclaRPressed = false;

                }

                // Verifica si la tecla L es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(TECLA_L) & 0x8000) != 0) {
                    if (!teclaLPressed) {
                        System.out.print("Nivel de juego ( L ): " + nivel + "\t\tNuevo nivel: ");
                        int nuevoNivel = 0; //Variable para crear partida nueva con un nuevo nivel

                        while (true) {
                            // Verifica si la tecla 1 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_1) & 0x8000) != 0) {
                                if (!tecla1Pressed) {
                                    nuevoNivel = 1;
                                    tecla1Pressed = true;
                                    break;
                                }
                            } else {
                                teclaRPressed = false;
                            }

                            // Verifica si la tecla 2 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_2) & 0x8000) != 0) {
                                if (!tecla2Pressed) {
                                    nuevoNivel = 2;
                                    tecla1Pressed = true;

                                    break;
                                }
                            } else {
                                tecla1Pressed = false;
                            }

                            // Verifica si la tecla 3 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_3) & 0x8000) != 0) {
                                if (!tecla3Pressed) {
                                    nuevoNivel = 3;
                                    tecla3Pressed = true;

                                    break;
                                }
                            } else {
                                tecla3Pressed = false;
                            }

                            // Verifica si la tecla 4 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_4) & 0x8000) != 0) {
                                if (!tecla4Pressed) {
                                    nuevoNivel = 4;
                                    tecla4Pressed = true;
                                    break;
                                }
                            } else {
                                tecla4Pressed = false;
                            }


                            // Verifica si la tecla 5 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_5) & 0x8000) != 0) {
                                if (!tecla5Pressed) {
                                    nuevoNivel = 5;
                                    tecla5Pressed = true;
                                    break;
                                }
                            } else {
                                tecla5Pressed = false;
                            }

                            // Verifica si la tecla 6 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_6) & 0x8000) != 0) {
                                if (!tecla6Pressed) {
                                    nuevoNivel = 6;
                                    tecla6Pressed = true;
                                    break;
                                }
                            } else {
                                tecla6Pressed = false;
                            }

                            // Verifica si la tecla 7 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_7) & 0x8000) != 0) {
                                if (!tecla7Pressed) {
                                    nuevoNivel = 7;
                                    tecla7Pressed = true;
                                    break;
                                }
                            } else {
                                tecla7Pressed = false;
                            }

                            // Verifica si la tecla 8 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_8) & 0x8000) != 0) {
                                if (!tecla8Pressed) {
                                    nuevoNivel = 8;
                                    tecla8Pressed = true;
                                    break;
                                }
                            } else {
                                tecla8Pressed = false;
                            }

                            // Verifica si la tecla 9 es presionada y aún no ha sido registrada
                            if ((User32.INSTANCE.GetAsyncKeyState(TECLA_9) & 0x8000) != 0) {
                                if (!tecla9Pressed) {
                                    nuevoNivel = 9;
                                    tecla9Pressed = true;
                                    break;
                                }
                            } else {
                                tecla9Pressed = false;
                            }
                        }  //Comprobar si cada tecla numérica (1-9) están pulsadas

                        if (nuevoNivel != nivel) { //Controlar la creación de partida con un nivel igual al actual
                            nivel = nuevoNivel;
                            generarTablero();
                        }

                        teclaLPressed = true;
                        break;
                    }
                } else {
                    teclaLPressed = false;

                }
            }

            //Comprobar ganador y mostrar mensajes en función de los golpes dados
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
                } //Pulsar enter para volver a generar un tablero y continuar el juego
            }

        } while (!opcion.equals("S")); //Condición para terminar el juego, pulsar la tecla S
    }

    //Función para generar un tablero
    public static void generarTablero() {
        //Inicializar el tablero a 0 y mostrarlo
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tablero[i][j] = 0;
            }
        }

        golpearInverso(); //Llamada a la función que rellena el tablero

        copiarTablero(); //Llamada a la función que copia un tablero generado en otro tablero copia

    }

    //Función para imprimir un tablero por pantalla
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

    //Función para copiar un tablero en otro tablero copia
    public static void copiarTablero() {
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tableroCopia[i][j] = tablero[i][j];
            }
        }
    }

    //Función para recuperar un tablero inicial a la hora de rehacer la partida (TECLA R)
    public static void recuperarTablero() {
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                tablero[i][j] = tableroCopia[i][j];
            }
        }
    }

    //Función para rellenar un tablero de 0 con golpes en posiciones aleatorias
    public static void golpearInverso() {
        //Generar golpes en posiciones aleatorias. La cantidad de golpes será 3 veces el nivel actual
        for (int i = 0; i < nivel * GOLPES_NIVEL; i++) {
            int fila = rand.nextInt(1, TAM - 1);
            int columna = rand.nextInt(1, TAM - 1);

            //Golpear inversamente las posiciones generadas y sus vecinas
            aumentar(fila, columna);
            aumentar(fila + 1, columna);
            aumentar(fila - 1, columna);
            aumentar(fila, columna - 1);
            aumentar(fila, columna + 1);

        }
    }

    //Función para generar los golpes del jugador en las posiciones seleccionadas
    public static void golpear() {

        //Generar arrays nuevos para guardar los golpes realizados con una posición más
        int[] auxGolpesFilas = new int[golpes + 1];
        int[] auxGolpesColumnas = new int[golpes + 1];

        //Copiar los datos actuales del array que ya tengo en el auxiliar creado
        for (int i = 0; i < golpes; i++) {
            auxGolpesFilas[i] = golpesFilas[i];
            auxGolpesColumnas[i] = golpesColumnas[i];
        }

        //Guardar las posiciones del último golpe en la última posición del nuevo array
        auxGolpesFilas[golpes] = filaPosicion;
        auxGolpesColumnas[golpes] = columnaPosicion;

        //Sobreescribir el valor de los arrays principales
        golpesFilas = auxGolpesFilas;
        golpesColumnas = auxGolpesColumnas;

        //Decrementar el valor en la posición seleccionada y sus vecinas (lo que sería la acción de golpear del jugador al pulsar ENTER)
        decrementar(filaPosicion, columnaPosicion);
        decrementar(filaPosicion + 1, columnaPosicion);
        decrementar(filaPosicion - 1, columnaPosicion);
        decrementar(filaPosicion, columnaPosicion - 1);
        decrementar(filaPosicion, columnaPosicion + 1);


        golpes++; //Incrementar contador de golpes
    }

    //Función para aumentar una posicion
    public static void aumentar(int fila, int columna) {
        tablero[fila][columna]++;
        if (tablero[fila][columna] > TAM_CASILLAS) {
            tablero[fila][columna] = 0;
        }
    }

    //Función para disminuir una posicion
    public static void decrementar(int fila, int columna) {
        golpesFilas[golpes] = filaPosicion;
        golpesColumnas[golpes] = columnaPosicion;

        tablero[fila][columna]--;
        if (tablero[fila][columna] < 0) {
            tablero[fila][columna] = TAM_CASILLAS;
        }
    }

    //Función para mover arriba la casilla seleccionada
    public static void moverArriba(int filas) {
        if (filas == 1) {
            filaPosicion = 6;
        } else {
            filaPosicion--;
        }
    }

    //Función para mover abajo la casilla seleccionada
    public static void moverAbajo(int filas) {
        if (filas == 6) {
            filaPosicion = 1;
        } else {
            filaPosicion++;
        }
    }

    //Función para mover a la izquierda la casilla seleccionada
    public static void moverIzquierda(int columnas) {
        if (columnas == 1) {
            columnaPosicion = 6;
        } else {
            columnaPosicion--;
        }
    }

    //Función para mover a la derecha la casilla seleccionada
    public static void moverDerecha(int columnas) {
        if (columnas == 6) {
            columnaPosicion = 1;
        } else {
            columnaPosicion++;
        }
    }

    //Función para comprobar si el jugador ha realizado más, menos o justo los golpes necesarios
    public static int obtenerGolpesNivel() {
        int golpesNivel = 0;
        switch (nivel) {
            case 1 -> {
                golpesNivel = GOLPES_NIVEL; //Nivel 1 es posible realizarlo en 3 golpes
            }
            case 2 -> {
                golpesNivel = 2 * GOLPES_NIVEL; //Nivel 2 es posible realizarlo en 6 golpes
            }
            case 3 -> {
                golpesNivel = 3 * GOLPES_NIVEL; //Nivel 3 es posible realizarlo en 9 golpes
            }
            case 4 -> {
                golpesNivel = 4 * GOLPES_NIVEL; //Nivel 4 es posible realizarlo en 12 golpes
            }
            case 5 -> {
                golpesNivel = 5 * GOLPES_NIVEL; //Nivel 5 es posible realizarlo en 15 golpes
            }
            case 6 -> {
                golpesNivel = 6 * GOLPES_NIVEL; //Nivel 6 es posible realizarlo en 18 golpes
            }
            case 7 -> {
                golpesNivel = 7 * GOLPES_NIVEL; //Nivel 7 es posible realizarlo en 21 golpes
            }
            case 8 -> {
                golpesNivel = 8 * GOLPES_NIVEL; //Nivel 8 es posible realizarlo en 24 golpes
            }
            case 9 -> {
                golpesNivel = 9 * GOLPES_NIVEL; //Nivel 9 es posible realizarlo en 27 golpes
            }
        }
        return golpesNivel; //Devolver la cantidad de golpes en funcion del nivel
    }

    //Función para golpear inversamente en la ultima posicion golpeada por el jugador
    public static void deshacerGolpe() {
        //Solo deshacer golpes en caso de que la cantidad de golpes sea superior a 0
        if (golpes > 0) {
            //Realizar golpe inverso en la ultima posición guardada en nuestros arrays utilizados para dicha función
            int fila = golpesFilas[golpes - 1];
            int columna = golpesColumnas[golpes - 1];

            //Realizar golpe inverso en la posición extraida del array y en sus vecinas
            aumentar(fila, columna);
            aumentar(fila + 1, columna);
            aumentar(fila - 1, columna);
            aumentar(fila, columna - 1);
            aumentar(fila, columna + 1);
        }
    }

    //Función para comprobar si el usuario ha ganado
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

    //Función para borrar pantalla en CMD
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
