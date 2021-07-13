package dev.pca;

import dev.pca.engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class PCALauncher implements CommandLineRunner {

    @Autowired
    private Engine listening;

    public static void main(String[] args) {
        SpringApplication.run(PCALauncher.class, args);
    }

    @Override
    public void run(String... args) {
        listening.startDefaultConfigs();
    }
}
