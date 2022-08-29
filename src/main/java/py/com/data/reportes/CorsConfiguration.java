package py.com.data.reportes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

	@Value("${cors.allowed}")
	private String allowed;

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**").allowedOrigins(allowed).allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE", "HEAD")
				.allowCredentials(true);
	}

}
