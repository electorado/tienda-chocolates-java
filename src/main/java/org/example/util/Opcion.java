package org.example.util;

import java.util.Scanner;

public class Opcion {

    public static int recibirOpcion(Scanner scanner, int min, int max) {
        int opcion = 0;
        boolean valida = false;

        while (!valida) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());

                if (opcion < min || opcion > max) {
                    System.out.println("Opción fuera de rango.");
                } else {
                    valida = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }

        return opcion;
    }
}