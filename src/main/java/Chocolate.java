import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa un producto(chocolate) de la tienda.
 * Cada producto se define por su origen y porcentaje de cacao.
 * Llama a métodos de la clase Main para evitar la duplicación de código.
 *
 * @author Pablo Andrés Moncayo Vega
 * @version 1.0
 */
public class Chocolate {

    private String idProducto;
    private String origen;
    private int porcentajeCacao;
    private double precio;
    private int stock;

    /**
     * Menú específico para la gestión de productos.
     */
    private static final String menuProducto = "***MENÚ DE GESTIÓN DE PRODUCTOS***\n1.Alta de nuevo producto\n" +
            "2.Recepción de producto (Añadir stock)\n3.Ver inventario\n4.Buscar por % de cacao\n5.Buscar por origen\n6.Volver";

    /**
     * Constructor para crear un nuevo objeto Chocolate.
     * El ID del producto se genera automáticamente a partir del origen y el porcentaje.
     *
     * @param origen País o región de origen del cacao.
     * @param porcentajeCacao Porcentaje de cacao del producto.
     * @param precio Precio de venta al público.
     * @param unidadesIngresadas Cantidad de unidades que se añaden al stock al crear el producto.
     */
    public Chocolate(String origen, int porcentajeCacao, double precio, int unidadesIngresadas) {
        this.origen = origen;
        this.porcentajeCacao = porcentajeCacao;
        this.precio = precio;
        this.stock = unidadesIngresadas;

        // Genera un ID usando las 3 primeras letras del origen y el porcentaje.
        String origenCorto = origen.length() < 3 ? origen : origen.substring(0, 3);
        this.idProducto = (origenCorto + porcentajeCacao).toUpperCase();
    }

    // --- Getters y Setters ---
    public String getIdProducto() { return idProducto; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public int getPorcentajeCacao() { return porcentajeCacao; }
    public void setPorcentajeCacao(int porcentajeCacao) { this.porcentajeCacao = porcentajeCacao; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Chocolate: ID: " + idProducto + " | Origen: " + origen + " | Cacao: " + porcentajeCacao +
                "% | Precio: " + String.format("%.2f", precio) + "€ | Stock: " + stock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chocolate otro) {
            return this.idProducto.equalsIgnoreCase(otro.idProducto);
        }
        return false;
    }

    // --- Funciones estáticas para gestionar el catálogo ---

    /**
     * Muestra el menú de gestión de productos y maneja la lógica de las opciones.
     * @param catalogo El ArrayList donde se almacenan los productos.
     * @param scanner El objeto Scanner para la entrada del usuario.
     */
    public static void gestionarProductos(ArrayList<Chocolate> catalogo, Scanner scanner) {
        int opcionProducto;
        do {
            Main.imprimirMenu(menuProducto);
            opcionProducto = Main.recibirOpcion(1, 6);

            switch (opcionProducto) {
                case 1 -> altaProducto(catalogo, scanner);
                case 2 -> recepcionProducto(catalogo, scanner);
                case 3 -> verInventario(catalogo);
                case 4 -> buscarPorCacao(catalogo, scanner);
                case 5 -> buscarPorOrigen(catalogo, scanner);
                case 6 -> System.out.println("Volviendo al menú principal.");
            }
        } while (opcionProducto != 6);
    }

    /**
     * Busca un chocolate en el catálogo por su ID.
     * @param catalogo La lista de chocolates donde buscar.
     * @param id El ID del chocolate a encontrar.
     * @return El objeto Chocolate si se encuentra, de lo contrario devuelve null.
     */
    static Chocolate encontrarChocolatePorId(ArrayList<Chocolate> catalogo, String id) {
        for (Chocolate choco : catalogo) {
            if (choco.getIdProducto().equalsIgnoreCase(id)) {
                return choco;
            }
        }
        return null;
    }

