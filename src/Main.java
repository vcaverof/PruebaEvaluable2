import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int TAM = 8;
    private static Random rand = new Random();
    private static int golpes = 0;
    private static int nivel = 5;
    private static int filaPosicion = 1;
    private static int columnaPosicion = 1;
    private static int[][] tablero = new int[TAM][TAM];
    private static int[][] tableroCopia = new int[TAM][TAM];

    //Arrays para guardar los golpes y usarlos al utilizar deshacer
    private int[] golpesFilas = new int[0];
    private int[] golpesColumnas = new int[0];


    public static void main(String[] args) {
        String opcion = "A";
        generarTablero(nivel); //Generar tablero lleno de 0

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
            tablero[fila[columna = 0;
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
            filas = 6;
        } else {
            filas--;
        }
    }

    public static void moverAbajo(int filas) {
        if (filas == 6) {
            filas = 1;
        }
    }

    public static void moverIzquierda(int columnas) {
        if (columnas == 1) {
            columnas = 6;
        }
    }

    public static void moverDerecha(int columnas) {
        if (columnas == 6) {
            columnas = 1;
        }
    }

    public static void obtenerGolpesNivel() {
        int opcion = nivel;
        switch (opcion) {
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
}