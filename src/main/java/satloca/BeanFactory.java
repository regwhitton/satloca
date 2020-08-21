package satloca;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import java.time.Clock;

@Factory
public class BeanFactory {

    @Bean
    public Clock utcClock() {
        return Clock.systemUTC();
    }
}
