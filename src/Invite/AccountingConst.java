package Invite;

public class AccountingConst {
    private String market;
    private String col;
    private float sum;

    public AccountingConst(String _market, String _col, float _sum){
        this.market = _market;
        this.col = _col;
        this.sum = _sum;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
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
}
