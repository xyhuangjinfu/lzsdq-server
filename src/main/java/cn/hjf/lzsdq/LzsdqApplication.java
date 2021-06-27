package cn.hjf.lzsdq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LzsdqApplication {

    public static void main(String[] args) {
        SpringApplication.run(LzsdqApplication.class, args);
    }
}
