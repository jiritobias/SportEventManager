package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class CreateCompetitionDTO {
    @NonNull
    private Long id;

    @NonNull
    private SportDTO sport;

}
