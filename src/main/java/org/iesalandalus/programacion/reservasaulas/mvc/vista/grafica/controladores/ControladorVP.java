package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.io.IOException;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorVP {

	@FXML
	private Button btConsultarDispo;

	@FXML
	private MenuItem acercaMIVP;

	@FXML
	private ContextMenu cmAulasVP;

	@FXML
	private ContextMenu cmProVP;

	@FXML
	private ContextMenu cmReserVP;

	@FXML
	private Label lbReseAula;

	@FXML
	private Label lbResePro;

	@FXML
	private MenuBar menuBarVP;

	@FXML
	private Menu menuIVP;

	@FXML
	private Menu menuMVP;

	@FXML
	private MenuItem miAulasComprobarDispoRVP;

	@FXML
	private MenuItem miAulasEliminarRVP;

	@FXML
	private MenuItem miEliminarReserVP;

	@FXML
	private MenuItem miProEliminarRVP;

	@FXML
	private Button rbInsertarAulaVP;

	@FXML
	private Button rbInsertarProVP;

	@FXML
	private Button rbInsertarReserVP;

	@FXML
	private MenuItem salirMIVP;

	@FXML
	private Tab tAulasVP;

	@FXML
	private Tab tProVP;

	@FXML
	private Tab tReserVP;

	@FXML
	private TabPane tpVP;

	@FXML
	private VBox vbAulasVP;

	@FXML
	private VBox vbPrincipalVP;

	@FXML
	private VBox vbProVP;

	@FXML
	private VBox vbReserVP;
	@FXML
	private HBox hbvoxbotones;

	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservasAula = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservasProfesor = FXCollections.observableArrayList();

	// Declaro la tabla Aulas y sus columnas
	@FXML
	private TableView<Aula> tvAulasVP;
	@FXML
	private TableColumn<Aula, String> tcNombreAulaVP;
	@FXML
	private TableColumn<Aula, String> tcPuestosAulaVP;

	// Declaro la tabla Profesor y sus columnas
	@FXML
	private TableView<Profesor> tvProfVP;
	@FXML
	private TableColumn<Profesor, String> tcNombreProfVP;
	@FXML
	private TableColumn<Profesor, String> tcCorreoProfVP;
	@FXML
	private TableColumn<Profesor, String> tcTelProfVP;

	// Declaro la tabla Reservas y sus columnas
	@FXML
	private TableView<Reserva> tvReserVP;
	@FXML
	private TableColumn<Reserva, String> tcNombreProfVPReser;
	@FXML
	private TableColumn<Reserva, String> tcCorreoProfVPReser;
	@FXML
	private TableColumn<Reserva, String> tcTelProfVPReser;
	@FXML
	private TableColumn<Reserva, String> tcNombreAulaVPReser;
	@FXML
	private TableColumn<Reserva, String> tcPuestosAulaVPReser;
	@FXML
	private TableColumn<Reserva, String> tcPerVPReser;
	@FXML
	private TableColumn<Reserva, String> tcPuntosVPReser;

	// Declaro la tabla de Reservas por Aula y sus columnas
	@FXML
	private TableView<Reserva> tvReserPorAulaVP;
	@FXML
	private TableColumn<Reserva, String> tcNombreProfVPReserAula;
	@FXML
	private TableColumn<Reserva, String> tcCorreoProfVPReserAula;
	@FXML
	private TableColumn<Reserva, String> tcTelProfVPReserAula;
	@FXML
	private TableColumn<Reserva, String> tcNombreAulaVPReserAula;
	@FXML
	private TableColumn<Reserva, String> tcPuestosAulaVPReserAula;

	@FXML
	private TableColumn<Reserva, String> tcPerVPReserAula;
	@FXML
	private TableColumn<Reserva, String> tcPuntosVPReserAula;

	// Declaro la tabla Reservas por Profesor y sus columnas
	@FXML
	private TableView<Reserva> tvReserPorProVP;
	@FXML
	private TableColumn<Reserva, String> tcNombreProfVPReserPro;
	@FXML
	private TableColumn<Reserva, String> tcCorreoProfVPReserPro;
	@FXML
	private TableColumn<Reserva, String> tcTelProfVPReserPro;
	@FXML
	private TableColumn<Reserva, String> tcNombreAulaVPReserPro;
	@FXML
	private TableColumn<Reserva, String> tcPuestosAulaVPReserPro;

	@FXML
	private TableColumn<Reserva, String> tcPerVPReserPro;
	@FXML
	private TableColumn<Reserva, String> tcPuntosVPReserPro;

	private IControlador controladorMVC;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}

