package co.edu.javeriana.as.personapp.mongo.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper
public class TelefonoMapperMongo {

	@Autowired
	private PersonaMapperMongo personaMapperMongo;

	public TelefonoDocument fromDomainToAdapter(Phone phone) {
		log.info("MapperMONGO");
		log.info("Into fromDomainToAdapter");
		TelefonoDocument telefonoDocument = new TelefonoDocument();
		telefonoDocument.setId(phone.getNumber());
		telefonoDocument.setOper(phone.getCompany());
		telefonoDocument.setPrimaryDuenio(validateDuenio(phone.getOwner()));
		return telefonoDocument;
	}

	private PersonaDocument validateDuenio(@NonNull Person owner) {
		log.info("MapperMONGO");
		log.info("Into validateDuenio");
		return owner != null ? personaMapperMongo.fromDomainToAdapter(owner) : new PersonaDocument();
	}

	public Phone fromAdapterToDomain(TelefonoDocument telefonoDocument) {
		log.info("MapperMONGO");
		log.info("Into fromAdapterToDomain");
		Phone phone = new Phone();
		phone.setNumber(telefonoDocument.getId());
		phone.setCompany(telefonoDocument.getOper());
		phone.setOwner(validateOwner(telefonoDocument.getPrimaryDuenio()));
		return phone;
	}

	private @NonNull Person validateOwner(PersonaDocument duenio) {
		log.info("MapperMONGO");
		log.info("Into validateOwner");
		Person owner = new Person();
	
		if (duenio != null) {
			owner.setIdentification(duenio.getId());
			owner.setFirstName(duenio.getNombre());
			owner.setLastName(duenio.getApellido());
			owner.setGender(duenio.getGenero().equals("M") ? Gender.MALE : Gender.FEMALE);
			owner.setAge(duenio.getEdad());
		}
	
		return owner;
	}
}