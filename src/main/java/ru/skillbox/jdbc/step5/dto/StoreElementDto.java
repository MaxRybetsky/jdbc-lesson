package ru.skillbox.jdbc.step5.dto;

/**
 * Код к шагу 5
 */
public class StoreElementDto {
    private String name;
    private long priceInRoubles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPriceInRoubles() {
        return priceInRoubles;
    }

    public void setPriceInRoubles(long priceInRoubles) {
        this.priceInRoubles = priceInRoubles;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\": \"" + name + "\", " +
                "\"priceInRoubles\": " + priceInRoubles +
                "}";
    }
}
