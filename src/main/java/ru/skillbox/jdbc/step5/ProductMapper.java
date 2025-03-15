package ru.skillbox.jdbc.step5;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skillbox.jdbc.step4.model.Product;
import ru.skillbox.jdbc.step5.dto.StoreElementDto;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Код к шагу 5 (MapStruct)
 */
@Mapper
public interface ProductMapper {
    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    //@Mapping(target = "priceInRoubles", source = "price")
    StoreElementDto toDto(Product product);

    @Mapping(target = "id", expression = "java(resultSet.getLong(\"id\"))")
    @Mapping(target = "name", expression = "java(resultSet.getString(\"name\"))")
    @Mapping(target = "price", expression = "java(resultSet.getLong(\"price\"))")
    Product toProduct(ResultSet resultSet) throws SQLException;
}
