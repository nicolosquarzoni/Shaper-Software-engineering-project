package demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient extends Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private ArrayList<String[]> weight;
    //private body photos;
    //private Map<String, String> fat;...
    @ManyToMany
    private List<Professional> professionals;
    @OneToMany
    private List<Questionnaire> questionnaire;
    @OneToMany
    private List<Diet> diets;
    @OneToMany
    private List<WorkoutPlan> workoutPlans;

    protected Patient() {}

    public Patient(String firstName, String lastName, String[] weight, String dateofb, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofb = dateofb;
        this.userName = userName;
        this.password = password;
        this.weight = new ArrayList<String[]>();
        this.weight.add(weight);
        this.professionals = new ArrayList<Professional>();
        this.questionnaire = new ArrayList<Questionnaire>();
        this.diets = new ArrayList<Diet>();
        this.workoutPlans = new ArrayList<WorkoutPlan>();

    }


    public Long getId() {
        return id;
    }

    public void addProfessional (Professional professional) {
        this.professionals.add(professional);
    }

    public void addDiet (Diet diet) {
        this.diets.add(diet);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateofBirth() {
        return dateofb;
    }

    @Override
    public String getUserName() { return super.getUserName();}

    @Override
    public String getPassword() { return super.getPassword();}

    public ArrayList<String[]> getWeight() { return weight; }

    public List<Diet> getDiets(){
        return diets;
    }

    public List<WorkoutPlan> getWorkoutPlans(){
        return workoutPlans;
    }

    public List<Professional> getProfessionals(){
        return professionals;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateofb = dateOfBirth;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addWeight(String[] weight) {
        this.weight.add(weight);
    }
}
