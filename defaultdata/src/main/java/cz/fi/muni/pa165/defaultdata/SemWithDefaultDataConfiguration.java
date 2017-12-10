package cz.fi.muni.pa165.defaultdata;

import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 *
 */
@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {DefaultDataLoadingFacadeImpl.class})
public class SemWithDefaultDataConfiguration {

    final static Logger log = LoggerFactory.getLogger(SemWithDefaultDataConfiguration.class);

    @Autowired
    DefaultDataLoadingFacade defaultDataLoadingFacade;

    @PostConstruct
    public void dataLoading() throws IOException {
        log.debug("dataLoading()");
        defaultDataLoadingFacade.loadData();
    }
}
