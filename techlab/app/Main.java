package com.techlab.app;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Bebida;
import com.techlab.productos.Comida;
import com.techlab.productos.Producto;
import com.techlab.servicios.PedidoService;
import com.techlab.servicios.ProductoService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase Principal que orquesta el menú interactivo.
 */
public class Main {
    // Servicios que encapsulan la lógica
    private static final ProductoService productoService = new ProductoService();
    private static final PedidoService pedidoService = new PedidoService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Carga inicial de datos de ejemplo (opcional)
        cargarDatosDeEjemplo();

        boolean salir = false;
        // Bucle del menú principal
        while (!salir) {
            mostrarMenuPrincipal();
            try {
                int opcion = leerEntero("");
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        gestionarAgregarProducto();
                        break;
                    case 2:
                        gestionarListarProductos();
                        break;
                    case 3:
                        gestionarBuscarActualizarProducto();
                        break;
                    case 4:
                        gestionarEliminarProducto();
                        break;
                    case 5:
                        gestionarCrearPedido();
                        break;
                    case 6:
                        gestionarListarPedidos();
                        break;
                    case 7:
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            } catch (Exception e) {
                // Captura genérica para otros errores (ej. IllegalArgumentException)
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTIÓN - TECHLAB ===");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
        System.out.print("Elija una opción: ");
    }

    // --- Métodos de Gestión ---

    private static void gestionarAgregarProducto() {
        System.out.println("\n--- Agregar Producto ---");
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            double precio = leerDouble("Precio: ");
            int stock = leerEntero("Stock: ");

            // Opcional: Preguntar por tipo de producto (Herencia)
            System.out.print("Tipo (1: Genérico, 2: Comida, 3: Bebida): ");
            int tipo = leerEntero("");
            scanner.nextLine(); // Consumir newline

            Producto nuevoProducto;
            switch (tipo) {
                case 2:
                    System.out.print("Fecha Vencimiento (AAAA-MM-DD): ");
                    LocalDate fecha = LocalDate.parse(scanner.nextLine());
                    nuevoProducto = new Comida(nombre, precio, stock, fecha);
                    break;
                case 3:
                    double litros = leerDouble("Volumen (Litros): ");
                    nuevoProducto = new Bebida(nombre, precio, stock, litros);
                    break;
                default:
                    nuevoProducto = new Producto(nombre, precio, stock);
                    break;
            }

            productoService.agregarProducto(nuevoProducto);

        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha incorrecto.");
        } catch (Exception e) {
            System.out.println("Error de entrada: " + e.getMessage());
        }
    }

    private static void gestionarListarProductos() {
        System.out.println("\n--- Listado de Productos ---");
        List<Producto> productos = productoService.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            // Usa el polimorfismo (toString) para mostrar
            for (Producto p : productos) {
                System.out.println(p);
            }
        }
    }

    private static void gestionarBuscarActualizarProducto() {
        System.out.println("\n--- Buscar y Actualizar Producto ---");
        int id = leerEntero("Ingrese ID del producto a buscar: ");
        scanner.nextLine(); // Consumir newline

        Optional<Producto> productoOpt = productoService.buscarProductoPorId(id);

        if (productoOpt.isPresent()) {
            Producto p = productoOpt.get();
            System.out.println("Producto encontrado:");
            System.out.println(p);

            System.out.print("¿Desea actualizar? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                double nuevoPrecio = leerDouble("Nuevo Precio (deje -1 para no cambiar): ");
                int nuevoStock = leerEntero("Nuevo Stock (deje -1 para no cambiar): ");
                scanner.nextLine(); // Consumir newline

                if (nuevoPrecio == -1)
                    nuevoPrecio = p.getPrecio();
                if (nuevoStock == -1)
                    nuevoStock = p.getStock();

                // Validación de coherencia
                if (nuevoPrecio <= 0 || nuevoStock < 0) {
                    System.out.println("Error: Valores no coherentes.");
                } else {
                    productoService.actualizarProducto(id, nuevoPrecio, nuevoStock);
                }
            }
        } else {
            System.out.println("Producto con ID " + id + " no encontrado.");
        }
    }

    private static void gestionarEliminarProducto() {
        System.out.println("\n--- Eliminar Producto ---");
        int id = leerEntero("Ingrese ID del producto a eliminar: ");
        scanner.nextLine(); // Consumir newline

        // Confirmación opcional
        System.out.print("¿Seguro que desea eliminar este producto? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            if (!productoService.eliminarProducto(id)) {
                System.out.println("No se pudo eliminar: Producto no encontrado.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private static void gestionarCrearPedido() {
        System.out.println("\n--- Crear Nuevo Pedido ---");
        Pedido nuevoPedido = new Pedido();
        boolean seguirAgregando = true;

        while (seguirAgregando) {
            System.out.println("Agregando producto al pedido (ingrese 0 para finalizar):");
            gestionarListarProductos(); // Mostrar productos para facilitar

            int idProducto = leerEntero("ID del producto: ");
            scanner.nextLine(); // Consumir newline

            if (idProducto == 0) {
                seguirAgregando = false;
                continue;
            }

            int cantidad = leerEntero("Cantidad deseada: ");
            scanner.nextLine(); // Consumir newline

            try {
                // 1. Validar Stock ANTES de agregar
                productoService.validarYReducirStock(idProducto, cantidad);

                // 2. Si hay stock, agregar a la línea de pedido
                Producto p = productoService.buscarProductoPorId(idProducto).get(); // Sabemos que existe por la
                                                                                    // validación
                LineaPedido linea = new LineaPedido(p, cantidad);
                nuevoPedido.agregarLinea(linea);
                System.out.println(cantidad + "x " + p.getNombre() + " agregado(s) al pedido.");

            } catch (StockInsuficienteException | IllegalArgumentException e) {
                // Captura la excepción personalizada
                System.out.println("Error al agregar producto: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (nuevoPedido.getLineas().isEmpty()) {
            System.out.println("Pedido cancelado (sin productos).");
        } else {
            // 3. Guardar y mostrar el pedido final
            pedidoService.guardarPedido(nuevoPedido);
            System.out.println("--- Pedido Confirmado ---");
            System.out.println(nuevoPedido);
        }
    }

    private static void gestionarListarPedidos() {
        System.out.println("\n--- Listado de Pedidos Realizados ---");
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            // Imprime usando el toString() de Pedido
            for (Pedido p : pedidos) {
                System.out.println(p);
            }
        }
    }

    // --- Métodos de utilidad para Scanner ---

    /**
     * Lee un entero de forma segura, manejando NumberFormatException.
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero válido.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    /**
     * Lee un double de forma segura, manejando NumberFormatException.
     */
    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número decimal válido (use '.' como separador).");
                scanner.nextLine(); // Limpiar buffer
            }
        }
    }

    // Método para tener datos al iniciar
    private static void cargarDatosDeEjemplo() {
        try {
            productoService.agregarProducto(new Producto("Café Premium", 1500.50, 100));
            productoService.agregarProducto(new Producto("Té Verde", 800.0, 80));
            productoService.agregarProducto(new Comida("Yogur", 500.75, 50, LocalDate.now().plusDays(10)));
            productoService.agregarProducto(new Bebida("Agua Mineral", 300.0, 120, 1.5));
            System.out.println("Datos de ejemplo cargados.");
        } catch (Exception e) {
            System.out.println("Error cargando datos de ejemplo: " + e.getMessage());
        }
    }
}