package ttv.poltoraha.pivka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "review_rating")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "review_rating",
        uniqueConstraints = @UniqueConstraint(columnNames = {"review_id", "username"}))
public class ReviewRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(name = "username")
    private MyUser user;

    @Column(nullable = false)
    private Integer rating;
}
