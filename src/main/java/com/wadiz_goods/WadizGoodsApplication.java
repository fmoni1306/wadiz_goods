package com.wadiz_goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WadizGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WadizGoodsApplication.class, args);
    }

}
