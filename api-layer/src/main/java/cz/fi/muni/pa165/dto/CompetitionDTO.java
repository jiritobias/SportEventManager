package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
public class CompetitionDTO {
    @NonNull
    private Long id;
    private SportDTO sport;

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
