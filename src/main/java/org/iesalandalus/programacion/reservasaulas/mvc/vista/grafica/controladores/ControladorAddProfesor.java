
package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorAddProfesor {
	
	@FXML
	private Button btAceptarProfesor;

	@FXML
	private Button btCancelarProfesor;

	@FXML
	private HBox hbACprofesor;

	@FXML
	private HBox hbaddcorreoProfesor;

	@FXML
	private HBox hbaddnombreProfesor;

	@FXML
	private HBox hbaddtelProfesor;

	@FXML
	private Label lbcorreoProfesor;

	@FXML
	private Label lbnombreProfesor;

	@FXML
	private Label lbtelProfesor;

	@FXML
	private TextField txcorreoProfesor;

	@FXML
	private TextField txnombreProfesor;

	@FXML
	private TextField txtelProfesor;

	@FXML
	private VBox vbaddProfesor;

	@FXML
	private Label lbtituloAP;
	
	private static final String ER_NOMBRE = "[a-zA-Z\\s]+";
	private static final String ER_TELEFONO = "^[69][0-9]{8}$";
	private static final String ER_CORREO = "^[a-z]+([a-z0-9\\-\\_\\.]*[a-z0-9])*+@([a-z]*\\.[a-z]{2,})+$";

	private IControlador controladorMVC;
	private ControladorVP controladorVP;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}

	public void setControladorVP(ControladorVP controladorVP) {
		this.controladorVP = controladorVP;
	}

	public void initialize() {
		txnombreProfesor.textProperty().addListener((ob, ov, nv) -> compruebaNombre(ER_NOMBRE, txnombreProfesor));
		txcorreoProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCorreo(ER_CORREO, txcorreoProfesor));
		txtelProfesor.textProperty().addListener((ob, ov, nv) -> compruebaTelefono(ER_TELEFONO, txtelProfesor));
		txtelProfesor.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
	}

	private void compruebaNombre(String nombre, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(ER_NOMBRE)) {
			txnombreProfesor.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {
			txnombreProfesor.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}

	private void compruebaCorreo(String correo, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(ER_CORREO)) {
			txcorreoProfesor.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {
			txcorreoProfesor.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}

	private void compruebaTelefono(String telefono, TextField campoTexto) {
		String texto = campoTexto.getText();

		if (texto.matches(ER_TELEFONO) || texto.isEmpty()) {
			txtelProfesor.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {

			txtelProfesor.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}

	}

	@FXML
	void aceptarAddProfesor(ActionEvent event) {
		Profesor profesor = null;
		try {
			profesor = addProfesor();
			controladorMVC.insertarProfesor(profesor);
			controladorVP.inicializa();
			Stage propietario = ((Stage) btAceptarProfesor.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Profesor", "Profesor añadido correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Profesor", e.getMessage());
		}

	}

	private Profesor addProfesor() {
		String nombre = txnombreProfesor.getText();
		String correo = txcorreoProfesor.getText();
		String telefono = txtelProfesor.getText();

		if (telefono.isEmpty()) {
			return new Profesor(nombre, correo);
		} else {
			return new Profesor(nombre, correo, telefono);
		}

	}

	@FXML
	void cancelarAddProfesor(ActionEvent event) {
		((Stage) btCancelarProfesor.getScene().getWindow()).close();
	}

}
