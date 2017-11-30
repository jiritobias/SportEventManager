package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.CompetitionFacade;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.service.CompetitionService;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.SportsmenService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Petra Halová on 28.11.17.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SportsmenFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SportsMenFacade sportsMenFacade;

    private User sportman;

    @BeforeClass
    public void setUp() {
        sportman = new User();
        sportman.setAddress("Death Star 1");
        sportman.setEmail("darthVader@darkside.com");
        sportman.setFirstname("Darth");
        sportman.setLastname("Vader");
        sportman.setGendre(Gendre.MAN);
        sportman.setRole(Role.SPORTSMEN);
        sportman.setPasswordHash("666");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date = cal.getTime();
        sportman.setBirthdate(date);
        sportman.setPhone("12");
    }

    @Test
    public void testCreateSportsMen() {
        CreateSportsMenDTO createdSportsMan = new CreateSportsMenDTO(sportman.getPasswordHash(),
                sportman.getEmail(), sportman.getFirstname(), sportman.getLastname(), sportman.getGendre(),
                sportman.getBirthdate(), sportman.getPhone(), sportman.getAddress(), sportman.getRole());
        Long sportsMan = sportsMenFacade.createSportsMen(createdSportsMan);
        Assertions.assertThat(sportsMan)
                .isNotNull();

        sportsMenFacade.delete(new SportsMenDTO(sportsMan, ""));
    }
}
