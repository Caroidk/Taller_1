package taller_1;

public class ListaClientes {

	private int cant;
	private int max;
	private Cliente[] listaClientes;
	
	public ListaClientes(int max) {
		this.max = max;
		cant = 0;
		listaClientes = new Cliente[max];
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
	
	public Cliente[] getListaClientes() {
		return listaClientes;
	}
	
	public void setListaClientes(Cliente[] listaClientes) {
		this.listaClientes = listaClientes;
	}
	
	public Cliente buscarCliente(String nombre) {
		int i;
		for(i=0; i<cant; i++) {
			if(listaClientes[i].getNombre().equals(nombre)) {
				break;
			}
		}
		if(i == max) {
			return null;
		}
		return listaClientes[i];
	}
	
	public boolean agregarCliente(Cliente cliente) {
		if(max>cant) {
			listaClientes[cant] = cliente;
			cant++;
			return true;
		}
		return false;
	}
	
	public boolean eliminarCliente(String nombre) {
		int i;
		for(i=0; i<listaClientes.length; i++) {
			if(listaClientes[i].getNombre().equals(nombre)) {
				break;
			}
		}
		if(i == max) {
			return false;
		}
		for(int j=i; j<listaClientes.length-1; j++) {
			listaClientes[j] = listaClientes[j+1];
		}
		cant--;
		return true;
	}
}
