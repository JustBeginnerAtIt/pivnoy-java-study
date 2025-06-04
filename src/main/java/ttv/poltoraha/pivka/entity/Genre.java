package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "genre")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String genreName;
    private String description;
    @OneToMany(mappedBy = "genre")
    @ToString.Exclude
    private List<Book> books;
}
