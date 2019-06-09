package it.almaviva.nerds.services.repository;

import it.almaviva.nerds.services.model.RichiestaIntervento;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface InterventoRepository extends CrudRepository<RichiestaIntervento, Long> {

}