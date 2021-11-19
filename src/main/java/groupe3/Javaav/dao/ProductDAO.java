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

    public List<Product> listAll(){
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

    public Product findById(int productId){
        String sql = "SELECT * FROM products WHERE id=?";
        Product product = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Product.class), productId);
        return product;
    }
    public int delete(Long categoryId){
        String sql = "DELETE FROM products WHERE id=?";
        return jdbcTemplate.update(sql, categoryId);
    }

    public List<Product> sortByType(String types){
        List<Product> products = new ArrayList<>();
        String[] words = types.split("-");
        for (String type: words) {
            String sql = "SELECT * FROM products where type=?";
            products.addAll(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), type));
        }
        return products;
    }

    public List<Product> sortByRate(String rates) {
        List<Product> products = new ArrayList<>();
        String[] rateArray = rates.split("-");
        for (String rate : rateArray) {
            String sql = "SELECT * FROM products where rating=?";
            products.addAll(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), rate));
        }
        return products;
    }

    public List<Product> sortByDate(String createdAt){
        List<Product> products = new ArrayList<>();
        String[] words = createdAt.split(",");
        for (String type: words) {
            String sql = "SELECT * FROM products where createdAt=?";
            products.addAll(jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), createdAt));
        }
        return products;
    }

    public List<Product> dosomething(List<String> keys, List<String> values){
        List<Product> products = new ArrayList<>();
        List<Product> rateProducts = new ArrayList<>();
        List<Product> typeProducts = new ArrayList<>();
        List<Product> dateProducts = new ArrayList<>();

        if (keys.size()==1){
            switch (keys.get(0)){
                case "rate":
                    products.addAll(sortByRate(values.get(0)));
                    break;
                case "type":
                    products.addAll(sortByType(values.get(0)));
                    break;
                case "createdAt":
                    products.addAll(sortByDate(values.get(0)));
                    break;
            }
            return products;
        }else{
            int i = 0;
            for (String key:keys) {
                switch (key){
                    case "rate":
                        rateProducts.addAll(sortByRate(values.get(i)));
                        break;
                    case "type":
                        typeProducts.addAll(sortByType(values.get(i)));
                        break;
                    case "createdAt":
                        dateProducts.addAll(sortByDate(values.get(i)));
                        break;
                }
                i++;
            }
            return test(rateProducts, typeProducts, dateProducts);
        }

    }

    public List<Product> test(List<Product> firstTable, List<Product> secondTable, List<Product> thirdTable){
        List<Product> products = new ArrayList<>();

        if (firstTable.size()<1){
            for (Product secondProducts: secondTable) {
                for (Product thirdProducts : thirdTable) {
                    if (thirdProducts.getId().equals(secondProducts.getId())) {
                        products.add(secondProducts);
                    }
                }
            }
        }else if (thirdTable.size()<1){
            for (Product firstProducts: firstTable) {
                for (Product secondProducts : secondTable) {
                    if (firstProducts.getId().equals(secondProducts.getId())) {
                        products.add(firstProducts);
                    }
                }
            }
        }else if (secondTable.size()<1){

            for (Product firstProducts: firstTable) {
                for (Product thirdProducts : thirdTable) {
                    if (firstProducts.getId().equals(thirdProducts.getId())) {
                        products.add(firstProducts);
                    }
                }
            }

        }else{
            for (Product firstProducts: firstTable) {
                for (Product secondProducts: secondTable) {
                    for (Product thirdProducts: thirdTable) {

                        if (firstProducts.getId().equals(secondProducts.getId()) && secondProducts.getId().equals(thirdProducts.getId())){
                            products.add(firstProducts);
                        }
                    }

                }

            }
        }
        return products;
    }



    public List<Product> sorting(String asc , String desc){
        String sql = null;
        switch( asc ){
            case "rating":
                sql = descTString(desc, "SELECT * FROM products ORDER BY rating ");
                break;
            case "type":
                sql = descTString(desc, "SELECT * FROM products ORDER BY type ");
                break;
            case "name":
                sql = descTString(desc, "SELECT * FROM products ORDER BY name ");
                break;
        }
        List<Product> products = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class));
        return products;
    }


    public String descTString(String desc, String sqlB){
        switch (desc) {
            case "desc":
                System.out.println(sqlB + "DESC");
                return sqlB + "DESC";
            case "asc":
                System.out.println(sqlB + "ASC");
                return sqlB + "ASC";
        }
        return sqlB;
    }
}