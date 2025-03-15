package ru.skillbox.jdbc.step4;

import ru.skillbox.jdbc.step4.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Код к шагу 4.1 (добавление модели данных)
 */
public class ProductsRepositoryWithModel {
    public static void main(String[] args) throws SQLException {
        List<Product> products = findAll();

        products.forEach(System.out::println);
    }

    private static List<Product> findAll() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        // Параметры соединения
        // URL Базы Данных
        String url = "jdbc:postgresql://localhost:5433/postgres";

        // Свойства подключения (credentials)
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");

        try (// Создаем соединение к БД, используя урл и свойства
             Connection connection = DriverManager.getConnection(url, props);

             // Создаем объект Statement для дальнейшего выполнения запроса.
             Statement statement = connection.createStatement();

             // Выполняем запрос и получаем результат в виде объекта ResultSet
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")
        ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long price = resultSet.getLong("price");

                Product product = new Product(id, name, price);
                products.add(product);
            }
        }

        return products;
    }
}
