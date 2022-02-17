package demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class Professional extends Person {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String profession;
    private String services;
    //certificates pdf
    @ManyToMany
    private List<Patient> patients;
    @OneToMany
    private List<Questionnaire> questionnaires;
    private HashMap<Long, Integer> reviews;
    @OneToMany
    private List<Diet> diets;
    @OneToMany
    private List<WorkoutPlan> workoutPlans;



    protected Professional() {}

    public Professional(String firstName, String lastName, String dateofb, String userName,
                        String password, String profession, String services ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateofb = dateofb;
        this.userName = userName;
        this.password = password;
        this.profession = profession;
        this.services = services;
        this.patients = new ArrayList<Patient>();
        this.questionnaires = new ArrayList<Questionnaire>();
        this.reviews = new HashMap<Long, Integer>();
        this.diets = new ArrayList<Diet>();
        this.workoutPlans = new ArrayList<WorkoutPlan>();

    }


    public Long getId() {
        return id;
    }



    public void addReview(long patientid, Integer rate) {
        this.reviews.put(patientid, rate);
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

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public void updateProfession(String profession) {
        this.profession = profession;
    }

    public void updateServices(String services) {
        this.services = services;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getServices() {
        return services;
    }

    public String getProfession() {
        return profession;
    }

    public List<Diet> getDiets(){
        return diets;
    }

    public List<Patient> getPatients() {return patients; }

    public String getDateofBirth() { return dateofb;}

    public List<WorkoutPlan> getWorkoutPlans(){
        return workoutPlans;
    }

    public HashMap<Long, Integer> getReviews() {return reviews; }

    public int getRate(){
        Integer sum = 0;
        int count = 0;
        for (Integer value : reviews.values()) {
            sum = sum + value;
            count++;
        }
        if (count == 0){
            return count;
        }
        else {
            int avg = sum/count;
            return avg;
        }

    }
}
