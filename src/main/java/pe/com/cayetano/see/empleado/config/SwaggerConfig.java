package pe.com.cayetano.see.empleado.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
            .info(new Info().title("API Empleado")
                    .description("Servicio para gestionar empleados.")
                    .version("0.0.1")
            );
  }
}
