package com.opennms.techchallenge.snmptraps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

@Configuration
public class YamlConfiguration {

    /**
     * A Yaml parser used to dynamically parse any provided yaml
     * document into an object we can use to query against.
     *
     * @return a yaml parser
     */
    @Bean
    public Yaml yaml() {
        return new Yaml();
    }
}
