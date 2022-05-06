package taller_1;

public class ListaProductos {

	private int cant;
	private int max;
	private Producto[] listaProductos;
	
	public ListaProductos(int max) {
		this.max = max;
		cant = 0;
		listaProductos = new Producto[max];
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

	public Producto[] getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(Producto[] listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	public Producto buscarProducto(String nombre) {
		int i;
		for(i=0; i<listaProductos.length; i++) {
			if(listaProductos[i]!=null) {
				if(listaProductos[i].getNombre().equals(nombre)) {
					break;
				}
			}
		}
		if(i == max) {
			return null;
		}
		return listaProductos[i];
	}
	
	public boolean agregarProducto(Producto producto) {
		if(max>cant) {
			listaProductos[cant] = producto;
			cant++;
			return true;
		}
		return false;
	}
	
	public boolean eliminarProducto(String nombre) {
		int i;
		for(i=0; i<listaProductos.length; i++) {
			if(listaProductos[i].getNombre().equals(nombre)) {
				break;
			}
		}
		if(i == max) {
			return false;
		}
		for(int j=i; j<listaProductos.length-1; j++) {
			listaProductos[j] = listaProductos[j+1];
		}
		cant--;
		return true;
	}
	
	public void imprimirStock() {
		System.out.println("*** CATÁLOGO DE PRODUCTOS DISPONIBLES ***");
		if(!listaProductos[0].equals(null)) {
			for(int i=0; i<listaProductos.length; i++) {
				if(listaProductos[i]!=null && listaProductos[i].getStock()>0) {
					System.out.println("- "+listaProductos[i].getNombre()+", $"+listaProductos[i].getPrecio()+", Stock: "+listaProductos[i].getStock());
				}
			}
		}else {
			System.out.println("Actualmente no tenemos productos disponibles, lo sentimos.");
		}
	}
}
