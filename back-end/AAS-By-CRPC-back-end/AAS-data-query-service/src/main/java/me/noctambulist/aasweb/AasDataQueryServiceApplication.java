package me.noctambulist.aasweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/29 22:55
 */
@SpringBootApplication
@EnableJpaAuditing
public class AasDataQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AasDataQueryServiceApplication.class, args);
    }

}
