
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.NumberFormat;

/**
 *
 * @author ambar
 */
public class TeatroMoroListasArreglos {
	/**
	 * @param args the command line arguments
	 */

	static final int[] precios = { 30000, 18000, 15000, 13000, 20000 };
	static final String[] ubicaciones = { "VIP", "Platea Alta", "Platea Baja", "Palco", "General" };
	static int[] asientosDisponible = { 5, 5, 5, 5, 5 };

	static ArrayList<Object> ventas = new ArrayList<Object>();

	static Scanner scanner = new Scanner(System.in);

	public static void main(String args[]) {
		try {
			int respuestaMenu;

			System.out.println("Bienvenido al Teatro Moro");

			do {
				mostrarMenu();
				respuestaMenu = scanner.nextInt();

				switch (respuestaMenu) {
					case 1:
						System.out.println("Usted selecciono comprar una entrada");
						int ubicacionSeleccionada = seleccionarEntrada(scanner);
						if (ubicacionSeleccionada > 4 || ubicacionSeleccionada < 0) {
							System.out.println("La opcion seleccionada no es valida.");
							return;
						}
						int cantidadEntradas = seleccionarCantidadEntradas(scanner);
						if (cantidadEntradas <= 0) {
							System.out.println("Tiene que comprar al menos 1 entrada.");
							return;
						}
						if (cantidadEntradas > asientosDisponible[ubicacionSeleccionada]) {
							System.out.println("No hay stock suficiente.");
							return;
						}
						asientosDisponible[ubicacionSeleccionada] -= cantidadEntradas;
						double descuento = seleccionarDescuento(scanner);
						int precioDescuento = calcularDescuento(precios[ubicacionSeleccionada], cantidadEntradas, descuento);
						int precioPorCompra = precios[ubicacionSeleccionada] * cantidadEntradas;

						comprarEntrada(ubicaciones[ubicacionSeleccionada], precioPorCompra, descuento,
								precioDescuento);
						break;
					case 2:
						System.out.println("Usted selecciono ver resumen de la compra");
						mostrarResumenCompra();
						break;
					case 3:
						System.out.println("Usted selecciono ver generar una boleta");
						generarBoleta();
						break;
					case 4:
						System.out.println("Usted selecciono mostrar totales de compra");
						calcularIngresosTotales();
						break;
					case 5:
						System.out.println("Gracias por visitar Teatro Moro");
						break;
					default:
						System.out.println("Opcion invalida. Ingrese otra opcion.");
				}
			} while (respuestaMenu != 5);
			scanner.close();
		} catch (InputMismatchException e) {
			System.out.println("Error: seleccione una opcion valida");
		} finally {
			System.out.println("Fin del programa.");
			scanner.close();
		}
	}

	public static void mostrarMenu() {

		System.out.println("\nPor favor elija una de las siguientes opciones:");
		System.out.println("1. Comprar Entrada");
		System.out.println("2. Ver resumen de compra");
		System.out.println("3. Generar boleta");
		System.out.println("4. Mostrar totales");
		System.out.println("5. Salir");
		System.out.print("Seleccione una opcion: ");

	}

	public static int seleccionarEntrada(Scanner scanner) {
		System.out.println("\nSeleccione la ubicacion (1-5):");
		for (int i = 0; i < ubicaciones.length; i++) {
			System.out
					.println((i + 1) + ". Ubicacion: " + ubicaciones[i] + " - stock: " + asientosDisponible[i] + " disponibles");
		}

		int ubicacionSeleccionada = scanner.nextInt();
		return ubicacionSeleccionada - 1;
	}

	public static int seleccionarCantidadEntradas(Scanner scanner) {
		System.out.println("\nCuantas entradas desea comprar: ");
		int cantidadEntradas = scanner.nextInt();
		return cantidadEntradas;
	}

	public static void comprarEntrada(String ubicacion, int precioBase, double descuentoAplicado, int precioFinal) {
		NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
		formatoPorcentaje.setMaximumFractionDigits(2);
		String porcentaje = formatoPorcentaje.format(1 - descuentoAplicado);
		Object[] venta = { ubicacion, precioBase, precioFinal, porcentaje };
		ventas.add(venta);
		System.out.println("Compra realizada: " + Arrays.toString(venta));
	}

	static double seleccionarDescuento(Scanner scanner) {

		System.out
				.println("Seleccione una de las siguientes opciones: 1. Publico general, 2. Estudiante, 3. Tercera edad ");
		int tipoDescuento = scanner.nextInt();

		switch (tipoDescuento) {
			case 1:
				System.out.println("Usted selecciono Publico general");
				return 1;

			case 2:
				System.out.println("Usted selecciono Estudiante");
				return 0.9;

			case 3:
				System.out.println("Usted selecciono Tercera edad");
				return 0.85;

			default:
				System.out.println("La opcion ingresada no es valida");
				System.out.println("Su compra se considerara como Publico General.");
				return 1;
		}

	}

	public static int calcularDescuento(int precio, int cantidadEntradas, double descuento) {
		return (int) ((precio * cantidadEntradas) * descuento);

	}

	public static void mostrarResumenCompra() {

		if (ventas.isEmpty()) {
			System.out.println("No hay ventas para mostrar.");
			return;
		}

		System.out.println("Historial de ventas: ");
		for (Object i : ventas) {
			Object[] fila = (Object[]) i;
			System.out.println("-------------------");
			System.out.println("Ubicacion: " + fila[0]);
			System.out.println("Costo Final: " + fila[2]);
			System.out.println("Descuento aplicado: " + fila[3]);
		}

	}

	public static void generarBoleta() {
		if (ventas.isEmpty()) {
			System.out.println("No hay ventas para generar una boleta. Por favor, proceda a comprar una entrada.");
			return;
		}
		System.out.println("-------------------");
		System.out.println("Boleta de Teatro Moro: ");
		for (Object i : ventas) {
			Object[] fila = (Object[]) i;
			System.out.println("-------------------");
			System.out.println("Ubicacion: " + fila[0]);
			System.out.println("Costo base: " + fila[1]);
			System.out.println("Descuento aplicado: " + fila[3]);
			System.out.println("Costo Final: " + fila[2]);
		}
		System.out.println("-------------------");
		System.out.println("Gracias por su visita al Teatro Moro");
		System.out.println("-------------------");

	}

	public static void calcularIngresosTotales() {
		int sumaTotal = 0;
		for (Object i : ventas) {
			Object[] fila = (Object[]) i;
			sumaTotal = sumaTotal + (int) fila[2];
		}
		System.out.println("Total de todas sus entradas: " + sumaTotal);

	}

}
