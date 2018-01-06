package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dao.UserDao;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author Petra Halová on 26.11.17.
 */

@ContextConfiguration(classes=ServiceConfiguration.class)
public class UserServiceImplTest extends AbstractTestNGSpringContextTests {


    @Mock
    private UserDao userDao;

    @InjectMocks
    @Qualifier("userServiceImpl")
    private UserService userService = new UserServiceImpl();

    private User testUser;
    private String password;
    private User anotherTestUser;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void setUpData(){
        testUser = new User();
        testUser.setAddress("Death Star 1");
        testUser.setEmail("darthVader@darkside.com");
        testUser.setFirstname("Darth");
        testUser.setLastname("Vader");
        testUser.setGendre(Gendre.MAN);
        testUser.setPasswordHash("666");
        password = "666";
        testUser.setRole(Role.USER);
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
        anotherTestUser.setRole(Role.USER);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2005, Calendar.MARCH, 1, 1, 1, 1);
        Date date2 = cal2.getTime();
        anotherTestUser.setBirthdate(date2);
   }
    @Test
    public void testMakeUserAdmin() {
        when(userDao.findById(anyLong())).thenReturn(testUser);

        userService.makeUserAdmin(testUser);

        Assertions.assertThat(testUser.getRole()).isEqualByComparingTo(Role.ADMINISTRATOR);
        verify(userDao).update(testUser);
    }

    @Test
    public void testFindById(){
    when(userDao.findById(testUser.getId())).thenReturn(testUser);
    Assertions.assertThat(userService.findById(testUser.getId()))
                .isEqualToComparingFieldByField(testUser);
    }

    @Test
    public void testFindAll(){
        when(userDao.findAll()).thenReturn(Arrays.asList(testUser, anotherTestUser));
        Assertions.assertThat(userService.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser, anotherTestUser);
    }


    @Test
    public void testFindAllByRole(){
        when(userDao.findAll(Role.USER)).thenReturn(Arrays.asList(testUser, anotherTestUser));
        Assertions.assertThat(userService.findAll(Role.USER))
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser, anotherTestUser);

        when(userDao.findAll(Role.SPORTSMEN)).thenReturn(Collections.emptyList());
        Assertions.assertThat(userService.findAll(Role.SPORTSMEN)).isEmpty();
    }

    @Test
    public void testDelete(){
        userService.delete(testUser);
        verify(userDao).delete(testUser);
    }

    @Test
    public void testFindByEmail() {
        when(userDao.findByEmail(testUser.getEmail())).thenReturn(testUser);
        Assertions.assertThat(userService.findByEmail(testUser.getEmail()))
                .isEqualToComparingFieldByField(testUser);
    }

    @Test
    public void testFindByNonExistingEmail() {
        when(userDao.findByEmail(testUser.getEmail())).thenReturn(null);
        Assertions.assertThat(userService.findByEmail(testUser.getEmail()))
            .isNull();
    }

    @Test
    public void testFindByGender(){
        anotherTestUser.setGendre(Gendre.WOMAN);
        when(userDao.findByGendre(Gendre.MAN)).thenReturn(Collections.singletonList(testUser));
        Assertions.assertThat(userService.findByGender(Gendre.MAN))
                .usingFieldByFieldElementComparator().
                containsOnly(testUser);
    }

    @Test
    public void testFindByBirthDate(){
        when(userDao.findByBirthdayWithinRange(testUser.getBirthdate(), testUser.getBirthdate()))
                .thenReturn(Collections.singletonList(testUser));
        Assertions.assertThat(userService.findByBirthdate(testUser.getBirthdate()))
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser);
    }

    @Test
    public void testFindBirthDateWithNoResult(){
        Calendar cal = Calendar.getInstance();
        cal.set(2005, Calendar.JANUARY, 1, 1, 1, 1);
        Date date = cal.getTime();
        when(userDao.findByBirthdayWithinRange(testUser.getBirthdate(), date))
                .thenReturn(Collections.emptyList());
        Assertions.assertThat(userService.findByBirthdate(testUser.getBirthdate())).isEmpty();
    }

    @Test
    public void testFindByBirthDateWithinRange(){
        Calendar cal = Calendar.getInstance();
        cal.set(2005, Calendar.JANUARY, 1, 1, 1, 1);
        Date date = cal.getTime();

        when(userDao.findByBirthdayWithinRange(testUser.getBirthdate(), date))
                .thenReturn(Collections.singletonList(testUser));
        Assertions.assertThat(userService.findByBirthdateWithinRange(testUser.getBirthdate(), date))
                .usingFieldByFieldElementComparator()
                .containsOnly(testUser);
    }

    @Test
    public void testWrongPassword(){
        userService.registerUser(testUser, testUser.getPasswordHash(), testUser.getEmail());
        Assertions.assertThat(userService.authenticate(testUser, "111")).isFalse();
    }

    @Test
    public void testAuthenticate(){
        userService.registerUser(testUser, password, testUser.getEmail());
        Assertions.assertThat(userService.authenticate(testUser, password)).isTrue();
    }

    @Test
    public void testUpdate() {
        Assertions.assertThat(testUser.getAddress()).isEqualToIgnoringCase("Death Star 1");
        Assertions.assertThat(testUser.getFirstname()).isEqualToIgnoringCase("Darth");

        doAnswer(invocationOnMock -> {
            testUser.setFirstname("Jmeno");
            testUser.setAddress("Hvezda");
            testUser.setRole(Role.SPORTSMEN);
            testUser.setEmail("novy@email.com");
            return null;
        }).when(userDao).update(testUser);

        userDao.update(testUser);

        Assertions.assertThat(testUser.getAddress()).isEqualToIgnoringCase("Hvezda");
        Assertions.assertThat(testUser.getFirstname()).isEqualToIgnoringCase("Jmeno");
        Assertions.assertThat(testUser.getRole()).isEqualByComparingTo(Role.SPORTSMEN);
        Assertions.assertThat(testUser.getEmail()).isEqualToIgnoringCase("novy@email.com");
        verify(userDao).update(testUser);
    }
}


