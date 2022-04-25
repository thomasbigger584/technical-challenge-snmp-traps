package com.opennms.techchallenge.snmptraps.filter;

import java.util.List;

/**
 * Used to determine if we should filter an OID and perform additional processing
 */
public interface Filter {

    /**
     * Process if the oid should be filtered based on the provided prefixes
     *
     * @param prefixes - the prefixes to filter against
     * @param oid      - the oid to filter
     * @return a string representation of the filter
     */
    String process(List<String> prefixes, String oid);
}
