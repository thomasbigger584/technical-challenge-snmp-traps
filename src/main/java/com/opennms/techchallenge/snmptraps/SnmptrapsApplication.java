package com.opennms.techchallenge.snmptraps;

import com.opennms.techchallenge.snmptraps.exception.InternalException;
import com.opennms.techchallenge.snmptraps.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main runner for the application.
 * See the README.md for details on how to run this class.
 */
@SpringBootApplication
public class SnmptrapsApplication implements CommandLineRunner {

    @Autowired
    private Yaml yaml;

    @Autowired
    private Filter filter;

    public static void main(String[] args) {
        SpringApplication.run(SnmptrapsApplication.class, args);
    }

    /**
     * Given user input from stdin, evaluate the trap type against the
     * provided prefixes in snmp.yml and output the results
     *
     * @param args - command line arguments not currently used
     */
    @Override
    public void run(String... args) {

        InputStream snmpInputStream = getClass().getClassLoader()
                .getResourceAsStream("snmp.yml");

        if (snmpInputStream == null) {
            throw new InternalException("SNMP Yaml resource couldn't be found");
        }

        Map<String, List<String>> snmpYmlProps = yaml.load(snmpInputStream);

        List<String> trapTypePrefixes = snmpYmlProps.get("trap-type-oid-prefix");

        System.out.print("Trap Type OID: ");
        Scanner scanner = new Scanner(System.in);
        String oid = scanner.nextLine();

        String output = filter.process(trapTypePrefixes, oid);
        System.out.println(output);
    }
}
