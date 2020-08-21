package satloca.service.location;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import satloca.model.NasaTle;

@Repository
interface NasaTleRepository extends CrudRepository<NasaTle, Long> {
}
