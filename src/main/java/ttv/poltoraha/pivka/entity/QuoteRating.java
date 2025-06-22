package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "quote_rating")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "quote_rating",
        uniqueConstraints = @UniqueConstraint(columnNames = {"quote_id", "username"}))
public class QuoteRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @ManyToOne
    @JoinColumn(name = "username")
    private MyUser user;

    @Column(nullable = false)
    private Integer rating;
}
