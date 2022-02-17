package demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Diet {

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
    private List<Option> options;

    protected Diet() {}

    public Diet(Patient patient, Professional professional, String title, String goal ) {
        this.patient = patient;
        this.professional = professional;
        this.options = new ArrayList<Option>();
        this.title = title;
        this.goal = goal;
    }


    public Long getId() {
        return id;
    }

    public void addPatient(Patient patient) {
        this.patient = patient;
    }

    public void addProfessional (Professional professional) {
        this.professional = professional;
    }

    public void addOption (Option option) {
        this.options.add(option);
    }

    public void updateOption(Option option) {

    int a = 0;
        for (Option o : this.options) {
            if (o.getId() == option.getId()) {
                this.options.set(this.options.indexOf(o), option);

            a = 1;
            }
        }
        if (option != null && a != 1){
            this.options.add(option);
        }

    }

    public String getTitle(){
        return title;
    }

    public String getGoal(){
        return goal;
    }

    public Professional getProfessional(){
        return professional;
    }

    public Patient getPatient(){
        return patient;
    }

    public List<Option> getOptions() {
        return options;
    }
    public Option findOption(Long id){
        for (Option o : this.options){
            if (o.getId() == id){
                return o;

            }
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
