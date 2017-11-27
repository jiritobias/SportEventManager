package cz.fi.muni.pa165.dto;

import lombok.*;

/**
 * @author Petra Halová
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCompetitionDTO {
    @NonNull
    private Long id;

    @NonNull
    private SportDTO sport;

}
