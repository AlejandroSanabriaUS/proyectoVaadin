package es.ayesa.proyectoVaadin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class LibroService {
	private static LibroService instancia;
	private final Map<Long,Libro> libros= new HashMap<>();
	private long siguienteId=0;
	
	private LibroService() {}

	public static LibroService getInstancia() {
		if(instancia==null) {
			instancia= new LibroService();
			instancia.generarDatos();
		}
		return instancia;
		
	}

	private void generarDatos() {
		if(findAll().isEmpty()) {
			Libro libro= new Libro();
			libro.setAutor("Patrick Rothfuss");
			libro.setTitulo("El nombre del Viento");
			libro.setCliente(null);
			Libro libro2= new Libro();
			libro2.setAutor("Patrick Rothfuss");
			libro2.setTitulo("El temor de un hombre sabio");
			libro2.setCliente(null);
			Libro libro3= new Libro();
			libro3.setAutor("JRR Tolkien");
			libro3.setTitulo("El Hobbit");
			libro3.setCliente(null);
			guardar(libro);
			guardar(libro2);
			guardar(libro3);
		}
		
	}
	
	public List<Libro> findAll(){
		return findAll(null);
	}
	
	public List<Libro>findAll(String cadena) {

		return libros.values()
				.stream()
				.filter(libro -> {
					return (cadena== null || cadena.isEmpty()) ||
						(libro.getAutor().toLowerCase().contains(cadena) ||
						libro.getTitulo().toLowerCase().contains(cadena));
				}).collect(Collectors.toList());
	}

	public long count() {
		return libros.size();
	}
	
	public void borrar(Libro libro) {
		libros.remove(libro.getId());
	}
	
	public void guardar(Libro libro) {
		if(libro==null)
			return;
		else {
			if(libro.getId()==null) {
				libro.setId(siguienteId++);
			}

		}
		try {
			libro=libro.clone();
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		libros.put(libro.getId(), libro);
	}
	
	public void prestar(Libro libro,Cliente clienteElegido) {
		Libro aux=libros.get(libro.getId());
		aux.setCliente(clienteElegido);
		libros.put(aux.getId(), aux);
	}
}
