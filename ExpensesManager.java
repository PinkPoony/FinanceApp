package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для управления расходами.
 * Содержит методы для добавления, удаления и анализа расходов.
 */
public class ExpensesManager {

    private static final Logger logger = LogManager.getLogger(ExpensesManager.class);

    // Мапа для хранения трат (категория -> сумма)
    private Map<String, Double> expenses = new HashMap<>();

    /**
     * Сохраняет расход в указанной категории.
     *
     * @param money Сумма денег, доступных для расхода.
     * @param expense Сумма расхода.
     * @param category Категория, в которой будет сохранён расход.
     * @return Оставшаяся сумма денег после вычета расхода.
     */
    public double saveExpense(double money, double expense, String category) {
        expenses.put(category, expenses.getOrDefault(category, 0.0) + expense);
        logger.info("Добавлена трата: категория = " + category + ", сумма = " + expense);  // Логирование добавления трат
        return money - expense;
    }

    /**
     * Возвращает общую сумму всех расходов.
     *
     * @return Общая сумма расходов.
     */
    public double getExpensesSum() {
        double sum = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        logger.info("Общая сумма расходов: " + sum);  // Логирование общей суммы расходов
        return sum;
    }

    /**
     * Находит максимальную трату в указанной категории.
     *
     * @param category Категория, для которой нужно найти максимальный расход.
     * @return Максимальная сумма расхода в данной категории.
     */
    public double findMaxExpenseInCategory(String category) {
        double maxExpense = expenses.getOrDefault(category, 0.0);
        logger.info("Максимальная трата в категории " + category + ": " + maxExpense);  // Логирование максимальной траты
        return maxExpense;
    }

    /**
     * Удаляет все расходы.
     */
    public void removeAllExpenses() {
        expenses.clear();
        logger.info("Все траты удалены!");  // Логирование удаления всех трат
    }

    /**
     * Удаляет расходы в указанной категории.
     *
     * @param category Категория, для которой нужно удалить все расходы.
     */
    public void removeCategory(String category) {
        expenses.remove(category);
        logger.info("Категория " + category + " удалена!");  // Логирование удаления категории
    }

    /**
     * Находит категорию с наибольшими расходами.
     *
     * @return Название категории с наибольшими расходами.
     */
    public String getMaxCategoryName() {
        String maxCategory = expenses.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Нет трат");
        logger.info("Категория с наибольшими расходами: " + maxCategory);  // Логирование категории с максимальными расходами
        return maxCategory;
    }

    /**
     * Выводит процентное соотношение каждой категории от общей суммы расходов.
     */
    public void printPercentageByCategory() {
        double total = getExpensesSum();
        if (total == 0) {
            System.out.println("Нет трат для анализа.");
            logger.warn("Нет трат для анализа!");  // Логирование предупреждения, если нет данных
            return;
        }

        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            double percentage = (entry.getValue() / total) * 100;
            System.out.printf("Категория: %s, Процент: %.2f%%%n", entry.getKey(), percentage);
            logger.info("Процент для категории " + entry.getKey() + ": " + percentage + "%");  // Логирование процентов по категориям
        }
    }

    /**
     * Возвращает все текущие расходы.
     *
     * @return Карта, содержащая категории и соответствующие суммы расходов.
     */
    public Map<String, Double> getExpenses() {
        return expenses;
    }
}
