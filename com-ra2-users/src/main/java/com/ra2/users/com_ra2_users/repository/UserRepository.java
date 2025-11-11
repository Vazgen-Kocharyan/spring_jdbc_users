package com.ra2.users.com_ra2_users.repository;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ra2.users.com_ra2_users.model.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Classe interna que transforma les files del ResultSet en objectes User
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Crea un nou objecte User i assigna els valors obtinguts de la base de dades
            User user = new User();

            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setDescription(rs.getString("description"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setImage_path(rs.getString("image_path"));
            user.setUltimAcces(rs.getTimestamp("ultimAcces"));
            user.setDataCreated(rs.getTimestamp("dataCreated"));
            user.setDataUpdated(rs.getTimestamp("dataUpdated"));

            // Retorna l’objecte User complet
            return user;
        }
    }

    // ---------------------- Mètode per inserir un nou usuari ----------------------
    public int save(String name, String description, String email, String password, Timestamp ultimAcces, Timestamp dataCreated, Timestamp dataUpdated) {
        // Sentència SQL d’inserció
        String sql = "INSERT INTO users (name, description, email, password, ultimAcces, dataCreated, dataUpdated) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // Executa la consulta amb els valors passats per paràmetre
        return jdbcTemplate.update(sql, name, description, email, password, ultimAcces, dataCreated, dataUpdated);
    }

    // ---------------------- Mètode per obtenir tots els usuaris ----------------------
    public List<User> findAll() {
        // Executa la consulta i converteix cada fila en un objecte User mitjançant UserRowMapper
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // ---------------------- Mètode per obtenir un usuari per ID ----------------------
    public User findOne(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
       
        // Executa la consulta i guarda els resultats en una llista
        List<User> list = jdbcTemplate.query(sql, new UserRowMapper(), id);

        // Si la llista és buida, retorna null (usuari no trobat)
        if (list.isEmpty()) return null;

        // Retorna el primer (i únic) element de la llista
        return list.get(0);
    }

    // ---------------------- Mètode per modificar un usuari existent ----------------------
    public int modifyUser(User user, Long id) {
        // Sentència SQL d’actualització
        String sql = "UPDATE users SET name = ?, description = ?, email = ?, password = ?, dataUpdated = ? WHERE id = ?";

        // Actualitza el registre amb els nous valors i posa la data actual com a dataUpdated
        return jdbcTemplate.update(sql, user.getName(), user.getDescription(), user.getEmail(), user.getPassword(), LocalDateTime.now(), id);
    }

    // ---------------------- Mètode per eliminar un usuari ----------------------
    public int deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        // Elimina l’usuari amb l’ID indicat
        return jdbcTemplate.update(sql, id);
    }

    public int saveImagePath(String path, Long id) {
        String sql = "UPDATE users SET image_path = ? WHERE id = ?";

        return jdbcTemplate.update(sql, path, id);
    }
}
