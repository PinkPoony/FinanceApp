package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * Основное приложение для управления личными тратами.
 * Обрабатывает ввод данных, отображение статистики и экспорт в Excel.
 */
public class ExpensesApp extends Application {

    private static final Logger logger = LogManager.getLogger(ExpensesApp.class);

    private ExpensesManager expensesManager = new ExpensesManager();
    private TextArea outputArea = new TextArea();

    /**
     * Метод для инициализации интерфейса приложения и обработки пользовательских событий.
     *
     * @param primaryStage Основная сцена для отображения интерфейса.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Менеджер Трат");

        // Поля ввода
        TextField amountField = new TextField();
        amountField.setPromptText("Введите сумму");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Введите категорию");

        TextField fileNameField = new TextField();
        fileNameField.setPromptText("Введите имя файла (например, расходы.xlsx)");

        // Кнопки
        Button addExpenseButton = new Button("Добавить Трату");
        addExpenseButton.setPrefWidth(150);

        Button showExpensesButton = new Button("Показать Траты");
        showExpensesButton.setPrefWidth(150);

        Button maxExpenseButton = new Button("Макс. Трата в Категории");
        maxExpenseButton.setPrefWidth(200);

        Button clearExpensesButton = new Button("Очистить Все");
        clearExpensesButton.setPrefWidth(150);

        Button removeCategoryButton = new Button("Удалить Категорию");
        removeCategoryButton.setPrefWidth(200);

        Button exportToExcelButton = new Button("Экспорт в Excel");
        exportToExcelButton.setPrefWidth(150);

        // Кнопка для расчета процента по категориям
        Button percentageButton = new Button("Процент по категориям");
        percentageButton.setPrefWidth(200);

        // Вывод сообщений
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Обработка событий
        addExpenseButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryField.getText();
                expensesManager.saveExpense(0, amount, category);
                outputArea.appendText("Добавлено: " + amount + " в категорию " + category + "\n");
                logger.info("Добавлена трата: " + amount + " в категорию " + category);  // Логирование
            } catch (NumberFormatException ex) {
                outputArea.appendText("Ошибка: Введите корректную сумму!\n");
                logger.error("Ошибка при вводе суммы", ex);  // Логирование ошибки
            }
        });

        showExpensesButton.setOnAction(e -> {
            outputArea.clear();
            Map<String, Double> expenses = expensesManager.getExpenses();
            if (expenses.isEmpty()) {
                outputArea.appendText("Трат пока нет.\n");
            } else {
                expenses.forEach((category, amount) ->
                        outputArea.appendText(category + ": " + amount + " руб.\n"));
            }
        });

        maxExpenseButton.setOnAction(e -> {
            String category = categoryField.getText();
            double maxExpense = expensesManager.findMaxExpenseInCategory(category);
            outputArea.appendText("Максимальная трата в категории " + category + ": " + maxExpense + " руб.\n");
        });

        clearExpensesButton.setOnAction(e -> {
            expensesManager.removeAllExpenses();
            outputArea.appendText("Все траты удалены.\n");
        });

        removeCategoryButton.setOnAction(e -> {
            String category = categoryField.getText();
            expensesManager.removeCategory(category);
            outputArea.appendText("Категория " + category + " удалена.\n");
        });

        exportToExcelButton.setOnAction(e -> {
            try {
                String fileName = fileNameField.getText();
                if (!fileName.isEmpty()) {
                    ExcelExporter exporter = new ExcelExporter();
                    exporter.exportExpensesToExcel(expensesManager.getExpenses(), fileName);
                    outputArea.appendText("Траты сохранены в файл: " + fileName + "\n");
                    logger.info("Файл экспортирован: " + fileName);  // Логирование
                } else {
                    outputArea.appendText("Введите корректное имя файла!\n");
                    logger.warn("Пользователь не указал имя файла для экспорта");  // Логирование
                }
            } catch (Exception ex) {
                outputArea.appendText("Ошибка: " + ex.getMessage() + "\n");
                logger.error("Ошибка при экспорте в Excel", ex);  // Логирование ошибки
            }
        });

        // Разметка
        VBox inputSection = new VBox(10, amountField, categoryField, fileNameField);
        inputSection.setPadding(new Insets(10));

        HBox buttonSection = new HBox(10, addExpenseButton, showExpensesButton, maxExpenseButton,
                clearExpensesButton, removeCategoryButton, exportToExcelButton, percentageButton);
        buttonSection.setPadding(new Insets(10));

        VBox layout = new VBox(10, inputSection, buttonSection, outputArea);

        // Сцена
        Scene scene = new Scene(layout, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
