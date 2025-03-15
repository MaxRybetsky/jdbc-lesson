package ru.skillbox.jdbc.step2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Код к шагу 2
 */
public class ProductsRepository {
    public static void main(String[] args) throws SQLException {
        // Параметры соединения
        // URL Базы Данных
        String url = "jdbc:postgresql://localhost:5433/postgres";

        // Свойства подключения (credentials)
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Создаем соединение к БД, используя урл и свойства
            connection = DriverManager.getConnection(url, props);

            // Создаем объект Statement для дальнейшего выполнения запроса.
            statement = connection.createStatement();

            // Выполняем запрос и получаем результат в виде объекта ResultSet
            resultSet = statement.executeQuery("SELECT * FROM products");

            while (resultSet.next()) {
                System.out.printf("ID: %s; Название: %s; Цена: %d\n",
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price")
                );
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            // Закрываем ресурсы
            resultSet.close();
            statement.close();
            connection.close();
        }
    }
}
