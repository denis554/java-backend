package rbn.edu.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = { //
	"rbn.edu.config", //
	"rbn.edu.controllers", //
	"rbn.edu.filters", //
	"rbn.edu.security", //
	"rbn.edu.jwt" })
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean(initMethod = "migrate")
    @DependsOn("dataSource")
    public Flyway flyway() {
	Flyway flyway = new Flyway();
	flyway.setDataSource(dataSource);
	return flyway;
    }

}
