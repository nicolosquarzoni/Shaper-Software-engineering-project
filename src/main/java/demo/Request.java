package demo;

import javax.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String services;
    private String message;
    private String date;
    @ManyToOne
    private Professional professional;
    @ManyToOne
    private Patient patient;
    private boolean accepted;

    protected Request() {}

    public Request(Patient patient, String services, String message, Professional professional) {
        this.services = services;
        this.patient = patient;
        this.professional = professional;
        this.message = message;
        this.date = java.time.LocalDate.now().toString();
        this.accepted = false;

    }

    public Long getId() {
        return id;
    }

    public String getServices() {
        return services;
    }

    public Patient getPatient() {
        return patient;
    }

    public Professional getProfessional() {
        return professional;
    }

    public String getMessage() {
        return message;
    }

    public void setAccepted(boolean accepted) {

         this.accepted = accepted;

        this.accepted = accepted;

    }
}
