package cz.fi.muni.pa165.rest.controllers;

import cz.fi.muni.pa165.RootWebContext;
import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.facade.SportFacade;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class SportControllerTest extends AbstractTestNGSpringContextTests {
    private MockMvc mockMvc;

    @Autowired
    @InjectMocks
    private SportController sportController;

    @Mock
    SportFacade sportFacade;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(sportController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

    }

    @Test
    public void testGetSports() throws Exception {
        doReturn(Collections.unmodifiableList(this.createSports())).when(sportFacade).getAll();

        mockMvc.perform(get("/sports"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("Tennis"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("Football"));
    }

    @Test
    public void getSport() throws Exception {
        List<SportDTO> categories = createSports();

        doReturn(categories.get(0)).when(sportFacade).load(1L);
        doReturn(categories.get(1)).when(sportFacade).load(2L);

        mockMvc.perform(get("/sports/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Tennis"));

        mockMvc.perform(get("/sports/2"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Football"));
    }

    @Test
    public void getInvalidSport() throws Exception {
        doReturn(null).when(sportFacade).load(1L);

        mockMvc.perform(get("/sport/1"))
                .andExpect(status().is4xxClientError());

    }

    private List<SportDTO> createSports() {
        SportDTO tennis = new SportDTO(1L, "Tennis");
        SportDTO football = new SportDTO(2L, "Football");

        return Arrays.asList(tennis, football);
    }
}