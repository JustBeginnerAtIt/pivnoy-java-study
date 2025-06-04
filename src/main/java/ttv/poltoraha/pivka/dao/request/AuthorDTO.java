package ttv.poltoraha.pivka.dao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDTO {
    private String fullName;
    private Double avgRating;
}
