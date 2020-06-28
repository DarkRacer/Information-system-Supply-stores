package Invite;

public class Invite {
    private int id;
    private String col;
    private float sum;
    private String date;
    private String status;
    private String market;
    private String tov;
    private String delivery;

    public Invite (int _id, String _col, float _sum, String _date, String _status, String _market, String _tov, String _delivery){
        this.id = _id;
        this.col = _col;
        this.sum = _sum;
        this.date = _date;
        this.status = _status;
        this.market = _market;
        this.tov = _tov;
        this.delivery = _delivery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getTov() {
        return tov;
    }

    public void setTov(String tov) {
        this.tov = tov;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
