package com.ra2.users.com_ra2_users.repository;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ra2.users.com_ra2_users.model.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();

            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setDescription(rs.getString("description"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setUltimAcces(rs.getTimestamp("ultimAcces"));
            user.setDataCreated(rs.getTimestamp("dataCreated"));
            user.setDataUpdated(rs.getTimestamp("dataUpdated"));

            return user;
        }
    }


    public int save(String name, String description, String email, String password, Timestamp ultimAcces, Timestamp dataCreated, Timestamp dataUpdated) {
        String sql = "INSERT INTO users (name, description, email, password, ultimAcces, dataCreated, dataUpdated) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, name, description, email, password, ultimAcces, dataCreated, dataUpdated);
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public User findOne(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int modifyUser(User user, Long id) {
        String sql = "UPDATE users SET name = ?, description = ?, email = ?, password = ?, dataUpdated = ? WHERE id = ?";

        return jdbcTemplate.update(sql, user.getName(), user.getDescription(), user.getEmail(), user.getPassword(), LocalDateTime.now(), id);
    }

    public int deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
