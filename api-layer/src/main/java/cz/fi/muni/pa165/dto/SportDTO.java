package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author jiritobias, Martin Smid
 */
@Getter
@AllArgsConstructor
public class SportDTO {
    @NonNull
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportDTO)) {
            return false;
        }

        SportDTO sportDTO = (SportDTO) o;

        return name != null ? name.equals(sportDTO.name) : sportDTO.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
