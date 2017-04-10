package fi.tamk.beerbros.kaljakauppa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"fi.tamk.beerbros.kaljakauppa.components"})
@EntityScan("fi.tamk.beerbros.kaljakauppa.components")
@EnableJpaRepositories("fi.tamk.beerbros.kaljakauppa.components")
@EnableAutoConfiguration(exclude = RepositoryRestMvcAutoConfiguration.class)
public class App {
    
    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
