package Login;

public class LoginConst {
    private int id;
    private String name;
    private String login;
    private String role;

    public LoginConst (int _id, String _name, String _login, String _role){
        this.id = _id;
        this.name = _name;
        this.login = _login;
        this.role = _role;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
