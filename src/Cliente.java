
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws RemoteException {
		Interfaz agenda = null;
		try {
			// System.out.println("Localizando el registro de objetos remitos");
			Registry registry = LocateRegistry.getRegistry("localhost", 5555);
			// System.out.println("Obteniendo el stab del objeto remoto");
			agenda = (Interfaz) registry.lookup("Agenda");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("AGENDA"); 
		System.out.println(
				".......................... \n .  0 Salir \n .  1 Registrar \n .  2 Log In \n ..........................");
		if (agenda != null) {
			try {
				int opcion = Integer.parseInt(sc.next());
				System.out.println("OPCION SELECCIONADA:" + opcion);

				switch (opcion) {
				case 0:
					System.out.println("TERMINANDO PROGRAMA");
					break;
				case 1:
					System.out.println("REGISTRO");
					System.out.println("Escribe el nombre de usuario");
					String nombreReg = sc.next();
					// comprobamos si ese user existe ya
					boolean comprobar = agenda.comprobarUser(nombreReg);
					while (comprobar) {
						System.out.println("El usuario que ha introducido ya existe, escoja otro");
						System.out.println("Escribe el nombre de usuario");
						nombreReg = sc.next();
						comprobar = agenda.comprobarUser(nombreReg);
					}
					System.out.println("Escribe la contraseña para tu nuevo usuario de nombre '" + nombreReg + "'");
					String passReg = sc.next();

					agenda.registrar(nombreReg, passReg);
					System.out.println("Usuario Registrado");
					// vuelve al principio para poder loguear o registrar otro
					// user
					main(args);
					break;
				case 2:
					System.out.println("LOGIN");
					System.out.println("Escribe el nombre de usuario");
					String nombre = sc.next();

					System.out.println("Escribe la contraseña de tu usuario");
					String pass = sc.next();
					int op = 1;
					if (agenda.login(nombre, pass)) {
						System.out.println("Realizando operaciones con el objeto remoto");
						System.out.println("Login correcto");
						System.out.println("BIENVENIDO " + nombre);
						while (op != 0) {
							System.out.println(
									".......................... \n .  0 Salir \n .  1 Crear Nuevo Contacto  \n .  2 Modificar Contacto Existente \n .  3 Borrar Contacto \n .  4 Ver lista contactos  \n .  5 Consultar Contactos Buscando por Nombre \n .  6 Log out \n ..........................");
							try {
								op = Integer.parseInt(sc.next());
								System.out.println("OPCION SELECCIONADA:" + op);
								switch (op) {
								case 0:
									System.out.println("TERMINANDO PROGRAMA");
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

									System.out
											.println(agenda.modificarContacto(nombreM, numero, emailM, apellidosM, id));
									break;
								case 3:
									System.out.println(agenda.verListaContacto());
									System.out.println("Introduzca la id del contacto que se quiere borrar ");
									int idB = sc.nextInt();

									System.out.println(agenda.borrarContacto(idB));
									break;
								case 4:
									ArrayList<String> result = agenda.verListaContacto();
									if (result.size() == 0) {
										System.out.println("No tiene ningun contacto");
									} else {
										System.out.println("Ver lista contactos \n " + result);
									}
									break;
								case 5:
									System.out.println(
											"Introduzca el nombre por el que desea hacer la busqueda en sus contactos");
									String nomBuscar = sc.next();

									ArrayList<String> result2 = agenda.verListaContacto(nomBuscar);
									if (result2.size() == 0) {
										System.out.println("No tiene ningun contacto con ese nombre");
									} else {
										System.out.println("Ver lista contactos buscando por nombre " + nomBuscar
												+ ": \n " + result2);
									}
									break;
								case 6:
									System.out.println("Log out");
									main(args); // vuelve a ejecutarse el main,
												// por lo que vuelve al menu
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

					} else {
						System.out.println("Login incorrecto");
						main(args);
					}
					break;
				default:
					System.out.println("Introduzca un numero valido");
					main(args);
					break;
				}
				sc.close();
			} catch (Exception e) {
				System.out.println("Introduzca un numero valido");
				// main(args);
			}
		}
	}
}