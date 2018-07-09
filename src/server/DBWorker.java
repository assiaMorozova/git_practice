package server;

import univer.oko.database.rmi.RMI;

public class DBWorker {
    private static RMI ourInstance = null;

    public static RMI instance() {
           return ourInstance;
    }

    public static void instantiate(RMI facade) {
        if (ourInstance == null) {
            ourInstance = facade;
        }
    }
}