package Market;

public class MarketBase {
    private String market;
    private String product;
    private String kolTov;

    public MarketBase (String _market, String _product, String _kolTov){
        this.market = _market;
        this.product = _product;
        this.kolTov = _kolTov;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getKolTov() {
        return kolTov;
    }

    public void setKolTov(String kolTov) {
        this.kolTov = kolTov;
    }
}
