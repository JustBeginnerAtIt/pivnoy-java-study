package ttv.poltoraha.pivka.dao.request;

import lombok.Builder;
import lombok.Data;
import ttv.poltoraha.pivka.entity.Genre;

@Data
@Builder
public class BookDTO {
    private String article;
    private String genreName;
    private Double rating;
    private String tags;
    private String authorFullName;
}
