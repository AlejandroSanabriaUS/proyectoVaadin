package es.ayesa.proyectoVaadin;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.data.Binder;

@SuppressWarnings("serial")
public class ClienteFormBack extends ClienteForm{
	
	private ClienteService clienteService = ClienteService.getInstancia();
	private Binder<Cliente> binder = new Binder<>(Cliente.class);
	private Cliente cliente;
	private ClienteView myUI;
	
	public ClienteFormBack(ClienteView myui) {
		this.myUI= myui;
		
		guardar.setClickShortcut(KeyCode.ENTER);
		estado.setItems( ClienteEstado.values() );
		
		binder.bindInstanceFields(this);
		
		guardar.addClickListener(e -> this.guardar());
		borrar.addClickListener(e -> this.borrar());
	}

	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
		binder.setBean(cliente);
		
		borrar.setVisible(cliente.getId()!=null);
		setVisible(true);
		nombre.selectAll();
	}
	
	private void borrar() {
		clienteService.borrar(cliente );
		myUI.actualizarTabla();
		setVisible(false);
	}

	private void guardar() {
		clienteService.guardar(cliente );
		myUI.actualizarTabla();
		setVisible(false);
	}
	
}
