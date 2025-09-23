package pl.kurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kurs.app.BikeRunner;
import pl.kurs.app.ClientRunner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);

        BikeRunner bikeRunner = ctx.getBean(BikeRunner.class);
        ClientRunner clientRunner = ctx.getBean(ClientRunner.class);

        bikeRunner.run();
        clientRunner.run();

    }
}