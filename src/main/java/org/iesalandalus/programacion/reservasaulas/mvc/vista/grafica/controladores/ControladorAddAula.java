package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorAddAula {

	@FXML
	private Button btaddAceptarAula;

	@FXML
	private Button btaddCancelarAula;

	@FXML
	private HBox hbACprofesor;

	@FXML
	private HBox hbaddnombreAula;

	@FXML
	private HBox hbaddpuestosAula;

	@FXML
	private Label lbnombreAula;

	@FXML
	private Label lbpuestosAula;

	@FXML
	private TextField txaddnombreAula;

	@FXML
	private VBox vbaddAula;

	@FXML
	private TextField txNumPuestos;

	@FXML
	private Label lbtituloAA;

	private static final String ER_NOMBRE_AULA = "[a-zA-Z\\s0-9]+";
	private int puestos;

	private IControlador controladorMVC;
	private ControladorVP controladorVP;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}

	public void setControladorVP(ControladorVP controladorVP) {
		this.controladorVP = controladorVP;
	}

	public void initialize() {
		txaddnombreAula.textProperty().addListener((ob, ov, nv) -> compruebaNombre(ER_NOMBRE_AULA, txaddnombreAula));
		txNumPuestos.textProperty().addListener((ob, ov, nv) -> compruebaPuestos(puestos, txNumPuestos));

	}

	private void compruebaNombre(String nombre, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(ER_NOMBRE_AULA)) {
			txaddnombreAula.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {
			txaddnombreAula.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}

	private void compruebaPuestos(int puestos, TextField campoTexto) {
		String texto = campoTexto.getText();

		puestos = Integer.parseInt(texto);

		if (puestos >= 10 && puestos <= 100) {
			txNumPuestos.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {
			txNumPuestos.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}

	@FXML
	void aceptarCrearAula(ActionEvent event) {
		Aula aula = null;
		try {
			aula = addAula();
			controladorMVC.insertarAula(aula);
			controladorVP.inicializa();
			Stage propietario = ((Stage) btaddAceptarAula.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Aula", "Aula añadida correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Aula", e.getMessage());

		}

	}

	private Aula addAula() {
		String nombreAula = txaddnombreAula.getText();
		int puestosAula = Integer.parseInt(txNumPuestos.getText());

		return new Aula(nombreAula, puestosAula);

	}

	@FXML
	void cancelarCrearAula(ActionEvent event) {
		((Stage) btaddCancelarAula.getScene().getWindow()).close();
	}

}
