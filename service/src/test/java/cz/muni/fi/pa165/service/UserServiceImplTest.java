package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.ServiceException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.util.Calendar;
import java.util.Date;


/**
 * @author Petra Halová on 26.11.17.
 */

@ContextConfiguration(classes=ServiceConfiguration.class)
public class UserServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserService userService;

    private User testUser;

    private User anotherTestUser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeMethod
    public void setUpData(){
        testUser = new User();
        testUser.setAddress("Death Star 1");
        testUser.setEmail("darthVader@darkside.com");
        testUser.setFirstname("Darth");
        testUser.setLastname("Vader");
        testUser.setGendre(Gendre.MAN);
        testUser.setPasswordHash("666");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date = cal.getTime();
        testUser.setBirthdate(date);

        anotherTestUser = new User();
        anotherTestUser.setAddress("Millennium Falcon");
        anotherTestUser.setEmail("hanSolo@loveMyShip.com");
        anotherTestUser.setFirstname("Han");
        anotherTestUser.setLastname("Solo");
        anotherTestUser.setGendre(Gendre.MAN);
        anotherTestUser.setPasswordHash("1");
        Calendar cal2 = Calendar.getInstance();
        cal.set(2005, Calendar.MARCH, 1, 1, 1, 1);
        Date date2 = cal.getTime();
        anotherTestUser.setBirthdate(date2);
   }

    @Test
    public void testCreate(){
        Assertions.assertThat(userService.findAll()).isEmpty();
        userService.create(testUser);

        Assertions.assertThat(userService.findById(testUser.getId()))
                .isEqualToComparingFieldByField(testUser);
    }

    @Test
    public void testFindById(){
    userService.create(testUser);

    Assertions.assertThat(userService.findById(testUser.getId()))
                .isEqualToComparingFieldByField(testUser);
    }

    @Test
    public void testFindAll(){
        userService.create(testUser);
        userService.create(anotherTestUser);

        Assertions.assertThat(userService.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser, anotherTestUser);
    }

    @Test
    public void testDelete(){
        userService.create(testUser);
        userService.create(anotherTestUser);
        Assertions.assertThat(userService.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser,anotherTestUser);

        userService.delete(anotherTestUser);
        Assertions.assertThat(userService.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser);
    }

    @Test
    public void testFindByEmail() {
        userService.create(testUser);
        userService.create(anotherTestUser);
        Assertions.assertThat(userService.findByEmail(testUser.getEmail()))
                .isEqualToComparingFieldByField(testUser);
    }

    @Test
    public void testFindByGender(){
        anotherTestUser.setGendre(Gendre.WOMAN);
        userService.create(testUser);
        userService.create(anotherTestUser);

        Assertions.assertThat(userService.findByGender(Gendre.MAN))
                .usingFieldByFieldElementComparator().
                containsOnly(testUser);
    }

    @Test
    public void testFindByBirthDate(){
        Assertions.assertThat(userService.findByBirthdate(testUser.getBirthdate())).isEmpty();
        userService.create(testUser);
        userService.create(anotherTestUser);
        Assertions.assertThat(userService.findByBirthdate(testUser.getBirthdate()))
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser);
    }

    @Test
    public void testFindByBirthDateWithinRange(){
        Calendar cal = Calendar.getInstance();
        cal.set(2005, Calendar.JANUARY, 1, 1, 1, 1);
        Date date = cal.getTime();
        Assertions.assertThat(userService.findByBirthdateWithinRange(testUser.getBirthdate(), date)).isEmpty();

        userService.create(testUser);
        userService.create(anotherTestUser);
        Assertions.assertThat(userService.findByBirthdateWithinRange(testUser.getBirthdate(), date))
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser);
    }

    @Test
    public void testRegisterUser(){
        Assertions.assertThat(userService.findAll()).isEmpty();
        userService.registerUser(testUser, testUser.getPasswordHash(), testUser.getEmail());
        Assertions.assertThat(userService.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser);
    }

    @Test
    public void testAlreadyRegisteredUser(){
        userService.registerUser(testUser, testUser.getPasswordHash(), testUser.getEmail());
        //expectedException.expect(IllegalArgumentException.class);
        userService.registerUser(testUser, testUser.getPasswordHash(), testUser.getEmail());
    }

    @Test
    public void testRegisterUserWithSameEmail(){
        userService.registerUser(testUser, testUser.getPasswordHash(), testUser.getEmail());
        anotherTestUser.setEmail(testUser.getEmail());

        expectedException.expect(IllegalArgumentException.class);
        userService.registerUser(anotherTestUser, anotherTestUser.getEmail(), anotherTestUser.getPasswordHash());
    }

    @Test
    public void testAuthenticate(){

    }

    @Test
    public void testWrongPassword(){
        userService.registerUser(testUser, testUser.getPasswordHash(), testUser.getEmail());
        expectedException.expect(IllegalArgumentException.class);
        userService.authenticate(testUser, "111");
    }
}


