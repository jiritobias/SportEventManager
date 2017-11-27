package cz.muni.fi.pa165.service.config;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Sport;
import cz.muni.fi.pa165.service.SportServiceImpl;
import cz.muni.fi.pa165.service.SportsmenServiceImpl;
import cz.muni.fi.pa165.service.UserServiceImpl;
import cz.muni.fi.pa165.service.facade.SportFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackageClasses = {SportServiceImpl.class, SportFacadeImpl.class})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Sport.class, SportDTO.class);
        }
    }

}
