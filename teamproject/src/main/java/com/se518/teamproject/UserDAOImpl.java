package com.se518.teamproject;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class UserDAOImpl implements UserDAO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<WebUser> getAllUsers() {
        String SQL = "SELECT id, email, password, active, first, last " +
                "FROM UserAcct ";
        List<WebUser> users = jdbcTemplate.query(SQL, new WebUserMapper());
        return users;
    }

    @Override
    public WebUser addUser(WebUser webUser) {
        String SQL = "insert into UserAcct (email, password, active, first, last) values (?, ?, true, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(dataSource -> {
            PreparedStatement ps = dataSource.prepareStatement(SQL,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, webUser.getEmail());
            ps.setString(2, webUser.getPassword());
            ps.setString(3, webUser.getFirst());
            ps.setString(4, webUser.getLast());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            webUser.setId((Integer) keyHolder.getKeys().get("id"));
        } else {
            logger.warn("No generated key returned for user.");
            webUser.setId(-1);
        }
        return webUser;
    }

    @Override
    public void addRole(String email) {
        String SQL = "insert into authority (email, role) values (?, 'ROLE_USER')";
        jdbcTemplate.update(SQL, email);
    }

    public WebUser registerUser(WebUser webUser) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            WebUser WU = addUser(webUser);
            webUser.setId(WU.getId());
            addRole(webUser.getEmail());
            // commit the transaction
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating user record, rolling back");
            transactionManager.rollback(status);
            throw e;
        }
        return webUser;
    }

    @Override
    public WebUser getRegisteredUserInfoById(int id) {
        String SQL = "SELECT id, email, password, active, first, last " +
                "FROM UserAcct " +
                "WHERE id = ?";

        return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new WebUserMapper());
    }

    @Override
    public WebUser getUserByEmail(String email) {
        String SQL = "SELECT * FROM useracct WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{email}, new WebUserMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    class WebUserMapper implements RowMapper<WebUser> {
        public WebUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new WebUser(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    null,
                    rs.getBoolean("active"),
                    rs.getString("first"),
                    rs.getString("last")
            );
        }
    }
}