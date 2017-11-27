package cz.fi.muni.pa165.dto;

import lombok.*;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistToCompetitionDTO {
    @NonNull
    Long competition;
    @NonNull
    Long sportsMen;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistToCompetitionDTO)) {
            return false;
        }

        RegistToCompetitionDTO that = (RegistToCompetitionDTO) o;

        if (competition != null ? !competition.equals(that.competition) : that.competition != null) {
            return false;
        }
        return sportsMen != null ? sportsMen.equals(that.sportsMen) : that.sportsMen == null;
    }

    @Override
    public int hashCode() {
        int result = competition != null ? competition.hashCode() : 0;
        result = 31 * result + (sportsMen != null ? sportsMen.hashCode() : 0);
        return result;
    }
}
