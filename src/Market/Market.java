package Market;

public class Market {
    private int id;
    private String name;
    private String requisites;

    public Market (int id, String name, String requisites){
        this.id = id;
        this.name = name;
        this.requisites = requisites;
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

    public String getRequisites() {
        return requisites;
    }

    public void setRequisites(String requisites) {
        this.requisites = requisites;
    }
}
