package ttv.poltoraha.pivka.mapping;

import ttv.poltoraha.pivka.dao.request.AuthorDTO;
import ttv.poltoraha.pivka.entity.Author;

public class AuthorMapping {

    public static Author fromDto(AuthorDTO dto) {
        if (dto == null) return null;

        return Author.builder()
                .fullName(dto.getFullName())
                .avgRating(dto.getAvgRating())
                .build();
    }

    public static AuthorDTO toDto(Author author) {
        if (author == null) return null;

        return AuthorDTO.builder()
                .fullName(author.getFullName())
                .avgRating(author.getAvgRating())
                .build();
    }
}
