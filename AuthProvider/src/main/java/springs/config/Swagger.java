package springs.config;

import java.util.Collections;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@Configuration
@EnableSwagger2
public class Swagger {
	
	  @Bean
	    public GroupedOpenApi publicApi() {
	        return GroupedOpenApi.builder()
	                .group("public-apis")
	                .pathsToMatch("/**")  
	                .build();
	    }

	    @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                    .title("My REST API")
	                    .version("1.0")
	                    .description("API description")
	                    .contact(new Contact()
	                        .name("John Doe")
	                        .url("http://www.example.com")
	                        .email("myeaddress@company.com"))
	                );
	    }
	

}
