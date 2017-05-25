package fi.tamk.beerbros.kaljakauppa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

/**
 * Entity class for high score entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.7
 */
@SpringBootApplication
@EntityScan("fi.tamk.beerbros.kaljakauppa.components")
@EnableJpaRepositories("fi.tamk.beerbros.kaljakauppa.components")
@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
@ComponentScan(basePackages = {
    "fi.tamk.beerbros.kaljakauppa.components",
    "fi.tamk.beerbros.kaljakauppa.security"})
public class App {

    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
