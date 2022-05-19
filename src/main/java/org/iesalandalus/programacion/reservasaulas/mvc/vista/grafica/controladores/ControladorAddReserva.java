package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorAddReserva {

	@FXML
	private Button btaddAceptarAula;

	@FXML
	private Button btaddCancelarAula;

	@FXML
	private DatePicker dpfechaPermanencia;

	@FXML
	private HBox hbACReserva;
	@FXML
	private HBox hbPerf;

	@FXML
	private HBox hbaddcorreoProfesor;

	@FXML
	private HBox hbaddaulanombre;

	@FXML
	private HBox hbfecha;

	@FXML
	private HBox hbxtodaPermanencia;

	@FXML
	private Label lbFechaPermanencia;
	@FXML
	private Label lbPermanenciaa;

	@FXML
	private Label lbtituloAR;
	@FXML
	private RadioButton rbHora;

	@FXML
	private RadioButton rbTramo;

	@FXML
	private TextField txaddHora;
	@FXML
	private VBox vbPerm;

	@FXML
	private VBox vbTHEscribir;

	@FXML
	private VBox vbTramoHora;

	@FXML
	private VBox vbaddReserva;

	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	protected static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<String> cbTramoPermanencia;

	@FXML
	private TableView<Profesor> tvProfesor;

	@FXML
	private TableColumn<Profesor, String> tcCorreoProf;

	@FXML
	private TableView<Aula> tvAula;

	@FXML
	private TableColumn<Aula, String> tcNombreAula;

	private IControlador controladorMVC;
	private ControladorVP controladorVP;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}

	public void setControladorVP(ControladorVP controladorVP) {
		this.controladorVP = controladorVP;
	}

	public void initialize() {
		//aulas, nombre
		tvAula.setItems(aulas);
		tcNombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		//profesores, correo
		tvProfesor.setItems(profesores);
		tcCorreoProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		//Permanencia y fecha
		cbTramoPermanencia.setItems(FXCollections.observableArrayList("Mañana", "Tarde"));
		cbTramoPermanencia.getSelectionModel().select("Mañana");
		dpfechaPermanencia.setValue(LocalDate.now().plusMonths(1));

	}

	public void inicializa() {
		aulas.setAll(controladorMVC.getAulas());
		profesores.setAll(controladorMVC.getProfesores());

	}

	ToggleGroup grupo = new ToggleGroup();

	@FXML
	void seleccionPermanencia(ActionEvent event) {
		rbHora.setToggleGroup(grupo);
		rbTramo.setToggleGroup(grupo);

		RadioButton botonElegido = (RadioButton) grupo.getSelectedToggle();
		if (botonElegido == rbHora) {

			cbTramoPermanencia.setDisable(true);
			txaddHora.setDisable(false);

		} else {

			cbTramoPermanencia.setDisable(false);
			txaddHora.setDisable(true);

		}
	}

	@FXML
	void aceptarAddReserva(ActionEvent event) {
		Reserva reserva = null;
		try {
			reserva = addReserva();
			controladorMVC.realizarReserva(reserva);
			controladorVP.inicializa();
			Stage propietario = ((Stage) btaddAceptarAula.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Reserva", "Reserva añadida correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Reserva", e.getMessage());
		}

	}

	private Reserva addReserva() {
		Aula aula = null;
		Profesor profesor = null;
		Tramo tramo = null;
		RadioButton botonElegido = (RadioButton) grupo.getSelectedToggle();
		LocalDate fecha = LocalDate.parse(dpfechaPermanencia.getValue().format(FORMATO_DIA), FORMATO_DIA);

		controladorVP.inicializa();
		Permanencia permanencia = null;
		String tramoElegido = cbTramoPermanencia.valueProperty().getValue();

		profesor = tvProfesor.getSelectionModel().getSelectedItem();
		aula = tvAula.getSelectionModel().getSelectedItem();
		if (botonElegido == rbTramo) {

			if (tramoElegido.equals("Mañana")) {

				tramo = Tramo.MANANA;
			} else {
				tramo = Tramo.TARDE;
			}
			permanencia = new PermanenciaPorTramo(fecha, tramo);

		} else {
			permanencia = new PermanenciaPorHora(fecha, LocalTime.parse(txaddHora.getText(), FORMATO_HORA));
		}

		return new Reserva(profesor, aula, permanencia);
	}

	@FXML
	void cancelarAddReserva(ActionEvent event) {
		((Stage) btaddCancelarAula.getScene().getWindow()).close();
	}

}