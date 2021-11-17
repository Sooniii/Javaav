package groupe3.Javaav.dao;

import groupe3.Javaav.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> listAll(){
        String sql = "SELECT * FROM categories";

        List<Category> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Category.class));

        return list;
    }

    public int add(Category c) {
        String sql = "INSERT INTO categories (name) VALUES (?);";
        return jdbcTemplate.update(sql, c.getName());
    }

    public Category findById(Long categoryId){
        String sql = "SELECT * FROM categories WHERE id=?";
        Category category = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Category.class), categoryId);

        return category;
    }
}