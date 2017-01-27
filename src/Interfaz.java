import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Interfaz extends Remote {

	public boolean login(String nombre, String pass) throws RemoteException;
	
	public String registrar(String nombre, String pass) throws RemoteException;
	
	public boolean comprobarUser(String nombre) throws RemoteException;
	
	public ArrayList<String> verListaContacto() throws RemoteException;
	
	public ArrayList<String> verListaContacto(String nombre) throws RemoteException;

	public String crearContacto(String nombre, int tlf, String email, String apellidos)throws RemoteException;

	public String modificarContacto(String nombre, int tlf, String email, String apellidos ,int id)throws RemoteException;

	public String borrarContacto(int id)throws RemoteException;
	
}
