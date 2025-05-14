package co.edu.javeriana.as.personapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.as.personapp.adapter.ProfessionInputAdapterRest;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.model.request.ProfessionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfessionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/profesion")
public class ProfessionControllerV1 {
	
	@Autowired
	private ProfessionInputAdapterRest professionInputAdapterRest;
	
	@ResponseBody
	@GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProfessionResponse> personas(@PathVariable String database) {
		log.info("Into personas REST API");
			return professionInputAdapterRest.historial(database.toUpperCase());
	}
	
	@ResponseBody
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ProfessionResponse crearProfession(@RequestBody ProfessionRequest request) {
		log.info("esta en el metodo crearTarea en el controller del api");
		return professionInputAdapterRest.crearProfesion(request);
	}

	@ResponseBody
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ProfessionResponse actualizarProfession(@RequestBody ProfessionRequest request) {
		log.info("esta en el metodo actualizarTarea en el controller del api");
		return professionInputAdapterRest.actualizarProfesion(request);
	}

	@ResponseBody
	@DeleteMapping(path = "/{id}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProfessionResponse eliminarPersona(@PathVariable Integer id, @PathVariable String database) throws InvalidOptionException {
		log.info("esta en el metodo eliminarTarea en el controller del api");
		return professionInputAdapterRest.eliminarProfession(database, id);
	}

	@ResponseBody
	@GetMapping(path = "/{id}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProfessionResponse persona(@PathVariable Integer id, @PathVariable String database) {
		log.info("Into persona REST API");
		return professionInputAdapterRest.buscarProfession(database, id);
	}
}
