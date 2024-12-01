package com.se518.teamproject;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


public class WebUser {
    private Integer id;
    private String email;
    private String password;
    private String confPassword;
    private Boolean active;
    private String first;
    private String last;
    
    public WebUser(Integer id, String email, String password, String confPassword, Boolean active,  String first, String last){
        this.id = id;
        this.email = email;
        this.password = password;
        this.confPassword = confPassword;
        this.active = active;
        this.first = first;
        this.last = last;

    }

    public WebUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confPassword='" + confPassword + '\'' +
                ", active=" + active +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}
