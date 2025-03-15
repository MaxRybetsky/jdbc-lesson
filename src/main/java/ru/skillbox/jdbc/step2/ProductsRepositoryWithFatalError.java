package ru.skillbox.jdbc.step2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Код к шагу 2.4 - пример ошибки при незакрытии ресурсов
 */
public class ProductsRepositoryWithFatalError {
    public static void main(String[] args) throws SQLException {
        while(true) {
            // Параметры соединения
            // URL Базы Данных
            String url = "jdbc:postgresql://localhost:5433/postgres";

            // Свойства подключения (credentials)
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");

            // Создаем соединение к БД, используя урл и свойства
            Connection conn = DriverManager.getConnection(url, props);

            // Создаем объект Statement для дальнейшего выполнения запроса.
            Statement st = conn.createStatement();

            // Выполняем запрос и получаем результат в виде объекта ResultSet
            ResultSet rs = st.executeQuery("SELECT * FROM products");

            while (rs.next()) {
                System.out.printf("ID: %s; Название: %s; Цена: %d\n",
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getLong("price")
                );
            }
        }

        // Ресурсы не закрываем
    }
}
