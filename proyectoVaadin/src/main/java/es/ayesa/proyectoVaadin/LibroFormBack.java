package es.ayesa.proyectoVaadin;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.data.Binder;

@SuppressWarnings("serial")
public class LibroFormBack extends LibroForm{
	
	private LibroService libroService = LibroService.getInstancia();
	private Binder<Libro> binder = new Binder<>(Libro.class);
	private Libro libro;
	private LibroView myUI;
	
	public LibroFormBack(LibroView myui) {
		this.myUI= myui;
		
		prestar.setClickShortcut(KeyCode.ENTER);
		clienteElegido.setItems( ClienteService.getInstancia().findAll());
		
		binder.bindInstanceFields(this);
		
		prestar.addClickListener(e -> this.prestar(libro,clienteElegido.getValue()));

	}

	private void guardar() {
		libroService.guardar(libro );
		myUI.actualizarTabla();
		setVisible(false);
	}
	
	private void borrar() {
		libroService.borrar(libro);
		myUI.actualizarTabla();
		setVisible(false);
	}
	
	
	public void setLibro(Libro libro) {
		this.libro=libro;
		binder.setBean(libro);
		
		
		setVisible(true);
		titulo.selectAll();
	}
	
	private void prestar(Libro libro,Cliente clienteElegido) {
		libroService.prestar(libro,clienteElegido );
		myUI.actualizarTabla();
		setVisible(false);
	}

}
