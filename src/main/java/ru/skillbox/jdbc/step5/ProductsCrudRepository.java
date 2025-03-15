package ru.skillbox.jdbc.step5;

import ru.skillbox.jdbc.step4.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.skillbox.jdbc.step5.ProductMapper.PRODUCT_MAPPER;

/**
 * Код к шагу 5 (MapStruct)
 */
public class ProductsCrudRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public boolean insertProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Получаем сгенерированный ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getLong(1));
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    // Метод для получения продукта по ID (Read)
    public Product getProductById(long id) throws SQLException {
        Product product = null;
        String sql = "SELECT id, name, price FROM products WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = PRODUCT_MAPPER.toProduct(resultSet);
                }
            }

        }

        return product;
    }

    // Метод для получения всех продуктов (Read)
    List<Product> getAllProducts() throws SQLException {
        final String sql = "SELECT * FROM products";
        final ArrayList<Product> products = new ArrayList<>();

        try (// Создаем соединение к БД, используя урл и логин/пароль
             Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             // Создаем объект Statement для дальнейшего выполнения запроса.
             Statement statement = connection.createStatement();

             // Выполняем запрос и получаем результат в виде объекта ResultSet
             ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                Product product = PRODUCT_MAPPER.toProduct(resultSet);
                products.add(product);
            }
        }

        return products;
    }


    // Метод для обновления существующего продукта (Update)
    public boolean updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        }
    }

    // Метод для удаления продукта по ID (Delete)
    public boolean deleteProduct(long id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;
        }
    }
}
