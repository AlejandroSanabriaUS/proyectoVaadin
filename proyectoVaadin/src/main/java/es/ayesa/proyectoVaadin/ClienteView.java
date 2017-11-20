package es.ayesa.proyectoVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ClienteView extends VerticalLayout	implements View {


	private ClienteService clienteService=ClienteService.getInstancia();
	private Grid<Cliente> grid = new Grid<>(Cliente.class);
    final TextField filterText = new TextField(); 
    private ClienteFormBack form=new ClienteFormBack(this);
    private Navigator navigator;
    
	public static final String NAME = "";

	public ClienteView(Navigator navigator) {
		this.navigator = navigator;
		
		form.setVisible(false);
		
    	grid.setSelectionMode(SelectionMode.SINGLE); // solo uno a la vez
    	grid.setColumns("nombre","apellido", "email"); // atributos a mostrar
    	grid.setSizeFull();
    	
        actualizarTabla();

        Button borrarFiltro = new Button(FontAwesome.TIMES);
        borrarFiltro.setDescription("Borrar Filtro");
        borrarFiltro.addClickListener(e -> filterText.clear());
        
        filterText.setPlaceholder("Filtrar por nombre:");
        filterText.addValueChangeListener(e -> actualizarTabla());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        CssLayout filtrado = new CssLayout(); // Para mostrar por pantalla
        filtrado.addComponents(filterText,borrarFiltro);
        filtrado.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP); // tema predeterminado, hace que se unan todos los componentes
        
        HorizontalLayout main = new HorizontalLayout();
        main.addComponents(grid,form);
        main.setSizeFull();
        grid.setSizeFull();
        form.setSizeFull();
        
        main.setExpandRatio(grid, 2);
        main.setExpandRatio(form, 1);
        
        grid.asSingleSelect().addValueChangeListener(evento ->{
        	if(evento.getValue()==null) {
        		form.setVisible(false);
        	}else {
        		form.setCliente(evento.getValue());
        	}
        });
        	
        HorizontalLayout botonera= new HorizontalLayout();
        Button nuevoCliente= new Button("Nuevo Cliente");
        nuevoCliente.addClickListener(evento -> {
        	grid.asSingleSelect().clear();
        	form.setCliente (new Cliente());
        });
        
        Button navegar= new Button("Libro View");
        navegar.addClickListener(event -> this.navigator.navigateTo(LibroView.NAME));
        
        botonera.addComponents(filtrado,nuevoCliente,navegar);
        
        this.addComponents(botonera,main);

	}

    public void actualizarTabla() {
    	grid.setItems(clienteService.findAll(filterText.getValue()));
    }
    
}
