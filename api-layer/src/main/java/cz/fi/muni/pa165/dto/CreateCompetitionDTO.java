package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Petra Halová
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompetitionDTO {
    @NonNull
    private Long id;

    @NonNull
    private SportDTO sport;

}
