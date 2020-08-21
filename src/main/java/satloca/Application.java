package satloca;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "SatLoca", version = "0.1", description = "Stream the position of a Satellite"))
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}