    /**
     * Da de alta un nuevo tipo de producto que no existía en el catálogo.
     * @param catalogo El ArrayList donde se guardará el nuevo producto.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void altaProducto(ArrayList<Chocolate> catalogo, Scanner scanner) {
        System.out.println("\n--- ALTA DE NUEVO PRODUCTO ---");

        System.out.print("Introduce el país de origen: ");
        String origen = scanner.nextLine();

        int porcentaje = 0;
        boolean porcentajeValido = false;
        while (!porcentajeValido) {
            System.out.print("Introduce el porcentaje de cacao (entero, Ej: 70): ");
            try {
                porcentaje = Integer.parseInt(scanner.nextLine());
                if(porcentaje >= 0 && porcentaje <= 100) {
                    porcentajeValido = true;
                } else {
                    System.out.println("Error: El porcentaje debe estar entre 0 y 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un número entero válido.");
            }
        }

        String origenCorto = origen.length() < 3 ? origen : origen.substring(0, 3);
        String idGenerado = (origenCorto + porcentaje).toUpperCase();

        if (encontrarChocolatePorId(catalogo, idGenerado) != null) {
            System.out.println("Error: El producto: " + idGenerado + " ya existe.");
            return;
        }

        double precio = 0;
        boolean precioValido = false;
        while (!precioValido) {
            System.out.print("Introduce el precio (Puedes usar '.' o ',' para decimales): ");
            String precioStr = scanner.nextLine().replace(',', '.');
            try {
                precio = Double.parseDouble(precioStr);
                precioValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Precio no válido. Por favor, introduce un número.");
            }
        }

        int unidades = 0;
        boolean unidadesValidas = false;
        while (!unidadesValidas) {
            System.out.print("Introduce la cantidad de unidades a ingresar: ");
            try {
                unidades = Integer.parseInt(scanner.nextLine());
                if (unidades > 0) {
                    unidadesValidas = true;
                } else {
                    System.out.println("Error: La cantidad debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un número entero válido.");
            }
        }

        Chocolate nuevoChocolate = new Chocolate(origen, porcentaje, precio, unidades);
        catalogo.add(nuevoChocolate);

        System.out.println("Producto con ID: " + nuevoChocolate.getIdProducto() + " dado de alta con " + unidades + " unidades en stock.");
    }

    /**
     * Añade stock a un producto ya existente en el catálogo.
     * @param catalogo El ArrayList donde se busca el producto.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void recepcionProducto(ArrayList<Chocolate> catalogo, Scanner scanner) {
        System.out.println("\n--- RECEPCIÓN DE PRODUCTO (AÑADIR STOCK) ---");
        System.out.print("Introduce el ID del producto para añadir stock: ");
        String id = scanner.nextLine().toUpperCase();

        Chocolate productoEncontrado = encontrarChocolatePorId(catalogo, id);

        if (productoEncontrado == null) {
            System.out.println("Error: No se encontró ningún producto con el ID " + id);
            return;
        }

        System.out.println("Producto encontrado: Chocolate de " + productoEncontrado.getOrigen() + " al " + productoEncontrado.getPorcentajeCacao() + "%.");
        System.out.println("Stock actual: " + productoEncontrado.getStock());

        int unidadesAñadir = 0;
        boolean unidadesValidas = false;
        while (!unidadesValidas) {
            System.out.print("Introduce la cantidad de unidades a añadir: ");
            try {
                unidadesAñadir = Integer.parseInt(scanner.nextLine());
                if (unidadesAñadir > 0) {
                    unidadesValidas = true;
                } else {
                    System.out.println("Error: La cantidad debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un número entero válido.");
            }
        }

        productoEncontrado.setStock(productoEncontrado.getStock() + unidadesAñadir);
        System.out.println("Stock actualizado correctamente.");
        System.out.println("Nuevo stock: " + productoEncontrado.getStock());
    }

    /**
     * Muestra en consola la lista completa de productos en el inventario.
     * @param catalogo El ArrayList de productos que se va a listar.
     */
    public static void verInventario(ArrayList<Chocolate> catalogo) {
        System.out.println("\n--- INVENTARIO DE CHOCOLATES ---");
        if (catalogo.isEmpty()) {
            System.out.println("No hay productos registrados en el catálogo.");
        } else {
            for (Chocolate choco : catalogo) {
                System.out.println(choco.toString());
            }
        }
    }

    /**
     * Busca y muestra los chocolates que igualan o superan un porcentaje de cacao determinado.
     * @param catalogo El ArrayList donde se buscan los productos.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void buscarPorCacao(ArrayList<Chocolate> catalogo, Scanner scanner) {
        System.out.println("\n--- BUSCAR POR % DE CACAO ---");

        int cacaoMinimo = 0;
        boolean cacaoValido = false;
        while (!cacaoValido) {
            System.out.print("Introduce el porcentaje mínimo de cacao a buscar: ");
            try {
                cacaoMinimo = Integer.parseInt(scanner.nextLine());
                cacaoValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Porcentaje no válido. Introduce un número entero.");
            }
        }

        ArrayList<Chocolate> encontrados = new ArrayList<>();
        for (Chocolate choco : catalogo) {
            if (choco.getPorcentajeCacao() >= cacaoMinimo) {
                encontrados.add(choco);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron chocolates con " + cacaoMinimo + "% o más de cacao.");
        } else {
            System.out.println("Chocolates encontrados:");
            for (Chocolate choco : encontrados) {
                System.out.println(choco.toString());
            }
        }
    }

    /**
     * Busca y muestra los chocolates de un origen específico.
     * @param catalogo El ArrayList donde se buscan los productos.
     * @param scanner El objeto Scanner para leer la entrada del usuario.
     */
    public static void buscarPorOrigen(ArrayList<Chocolate> catalogo, Scanner scanner) {
        System.out.println("\n--- BUSCAR POR ORIGEN ---");
        System.out.print("Introduce el país de origen a buscar: ");
        String origenBuscado = scanner.nextLine();

        ArrayList<Chocolate> encontrados = new ArrayList<>();
        for (Chocolate choco : catalogo) {
            if (choco.getOrigen().equalsIgnoreCase(origenBuscado)) {
                encontrados.add(choco);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron chocolates de '" + origenBuscado + "'.");
        } else {
            System.out.println("Chocolates encontrados de " + origenBuscado + ":");
            for (Chocolate choco : encontrados) {
                System.out.println(choco.toString());
            }
        }
    }
}
