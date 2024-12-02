package br.com.fiap.tastytap_payments.infraestructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class AppConfiguration {

    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
