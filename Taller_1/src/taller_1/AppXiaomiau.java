package taller_1;
import java.util.*;
import java.io.*;

public class AppXiaomiau {

	public static void main(String[] args) throws IOException{

		ListaClientes clientes = new ListaClientes(100);
		ListaProductos productos = new ListaProductos(100);
		ListaVentas ventas = new ListaVentas(100);
		String[][] carrito = new String[100][100];
		
		boolean ver = false;
		
		try {
			leerClientes(clientes);
			leerProductos(productos);
			leerVentas(ventas);
			
			System.out.println("cant: "+clientes.getMax());
			
			String usuario = iniciarSesion(clientes);
			if(usuario!=null) {
				System.out.println("\n*** BIENVENIDO "+usuario+" ***\n");
			}
			int fila = 0;
			while(usuario!=null){
				if(!usuario.equals("ADMIN")) {
					System.out.println("*** MENÚ ***");
					System.out.print("1) Elegir productos\n2) Cambiar contraseña\n3) Ver catálogo de productos\n4) Ver saldo\n5) Recargar saldo\n6) Ver carrito\n7) Quitar producto del carrito\n8) Pagar carrito\nSu opción:  ");
					
					int op = opcion();
					while(op<1 || op>8) {
						System.out.print("Opción no válida. Ingrese nuevamente: ");
						op = opcion();
					}
						
					switch(op) {
						case 1:
							fila = elegirProds(productos, carrito, fila);
							break;
						case 2:
							cambiarPass(clientes, usuario);
							break;
						case 3:
							productos.imprimirStock();
							break;
						case 4:
							System.out.println("- Saldo disponible: $"+clientes.buscarCliente(usuario).getSaldo());
							break;
						case 5:
							recargarSaldo(clientes, usuario);
							break;
						case 6:
							verCarrito(carrito);
							break;
						case 7:
							if(carrito[0][0]!=null) {
								quitarCarrito(carrito, fila);
							}else {
								System.out.println("** Carrito de compras vacío. **");
							}
							break;
						case 8:
							if(carrito[0][0]!=null) {
								ver = pagarCarrito(carrito, clientes, productos, ventas, usuario, fila);
							}else {
								System.out.println("** Carrito de compra vacío. **");
							}
							break;
					}
					if(op==8 && ver==true) {
						break;
					}
				}else {
					if(usuario.equals("ADMIN")) {
						System.out.println("*** MENÚ ***");
						System.out.print("1) Bloquear usuario\n2) Ver historial de compras\n3) Agregar producto\n4) Agregar stock\n5) Actualizar datos\n6) Salir\nSu opción: ");
						int op = opcion();
						while(op<1 || op>6) {
							System.out.print("Opción no válida. Ingrese nuevamente: ");
							op = opcion();
						}
						switch(op) {
							case 1:
								bloquearUsuario(clientes);
								break;
							case 2:
								verHistorial(ventas);
								break;
							case 3:
								agregarProd(productos);
								break;
							case 4:
								agregarStock(productos);
								break;
							case 5:
								actualizarDatos(productos);
								break;
						}
						if(op==6) {
							System.out.println("Saliendo del sistema...");
							break;
						}
					}else {
						break;
					}
				}
			}
			guardarArchivos(clientes, productos, ventas);
		}catch (FileNotFoundException e){
			System.out.println("ERROR: Archivo(s) no encontrado(s).");
		}
		
		System.out.println("*** SISTEMA FINALIZADO ***");
	}
	/**
	 * The file "Clientes.txt" is opened and the informatio within it is saved in a list.
	 * @param clientes - Customers list
	 * @throws IOException - An exception
	 */
	public static void leerClientes(ListaClientes clientes) throws IOException{
		BufferedReader archivo = new BufferedReader(new FileReader("Clientes.txt"));
		String linea;
		while((linea = archivo.readLine())!=null) {
			String[] partes = linea.split(",");
			Cliente cliente = new Cliente(partes[0],partes[1],Integer.parseInt(partes[2]),partes[3]);
			clientes.agregarCliente(cliente);
		}
		archivo.close();
	}
	/**
	 * The file "Productos.txt" is opened and the information within it is saved in a list.
	 * @param productos - Items list
	 * @throws IOException - An exception
	 */
	public static void leerProductos(ListaProductos productos) throws IOException{
		BufferedReader archivo = new BufferedReader(new FileReader("Productos.txt"));
		String linea;
		while((linea = archivo.readLine())!=null) {
			String[] partes = linea.split(",");
			Producto prod = new Producto(partes[0],Integer.parseInt(partes[1]),Integer.parseInt(partes[2]));
			productos.agregarProducto(prod);
		}
		archivo.close();
	}
	/**
	 * The file "Ventas.txt" is opened and the information within it is saved in a list.
	 * @param ventas - Sales list
	 * @throws IOException - An exception
	 */
	public static void leerVentas(ListaVentas ventas) throws IOException{
		BufferedReader archivo = new BufferedReader(new FileReader ("Ventas.txt"));
		String linea;
		while((linea = archivo.readLine())!=null) {
			String[] partes = linea.split(",");
			Venta venta = new Venta(partes[0],Integer.parseInt(partes[1]));
			ventas.agregarVenta(venta);
		}
		archivo.close();
	}
	/**
	 * It takes the information within in the list clientes to loggin in the system.
	 * @param clientes - Customers list
	 * @return the name of the customer who logged in
	 */
	public static String iniciarSesion(ListaClientes clientes) {
		Scanner inicio = new Scanner(System.in);
		System.out.println("*** INICIAR SESIÓN ***");
		System.out.print("Ingrese nombre de usuario: ");
		String nombre = inicio.nextLine();
		if(clientes.buscarCliente(nombre)!=null) {
			if(clientes.buscarCliente(nombre).getNombre().equals(nombre)) {
				System.out.print("Ingrese su contraseña: ");
				String pass = inicio.nextLine();
				if(nombre.equals("ADMIN") && pass.equals("NYAXIO")) {
					return nombre;
				}
				while(!clientes.buscarCliente(nombre).getContrasena().equals(pass)) {
					System.out.print("Contraseña incorrecta. Ingrese nuevamente: ");
					pass = inicio.nextLine();
				}
				if(clientes.buscarCliente(nombre).getContrasena().equals(pass)) {
					System.out.println("** SESIÓN INICIADA **");
					return nombre;
				}
			}}
			if(nombre.equals("ADMIN")) {
				System.out.print("Ingrese su contraseña: ");
				String pass = inicio.nextLine();
				if(nombre.equals("ADMIN") && pass.equals("NYAXIO")) {
					return nombre;
				}
				System.out.println("Contraseña incorrecta.");
				return null;
			}
			System.out.print("** Usuario no encontrado. **\n¿Desea agregar un nuevo jugador?\n1) Sí\n2) No\nSu opción: ");
			int op = 0;
			while(op!=1 && op!=2) {
				try {
					op = inicio.nextInt();
					if(op!=1 && op!=2) {
						System.out.print("Ingrese nuevamente: ");
						op = inicio.nextInt();
					}
				}catch (InputMismatchException e) {
					System.out.println("Opción ingresada no es un número.");
				}
			}
			if(op == 2) {
				return null;
			}
			return crearUsuario(clientes);
		
		
	}
	/**
	 * It creates a new customer in the system and saves the information in the customers list.
	 * @param clientes - Customers list
	 * @return the name of the new customer
	 */
	public static String crearUsuario(ListaClientes clientes) {
		Scanner crear = new Scanner(System.in);
		System.out.println("*** CREAR NUEVO USUARIO ***");
		System.out.print("Ingrese nuevo nombre de usuario: ");
		String nombre = crear.nextLine();
		while(clientes.buscarCliente(nombre)!=null || nombre.equals("")) {
			System.out.print("Nombre de usuario no disponible. Ingrese otro nombre: ");
			nombre = crear.nextLine();
		}
		System.out.print("Ingrese nueva contraseña (máximo 10 carácteres): ");
		String pass = crear.nextLine();
		while(pass.equals("") || pass.length()>10) {
			System.out.print("Contraseña no válida, recuerde que puede ser de máximo 10 carácteres. Ingrese nuevamente: ");
			pass = crear.nextLine();
		}
		System.out.print("Ingrese su correo electrónico: ");
		String correo = crear.nextLine();
		while(correo.equals("") || !correo.contains("@")) {
			System.out.print("Correo no válido. Ingrese nuevamente: ");
			correo = crear.nextLine();
		}
		Cliente cliente = new Cliente(nombre,pass,0,correo);
		clientes.agregarCliente(cliente);
		System.out.println("** Usuario creado exitosamente. **");
		return nombre;
	}
	/**
	 * Catch the customers and admin options from the menu.
	 * @return op - The entered option
	 */
	public static int opcion() {
		Scanner leer = new Scanner(System.in);
		int op = 0;
		try {
			op = leer.nextInt();
		}catch (InputMismatchException e) {
			return -1;
		}
		return op;
	}
	/**
	 * Adds an avalaible item from the items list to the shopping cart.
	 * @param prods - Items list
	 * @param carro - Shopping cart list
	 * @param fila - Number of all different items in the shopping cart
	 * @return fila - he number of all different items in the shopping cart -1
	 */
	public static int elegirProds(ListaProductos prods, String[][] carro, int fila) {
		Scanner elegir = new Scanner(System.in);
		System.out.println("*** ELEGIR PRODUCTOS ***");
		System.out.print("Ingrese nombre de producto: ");
		String producto = elegir.nextLine();
		Producto prod = prods.buscarProducto(producto);
		int i;
		if(prod!=null) {
			if(prod.getStock()>0) {
				System.out.print("¿Cuántos desea comprar?: ");
				int cantidad = opcion();
				while(cantidad>prod.getStock() || cantidad<=0) {
					System.out.println("La cantidad ingresada no válida. Stock: "+prod.getStock());
					System.out.print("Ingrese otra cantidad: ");
					cantidad = opcion();
				}
				if(cantidad<=prod.getStock() && cantidad>0) {
					if(carro[0][0]!=null) {
						for(i=0; i<carro.length; i++) {
							if(carro[i][0]!=null) {
								if(carro[i][0].equals(prod.getNombre())) {
									break;
								}
							}
						}
						if(i == carro.length) {
							carro[fila][0] = producto;
							carro[fila][1] = cantidad+"";
							carro[fila][2] = prod.getPrecio()*cantidad+"";
							fila++;
						}else {
							carro[i][1] = (Integer.parseInt(carro[i][1])+cantidad)+"";
							carro[i][2] = prod.getPrecio()*Integer.parseInt(carro[i][1])+"";
							
						}
					}else {
						carro[fila][0] = producto;
						carro[fila][1] = cantidad+"";
						carro[fila][2] = prod.getPrecio()*cantidad+"";
						fila++;
					}
					prod.setStock(prod.getStock()-cantidad);
					System.out.println("Producto agregado al carrito.");
					return fila;
				}
			}
			System.out.println("*** Producto fuera de stock. ***");
			return fila;
		}
		System.out.println("*** Producto no encontrado. ***");
		return fila;
	}
	/**
	 * Changes the password of the current customer.
	 * @param clientes - Customers list
	 * @param usuario - Customer name
	 */
	public static void cambiarPass(ListaClientes clientes, String usuario) {
		Scanner cambiar = new Scanner(System.in);
		System.out.println("*** CAMBIAR CONTRASEÑA ***");
		String passActual = clientes.buscarCliente(usuario).getContrasena();
		System.out.print("Ingrese su contraseña actual: ");
		String pass = cambiar.nextLine();
		while(!passActual.equals(pass)) {
			System.out.print("La contraseña ingresada no coincide. Ingrese nuevamente: ");
			pass = cambiar.nextLine();
		}
		System.out.print("Ingrese su nueva contraseña (máximo 10 carácteres): ");
		pass = cambiar.nextLine();
		while(pass.length() < passActual.length() || pass.length() == 10) {
			System.out.print("La contrasena debe ser igual o más larga que la anterior y no puede superar los 10 carácteres. Ingrese otra contraseña: ");
			pass = cambiar.nextLine();
		}
		clientes.buscarCliente(usuario).setContrasena(pass);
		System.out.println("** Contraseña cambiada exitosamente. **");
	}
	/**
	 * Recharge the customer balance.
	 * @param clientes - Customers list
	 * @param usuario - Customer name
	 */
	public static void recargarSaldo(ListaClientes clientes, String usuario) {
		Scanner recarga = new Scanner(System.in);
		System.out.println("*** RECARGAR SALDO ***");
		Cliente cliente = clientes.buscarCliente(usuario);
		System.out.print("Ingrese la cantidad de dinero que desea recargar: ");
		try {
			int dinero = recarga.nextInt();
			while(dinero<0) {
				System.out.print("Saldo ingresado no válido. Ingrese nuevo saldo: ");
				dinero = recarga.nextInt();
			}
			cliente.setSaldo(cliente.getSaldo()+dinero);
			System.out.println("Saldo recargado exitosamente.");
		}catch(InputMismatchException e) {
			System.out.println("Entrada no válida.");
		}
	}
	/**
	 * Shows all the items within the shopping cart.
	 * @param carro - Shopping cart list
	 * @return true or false - It depends if the shopping cart is empty or not
	 */
	public static boolean verCarrito(String[][] carro) {
		if(carro[0][0]!=null) {
			System.out.println("*** CARRITO DE COMPRAS ***");
			for(int i=0; i<carro.length; i++) {
				if(carro[i][0]!=null) {
					System.out.println("- "+carro[i][0]+" ("+carro[i][1]+"), $"+carro[i][2]);
				}
			}
			return true;
		}
		System.out.println("*** CARRITO VACÍO ***");
		return false;
	}
	/**
	 * Remove a certain item and its quantity from the shopping cart.
	 * @param carro - Shopping cart list
	 * @param fila - Number of all different items within the shopping cart
	 * @return true or false - It depends if the shopping cart is empty or not
	 */
	public static boolean quitarCarrito(String[][] carro, int fila) {
		Scanner quitar = new Scanner(System.in);
		System.out.println("*** QUITAR PRODUCTO ***");
		System.out.print("Ingrese nombre del producto a quitar: ");
		String prod = quitar.nextLine();
		int i;
		for(i=0; i<carro.length; i++) {
			if(carro[i][0].equals(prod)) {
				break;
			}
		}
		if(i == carro.length) {
			System.out.println("Producto no encontrado.");
			return false;
		}
		System.out.print("Ingrese la cantidad de productos que desea eliminar: ");
		int cantidadP = quitar.nextInt();
		while(cantidadP > Integer.parseInt(carro[i][1])) {
			System.out.print("Cantidad ingresada excede el máximo de productos disponibles en el carrito ("+carro[i][1]+"). Ingrese nuevamente: ");
			cantidadP = quitar.nextInt();
		}
		if(cantidadP > 0) {
			carro[i][2] = (Integer.parseInt(carro[i][2])/Integer.parseInt(carro[i][1]))+"";
			carro[i][1] = (Integer.parseInt(carro[i][1])-cantidadP)+"";
			carro[i][2] = (Integer.parseInt(carro[i][2])*Integer.parseInt(carro[i][1]))+"";
			fila--;
			System.out.println("Productos eliminado exitosamente.");
			return true;
		}
		System.out.println("No se eliminó nada.");
		return false;
	}
	/**
	 * Shows the total price of the current shopping cart and asks the customer if he wants to pay it or not.
	 * @param carro- Shopping cart list
	 * @param clientes - Customers list
	 * @param prods - Items list
	 * @param ventas - Sales list
	 * @param usuario - Customer name
	 * @param fila - Number of all the different items within the shopping cart
	 * @return true or false - It depends if the customer wants to pay the total price or not
	 */
	public static boolean pagarCarrito(String[][] carro, ListaClientes clientes, ListaProductos prods, ListaVentas ventas, String usuario, int fila) {
		Scanner pagar = new Scanner(System.in);
		Cliente cliente = clientes.buscarCliente(usuario);
		System.out.println("*** PANTALLA DE PAGO ***");
		int total = 0;
		for(int i=0; i<carro.length; i++) {
			if(carro[i][0]!=null){
				total += Integer.parseInt(carro[i][2]);
			}
		}
		System.out.println("- Total de compra: $"+total);
		System.out.println(prods.buscarProducto(carro[0][0]).toString());
		System.out.print("¿Desea pagar?\n1)Sí\n2)No\nSu opción: ");
		int op = opcion();
		while(op!=1&&op!=2) {
			System.out.print("Opción no encontrada. Ingrese nuevamente: ");
			op = opcion();
		}
		if(op==1) {
			if(cliente.getSaldo()>=total) {
				cliente.setSaldo(cliente.getSaldo()-total);
				for(int i=0; i<carro.length; i++) {
					if(carro[i][0]!=null) {
						if(ventas.buscarVenta(carro[i][0])==null) {
							Venta venta = new Venta(carro[i][0],Integer.parseInt(carro[i][1]));
							ventas.agregarVenta(venta);
						}else {
							ventas.buscarVenta(carro[i][0]).agregarVentaProducto(Integer.parseInt(carro[i][1]));
						}
						carro[i][0] = null;
						carro[i][1] = null;
						carro[i][2] = null;
					}
				}
				System.out.println("** Compra realizada con exito. **");
				fila = 0;
				return true;
			}
			System.out.println("** Saldo insuficiente.**");
		}
		System.out.println("** Compra no relizada. **");
		return false;
	}
	/**
	 * Blocks a customer within the customers list by entering his name.
	 * @param clientes - Customers list
	 * @return true or false - It depends if the customer exists within the customers list or not
	 */
	public static boolean bloquearUsuario(ListaClientes clientes) {
		Scanner bloquear = new Scanner(System.in);
		System.out.print("Ingrese nombre de usuario a bloquear: ");
		String nombre = bloquear.nextLine();
		if(clientes.buscarCliente(nombre)!=null) {
			clientes.eliminarCliente(nombre);
			System.out.println("** Cliente bloqueado exitosamente. **");
			return true;
		}
		System.out.println("** Cliente no encontrado. **");
		return false;
	}
	/**
	 * Shows all the products and its number of sales within sales list.
	 * @param ventas - Sales list
	 */
	public static void verHistorial(ListaVentas ventas) {
		Venta[] venta = ventas.getListaVentas();
		if(ventas.getCant()>0) {
			for(int i=0; i<ventas.getCant();i++) {
				System.out.println("- Producto: "+venta[i].getNombreProducto() + ", cantidad de veces comprado: " + venta[i].getCantVentas());
			}
		}else {
			System.out.println("** No hay ventas registradas. **");
		}
	}
	/**
	 * Adds a new item, its price and its stock to the items list.
	 * @param prods - Items list
	 * @return true or false - It depends if the entered item name exists within the items list or not
	 */
	public static boolean agregarProd(ListaProductos prods) {
		Scanner agregar = new Scanner(System.in);
		System.out.println("*** AGREGAR NUEVO PRODUCTO ***");
		System.out.print("Ingrese nombre de producto nuevo: ");
		String nombre = agregar.nextLine();
		if(prods.buscarProducto(nombre)==null) {
			System.out.print("Ingrese el precio del producto: ");
			int precio = opcion();
			while(precio<0) {
				System.out.print("Precio no válido. Ingrese nuevamente: ");
				precio = agregar.nextInt();
			}
			System.out.print("Ingrese stock inicial: ");
			int stock = opcion();
			while(stock<0) {
				System.out.print("Stock no válido. Ingrese nuevamente: ");
				stock = agregar.nextInt();
			}
			Producto producto = new Producto(nombre,precio,stock);
			prods.agregarProducto(producto);
			System.out.println("** Producto agregado exitosamente. **");
			return true;
		}
		System.out.println("** Producto ingresado ya existe. **");
		return false;
	}
	/**
	 * Adds stock to a certain item within the items list.
	 * @param prods - Items lis
	 * @return true or false - It depends if the entered item name exists within the items list or not
	 */
	public static boolean agregarStock(ListaProductos prods) {
		Scanner stock = new Scanner(System.in);
		System.out.println("*** AGREGAR STOCK ***");
		System.out.print("Ingrese nombre de producto: ");
		String nombre = stock.nextLine();
		if(prods.buscarProducto(nombre)!=null) {
			System.out.print("Ingrese stock a agregar: ");
			int cant = opcion();
			while(cant<0) {
				System.out.print("Stock no válido. Ingrese nuevamente: ");
				cant = opcion();
			}
			prods.buscarProducto(nombre).setStock(prods.buscarProducto(nombre).getStock()+cant);
			System.out.println("** Stock agregado exitosamente. **");
			return true;
		}
		System.out.println("** Producto no existe. **");
		return false;
	}
	/**
	 * Refresh the price of a certain item within items list.
	 * @param prods - Items list
	 * @return true or false - It depends if the entered item name exists within items list or not
	 */
	public static boolean actualizarDatos(ListaProductos prods) {
		Scanner act = new Scanner(System.in);
		System.out.println("*** ACTUALIZAR DATOS ***");
		System.out.print("Ingrese nombre de producto: ");
		String nombre = act.nextLine();
		if(prods.buscarProducto(nombre)!=null) {
			System.out.print("Ingrese nuevo precio: ");
			int precio = opcion();
			while(precio<0) {
				System.out.print("Precio ingresado no válido. Ingrese nuevamente: ");
				precio = opcion();
			}
			prods.buscarProducto(nombre).setPrecio(precio);
			System.out.println("** Precio actualizado exitosamente. **");
			return true;
		}
		System.out.println("** Producto no encontrado. **");
		return false;
	}
	/**
	 * Saves all the current information within the lists in the files "Clientes.txt", "Productos.txt" and "Ventas.txt".
	 * @param clientes - Customers list
	 * @param prods - Items list
	 * @param ventas - Sales list
	 * @throws IOException - An exception
	 */
	public static void guardarArchivos(ListaClientes clientes, ListaProductos prods, ListaVentas ventas) throws IOException{
		BufferedWriter archCliente = new BufferedWriter(new FileWriter("Clientes.txt"));
		Cliente[] cliente = clientes.getListaClientes();
		for(int i=0; i<cliente.length;i++) {
			if(cliente[i]!=null) {
				String linea = cliente[i].getNombre()+","+cliente[i].getContrasena()+","+cliente[i].getSaldo()+","+cliente[i].getCorreo()+"\n";
				archCliente.write(linea);
			}
		}
		archCliente.close();
		
		BufferedWriter archProd = new BufferedWriter(new FileWriter("Productos.txt"));
		Producto[] prod = prods.getListaProductos();
		for(int i=0; i<prod.length;i++) {
			if(prod[i]!=null) {
				String linea = prod[i].getNombre()+","+prod[i].getPrecio()+","+prod[i].getStock()+"\n";
				archProd.write(linea);
			}
		}
		archProd.close();
		
		BufferedWriter archVenta = new BufferedWriter(new FileWriter("Ventas.txt"));
		Venta[] venta = ventas.getListaVentas();
		for(int i=0; i<venta.length;i++) {
			if(venta[i]!=null) {
				String linea = venta[i].getNombreProducto()+","+venta[i].getCantVentas()+"\n";
				archVenta.write(linea);
			}
		}
		archVenta.close();
		System.out.println("** Información guardada correctamente. **");
	}
}
