/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bingo;

import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal para el juego de Bingo.
 *
 * @author david
 */
public class PicazoDavid_Pt1_UF2 {

    public static final String GREEN = "\u001B[32m"; // Color verde
    public static final String RESET = "\u001B[0m"; // Restablecer color
    public static final Random rdm = new Random(); // Objeto Random para generar números aleatorios
    public static final Scanner teclat = new Scanner(System.in); // Escáner para entradas de teclado

    /**
     * Método principal que ejecuta el juego de Bingo.
     */
    public static void main(String[] args) {

        boolean joc = true; // Variable que controla si el juego continúa
        int[] bombo = new int[90]; // Array que representa los números en el bombo
        int array[] = new int[90]; // Array para los números generados
        int bola = 0; // Número de la bola actual
        final int tam = 3, dim = 9; // Tamaño del cartón y dimensiones del tablero
        int taulell1[][] = new int[tam][dim]; // Primer cartón de bingo
        boolean bingo = false; // Variable que indica si se ha ganado
        boolean linea = true; // Variable que indica si se ha hecho una línea
        int taulell2[][] = new int[tam][dim]; // Segundo cartón para copiar el original

        // Mostrar el título en ASCII
        System.out.println("██████╗  ██╗ ███╗   ██╗  ██████╗   ██████╗ ");
        System.out.println("██╔══██╗ ██║ ████╗  ██║ ██╔════╝  ██╔═══██╗");
        System.out.println("██████╔╝ ██║ ██╔██╗ ██║ ██║  ███╗ ██║   ██║");
        System.out.println("██╔══██╗ ██║ ██║╚██╗██║ ██║   ██║ ██║   ██║");
        System.out.println("██████╔╝ ██║ ██║ ╚████║ ╚██████╔╝ ╚██████╔╝");
        System.out.println("╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚═════╝  ╚═════╝ \n");

        // Imprimir mensaje de bienvenida en castellano
        System.out.println("Aquí tienes tu cartón");
        taulell(taulell1, tam, dim, bola);
        taulell2 = copia(taulell1, taulell2);

        do {
            joc = tirada_posterior(); // Comprobar si el jugador quiere continuar
            bola = tirada_bombo(bombo, array, bola); // Obtener el siguiente número del bombo
            faketirada_bombo(bombo, bola); // Imprimir el número sacado
            mostrartaulell(taulell1, bombo, bola, joc); // Mostrar el cartón actualizado

            if (linea == true) {
                linea = linea(bombo, bola, taulell1, linea); // Comprobar si se ha hecho línea
            }
            bingo = bingo(taulell1, bombo); // Comprobar si se ha ganado
        } while (joc == true && bingo == false); // Continuar hasta que haya bingo o el jugador termine
        finalbombo(bombo, taulell2); // Mostrar el estado final del bombo

    }

