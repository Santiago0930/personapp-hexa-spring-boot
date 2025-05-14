package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.model.request.PhoneRequest;
import co.edu.javeriana.as.personapp.model.response.PhoneResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.lang.NonNull;

@Mapper
public class PhoneMapperRest {
	private static final Logger log = LoggerFactory.getLogger(PersonaMapperRest.class);
	
	public PhoneResponse fromDomainToAdapterRestMaria(Phone phone) {
		return fromDomainToAdapterRest(phone, "MariaDB");
	}
	public PhoneResponse fromDomainToAdapterRestMongo(Phone phone) {
		return fromDomainToAdapterRest(phone, "MongoDB");
	}
	
	public PhoneResponse fromDomainToAdapterRest(Phone phone, String database) {
		return new PhoneResponse(
                phone.getNumber()+"", 
                phone.getCompany(), 
                phone.getOwner().getIdentification()+"",
				database,
				"OK");
	}

	public Phone fromAdapterToDomain(PhoneRequest request,Person owner) {
		log.info("Into fromAdapterToDomain");
        Phone phone = new Phone();
        phone.setNumber(request.getNumber());
        phone.setCompany(request.getCompany());
        phone.setOwner(owner);
        return phone;
    
    }
		
    private @NonNull Person validateDuenio(@NonNull Person owner) {
        Person duenio = new Person();
        duenio.setIdentification(owner.getIdentification());
        return duenio;
    }
    private @NonNull Person validateOwner(PersonaEntity duenio) {
		Person owner = new Person();
		owner.setIdentification(duenio.getCc());
		return owner;
	}
}
