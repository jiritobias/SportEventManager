package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jiritobias, Martin Smid
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SportDTO {
    @NonNull
    private Long id;

    @NonNull
    @Size(max = 40)
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
