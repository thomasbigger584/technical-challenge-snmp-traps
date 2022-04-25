package com.opennms.techchallenge.snmptraps.filter.impl;


import com.opennms.techchallenge.snmptraps.exception.InternalException;
import com.opennms.techchallenge.snmptraps.filter.Filter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * An SNMP Trap Filter implementation of {@link Filter}
 */
@Component
public class SnmpTrapFilter implements Filter {

    /**
     * {@inheritDoc}
     * Evaluate whether or not a given trap type OID starts with any of the given
     * prefixes so we can perform additional processing on this trap or not. If the list is null, empty or the oid is
     * not in the list then it will return false else it has been found so will return true.
     *
     * @param prefixes - the list of provided prefixes
     * @param oid      - the oid to filter
     * @return the oid concatenated with a boolean if we should perform additional processing
     */
    @Override
    public String process(List<String> prefixes, String oid) {

        if (oid == null || oid.length() == 0) {
            throw new InternalException("Provided OID to filter is blank.");
        }

        if (prefixes == null || prefixes.isEmpty()) {
            return oid + ": false";
        }

        for (String prefix : prefixes) {
            if (oid.startsWith(prefix)) {
                return oid + ": true";
            }
        }
        return oid + ": false";
    }
}
