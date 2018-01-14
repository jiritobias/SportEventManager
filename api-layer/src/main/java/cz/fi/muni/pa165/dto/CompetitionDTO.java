package cz.fi.muni.pa165.dto;

import lombok.*;
import java.util.List;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompetitionDTO {

    @NonNull
    private Long id;
    private SportDTO sport;
    private List<SportsMenDTO> sportsMen;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitionDTO)) {
            return false;
        }

        CompetitionDTO that = (CompetitionDTO) o;

        return sport.equals(that.sport);
    }

    @Override
    public int hashCode() {
        return sport.hashCode();
    }
}
