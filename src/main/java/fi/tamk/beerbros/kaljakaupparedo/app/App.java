package fi.tamk.beerbros.kaljakaupparedo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"fi.tamk.beerbros.kaljakaupparedo.components"})
@EntityScan("fi.tamk.beerbros.kaljakaupparedo.components")
@EnableJpaRepositories("fi.tamk.beerbros.kaljakaupparedo.components")
@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
public class App {
    
    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
