package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Класс для экспорта данных о тратах в Excel файл.
 * Использует библиотеку Apache POI для создания и записи в Excel файл.
 */
public class ExcelExporter {

    private static final Logger logger = LogManager.getLogger(ExcelExporter.class);

    /**
     * Экспортирует данные о тратах в Excel файл на рабочем столе.
     *
     * @param expenses Список трат (категории и суммы).
     * @param filePath Имя файла для сохранения.
     */
    public void exportExpensesToExcel(Map<String, Double> expenses, String filePath) throws IOException {
        // Создание книги
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Траты");

        // Добавление строки заголовка
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Категория");
        headerRow.createCell(1).setCellValue("Сумма");

        // Заполнение данными из Map
        int rowNum = 1;
        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        // Автоматическая настройка ширины столбцов
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // Сохранение файла
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }
}

