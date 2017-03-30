package fi.tamk.beerbros.kaljakauppa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"fi.tamk.beerbros.kaljakauppa.components"})
@EntityScan("fi.tamk.beerbros.kaljakauppa.components.region")
@EnableJpaRepositories("fi.tamk.beerbros.kaljakauppa.components.region")
public class Main {

    public static void main(String... args) {
        SpringApplication.run(Main.class, args);
    }
}
