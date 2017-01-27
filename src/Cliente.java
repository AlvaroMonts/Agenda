
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws RemoteException {
		Interfaz agenda = null;
		try {
			System.out.println("Localizando el registro de objetos remitos");
			Registry registry = LocateRegistry.getRegistry("localhost", 5555);
			System.out.println("Obteniendo el stab del objeto remoto");
			agenda = (Interfaz) registry.lookup("Agenda");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int op = 1;

		System.out.println("LOGIN");
		Scanner sc = new Scanner(System.in);

		System.out.println("Escribe el nombre de usuario");
		String nombre = sc.next();

		System.out.println("Escribe la contraseña de tu usuario");
		String pass = sc.next();

		if (agenda.login(nombre, pass)) {
			System.out.println("Realizando operaciones con el objeto remoto");
			System.out.println("Login correcto");
			System.out.println("BIENVENIDO " + nombre);
			while (op != 0) {
				if (agenda != null) {
					System.out.println(".......................... \n" + ".  0 Salir \n"
							+ ".  1 Crear Nuevo Contacto  \n" + ".  2 Modificar Contacto Existente \n"
							+ ".  3 Borrar Contacto \n" + ".  4 Ver lista contactos  \n"
							+ ". 5 Consultar Contactos Buscando por Nombre \n" + ". 6 Log out  \n");
					try {
						op = Integer.parseInt(sc.next());
						System.out.println("OPCION SELECCIONADA:" + op);

						switch (op) {
						case 0:
							break;
						case 1:
							System.out.println("Introduzca el nombre");
							String Nombre = sc.next();

							System.out.println("Introduzca los apellidos");
							String apellidos = sc.next();

							System.out.println("Introduzca el numero de su telefono");
							int tlf = sc.nextInt();

							System.out.println("Introduzca el email");
							String email = sc.next();
							String comp = agenda.crearContacto(Nombre, tlf, email, apellidos);
							if (comp == null) {
								System.out.println("No se ha creado correctamente el usuario");
							} else {
								System.out.println("Contacto creado: " + comp);
							}
							break;
						case 2:
							System.out.println(agenda.verListaContacto());
							System.out.println("Introduzca la id del contacto que se quiere modificar: ");
							int id = sc.nextInt();

							System.out.println("Introduzca el nombre");
							String nombreM = sc.next();

							System.out.println("Introduzca apellidos");
							String apellidosM = sc.next();

							System.out.println("Introduzca el numero de su telefono");
							int numero = sc.nextInt();

							System.out.println("Introduzca el email");
							String emailM = sc.next();

							System.out.println(agenda.modificarContacto(nombreM, numero, emailM, apellidosM, id));
							break;
						case 3:
							System.out.println(agenda.verListaContacto());
							System.out.println("Introduzca la id del contacto que se quiere borrar ");
							int idB = sc.nextInt();

							System.out.println(agenda.borrarContacto(idB));
							break;
						case 4:
							System.out.println("Ver lista contactos \n " + agenda.verListaContacto());
							break;
						case 5:
							System.out.println("Introduzca el nombre por el que desea hacer la busqueda en sus contactos");
							String nomBuscar = sc.next();
							System.out.println("Ver lista contactos buscando por nombre " + nomBuscar + ": \n " + agenda.verListaContacto());
							break;
						case 6:
							System.out.println("Log out");
							main(args); // vuelve a ejecutarse el main, por lo que vuelve al login
							break;
						default:
							System.out.println("Introduzca un numero valido");
							break;
						}
					} catch (NumberFormatException e) {
						System.out.println("Introduzca un numero");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("Login incorrecto");
			main(args); // vuelve a ejecutarse el main
		}

	}
}