package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "genre")
@Data
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "genre_name", nullable = false)
    private String genreName;
    private String description;
    @Column(name = "genre_rating", nullable = false)
    private Integer genreRating;
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
}
