package com.opennms.techchallenge.snmptraps;

import com.opennms.techchallenge.snmptraps.exception.InternalException;
import com.opennms.techchallenge.snmptraps.filter.Filter;
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
    private static final String SNMP_YML = "snmp.yml";
    private static final String TRAP_TYPE_OID_PREFIX = "trap-type-oid-prefix";

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

        List<String> trapTypePrefixes = null;
        try {
            trapTypePrefixes = getTrapTypePrefixes();
        } catch (InternalException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        System.out.print("Enter Trap Type OID: ");
        Scanner scanner = new Scanner(System.in);
        String oid = scanner.nextLine();

        try {
            String output = filter.process(trapTypePrefixes, oid);
            System.out.println(output);
        } catch (InternalException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private List<String> getTrapTypePrefixes() {
        InputStream snmpInputStream = getClass().getClassLoader()
                .getResourceAsStream(SNMP_YML);

        if (snmpInputStream == null) {
            throw new InternalException("SNMP Yaml resource couldn't be found for file name: " + SNMP_YML);
        }

        Map<String, List<String>> snmpYmlProps = yaml.load(snmpInputStream);
        if (snmpYmlProps.containsKey(TRAP_TYPE_OID_PREFIX)) {
            return snmpYmlProps.get(TRAP_TYPE_OID_PREFIX);
        }
        throw new InternalException("No Trap Type OID Prefixes found for key: " + TRAP_TYPE_OID_PREFIX);
    }
}
