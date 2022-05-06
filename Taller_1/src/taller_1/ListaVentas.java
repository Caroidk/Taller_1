package taller_1;

public class ListaVentas {

	private int cant;
	private int max;
	private Venta[] listaVentas;
	
	public ListaVentas(int max) {
		this.max = max;
		cant = 0;
		listaVentas = new Venta[max];
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		this.cant = cant;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public Venta[] getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(Venta[] listaVentas) {
		this.listaVentas = listaVentas;
	}
	
	public Venta buscarVenta(String nombreProducto) {
		int i;
		for(i=0; i<listaVentas.length; i++) {
			if(listaVentas[i]!=null) {
				if(listaVentas[i].getNombreProducto().equals(nombreProducto)) {
					return listaVentas[i];
				}
			}
		}
		return null;
	}
	
	public boolean agregarVenta(Venta venta) {
		if(max > cant) {
			listaVentas[cant] = venta;
			cant++;
			return true;
		}
		return false;
	}
	
	public boolean eliminarVenta(String nombreProducto) {
		int i;
		for(i=0; i<listaVentas.length; i++) {
			if(listaVentas[i].getNombreProducto().equals(nombreProducto)) {
				break;
			}
		}
		if(i == max) {
			return false;
		}
		for(int j=i; j<listaVentas.length-1; j++) {
			listaVentas[j] = listaVentas[j+1];
		}
		cant--;
		return true;
	}
}
