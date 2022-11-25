package de.idsmannheim.lza.inveniojavaapi;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvenioJavaApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(InvenioJavaApiApplication.class, args);
                ControlledVocabulary.Language langs = new ControlledVocabulary.Language();
	}

}
