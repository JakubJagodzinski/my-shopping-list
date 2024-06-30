package server;

import server.databaseapi.DatabaseOperations;

import java.util.concurrent.ConcurrentSkipListMap;

public class Changelog {

    private final String listName;
    private final String ownerName;
    private final String shopName;
    private final ConcurrentSkipListMap<Long, String> entries;

    public Changelog(String listName, String ownerName, String shopName) {
        this.listName = listName;
        this.ownerName = ownerName;
        this.shopName = shopName;
        this.entries = new ConcurrentSkipListMap<>();
    }

    public void clear() {
        this.entries.clear();
    }

    public void addEntry(String content) {
        this.entries.put(System.currentTimeMillis(), content);
    }

    public void pushOneEntry(String content) {
        ConcurrentSkipListMap<Long, String> oneEntry = new ConcurrentSkipListMap<>();
        oneEntry.put(System.currentTimeMillis(), content);
        DatabaseOperations.pushEntriesToChangelog(this.ownerName, this.shopName, this.listName, oneEntry);
    }

    public void pushEntriesToDatabase() {
        DatabaseOperations.pushEntriesToChangelog(this.ownerName, this.shopName, this.listName, this.entries);
        this.clear();
    }

}
