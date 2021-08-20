package ru.hilariousstartups.javaskills.perfectstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.hilariousstartups.javaskills.perfectstore.config.Dictionary;

@SpringBootApplication
@EnableConfigurationProperties(Dictionary.class)
public class PerfectStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfectStoreApplication.class, args);
	}

}
