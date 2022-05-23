package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.mongodb;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

public class Profesores implements IProfesores {

	private static final String COLECCION = "profesores";

	private MongoCollection<Document> coleccionProfesores;

	public Profesores() {

	}

	@Override
	public void comenzar() {
		coleccionProfesores = MongoDB.getBD().getCollection(COLECCION);
	}

	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}

	@Override
	public List<Profesor> getProfesores() {
		List<Profesor> profesores = new ArrayList<>();
		for (Document documentoProfesor : coleccionProfesores.find().sort(MongoDB.getCriterioOrdenacionProfesor())) {
			Profesor profesor = MongoDB.getProfesor(documentoProfesor);
			profesores.add(profesor);
		}
		return profesores;
	}

	@Override
	public int getNumProfesores() {
		return (int) coleccionProfesores.countDocuments();
	}

	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}

		if (buscar(profesor) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		}
		coleccionProfesores.insertOne(MongoDB.getDocumento(profesor));
	}

	@Override
	public Profesor buscar(Profesor profesor) {
		Document documentoProfesor = coleccionProfesores.find().filter(eq(MongoDB.CORREO, profesor.getCorreo()))
				.first();
		return MongoDB.getProfesor(documentoProfesor);
	}

	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		if (buscar(profesor) != null) {
			coleccionProfesores.deleteOne(eq(MongoDB.CORREO, profesor.getCorreo()));
			System.out.println("El profesor " + profesor.getNombre() + " se ha eliminado con éxito.");

		} else {
			throw new OperationNotSupportedException("RROR: No existe ningún profesor con ese correo.");
		}
	}

	@Override
	public List<String> representar() {
		return null;
	}

}
