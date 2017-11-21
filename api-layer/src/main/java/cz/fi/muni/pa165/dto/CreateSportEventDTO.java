package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import java.util.Date;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
public class CreateSportEventDTO {
    @NonNull
    private String name;
    @NonNull
    private String place;
    @NonNull
    private Date date;
}
