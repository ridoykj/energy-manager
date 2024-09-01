package com.itbd.energymanager.config;


import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource({"classpath:application.properties", "classpath:logback.properties", "classpath:application-${spring.profiles.active}.properties"})
@Slf4j
@EnableScheduling
@EnableAsync
public class AppConfig {
//    @Bean
//    public TaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//        threadPoolTaskScheduler.setPoolSize(4);
//        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
//        return threadPoolTaskScheduler;
//    }


    @Bean("WebClient")
    public WebClient webclient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

//    public StompClient stompClient(){
//        StompClient stompSocket = new StompClient(URI.create(absMasterApi.getServer().getWs() + "/desoft/wsconnect"));
//        stompSocket.addHeader("Authorization", token);
//    }

    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        // Would also work with javafx-weaver-core only:
        // return new FxWeaver(applicationContext::getBean, applicationContext::close);
        return new SpringFxWeaver(applicationContext);
    }
}
