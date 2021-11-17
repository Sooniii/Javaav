package groupe3.Javaav.model.viewmodels;

import java.sql.Date;

public class ProductViewModel {
    private String type;
    private int rating;
    private String name;
    private Long categoryId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId(){ return categoryId; }

    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

}
