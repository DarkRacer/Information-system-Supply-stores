package Product;

public class Product {
    private int id;
    private String name;
    private String kolTov;
    private float price;

    public Product (int _id, String _name, String _kolTov, float _price){
        this.id = _id;
        this.name = _name;
        this.kolTov = _kolTov;
        this.price = _price;
    }

    public Product (int _id, String _name, String _kolTov){
        this.id = _id;
        this.name = _name;
        this.kolTov = _kolTov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKolTov() {
        return kolTov;
    }

    public void setKolTov(String kolTov) {
        this.kolTov = kolTov;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
