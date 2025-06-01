package ttv.poltoraha.pivka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
}
