package com.codefleckbot;

import com.codefleckbot.core.engine.TradingEngine;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class CodefleckbotApplication implements CommandLineRunner {

    private TradingEngine tradingEngine;

    public static void main(String[] args) {
        SpringApplication.run(CodefleckbotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Warming up the engines...");
        //tradingEngine.start();
    }
}
