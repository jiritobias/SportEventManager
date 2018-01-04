package cz.fi.muni.pa165.defaultdata;

import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import cz.muni.fi.pa165.service.CompetitionService;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.SportsmenService;
import cz.muni.fi.pa165.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 *
 */
@Component
@Transactional
public class DefaultDataLoadingFacadeImpl implements DefaultDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(DefaultDataLoadingFacadeImpl.class);

    @Autowired
    private SportService sportService;

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private SportsmenService sportsmenService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Override
    public void loadData() throws IOException {
        log.debug("creating sports");

        Long ice_hockey = createSport("Ice hockey");
        Long basketball = createSport("Basketball");
        Long voleyball = createSport("Voleyball");
        Long football = createSport("Football");
        Long tennis = createSport("Tennis");

        log.debug("creating competitions");

        Long icehockeyCompetition = createCompetition(ice_hockey);
        Long basketBallCompetition = createCompetition(basketball);
        Long volleybalCompetiton = createCompetition(voleyball);
        Long footballCompetition = createCompetition(football);
        Long tennisCompetition = createCompetition(tennis);

        log.debug("crating users and sportsmen");
        User sportsMen = createSportsMan();
        User admin = createAdmin();
        User user = createUser();


        log.debug("registering sportsmen to competiton");


    }

    private User createSportsMan() {
        User user = new User();
        user.setFirstname("Honza");
        user.setLastname("Vyborny");
        user.setGendre(Gendre.MAN);
        user.setBirthdate(createDate(1990, 10, 10));
        user.setPhone("600700800");
        user.setAddress("Fake");
        user.setRole(Role.SPORTSMEN);
        sportsmenService.registerUser(user, "sportsmenHeslo", "prvni@gmail.com");
        return user;
    }

    private User createAdmin() {
        User user = new User();
        user.setFirstname("Admin");
        user.setLastname("administrator");
        user.setGendre(Gendre.MAN);
        user.setEmail("admin@gmail.com");
        user.setBirthdate(createDate(1992, 11, 1));
        user.setPhone("800900500");
        user.setAddress("Fake");
        user.setRole(Role.ADMINISTRATOR);
        userService.registerUser(user, "admin", "admin@gmail.com");
        userService.makeUserAdmin(user);
        return user;
    }

    private User createUser() {
        User user = new User();
        user.setFirstname("User");
        user.setLastname("Eva");
        user.setGendre(Gendre.WOMAN);
        user.setBirthdate(createDate(1991, 1, 6));
        user.setPhone("800900500");
        user.setAddress("Fake");
        user.setRole(Role.USER);

        userService.registerUser(user, "userHeslo", "user@gmail.com");
        return user;
    }

    private Date createDate(int year, int month, int day) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
    }

    private Long createCompetition(Long sportId) {
        assert sportId != null;

        Sport sport = sportService.findById(sportId);
        Competition competition = new Competition();
        competition.setDate(new Date());
        competition.setSport(sport);

        competitionService.create(competition);
        return competition.getId();
    }

    private Long createSport(String name) {
        assert name != null;
        Sport sport = new Sport();
        sport.setName(name);

        sportService.create(sport);

        return sport.getId();
    }
}
