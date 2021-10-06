package ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
@DataSourceDefinition(name = "java:app/examplesDS", className = "org.h2.Driver",url = "jdbc:h2:C:/Users/BorodinovI/test", user = "sa", password = "")
public class BootStrapEJB {

    private Logger logger = LoggerFactory.getLogger(BootStrapEJB.class);

    @PostConstruct
    public void start(){
        logger.info("Enter @PostConstruct start() of BootStrapEJB.class");

    }
    @PreDestroy
    public void stop(){
        logger.info("Enter @PreDestroy stop() of BootStrapEJB.class");
    }
}
