package con_con;

public class memberDTO {
    private String user_num;
    private String user_name;
    private String decision;
    private String reg_date;

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getPostdate() {
        return reg_date;
    }

    public void setPostdate(String postdate) {
        this.reg_date = postdate;
    }

}