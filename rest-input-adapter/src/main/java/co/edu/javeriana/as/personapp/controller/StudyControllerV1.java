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

import co.edu.javeriana.as.personapp.adapter.StudyInputAdapterRest;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.model.request.StudyRequest;
import co.edu.javeriana.as.personapp.model.response.StudyResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/study")
public class StudyControllerV1 {

    @Autowired
    private StudyInputAdapterRest studyInputAdapterRest;

    @ResponseBody
    @GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudyResponse> estudios(@PathVariable String database) {
        log.info("Into studies REST API");
        return studyInputAdapterRest.historial(database.toUpperCase());
    }

    @ResponseBody
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public StudyResponse crearEstudios(@RequestBody StudyRequest request) throws NumberFormatException, NoExistException {
        log.info("Into createStudy method in StudyControllerV1");
        return studyInputAdapterRest.crearEstudio(request);
    }

    @ResponseBody
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public StudyResponse actualizarEstudio(@RequestBody StudyRequest request) {
        log.info("Into updateStudy method in StudyControllerV1");
        return studyInputAdapterRest.actualizarEstudio(request);
    }

    @ResponseBody
    @DeleteMapping(path = "/{idProfession}/{idCcPerson}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyResponse eliminarEstudio(@PathVariable String idProfession, @PathVariable String idCcPerson, @PathVariable String database) throws InvalidOptionException {
        log.info("Into deleteStudy method in StudyControllerV1");
        return studyInputAdapterRest.eliminarEstudio(database, idProfession, idCcPerson);
    }

    @ResponseBody
    @GetMapping(path = "/{idProfession}/{idCcPerson}/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudyResponse estudio(@PathVariable String idProfession, @PathVariable String idCcPerson, @PathVariable String database) {
        log.info("Into findStudy method in StudyControllerV1");
        return studyInputAdapterRest.buscarEstudio(database, idProfession, idCcPerson);
    }
}
