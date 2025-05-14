package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper
public class EstudiosMapperMaria {

	public EstudiosEntity fromDomainToAdapter(Study study) {
		EstudiosEntityPK estudioPK = new EstudiosEntityPK();
		estudioPK.setCcPer(study.getPerson().getIdentification());
		estudioPK.setIdProf(study.getProfession().getIdentification());
	
		EstudiosEntity estudio = new EstudiosEntity();
		estudio.setEstudiosPK(estudioPK);
		estudio.setFecha(validateFecha(study.getGraduationDate()));
		estudio.setUniver(validateUniver(study.getUniversityName()));
		estudio.setPersona(createPersonaEntity(study.getPerson()));
		estudio.setProfesion(createProfesionEntity(study.getProfession()));
	
		return estudio;
	}
	
	private PersonaEntity createPersonaEntity(Person person) {
		PersonaEntity persona = new PersonaEntity();
		persona.setCc(person.getIdentification());
		persona.setNombre(person.getFirstName());
		persona.setApellido(person.getLastName());
		persona.setEdad(person.getAge());
		persona.setGenero(person.getGender() == Gender.MALE ? 'M' : 'F');
		return persona;
	}
	
	private ProfesionEntity createProfesionEntity(Profession profession) {
		ProfesionEntity profesion = new ProfesionEntity();
		profesion.setId(profession.getIdentification());
		profesion.setNom(profession.getName());
		profesion.setDes(profession.getDescription());
		return profesion;
	}

	private Date validateFecha(LocalDate graduationDate) {
		return graduationDate != null
				? Date.from(graduationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
				: null;
	}

	private String validateUniver(String universityName) {
		return universityName != null ? universityName : "";
	}

	public Study fromAdapterToDomain(EstudiosEntity estudiosEntity) {
		Study study = new Study();
		
		log.warn("Mapping from adapter to domain: " + estudiosEntity + " " + estudiosEntity.getPersona());
	
		study.setPerson(validatePrimaryPersona(estudiosEntity.getPersona()));
		study.setProfession(validatePrimaryProfesion(estudiosEntity.getProfesion()));
		
		study.setGraduationDate(validateGraduationDate(estudiosEntity.getFecha()));
		study.setUniversityName(validateUniversityName(estudiosEntity.getUniver()));
		
		log.warn("Resultado: " + study);
	
		return study;
	}

	private LocalDate validateGraduationDate(Date graduationDate) {
		log.warn("Mapping validateGraduationDate: {}", graduationDate);

        if (graduationDate == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(graduationDate);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        LocalDate localGraduationDate = LocalDate.of(year, month, day);
        
        return localGraduationDate;
	}

	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}

	private @NonNull Person validatePrimaryPersona(@NonNull PersonaEntity persona) {
		Person owner = new Person();
		owner.setIdentification(persona.getCc());
		owner.setFirstName(persona.getNombre());
		owner.setLastName(persona.getApellido());
		owner.setAge(persona.getEdad());
		owner.setGender(persona.getGenero() == 'M' ? Gender.MALE : Gender.FEMALE);
		return owner;
	}

	private @NonNull Profession validatePrimaryProfesion(@NonNull ProfesionEntity profesion) {
		Profession profession = new Profession();
		profession.setIdentification(profesion.getId());
		profession.setDescription(profesion.getDes());
		profession.setName(profesion.getNom());
		return profession;
	}
}