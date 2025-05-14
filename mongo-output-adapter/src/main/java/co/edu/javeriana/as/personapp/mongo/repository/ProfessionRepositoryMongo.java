package co.edu.javeriana.as.personapp.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;

public interface ProfessionRepositoryMongo extends MongoRepository<ProfesionDocument, Integer> {

}
