package co.edu.javeriana.as.personapp.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.application.usecase.PhoneUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mapper.PhoneMapperRest;
import co.edu.javeriana.as.personapp.model.request.PhoneRequest;
import co.edu.javeriana.as.personapp.model.response.PhoneResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PhoneInputAdapterRest {
	//Maria
	@Autowired
	@Qualifier("phoneOutputAdapterMaria")
	private PhoneOutputPort phoneOutputPortMaria;

    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("phoneOutputAdapterMongo")
    private PhoneOutputPort phoneOutputPortMongo;
	//Mongo
    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PhoneMapperRest phoneMapperRest;

	PhoneInputPort phoneInputPort;
    PersonInputPort personInputPort;

	private String setPhoneOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMaria);
            personInputPort = new PersonUseCase(personOutputPortMaria);
			return DatabaseOption.MARIA.toString();
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMongo);
            personInputPort = new PersonUseCase(personOutputPortMongo);
			return  DatabaseOption.MONGO.toString();
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public List<PhoneResponse> historial(String database) {
		log.info("Into historial PersonaEntity in Input Adapter");
		try {
			if(setPhoneOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())){
				return phoneInputPort.findAll().stream().map(phoneMapperRest::fromDomainToAdapterRestMaria)
						.collect(Collectors.toList());
			}else {
				return phoneInputPort.findAll().stream().map(phoneMapperRest::fromDomainToAdapterRestMongo)
						.collect(Collectors.toList());
			}
			
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new ArrayList<PhoneResponse>();
		}
	}

	public PhoneResponse crearPhone(PhoneRequest request) throws NumberFormatException, NoExistException {
		try {
			setPhoneOutputPortInjection(request.getDatabase());
			Person owner = personInputPort.findOne(Integer.parseInt(request.getDueno()));
			Phone phone = phoneInputPort.create(phoneMapperRest.fromAdapterToDomain(request, owner));
	
			if (request.getDatabase().equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return phoneMapperRest.fromDomainToAdapterRestMaria(phone);
			} else {
				return phoneMapperRest.fromDomainToAdapterRestMongo(phone);
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(request.getNumber(), "", "", request.getDatabase(), "Error: Invalid Database Option");
		} catch (NoExistException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(request.getNumber(), "", "", request.getDatabase(), "Error: Owner not found");
		} catch (NumberFormatException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(request.getNumber(), "", "", request.getDatabase(), "Error: Invalid Owner ID");
		}
	}
	

	public PhoneResponse buscarPhone(String database, String number) {
		try {
			setPhoneOutputPortInjection(database);
			Phone phone = phoneInputPort.findOne(number);
	
			if (database.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return phoneMapperRest.fromDomainToAdapterRestMaria(phone);
			} else {
				return phoneMapperRest.fromDomainToAdapterRestMongo(phone);
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(number, "", "", database, "Error: Invalid Database Option");
		} catch (NoExistException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(number, "", "", database, "Error: Phone not found");
		}
	}
	

	public PhoneResponse eliminarPhone(String database, String number){
		try {
			setPhoneOutputPortInjection(database);
			Boolean eliminado = phoneInputPort.drop(number);
	
			return new PhoneResponse(number, "", "", database, eliminado ? "Deleted" : "Failed to Delete");
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(number, "", "", database, "Error: Invalid Database Option");
		} catch (NoExistException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(number, "", "", database, "Error: Phone not found");
		}
	}
	

	public PhoneResponse actualizarPhone(PhoneRequest request){
		try {
			setPhoneOutputPortInjection(request.getDatabase());
			Person owner = personInputPort.findOne(Integer.parseInt(request.getDueno()));
			Phone phone = phoneInputPort.edit(request.getNumber(), phoneMapperRest.fromAdapterToDomain(request, owner));
	
			if (request.getDatabase().equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
				return phoneMapperRest.fromDomainToAdapterRestMaria(phone);
			} else {
				return phoneMapperRest.fromDomainToAdapterRestMongo(phone);
			}
		} catch (InvalidOptionException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(request.getNumber(), "", "", request.getDatabase(), "Error: Invalid Database Option");
		} catch (NoExistException e) {
			log.warn(e.getMessage());
			return new PhoneResponse(request.getNumber(), "", "", request.getDatabase(), "Error: Update Failed - Phone not found");
		}
	}
	


}
