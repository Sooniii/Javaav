package groupe3.Javaav.dao;

import groupe3.Javaav.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> listAll(){
        String sql = "SELECT * FROM products";

        List<User> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));

        return list;
    }

    public int add(Product product) {
        String sql = "INSERT INTO products (name, type, rating) VALUES (?, ?, ?);";
        return jdbcTemplate.update(sql, product.getName(), product.getType(), product.getRating());
    }
}