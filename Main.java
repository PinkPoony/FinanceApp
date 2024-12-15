package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Главный класс для запуска приложения.
 * Запускает приложение для управления личными тратами.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Запуск приложения");
        ExpensesApp.main(args);
    }
}
