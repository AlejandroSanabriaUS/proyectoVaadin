package es.ayesa.proyectoVaadin;


import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;

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
public class LibroView extends VerticalLayout	implements View {


	private LibroService libroService=LibroService.getInstancia();
	private Grid<Libro> grid = new Grid<>(Libro.class);
    final TextField filterText = new TextField(); 
    private LibroFormBack form= new LibroFormBack(this);
    private Navigator navigator;
    
	public static final String NAME = "LibroView";

	public LibroView(Navigator navigator) {
		this.navigator = navigator;
		
		form.setVisible(false);
		
    	grid.setSelectionMode(SelectionMode.SINGLE); // solo uno a la vez
    	grid.setColumns("titulo","autor","cliente"); // atributos a mostrar
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
        		form.setLibro(evento.getValue());
        	}
        });     	
        
        Button navegar= new Button("Clientes View");
        navegar.addClickListener(event -> this.navigator.navigateTo(ClienteView.NAME));
        
        this.addComponents(filtrado,navegar,main);

	}

    public void actualizarTabla() {
    	grid.setItems(libroService.findAll(filterText.getValue()));
    }
    
}
