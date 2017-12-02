package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    private User sportsman;
    private CreateSportsMenDTO createSportsMenDTO;
    private Long sportsManId;
    private String password;

    @BeforeMethod
    public void setUp() {
        sportsman = new User();
        sportsman.setAddress("Death Star 1");
        sportsman.setEmail("darthVader@darkside.com");
        sportsman.setFirstname("Darth");
        sportsman.setLastname("Vader");
        sportsman.setGendre(Gendre.MAN);
        sportsman.setRole(Role.SPORTSMEN);
        password = "666";
        sportsman.setPasswordHash(password);
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date = cal.getTime();
        sportsman.setBirthdate(date);
        sportsman.setPhone("12");

        createSportsMenDTO = new CreateSportsMenDTO("password",
                sportsman.getEmail(), sportsman.getFirstname(), sportsman.getLastname(), sportsman.getGendre(),
                sportsman.getBirthdate(), sportsman.getPhone(), sportsman.getAddress(), sportsman.getRole());

        sportsManId = sportsMenFacade.createSportsMen(createSportsMenDTO);
    }

    @AfterMethod
    public void tearDown() {
        try {
            sportsMenFacade.delete(sportsMenFacade.load(sportsManId));
        } catch (Exception e) {
        }
    }

    @Test
    public void testCreateSportsMen() {
        Assertions.assertThat(sportsManId)
                .isNotNull();
    }

    @Test
    public void testFindByBirthDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.FEBRUARY, 1, 1, 1, 1);
        Date date = cal.getTime();

        List<SportsMenDTO> found = sportsMenFacade.findByBirthDay(date);
        Assertions.assertThat(found).isEmpty();

        found = sportsMenFacade.findByBirthDay(sportsman.getBirthdate());
        Assertions.assertThat(found.size()).isEqualTo(1);
        Assertions.assertThat(found.get(0).getId()).isEqualTo(sportsManId);
    }

    @Test
    public void testResetPassword() {
        SportsMenDTO dto = sportsMenFacade.load(sportsManId);
        String resetPass = sportsMenFacade.resetPassword(new ResetPasswordDTO(sportsManId, createSportsMenDTO.getEmail()));
        SportsMenDTO load = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(dto.getPasswordHash()).isNotEqualTo(load.getPasswordHash());

        Assertions.assertThat(userService.authenticate(sportsman, resetPass)).isTrue();
    }

    @Test
    public void testChangePassword() {
        String newPassword = "newPassword";
        ChangePasswordDTO passwordDTO = new ChangePasswordDTO(sportsManId, password, newPassword);
        sportsMenFacade.changePassword(passwordDTO);

        Assertions.assertThat(userService.authenticate(sportsman, newPassword)).isTrue();
    }

    @Test
    public void testDelete() {
        SportsMenDTO dto = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(dto.getId()).isNotNull();

        sportsMenFacade.delete(dto);
        Assertions.assertThatThrownBy(() -> sportsMenFacade.load(sportsManId)).isInstanceOf(MappingException.class);
    }

    @Test
    public void testLoad() {
        SportsMenDTO sportsMenDTO = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(sportsMenDTO.getFirstname()).isEqualTo(createSportsMenDTO.getFirstname());
        Assertions.assertThat(sportsMenDTO.getLastname()).isEqualTo(createSportsMenDTO.getLastname());
    }

    @Test
    public void testGetAll() {
        List<SportsMenDTO> all = sportsMenFacade.getAll();
        SportsMenDTO sportsMenDTO = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(all).containsOnly(sportsMenDTO);
    }
}