//TODO
	public void inicializa() {
		aulas.setAll(controladorMVC.getAulas());
		profesores.setAll(controladorMVC.getProfesores());
		reservas.setAll(controladorMVC.getReservas());

		reservasAula.setAll(controladorMVC.getReservas());
		reservasProfesor.setAll(controladorMVC.getReservas());
	}

	public void initialize() {

		// aulas
		tvAulasVP.setItems(aulas);
		tcNombreAulaVP.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		tcPuestosAulaVP
				.setCellValueFactory(aula -> new SimpleStringProperty(Integer.toString(aula.getValue().getPuestos())));

		// profesores
		tvProfVP.setItems(profesores);
		tcNombreProfVP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		tcCorreoProfVP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		tcTelProfVP.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));

		// reservas
		tvReserVP.setItems(reservas);
		tcNombreProfVPReser
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		tcCorreoProfVPReser
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getCorreo()));
		tcTelProfVPReser.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getTelefono()));
		tcNombreAulaVPReser
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		tcPuestosAulaVPReser.setCellValueFactory(
				reserva -> new SimpleStringProperty(Integer.toString(reserva.getValue().getAula().getPuestos())));
		tcPerVPReser.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		tcPuntosVPReser.setCellValueFactory(
				reserva -> new SimpleStringProperty(Float.toString(reserva.getValue().getPermanencia().getPuntos())));

		// reservas por aula //TODO
		tvReserPorAulaVP.setItems(reservasAula);
		tcNombreAulaVPReserAula
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		tcPuestosAulaVPReserAula.setCellValueFactory(
				reserva -> new SimpleStringProperty(Integer.toString(reserva.getValue().getAula().getPuestos())));
		tcNombreProfVPReserAula
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		tcCorreoProfVPReserAula
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getCorreo()));
		tcTelProfVPReserAula.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getTelefono()));
		tcPerVPReserAula.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		tcPuntosVPReserAula.setCellValueFactory(
				reserva -> new SimpleStringProperty(Float.toString(reserva.getValue().getPermanencia().getPuntos())));

		// reservas por profesor //TODO
		tvReserPorProVP.setItems(reservasProfesor);
		tcNombreProfVPReserPro
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		tcCorreoProfVPReserPro
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getCorreo()));
		tcTelProfVPReserPro.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getTelefono()));
		tcNombreAulaVPReserPro
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		tcPuestosAulaVPReserPro.setCellValueFactory(
				reserva -> new SimpleStringProperty(Integer.toString(reserva.getValue().getAula().getPuestos())));
		tcPerVPReserPro.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		tcPuntosVPReserPro.setCellValueFactory(
				reserva -> new SimpleStringProperty(Float.toString(reserva.getValue().getPermanencia().getPuntos())));

	}

	private Stage addAula;
	private ControladorAddAula cAddAula;
	private Stage addProf;
	private ControladorAddProfesor cAddProf;
	private Stage addReserva;
	private ControladorAddReserva cAddReserva;
	private Stage consultarDispo;
	private ControladorConsultarDisponibilidad cConsultarDispo;

	@FXML
	void addAulaVP(ActionEvent event) throws IOException {
		addAula = new Stage();
		FXMLLoader cargadorVentanaAddAula = new FXMLLoader(
				LocalizadorRecursos.class.getResource("vistas/AddAula.fxml"));
		VBox raizAddAula = cargadorVentanaAddAula.load();
		cAddAula = cargadorVentanaAddAula.getController();
		cAddAula.setControladorMVC(controladorMVC);
		cAddAula.setControladorVP(this);

		Scene escenaAddAula = new Scene(raizAddAula);
		addAula.setTitle("Gestión de reservas del IES. Al-Ándalus");
		addAula.setScene(escenaAddAula);
		addAula.setResizable(false);
		addAula.showAndWait();

	}

	@FXML
	void addProVP(ActionEvent event) throws IOException {
		addProf = new Stage();
		FXMLLoader cargadorVentanaAddProf = new FXMLLoader(
				LocalizadorRecursos.class.getResource("vistas/AddProfesor.fxml"));
		VBox raizAddProf = cargadorVentanaAddProf.load();
		cAddProf = cargadorVentanaAddProf.getController();
		cAddProf.setControladorMVC(controladorMVC);
		cAddProf.setControladorVP(this);

		Scene escenaAddProfesor = new Scene(raizAddProf);
		addProf.setTitle("Gestión de reservas del IES. Al-Ándalus");
		addProf.setScene(escenaAddProfesor);
		addProf.setResizable(false);
		addProf.showAndWait();

	}

	@FXML
	void addReserVP(ActionEvent event) throws IOException {
		addReserva = new Stage();
		FXMLLoader cargadorVentanaAddReserva = new FXMLLoader(
				LocalizadorRecursos.class.getResource("vistas/AddReserva.fxml"));
		VBox raizAddReserva = cargadorVentanaAddReserva.load();
		cAddReserva = cargadorVentanaAddReserva.getController();
		cAddReserva.setControladorMVC(controladorMVC);
		cAddReserva.setControladorVP(this);
		cAddReserva.inicializa();

		Scene escenaAddReserva = new Scene(raizAddReserva);
		addReserva.setTitle("Gestión de reservas del IES. Al-Ándalus");
		addReserva.setScene(escenaAddReserva);
		addReserva.setResizable(false);
		addReserva.showAndWait();

	}

	@FXML
	void consultarDisponibilidad(ActionEvent event) throws IOException {
		consultarDispo = new Stage();
		FXMLLoader cargadorVentanaCDispo = new FXMLLoader(
				LocalizadorRecursos.class.getResource("vistas/VConsultarDisponibilidad.fxml"));
		VBox raizCDispo = cargadorVentanaCDispo.load();
		cConsultarDispo = cargadorVentanaCDispo.getController();
		cConsultarDispo.setControladorMVC(controladorMVC);
		cConsultarDispo.setControladorVP(this);
		cConsultarDispo.inicializa();

		Scene escenaCDispo = new Scene(raizCDispo);
		consultarDispo.setTitle("Gestión de reservas del IES. Al-Ándalus");
		consultarDispo.setScene(escenaCDispo);
		consultarDispo.setResizable(false);
		consultarDispo.showAndWait();
	}

	@FXML
	void eliminarAulaVP(ActionEvent event) {

		Aula aula = null;
		try {
			aula = tvAulasVP.getSelectionModel().getSelectedItem();
			if (aula != null && Dialogos.mostrarDialogoConfirmacion("Eliminar aula",
					"¿Estás seguro de que quieres eliminar este aula?", null)) {
				controladorMVC.borrarAula(aula);
				aulas.remove(aula);
				inicializa();
				Dialogos.mostrarDialogoInformacion("Eliminar aula", "Aula eliminada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Eliminar aula", e.getMessage());
		}

	}

	@FXML
	void eliminarProVP(ActionEvent event) {
		Profesor profesor = null;
		try {
			profesor = tvProfVP.getSelectionModel().getSelectedItem();
			if (profesor != null && Dialogos.mostrarDialogoConfirmacion("Eliminar profesor",
					"¿Estás seguro de que quieres eliminar este profesor?", null)) {
				controladorMVC.borrarProfesor(profesor);
				profesores.remove(profesor);
				inicializa();
				Dialogos.mostrarDialogoInformacion("Eliminar profesor", "Profesor eliminado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Eliminar profesor", e.getMessage());
		}

	}

	@FXML
	void eliminarReserVP(ActionEvent event) {
		Reserva reserva = null;
		try {
			reserva = tvReserVP.getSelectionModel().getSelectedItem();
			if (reserva != null && Dialogos.mostrarDialogoConfirmacion("Eliminar reserva",
					"¿Estás seguro de que quieres eliminar esta reserva?", null)) {
				controladorMVC.anularReserva(reserva);
				
				reservas.remove(reserva);
				inicializa();
				Dialogos.mostrarDialogoInformacion("Eliminar reserva", "Reserva eliminada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Eliminar reserva", e.getMessage());
		}

	}

	@FXML
	void acercaDe(ActionEvent event) throws IOException {
		VBox contenido = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/VentanaAcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Acerca de...", contenido);

	}

	@FXML
	void salir(ActionEvent event) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?",
				null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}

}
