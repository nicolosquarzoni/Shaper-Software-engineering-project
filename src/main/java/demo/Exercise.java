package demo;

import javax.persistence.*;

@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String details;
    @ManyToOne
    private Workout workout;

    protected Exercise() {}

    public Exercise(Workout workout, String title, String description, String details) {
        this.workout = workout;
        this.title = title;
        this.description = description;
        this.details = details;

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public void editDetails(String details) {
        this.details = details;
    }

    public void updateExercise(String title, String description, String details) {
        this.title = title;
        this.description = description;
        this.details = details;
    }



}
