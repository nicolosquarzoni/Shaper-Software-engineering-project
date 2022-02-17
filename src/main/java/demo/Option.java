package demo;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne
    private Diet diet;
    private ArrayList<String[]> meals;

    protected Option() {}

    public Option(Diet diet, String title) {
        this.diet = diet;
        this.meals = new ArrayList<String[]>();
        this.title = title;

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String[]> getMeals() {
        return meals;
    }

    public void addMeal(String[] meal) {
        this.meals.add(meal);
    }

    public void setMeals(ArrayList<String[]> meals) {
        this.meals = meals;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Diet getDiet() {
        return diet;
    }
}
