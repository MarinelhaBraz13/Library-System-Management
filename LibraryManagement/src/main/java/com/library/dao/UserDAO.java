package com.library.dao;
import com.library.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class UserDAO {
    public User login(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}