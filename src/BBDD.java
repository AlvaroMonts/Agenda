import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BBDD {
	Connection con;
	static int id;

	public void crearConexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			System.out.println("Conecta con base de datos");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean comprobarUser(String nombre, String pass) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nombre,pass,id FROM implementacion.usuarios");
			while (rs.next()) {
				String nombreBBDD = rs.getString("nombre");
				String passBBDD = rs.getString("pass");
				int idUserLogueado = rs.getInt("id");
				if (nombre.equals(nombreBBDD) && pass.equals(passBBDD)) {
					this.id = idUserLogueado;
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean insertarUsuario(String nombre, String pass) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			String sql = "INSERT INTO implementacion.usuarios (nombre,pass) VALUES (?,?)";
			PreparedStatement psts = con.prepareStatement(sql);
			psts.setString(1, nombre);
			psts.setString(2, pass);
			psts.executeUpdate();
			psts.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean comprobarUserReg(String nombre) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nombre FROM implementacion.usuarios");
			while (rs.next()) {
				String nombreBBDD = rs.getString("nombre");
				if (nombre.equals(nombreBBDD)) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean modificarContacto(String nombre, int numero, String email, int id, String apellidos) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			String sql = "UPDATE implementacion.personas SET nombre='" + nombre + "', numerotlf='" + numero
					+ "', email='" + email + "', apellidos='" + apellidos + "' WHERE id='" + id + "';";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public boolean borrarcontacto(int id) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			String query = "DELETE FROM implementacion.personas WHERE id='" + id + "';";
			PreparedStatement psts = con.prepareStatement(query);
			psts.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertarContacto(String nombre, int tlf, String email, String apellidos) {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			String sql = "INSERT INTO implementacion.personas (nombre,numerotlf,email,idUser,apellidos) VALUES (?,?,?,?,?)";
			PreparedStatement psts = con.prepareStatement(sql);
			psts.setString(1, nombre);
			psts.setInt(2, tlf);
			psts.setString(3, email);
			System.out.println(id);
			psts.setInt(4, id);
			psts.setString(5, apellidos);
			psts.executeUpdate();
			psts.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<String> verContactos() {
		ArrayList<String> personas = new ArrayList<>();
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM implementacion.personas where idUser = " + id);
			while (rs.next()) {
				String nom = rs.getString("nombre");
				int tlf = rs.getInt("numerotlf");
				String email = rs.getString("email");
				String ape = rs.getString("apellidos");
				int id = rs.getInt("id");
				personas.add(nom + " ");
				personas.add(ape + " ");
				personas.add(tlf + " ");
				personas.add(email + " ");
				personas.add("id= " + id + "\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personas;
	}
	
	public ArrayList<String> verContactos(String nombre) {
		ArrayList<String> personas = new ArrayList<>();
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/implementacion", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM implementacion.personas where idUser = " + id + " and nombre = '" + nombre + "';");
			while (rs.next()) {
				String nom = rs.getString("nombre");
				int tlf = rs.getInt("numerotlf");
				String email = rs.getString("email");
				String ape = rs.getString("apellidos");
				int id = rs.getInt("id");
				personas.add(nom + " ");
				personas.add(ape + " ");
				personas.add(tlf + " ");
				personas.add(email + " ");
				personas.add("id=" + id + "\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personas;
	}
}