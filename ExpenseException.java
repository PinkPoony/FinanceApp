package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для пользовательского исключения, связанного с ошибками в расходах.
 * Используется для обработки специфичных ошибок, связанных с операциями с тратами.
 */
public class ExpenseException extends Exception {

    // Логгер для записи событий
    private static final Logger logger = LogManager.getLogger(ExpenseException.class);

    /**
     * Конструктор исключения, принимающий сообщение об ошибке.
     *
     * @param message Сообщение об ошибке.
     */
    public ExpenseException(String message) {
        super(message);
        logger.error("Произошла ошибка: " + message);  // Логирование ошибки
    }
}
