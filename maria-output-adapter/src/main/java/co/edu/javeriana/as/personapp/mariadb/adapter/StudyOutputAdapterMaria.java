package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.EstudiosMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.StudyRepositoryMaria;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Adapter("studyOutputAdapterMaria")
@Transactional
public class StudyOutputAdapterMaria implements StudyOutputPort {

	@Autowired
	private StudyRepositoryMaria studyRepositoryMaria;

	@Autowired
	private EstudiosMapperMaria estudioMapperMaria;

	@Override
	public Study save(Study study) {
		log.debug("Into save on Adapter MariaDB");
		log.warn("Mapping from domain to adapter, estudio: "+ study);
		log.warn("Probando si se imprime");

		log.warn("Estudio a mapear: "+ study);

        log.warn("Mapping from domain to adapter, mapper: "+ estudioMapperMaria.fromDomainToAdapter(study).getPersona());

        EstudiosEntity persistedStudio = studyRepositoryMaria.save(estudioMapperMaria.fromDomainToAdapter(study));

		log.warn("Persisted estudio: "+ persistedStudio);

		return estudioMapperMaria.fromAdapterToDomain(persistedStudio);
	}

	@Override
	public Boolean delete(Integer professionID, Integer personID) {
		log.debug("Into delete on Adapter MariaDB");
        EstudiosEntity estudio = studyRepositoryMaria.findByProfesionAndPersona(professionID, personID);

		if(estudio == null) {
			return false;
		}

		studyRepositoryMaria.delete(estudio);
		return studyRepositoryMaria.findByProfesionAndPersona(professionID,personID) == null;
	}

	@Override
	public List<Study> find() {
		log.debug("Into find on Adapter MariaDB");
		return studyRepositoryMaria.findAll().stream().map(estudioMapperMaria::fromAdapterToDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Study findById(Integer professionID, Integer personID) {
		log.debug("Into findById on Adapter MariaDB");
		EstudiosEntity estudio = studyRepositoryMaria.findByProfesionAndPersona(professionID, personID);

		if(estudio == null) {
			return null;
		}

		return estudioMapperMaria.fromAdapterToDomain(estudio);
	}

}
