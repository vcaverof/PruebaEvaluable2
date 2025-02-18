import java.io.IOException;
import java.util.Random;

public class Main {
    private static final int TAM = 8;
    private static Random rand = new Random();
    private static int golpes = 0;
    private static int nivel = 5;
    private static int filaCorchete = 1;
    private static int columnaCorchete = 1;
    private static int[][] tablero = new int[TAM][TAM];
    private static int[][] tableroCopia = new int[TAM][TAM];
    private static int[] golpesFilas = new int[0];
    private static int[] golpesColumnas = new int[0];


    public static void main(String[] args) {

        System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S ) - Borrar Cal. (B)");
        generarTablero(nivel);
        mostrarTableroConCorchetes();

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

        tableroCopia = tablero;
    }

    public static void mostrarTableroConCorchetes() {
        for (int i = 1; i < TAM - 1; i++) {
            for (int j = 1; j < TAM - 1; j++) {
                if (i == filaCorchete && j == columnaCorchete) {
                    System.out.print("[" + tablero[i][j] + "]");
                } else {
                    System.out.print(" " + tablero[i][j] + " ");
                }
            }
            System.out.println();
        }

    }

    public static void copiarTableroConCorchetes() {

    }

    public static void golpear() {


    }

    public static void aumentar(int filas, int columnas) {

    }

    public static void decrementar(int filas, int columnas) {

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