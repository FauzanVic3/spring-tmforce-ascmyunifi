/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Fauzan
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AscmyunifiConfiguration {
 
    private boolean logPayload = true;
    private Integer logPayloadSize = 4096;
    boolean corsEnabled = true;

    
}
