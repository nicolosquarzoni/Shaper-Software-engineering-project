package demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    //public int order;
    @ManyToOne
    private WorkoutPlan workoutPlan;
    @OneToMany
    private List<Exercise> exercises;

    protected Workout() {}

    public Workout(WorkoutPlan workoutPlan, String title) {
        this.workoutPlan = workoutPlan;
        this.exercises = new ArrayList<Exercise>();
        this.title = title;

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public WorkoutPlan getWorkoutPlan() {
        return workoutPlan;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
