package taller_1;

public class Cliente {

	private String nombre;
	private String contrasena;
	private int saldo;
	private String correo;
	
	public Cliente(String nombre, String contrasena, int saldo, String correo) {
		this.nombre = nombre;
		this.correo = correo;
		this.saldo = saldo;
		this.contrasena = contrasena;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public int getSaldo() {
		return saldo;
	}
	
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
}
