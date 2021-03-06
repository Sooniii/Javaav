package groupe3.Javaav.dao;

import groupe3.Javaav.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;

@Repository
public class CategoryDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> listAll() {
        String sql = "SELECT * FROM categories";
        List<Category> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Category.class));

        return list;
    }

    public List<Category> paginatedList(String range) {
        String[] value = range.split("-");
        String sql = "SELECT * FROM categories LIMIT ? OFFSET ?";
        int limit = Integer.parseInt(value[1]);
        int offset = Integer.parseInt(value[0]);
        List<Category> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Category.class), limit, offset);
        return list;
    }

    public int add(Category c) {
        String sql = "INSERT INTO categories (name) VALUES (?);";
        return jdbcTemplate.update(sql, c.getName());
    }

    public Category findById(Long categoryId) {
        String sql = "SELECT * FROM categories WHERE id=?";
        Category category = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Category.class), categoryId);

        return category;
    }
    public int delete(Long categoryId){
        String sql = "DELETE FROM categories WHERE id=?";
        return jdbcTemplate.update(sql, categoryId);
    }

    public int update(Category c, Long categoryId) {
        String sql = "UPDATE categories SET name=? WHERE id=?";
        return jdbcTemplate.update(sql, c.getName(), categoryId);
    }
}
