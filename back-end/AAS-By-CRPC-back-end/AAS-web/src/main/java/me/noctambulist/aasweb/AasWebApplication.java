package me.noctambulist.aasweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/18 15:31
 */
@SpringBootApplication
@EnableJpaAuditing
public class AasWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AasWebApplication.class, args);
    }

}
