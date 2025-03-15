package ru.skillbox.jdbc.step5;

import ru.skillbox.jdbc.step4.model.Product;

import java.sql.SQLException;
import java.util.List;

import static ru.skillbox.jdbc.step5.ProductMapper.PRODUCT_MAPPER;

/**
 * Код к шагу 5 (MapStruct)
 */
public class StoreIntegration {
    public static void main(String[] args) throws SQLException {
        ProductsCrudRepository repository = new ProductsCrudRepository();

        List<Product> products = repository.getAllProducts();
        products.stream().map(PRODUCT_MAPPER::toDto)
                .forEach(System.out::println);
    }
}
