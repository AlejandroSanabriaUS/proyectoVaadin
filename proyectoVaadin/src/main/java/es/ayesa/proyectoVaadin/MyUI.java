package es.ayesa.proyectoVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	private ClienteService clienteService=ClienteService.getInstancia();
	private Grid<Cliente> grid = new Grid<>(Cliente.class);
    final TextField filterText = new TextField();

	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
    	grid.setSelectionMode(SelectionMode.SINGLE); // solo uno a la vez
    	grid.setColumns("nombre","apellido", "email"); // atributos a mostrar
    	
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
   
        layout.addComponents(filtrado,grid);
        
        setContent(layout);
    }

    private void actualizarTabla() {
    	grid.setItems(clienteService.findAll(filterText.getValue()));
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
