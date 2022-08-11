/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Fauzan
 */
@SpringBootApplication
public class AscmyunifiApp implements CommandLineRunner{

    @Autowired
    private AscmyunifiConfiguration config;
    
    private final Logger log = LoggerFactory.getLogger(getClass().getName());
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AscmyunifiApp.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting service");
    }
    
}
