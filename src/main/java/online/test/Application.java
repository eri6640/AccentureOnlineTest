package online.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;



@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
  
	@Configuration
	@Order( SecurityProperties.ACCESS_OVERRIDE_ORDER )
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure( HttpSecurity http ) throws Exception {		
			http
			.httpBasic().and()
	        .authorizeRequests()
	        .antMatchers( "/index.html", "/createa", "/home.html", "/login.html", "/data/userdata/", "/img/*", "/data/*", "/data/repeat/", "/" ).permitAll().anyRequest()
	        .authenticated().and().addFilterAfter( new CsrfHeaderFilter(), CsrfFilter.class );
		}
	}

}