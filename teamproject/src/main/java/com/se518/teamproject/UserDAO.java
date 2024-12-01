package com.se518.teamproject;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    public void setDataSource(DataSource dataSource);
    public List<WebUser> getAllUsers();
    public WebUser addUser(WebUser webUser);
    public void addRole(String email);
    public WebUser registerUser(WebUser webUser);
    public WebUser getRegisteredUserInfoById(int id);
    public WebUser getUserByEmail(String email);
}
