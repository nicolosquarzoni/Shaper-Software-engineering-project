package demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String goal;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Professional professional;
    @OneToMany
    private List<Workout> workouts;

    protected WorkoutPlan() {}

    public WorkoutPlan(Patient patient, Professional professional, String title, String goal ) {
        this.patient = patient;
        this.professional = professional;
        this.workouts = new ArrayList<Workout>();
        this.title = title;
        this.goal = goal;


    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getGoal() {
        return goal;
    }

    public Professional getProfessional() {
        return professional;
    }

    public Patient getPatient() {
        return patient;
    }

    public void addPatient(Patient patient) {
        this.patient = patient;
    }

    public void addProfessional (Professional professional) {
        this.professional = professional;
    }

    public void addWorkout (Workout workout) {
        this.workouts.add(workout);
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void updateWorkout(Workout workout) {
        int a = 0;
        for (Workout w : this.workouts) {
            if (w.getId() == workout.getId()) {
                this.workouts.set(this.workouts.indexOf(w), workout);
                a = 1;
            }
        }
        if (workout != null && a != 1){
            this.workouts.add(workout);
        }
    }
}
