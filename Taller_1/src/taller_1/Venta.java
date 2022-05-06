package taller_1;

public class Venta {

	private String nombreProducto;
	private int cantVentas;
	
	public Venta(String nombreProducto, int cantVentas) {
		this.nombreProducto = nombreProducto;
		this.cantVentas = cantVentas;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}
	
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	public int getCantVentas() {
		return cantVentas;
	}
	
	public void setCantVentas(int cantVentas) {
		this.cantVentas = cantVentas;
	}
	
	public void agregarVentaProducto(int cantidad) {
		cantVentas += cantidad;
	}
}
