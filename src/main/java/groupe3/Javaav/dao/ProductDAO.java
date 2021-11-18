package groupe3.Javaav.dao;

import groupe3.Javaav.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> listAll() {
        String sql = "SELECT * FROM products";
        List<Product> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
        return list;
    }

    public List<Product> paginatedList(String range) {
        String[] value = range.split("-");
        String sql = "SELECT * FROM products LIMIT ? OFFSET ?";
        int limit = Integer.parseInt(value[1]);
        int offset = Integer.parseInt(value[0]);
        List<Product> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), limit, offset);
        return list;
    }

    public int add(Product p) {
        String sql = "INSERT INTO products (name, type, rating) VALUES (?, ?, ?);";
        return jdbcTemplate.update(sql, p.getName(), p.getType(), p.getRating());
    }

    public Product findById(int productId) {
        String sql = "SELECT * FROM products WHERE id=?";
        Product product = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Product.class), productId);
        return product;
    }

    public int delete(Long categoryId) {
        String sql = "DELETE FROM products WHERE id=?";
        return jdbcTemplate.update(sql, categoryId);
    }

    public List<Product> sortByType(String types) {
        List<Product> products = new ArrayList<>();
        String[] words = types.split(",");
        for (String type : words) {
            String sql = "SELECT * FROM products where type=?";
            products.addAll(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), type));
        }
        return products;
    }

    public List<Product> sortByRate(String rates) {
        List<Product> products = new ArrayList<>();
        String[] rateArray = rates.split(",");
        for (String rate : rateArray) {
            String sql = "SELECT * FROM products where rating=?";
            products.addAll(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), rate));
        }
        return products;
    }

    public List<Product> sortByDate(String createdAt) {
        List<Product> products = new ArrayList<>();
        String[] words = createdAt.split(",");
        for (String type : words) {
            String sql = "SELECT * FROM products where createdAt=?";
            products.addAll(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), createdAt));
        }
        return products;
    }
}