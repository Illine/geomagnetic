package net.c7j.weather.geomagnetic.config.rest;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestProperties.class)
class RestTemplateFactory {

    private final RestProperties properties;

    @Autowired
    RestTemplateFactory(RestProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestTemplate swpNoaaRestTemplate(HttpComponentsClientHttpRequestFactory commonHttpRequestFactory) {
        return new RestTemplate(commonHttpRequestFactory);
    }

    @Bean
    HttpComponentsClientHttpRequestFactory commonHttpRequestFactory(PoolingHttpClientConnectionManager commonConnection) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(
                        HttpClients
                                .custom()
                                .setConnectionManager(commonConnection)
                                .build()
                );
        httpRequestFactory.setReadTimeout(properties.getReadTimeout());
        httpRequestFactory.setConnectTimeout(properties.getConnectionTimeout());
        return httpRequestFactory;
    }

    @Bean
    PoolingHttpClientConnectionManager commonConnection() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(properties.getMaxThread());
        connectionManager.setDefaultMaxPerRoute(properties.getMaxRoute());
        return connectionManager;
    }
}