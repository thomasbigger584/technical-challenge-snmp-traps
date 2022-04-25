package com.opennms.techchallenge.snmptraps.filter.impl;

import com.opennms.techchallenge.snmptraps.exception.InternalException;
import com.opennms.techchallenge.snmptraps.filter.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class SnmpTrapFilterTest {

    private Filter filter;

    @BeforeEach
    void init() {
        filter = new SnmpTrapFilter();
    }

    @Test
    void testSuccessfulFilterExample1() {
        List<String> prefixes = getPrefixes();
        String oid = ".1.3.6.1.4.1.9.9.117.2.0.1";

        String output = filter.process(prefixes, oid);
        assertEquals(".1.3.6.1.4.1.9.9.117.2.0.1: true", output);
    }

    @Test
    void testUnsuccessfulFilterExample2() {
        List<String> prefixes = getPrefixes();
        String oid = ".1.3.6.1.4.1.9.9.117";

        String output = filter.process(prefixes, oid);
        assertEquals(".1.3.6.1.4.1.9.9.117: false", output);
    }

    @Test
    void testUnsuccessfulFilterExample3() {
        List<String> prefixes = getPrefixes();
        String oid = ".1.3.6.1.4.1.9.9.118.2.0.1";

        String output = filter.process(prefixes, oid);
        assertEquals(".1.3.6.1.4.1.9.9.118.2.0.1: false", output);
    }

    @Test
    void testNullPrefixesReturnsFalse() {
        String oid = ".1.3.6.1.4.1.9.9.117.2.0.1";

        String output = filter.process(null, oid);
        assertEquals(".1.3.6.1.4.1.9.9.117.2.0.1: false", output);
    }

    @Test
    void testNullProvidedOidReturnsException() {
        List<String> prefixes = getPrefixes();

        boolean exceptionThrown = false;
        try {
            filter.process(prefixes, null);
        } catch (InternalException e) {
            assertEquals("Provided OID to filter is blank.", e.getMessage());
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    private List<String> getPrefixes() {
        return Arrays.asList(
                ".1.3.6.1.6.3.1.1.5",
                ".1.3.6.1.2.1.10.166.3",
                ".1.3.6.1.4.1.9.9.117.2",
                ".1.3.6.1.2.1.10.32.0.1",
                ".1.3.6.1.2.1.14.16.2.2",
                ".1.3.6.1.4.1.9.10.137.0.1");
    }
}