    /**
     * Método para generar un cartón de Bingo.
     */
    public static void taulell(int taulell1[][], int tam, int dim, int bola) {
        Random rdm = new Random();
        int n, avan = 0;

        // Generar el cartón con números aleatorios sin repeticiones
        for (int f = 0; f < taulell1.length; f++) {
            for (int c = 0; c < taulell1[f].length; c++) {
                n = rdm.nextInt(90 - 1 + 1) + 1;
                taulell1[f][c] = NumsRepetitsCartro(taulell1);
            }

            // Poner espacios en blanco aleatorios
            for (int i = 4; i > 0; i--) {
                int blanc = rdm.nextInt(9);
                if (taulell1[f][blanc] == 0) {
                    do {
                        blanc = rdm.nextInt(9);
                    } while (taulell1[f][blanc] == 0);
                }
                taulell1[f][blanc] = 0;
            }
        }

        // Imprimir el cartón
        for (int f = 0; f < taulell1.length; f++) {
            for (int c = 0; c < taulell1[f].length; c++) {
                System.out.printf(" %3d ", taulell1[f][c]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Método para copiar el cartón a otro array.
     */
    public static int[][] copia(int taulell1[][], int taulell2[][]) {
        for (int f = 0; f < taulell1.length; f++) {
            for (int c = 0; c < taulell1[f].length; c++) {
                taulell2[f][c] = taulell1[f][c];
            }
        }
        return taulell2;
    }

    /**
     * Método para mostrar el cartón y marcar los números sacados.
     */
    public static void mostrartaulell(int taulell1[][], int array[], int bola, boolean joc) {
        int guany = 0;
        String marcat = "X";
        String zero = "@";

        // Marcar los números que han salido
        for (int f = 0; f < taulell1.length; f++) {
            for (int c = 0; c < taulell1[f].length; c++) {
                if (taulell1[f][c] == bola) {
                    taulell1[f][c] = -1;
                    System.out.println("Te ha tocado, lo marco con una X");
                }
            }
        }

        // Imprimir el cartón actualizado
        for (int f = 0; f < taulell1.length; f++) {
            System.out.print("|");
            for (int c = 0; c < taulell1[f].length; c++) {
                if (taulell1[f][c] == -1) {
                    System.out.printf("%2s |", marcat);
                } else if (taulell1[f][c] == 0) {
                    System.out.printf("%2s |", zero);
                } else {
                    System.out.printf("%2d |", taulell1[f][c]);
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Método para generar un array de números aleatorios sin repeticiones.
     */
    public static void ArrayRandomNoRep(int taulell1[][]) {
        Random rdm = new Random();
        int i = 0, cantidad = 90, rango = 90;
        int array[] = new int[cantidad];

        // Crear un array de números aleatorios sin repeticiones
        array[i] = (int) (Math.random() * rango + 1);
        for (i = 1; i < cantidad; i++) {
            array[i] = (int) (Math.random() * rango + 1);
            for (int j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    i--;
                }
            }
        }
        for (int k = 0; k < cantidad; k++) {
            System.out.printf("%3d\n", array[k]);
        }
    }

    /**
     * Método para preguntar si el jugador quiere continuar con el juego.
     */
    public static boolean tirada_posterior() {
        Scanner teclat = new Scanner(System.in);
        boolean joc = true;
        String resposta;
        char acabar;
        do {
            System.out.println("");
            System.out.println("¿Quieres continuar? s/n");
            resposta = teclat.nextLine();
            acabar = resposta.charAt(0);
        } while (!(acabar == 's' || acabar == 'S' || acabar == 'n' || acabar == 'N'));
        if (acabar == 's' || acabar == 'S') {
            joc = true;
        } else if (acabar == 'n' || acabar == 'N') {
            joc = false;
        }
        return joc;
    }

    /**
     * Método para obtener el siguiente número del bombo.
     */
    public static int tirada_bombo(int bombo[], int array[], int bola) {
        boolean colocat = false;
        int n = 0;
        do {
            n = rdm.nextInt(bombo.length - 1 + 1) + 1;
            if (bombo[n - 1] == 0) {
                bombo[n - 1] = 1;
                colocat = true;
                bola = n;
            }
        } while (colocat == false);
        return bola;
    }

    /**
     * Método para imprimir el número sacado del bombo.
     */
    public static void faketirada_bombo(int bombo[], int bola) {
        int sortida = bola;
        System.out.println("");
        System.out.println("Ha salido el número: " + sortida);
        System.out.println("");
    }

    /**
     * Método para comprobar si se ha ganado el bingo.
     */
    public static boolean bingo(int taulell1[][], int array[]) {
        boolean bingo = false;
        int linea = 0;
        for (int f = 0; f < taulell1.length; f++) {
            for (int c = 0; c < taulell1[f].length; c++) {
                if (taulell1[f][c] == -1) {
                    linea++;
                }
            }
        }
        if (linea == 15) {
            bingo = true;
            System.out.println("BINGO, HAS GANADO");
        }
        return bingo;
    }

    /**
     * Método para comprobar si se ha hecho línea.
     */
    public static boolean linea(int array[], int bola, int taulell1[][], boolean linea) {
        int cont = 0;
        for (int f = 0; f < taulell1.length; f++) {
            cont = 0;
            for (int c = 0; c < taulell1[f].length; c++) {
                if (taulell1[f][c] <= 0) {
                    cont++;
                }
            }
            if (cont == 9) {
                System.out.println("¡HAS CONSEGUIDO LÍNEA, MUY BIEN!");
                linea = false;
            }
        }
        return linea;
    }

    /**
     * Método para mostrar el estado final del bombo.
     */
    public static void finalbombo(int bombo[], int taulell2[][]) {
        int contador = 0;
        boolean trobat;
        System.out.println("\nEL BOMBO TOTAL HA SIDO:\n");
        System.out.print("|");
        for (int i = 0; i < bombo.length; i++) {
            if (bombo[i] == 0) {
                System.out.printf("%3s|", "--");
            } else {
                trobat = buscarNumero(i + 1, taulell2);
                if (trobat == true) {
                    System.out.printf(GREEN + "%3d" + RESET + "|", i + 1);
                } else {
                    System.out.printf("%3d|", i + 1);
                }
                contador++;
            }
        }
        System.out.println("");
    }

    /**
     * Método para buscar un número en el cartón.
     */
    public static boolean buscarNumero(int numero, int taulell2[][]) {
        boolean trobat = false;
        for (int f = 0; f < taulell2.length; f++) {
            for (int c = 0; c < taulell2[f].length; c++) {
                if (taulell2[f][c] == numero) {
                    trobat = true;
                }
            }
        }
        return trobat;
    }

    /**
     * Método para asegurarse de que no haya números repetidos en el cartón.
     */
    public static int NumsRepetitsCartro(int taulell1[][]) {
        int n = 0;
        boolean repetit;
        do {
            repetit = false;
            n = rdm.nextInt(90 - 1 + 1) + 1;
            for (int f = 0; f < taulell1.length; f++) {
                for (int c = 0; c < taulell1[f].length; c++) {
                    if (taulell1[f][c] == n) {
                        repetit = true;
                    }
                }
            }
        } while (repetit == true);
        return n;
    }
}
