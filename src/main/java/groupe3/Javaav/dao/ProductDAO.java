package groupe3.Javaav.dao;

import groupe3.Javaav.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> listAll(){
        String sql = "SELECT * FROM products";

        List<Product> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));

        return list;
    }

    public int add(Product p) {
        String sql = "INSERT INTO products (name, type, rating) VALUES (?, ?, ?);";
        return jdbcTemplate.update(sql, p.getName(), p.getType(), p.getRating());
    }

    public Product findById(int productId){
        String sql = "SELECT * FROM products WHERE id=?";
        Product product = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Product.class), productId);

    return product;
    }
    public String delete(Long categoryId){
        String sql = "DELETE FROM products WHERE id=?";
        jdbcTemplate.update(sql, categoryId);
        return "DELETE FROM products WHERE id=" + categoryId;
    }
}