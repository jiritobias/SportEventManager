package cz.fi.muni.pa165.dto;

import lombok.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jiritobias, Martin Smid
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SportDTO {

    private Long id;

    @NonNull
    @Size(max = 40)
    private String name;

    private List<SportsMenDTO> sportsMenDTOList;

    public SportDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
