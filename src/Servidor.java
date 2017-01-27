import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Servidor implements Interfaz {
	BBDD bbdd;

	@Override
	public ArrayList<String> verListaContacto() {
		bbdd = new BBDD();
		System.out.println(bbdd.verContactos());

		// TODO Auto-generated method stub
		return bbdd.verContactos();
	}

	@Override
	public String crearContacto(String nombre, int tlf, String email, String apellidos) {
		bbdd = new BBDD();
		String contacto = "Nombre: "  + nombre+ ", Apellidos: " + apellidos + ", Email: " + email + ", Numero de Telefono: " + tlf; 
		if(bbdd.insertarContacto(nombre, tlf, email, apellidos)){
			return contacto;
		} else {
			return null;
		}
		
	}

	@Override
	public String modificarContacto(String nombre, int numero, String email, String apellidos, int id) {
		bbdd = new BBDD();
		if(bbdd.modificarContacto(nombre, numero, email, id, apellidos)) {
			return "Hecho correctamente (Modificacion)";
		} else {
			return "Hecho incorrectamente (Modificacion)";
		}
	}

	@Override
	public String borrarContacto(int id) {
		bbdd = new BBDD();
		if(bbdd.borrarcontacto(id)) {
			return "Hecho correctamente (Borrado)";
		} else {
			return "Hecho incorrectamente (Borrado)";
		}
		
	}

	public static void main(String[] args) {
		Registry reg = null;
		try {
			System.out.println("Crea el registro de objetos, escuchando en el puerto 5555");
			reg = LocateRegistry.createRegistry(5555);
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido crear el registro");
			e.printStackTrace();
		}
		System.out.println("Creando el objeto servidor");
		Servidor serverObject = new Servidor();
		try {
			System.out.println("Inscribiendo el objeto servidor en el registro");
			System.out.println("Se le da un nombre único: Agenda");
			reg.rebind("Agenda", (Interfaz) UnicastRemoteObject.exportObject(serverObject, 0));
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido inscribir el objeto servidor.");
			e.printStackTrace();
		}
	}

	@Override
	public boolean login(String nombre, String pass) throws RemoteException {
		bbdd = new BBDD();
		if (bbdd.comprobarUser(nombre, pass)) {
			return true;
		} else {
			return false;
		}
	}

}