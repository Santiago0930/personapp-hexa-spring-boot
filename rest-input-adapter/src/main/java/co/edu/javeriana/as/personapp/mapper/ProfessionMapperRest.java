package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.model.request.ProfessionRequest;
import co.edu.javeriana.as.personapp.model.response.ProfessionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapper
public class ProfessionMapperRest {
	private static final Logger log = LoggerFactory.getLogger(PersonaMapperRest.class);
	
	public ProfessionResponse fromDomainToAdapterRestMaria(Profession profession) {
		return fromDomainToAdapterRest(profession, "MariaDB");
	}
	public ProfessionResponse fromDomainToAdapterRestMongo(Profession profession) {
		return fromDomainToAdapterRest(profession, "MongoDB");
	}
	
	public ProfessionResponse fromDomainToAdapterRest(Profession profession, String database) {
		return new ProfessionResponse(
				profession.getIdentification()+"",
				profession.getName(),
				profession.getDescription(),
				database,
				"OK");
	}

	public Profession fromAdapterToDomain(ProfessionRequest request) {
		log.info("Into fromAdapterToDomain");
        Profession profession  = new Profession();
		profession.setIdentification(Integer.parseInt(request.getIdentification()));
		profession.setName(request.getName());
		profession.setDescription(request.getDescription());
        
        return profession;
    }


		
}
