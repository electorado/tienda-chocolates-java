# Tienda de Chocolate

## Descripción

Este proyecto es una aplicación de consola para la gestión de una tienda de chocolates. Permite administrar el inventario de productos (chocolates), gestionar una base de datos de clientes y registrar ventas. La aplicación utiliza una arquitectura DAO (Data Access Object) para interactuar con una base de datos PostgreSQL.

## Requisitos

Para compilar y ejecutar este proyecto, necesitarás:

*   **Java Development Kit (JDK)**: Versión 21 o superior.
*   **Apache Maven**: Versión 3.6 o superior.
*   **PostgreSQL**: Un servidor de base de datos PostgreSQL activo.
*   Una base de datos creada en PostgreSQL llamada `tienda_chocolate`.

## Instrucciones de Uso

### 1. Configuración de la Base de Datos

Antes de ejecutar la aplicación, es necesario configurar la conexión a la base de datos.

*   **Edita el archivo**: `src/main/java/org/example/database/DatabaseConnection.java`
*   **Modifica los valores**: Actualiza las constantes `URL`, `USER` y `PASSWORD` con tus credenciales de PostgreSQL.

```java
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda_chocolate";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_contraseña";
    // ...
}
```

### 2. Creación de las Tablas

La aplicación requiere que las siguientes tablas existan en la base de datos `tienda_chocolate`. Puedes usar el siguiente script SQL para crearlas:

```sql
CREATE TABLE cliente (
    cliente_id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono VARCHAR(20)
);

CREATE TABLE producto (
    producto_id VARCHAR(50) PRIMARY KEY,
    origen VARCHAR(100) NOT NULL,
    porcentaje_cacao INT NOT NULL,
    precio NUMERIC(10, 2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE venta (
    venta_id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    total_venta NUMERIC(10, 2) NOT NULL,
    fecha_venta TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

CREATE TABLE detalle_venta (
    detalle_id SERIAL PRIMARY KEY,
    venta_id INT NOT NULL,
    producto_id VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL,
    subtotal NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES venta(venta_id),
    FOREIGN KEY (producto_id) REFERENCES producto(producto_id)
);
```

### 3. Compilación y Ejecución

1.  Abre una terminal en la raíz del proyecto.
2.  Compila el proyecto usando Maven:
    ```sh
    mvn compile
    ```
3.  Ejecuta la aplicación:
    ```sh
    mvn exec:java -Dexec.mainClass="Main"
    ```
4.  Sigue las instrucciones que aparecen en el menú de la consola para interactuar con la aplicación.

## Autoría y Licencia

*   **Autor**: [Tu Nombre Aquí]
*   **Licencia**: Este proyecto está bajo la Licencia MIT. Puedes ver el archivo `LICENSE` para más detalles.
