package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity(name="book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String article;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    private Double rating;
    private String tags;
    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Quote> quotes;

    public Set<String> getTags() {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptySet();
        }

        String[] tagArray = tags.split(", ");
        return new HashSet<>(Arrays.asList(tagArray));
    }

}
