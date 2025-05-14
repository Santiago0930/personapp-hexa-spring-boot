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

import co.edu.javeriana.as.personapp.adapter.PhoneInputAdapterRest;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.model.request.PhoneRequest;
import co.edu.javeriana.as.personapp.model.response.PhoneResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/phone")
public class PhoneControllerV1 {
    
    @Autowired
    private PhoneInputAdapterRest phoneInputAdapterRest;
    
    @ResponseBody
    @GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PhoneResponse> personas(@PathVariable String database) {
        log.info("Into personas REST API");
            return phoneInputAdapterRest.historial(database.toUpperCase());
    }
    
    @ResponseBody
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PhoneResponse crearPersona(@RequestBody PhoneRequest request) throws NumberFormatException, NoExistException {
		log.info("esta en el metodo Telefono en el controller del api");
		return phoneInputAdapterRest.crearPhone(request);
	}

	@ResponseBody
	@PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PhoneResponse actualizarPersona(@RequestBody PhoneRequest request) {
		log.info("esta en el metodo actualizarTelefono en el controller del api");
		return phoneInputAdapterRest.actualizarPhone(request);
	}

	@ResponseBody
	@DeleteMapping(path = "/{number}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PhoneResponse eliminarPersona(@PathVariable String number, @PathVariable String database) throws InvalidOptionException {
		log.info("esta en el metodo eliminarTelefono en el controller del api");
		return phoneInputAdapterRest.eliminarPhone(database, number);
	}

	@ResponseBody
	@GetMapping(path = "/{number}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PhoneResponse Phone(@PathVariable String number, @PathVariable String database) {
		log.info("Into persona REST API");
		return phoneInputAdapterRest.buscarPhone(database, number);
	}
}
