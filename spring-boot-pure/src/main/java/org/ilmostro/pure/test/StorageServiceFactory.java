package org.ilmostro.pure.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageServiceFactory {

    private static final Map<String, StorageService> FACTORY = new ConcurrentHashMap<>();

    public static void register(String name, StorageService service){
        FACTORY.put(name, service);
    }

    public static StorageService getStorageService(String name){
        return FACTORY.get(name);
    }
}
