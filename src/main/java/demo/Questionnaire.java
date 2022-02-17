package demo;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Questionnaire {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Professional professional;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private boolean answered;

    protected Questionnaire() {}

    public Questionnaire(Patient patient, Professional professional ) {
        this.patient = patient;
        this.professional = professional;
        this.questions = new ArrayList<String>();
        this.questions.add("");
        this.answers = new ArrayList<String>();
        this.answers.add("");
        this.answered = false;





    }


    public Long getId() {
        return id;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }

    public void addQuestion(String question) {
        this.questions.add(question);
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isAnswered() {
        return this.answered;
    }
}
