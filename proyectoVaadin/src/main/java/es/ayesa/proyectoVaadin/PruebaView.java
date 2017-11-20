package es.ayesa.proyectoVaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class PruebaView extends HorizontalLayout implements View{
	public static final String NAME="prueba";
	private Navigator navigator;
	
	public PruebaView(Navigator navigator) {
		this.navigator = navigator;
		
		Label label= new Label("Prueba");
		this.addComponents(label);
	}
}
