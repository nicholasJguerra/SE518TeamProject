package com.se518.teamproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AppService {
    @Qualifier("userDAOImpl")
    @Autowired
    private UserDAO userDAO;

    public WebUser getRegisteredUserInfoById(int id) {
        return userDAO.getRegisteredUserInfoById(id);
    }

    public WebUser registerUser(WebUser webUser) {
        return userDAO.registerUser(webUser);
    }

    public List<WebUser> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public WebUser getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}
