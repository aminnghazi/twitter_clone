package models;

public class Card {
    String name;
    String id;
    String image;

    public Card(String name, String id, String image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
