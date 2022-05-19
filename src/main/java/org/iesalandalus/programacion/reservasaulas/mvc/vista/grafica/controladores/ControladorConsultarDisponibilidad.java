package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorConsultarDisponibilidad {

	@FXML
	private Button btAceptarCD;

	@FXML
	private Button btCancelarCD;

	@FXML
	private DatePicker dpCDFechaPermanencia;

	@FXML
	private GridPane gpComprobarDispo;

	@FXML
	private HBox hbACCompDispo;

	@FXML
	private HBox hbCDEstado;

	@FXML
	private Label lbEstadoCD;

	@FXML
	private Label lbFechaPermanenciaCD;

	@FXML
	private RadioButton rbHora;

	@FXML
	private RadioButton rbTramo;

	@FXML
	private Label lbtituloCD;

	@FXML
	private TextField tfCDHoraP;

	@FXML
	private VBox vbConsultarDisponibilidad;

	@FXML
	private HBox hbaddaulanombre;

	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	protected static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	private ObservableList<Aula> aulas = FXCollections.observableArrayList();

	@FXML
	private ChoiceBox<String> cbCDTramoPermanencia;

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
		//permanencia y fecha
		cbCDTramoPermanencia.setItems(FXCollections.observableArrayList("Mañana", "Tarde"));
		cbCDTramoPermanencia.getSelectionModel().select("Mañana");
		dpCDFechaPermanencia.setValue(LocalDate.now().plusMonths(1));

	}

	public void inicializa() {
		aulas.setAll(controladorMVC.getAulas());
	}

	ToggleGroup grupo = new ToggleGroup();

	@FXML
	void seleccionPermanencia(ActionEvent event) {
		rbHora.setToggleGroup(grupo);
		rbTramo.setToggleGroup(grupo);

		RadioButton botonElegido = (RadioButton) grupo.getSelectedToggle();
		if (botonElegido == rbHora) {

			cbCDTramoPermanencia.setDisable(true);
			tfCDHoraP.setDisable(false);

		} else {

			cbCDTramoPermanencia.setDisable(false);
			tfCDHoraP.setDisable(true);

		}
	}

	@FXML
	void aceptarCD(ActionEvent event) {
		Permanencia permanencia = null;
		try {
			Aula aula = tvAula.getSelectionModel().getSelectedItem();
			RadioButton botonElegido = (RadioButton) grupo.getSelectedToggle();
			String tramoElegido = cbCDTramoPermanencia.valueProperty().getValue();
			LocalDate fecha = LocalDate.parse(dpCDFechaPermanencia.getValue().format(FORMATO_DIA), FORMATO_DIA);
			Tramo tramo = null;
			if (botonElegido == rbTramo) {

				if (tramoElegido.equals("Mañana")) {

					tramo = Tramo.MANANA;
				} else {
					tramo = Tramo.TARDE;
				}
				permanencia = new PermanenciaPorTramo(fecha, tramo);

			} else if (botonElegido == rbHora) {
				permanencia = new PermanenciaPorHora(fecha, LocalTime.parse(tfCDHoraP.getText(), FORMATO_HORA));
			}

			controladorVP.inicializa();
			controladorMVC.consultarDisponibilidad(aula, permanencia);

			if (controladorMVC.consultarDisponibilidad(aula, permanencia) == true) {
				lbEstadoCD.setText("ESTADO: DISPONIBLE");
			} else {
				lbEstadoCD.setText("ESTADO: NO DISPONIBLE");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Consultar Disponibilidad", e.getMessage());
		}

	}

	@FXML
	void cancelarCD(ActionEvent event) {
		((Stage) btCancelarCD.getScene().getWindow()).close();
	}

}
