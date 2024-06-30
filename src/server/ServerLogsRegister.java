package server;

import server.databaseapi.DatabaseOperations;

import java.util.concurrent.ConcurrentSkipListMap;

public class ServerLogsRegister {

    private static final ConcurrentSkipListMap<Long, String> logs = new ConcurrentSkipListMap<>();
    private static final int SERVER_LOGS_SAVE_SECONDS_INTERVAL = 5;
    private static boolean isClosed = true;

    public static void openLogsRegister() {
        isClosed = false;
    }

    public static void closeLogsRegister() {
        isClosed = true;
    }

    public static synchronized void addLog(String log) {
        logs.put(System.currentTimeMillis(), log);
    }

    public static synchronized void pushLogsToFile() {
        DatabaseOperations.pushLogsToFile(logs);
        logs.clear();
    }

    public static void run() {
        openLogsRegister();
        addLog("Server turned on");
        pushLogsToFile();
        while (!isClosed) {
            try {
                Thread.sleep(SERVER_LOGS_SAVE_SECONDS_INTERVAL + 1_000);
            } catch (Exception ignore) {
            }
            pushLogsToFile();
        }
        addLog("Server turned off");
        pushLogsToFile();
    }

}
