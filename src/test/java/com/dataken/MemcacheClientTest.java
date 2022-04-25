package com.dataken;

import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

public class MemcacheClientTest {

    public static void main(String[] args) throws IOException {

        implementCrunchifySpyMemcached();

    }

    // Approach-1: net.spy.spymemcached
    // Use dependency net.spy.spymemcached to retrieve, store Key Value pair from MemCached Server
    private static void implementCrunchifySpyMemcached() throws IOException {

        // Get a memcache client operating on the specified memcached locations.
        MemcachedClient memcacheClient;
        try {
            memcacheClient = new MemcachedClient(new InetSocketAddress("e2eqa1", 11211));

            log("=====> Approach-1: SpyMemcached <=====\n");

            log("==> Connected to Crunchify's Local Server Successfully." + " Host: e2eqa1, Port: 11211");

            Map<SocketAddress, Map<String, String>> stats = memcacheClient.getStats();
            stats.forEach((key, val) -> {
                log("Key: " + key);
                log("Val: " + val);
            });
            Object val = memcacheClient.get("0F81744A6670894EB62102939354724F-n1");
            log("Sess val: " + val);

        } catch (IOException e) {
            // Prints this throwable and its backtrace to the standard error stream
            e.printStackTrace();
        }

    }

    private static void log(Object object) {
        System.out.println(object);

    }
}
