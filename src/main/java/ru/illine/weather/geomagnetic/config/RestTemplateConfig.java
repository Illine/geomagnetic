package ru.illine.weather.geomagnetic.config;

import ru.illine.weather.geomagnetic.config.property.RestProperties;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

@Configuration
class RestTemplateConfig {

    private final RestProperties properties;

    @Autowired
    RestTemplateConfig(RestProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestTemplate swpcNoaaRestTemplate(HttpComponentsClientHttpRequestFactory commonHttpRequestFactory) {
        return new RestTemplate(commonHttpRequestFactory);
    }

    @Bean
    HttpComponentsClientHttpRequestFactory commonHttpRequestFactory(Logbook logbook,
                                                                    PoolingHttpClientConnectionManager commonConnection) {
        var httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(
                        HttpClients
                                .custom()
                                .setConnectionManager(commonConnection)
                                .addInterceptorFirst(new LogbookHttpRequestInterceptor(logbook))
                                .addInterceptorFirst(new LogbookHttpResponseInterceptor())
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