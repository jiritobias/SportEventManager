package cz.muni.fi.pa165.restapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.fi.muni.pa165.defaultdata.SemWithDefaultDataConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.validation.Validator;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

/**
 *
 */

@EnableHypermediaSupport(type = HypermediaType.HAL)
@EnableWebMvc
@Configuration
@Import({SemWithDefaultDataConfiguration.class})
@ComponentScan(basePackages = {"cz.muni.fi.pa165.restapi.controllers", "cz.muni.fi.pa165.restapi.hateoas"})
public class RestSpringMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer c) {
        c.defaultContentType(MediaTypes.HAL_JSON);
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = beanFactory.getBean("_halObjectMapper", ObjectMapper.class);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH));
        return objectMapper;
    }

    @Autowired
    private BeanFactory beanFactory;

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }


}
