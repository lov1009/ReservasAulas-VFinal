package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores.ControladorVP;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaGrafica extends Application implements IVista {

	private static IControlador controladorMVC = null;

	@Override
	public void setControlador(IControlador controlador) {
		controladorMVC = controlador;

	} 
	@Override
	public void comenzar() {
		launch(this.getClass());
	}
	
	@Override
	public void salir() {
		controladorMVC.terminar();
	//	System.exit(0);
	}
	

	
	@Override
	public void start(Stage ventanaPrincipal) {
		try {
			FXMLLoader cargadorVentanaPrincipal = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ControladorVP.fxml"));
			VBox raiz = cargadorVentanaPrincipal.load();	
			ControladorVP cVentanaPrincipal = cargadorVentanaPrincipal.getController();
			cVentanaPrincipal.setControladorMVC(controladorMVC);
			cVentanaPrincipal.inicializa();

			Scene escena = new Scene(raiz);
			ventanaPrincipal.setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal, e));
			ventanaPrincipal.setTitle("Gestión de reservas del IES. Al-Ándalus");
			ventanaPrincipal.setScene(escena);
			ventanaPrincipal.setResizable(false);
			ventanaPrincipal.show();
			ventanaPrincipal.eventDispatcherProperty();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void confirmarSalida(Stage ventanaPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", ventanaPrincipal)) {
			salir();
			ventanaPrincipal.close();
		}
		else {
			e.consume();	
		}
	}

	

}
