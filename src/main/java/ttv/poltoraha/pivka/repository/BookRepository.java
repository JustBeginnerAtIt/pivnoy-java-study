package ttv.poltoraha.pivka.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttv.poltoraha.pivka.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    // JPQL вариант
    @Query(value = """
       SELECT b FROM book b
       WHERE b.author.fullName = :fullName
       AND b.author.avgRating = (
           SELECT MAX(a.avgRating) FROM author a WHERE a.fullName = :fullName
           )
    """)
    List<Book> findBooksByAuthorFullNameAndRating(@Param("fullName") String fullName);

    // Второй вариант через SQL
//    @Query(value = """
//       SELECT b.*
//       FROM book b
//       JOIN author a ON b.author_id = a.id
//       WHERE a.full_name = :fullName
//       AND a.rating = (
//           SELECT MAX(a2.rating) FROM author a2 WHERE a2.full_name = :fullName
//           )
//    """, nativeQuery = true)
//    List<Book> findBooksByAuthorFullNameAndRating(@Param("fullName") String fullName);
}
