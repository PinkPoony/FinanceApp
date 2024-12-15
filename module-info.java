module org.example {
    // Требования для использования JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Требования для использования Apache POI
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    // Требования для использования Log4j
    requires org.apache.logging.log4j;
    requires java.logging;

    // Экспортируем пакеты для использования в других модулях
    exports org.example;
}
