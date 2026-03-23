package com.bizeff.procurement.models;

import java.util.HashMap;

public class IdGenerator {
    private static final HashMap<String, Integer> idCounters = new HashMap<>();

    public static String generateId(String prefix) {
        idCounters.putIfAbsent(prefix, 0); // Initialize counter if not exists
        int nextId = idCounters.get(prefix) + 1; // Increment count
        idCounters.put(prefix, nextId); // Store new count

        return String.format("%s-%03d", prefix, nextId); // Formats as PO-001, SUP-002
    }
}

