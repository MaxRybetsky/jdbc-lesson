package ru.skillbox.jdbc.step5;

import ru.skillbox.jdbc.step4.model.Product;
import ru.skillbox.jdbc.step5.dto.StoreElementDto;

import java.sql.SQLException;
import java.util.List;

import static ru.skillbox.jdbc.step5.ProductMapper.PRODUCT_MAPPER;

/**
 * Код к шагу 5 (Без MapStruct)
 */
public class StoreIntegrationWithManualMapping {
    public static void main(String[] args) throws SQLException {
        ProductsCrudRepository repository = new ProductsCrudRepository();

        List<Product> products = repository.getAllProducts();
        products.stream().map(StoreIntegrationWithManualMapping::toDto)
                .forEach(System.out::println);
    }

    private static StoreElementDto toDto(Product product) {
        StoreElementDto storeElementDto = new StoreElementDto();

        storeElementDto.setName(product.getName());
        storeElementDto.setPriceInRoubles(product.getPrice());

        return storeElementDto;
    }
}
