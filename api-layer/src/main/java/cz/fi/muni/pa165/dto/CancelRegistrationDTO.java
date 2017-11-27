package cz.fi.muni.pa165.dto;

import lombok.*;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelRegistrationDTO {
    @NonNull
    Long sportsMen;
    @NonNull
    Long competition;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CancelRegistrationDTO)) {
            return false;
        }

        CancelRegistrationDTO that = (CancelRegistrationDTO) o;

        if (sportsMen != null ? !sportsMen.equals(that.sportsMen) : that.sportsMen != null) {
            return false;
        }
        return competition != null ? competition.equals(that.competition) : that.competition == null;
    }

    @Override
    public int hashCode() {
        int result = sportsMen != null ? sportsMen.hashCode() : 0;
        result = 31 * result + (competition != null ? competition.hashCode() : 0);
        return result;
    }
}
