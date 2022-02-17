package demo;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UnitTests {
    @Test
    public void patientTest(){
        Patient p = new Patient("Paziente", "1", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        Professional pr = new Professional("Professionista", "1", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        assertEquals("Paziente", p.getFirstName());
        assertEquals("1", p.getLastName());

        p.addWeight(new String[]{"06-01-2022", "71"});
        assertEquals(new String[]{"06-02-2022", "81"}, p.getWeight().get(0));
        assertEquals(new String[]{"06-01-2022", "71"}, p.getWeight().get(1));
        assertEquals("10-02-1987", p.getDateofBirth());
        p.setDateOfBirth("11-02-1987");
        assertEquals("11-02-1987", p.getDateofBirth());
        assertEquals("paziente1", p.getUserName());
        p.setUserName("Paziente1");
        assertEquals("Paziente1", p.getUserName());
        assertEquals("1234", p.getPassword());
        p.setPassword("12345");
        assertEquals("12345", p.getPassword());
        p.addProfessional(pr);
        assertEquals(pr, p.getProfessionals().get(0));
        p.setFirstName("paziente");
        p.setLastName("0");
        assertEquals("paziente", p.getFirstName());
        assertEquals("0", p.getLastName());


    }

    @Test
    public void professionalTest(){
        Professional p = new Professional("Professionista", "1", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        Patient pt = new Patient("Paziente", "1", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        assertEquals("Professionista", p.getFirstName());
        assertEquals("1", p.getLastName());
        assertEquals("10-02-1987", p.getDateofBirth());
        p.setDateOfBirth("11-02-1987");
        assertEquals("11-02-1987", p.getDateofBirth());
        assertEquals("prof1", p.getUserName());
        p.setUserName("Prof1");
        assertEquals("Prof1", p.getUserName());
        assertEquals("1234", p.getPassword());
        p.setPassword("12345");
        assertEquals("12345", p.getPassword());
        assertEquals("Personal Trainer", p.getProfession());
        p.setProfession("Dietologo");
        assertEquals("Dietologo", p.getProfession());
        assertEquals("workout, diete", p.getServices());
        p.setServices("workout, diete and mental coaching");
        assertEquals("workout, diete and mental coaching", p.getServices());
        p.addPatient(pt);
        assertEquals(pt, p.getPatients().get(0));
        p.setFirstName("professionista");
        p.setLastName("0");
        assertEquals("professionista", p.getFirstName());
        assertEquals("0", p.getLastName());
        p.addReview(0, 5);
        p.addReview(1, 3);
        assertEquals(4, p.getRate());
    }

    @Test
    public void dietTest(){
        Professional pr = new Professional("Professionista", "1", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        Patient p = new Patient("Paziente", "1", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        Diet d = new Diet(p,pr, "dieta di paziente 1", "perdita peso");
        assertEquals("dieta di paziente 1", d.getTitle());
        d.setTitle("no dieta");
        assertEquals("no dieta", d.getTitle());
        assertEquals("perdita peso", d.getGoal());
        d.setGoal("nessuno");
        assertEquals("nessuno", d.getGoal());
        assertEquals(p, d.getPatient());
        Patient p2 = new Patient("Paziente", "2", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        d.addPatient(p2);
        assertEquals(p2, d.getPatient());
        assertEquals(pr, d.getProfessional());
        Professional pr2 = new Professional("Professionista", "2", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        d.addProfessional(pr2);
        assertEquals(pr2, d.getProfessional());
        Option o1 = new Option(d, "Giorno on");
        Option o2 = new Option(d, "Giorno off");
        d.addOption(o1);
        d.addOption(o2);
        o1.addMeal(new String[]{"Colazione", "Yogurt greco"});
        o1.addMeal(new String[]{"Pranzo", "Salmone e riso"});
        o2.addMeal(new String[]{"Spuntino", "mela e mandorle"});
        o2.addMeal(new String[]{"Cena", "pesce"});
        assertEquals(d, o1.getDiet());
        assertEquals("Giorno on", d.getOptions().get(0).getTitle());
        o1.setTitle("Giorno");
        d.updateOption(o1);
        assertEquals("Giorno", d.getOptions().get(0).getTitle());
        p.addDiet(d);
        assertEquals(d, p.getDiets().get(0));

    }

    @Test
    public void workoutplanTest(){
        Professional pr = new Professional("Professionista", "1", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        Patient p = new Patient("Paziente", "1", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        WorkoutPlan wp = new WorkoutPlan(p,pr, "allenamento di paziente 1", "perdita peso");
        assertEquals("allenamento di paziente 1", wp.getTitle());
        wp.setTitle("no allenamento");
        assertEquals("no allenamento", wp.getTitle());
        assertEquals("perdita peso", wp.getGoal());
        wp.setGoal("nessuno");
        assertEquals("nessuno", wp.getGoal());
        assertEquals(p, wp.getPatient());
        Patient p2 = new Patient("Paziente", "2", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        wp.addPatient(p2);
        assertEquals(p2, wp.getPatient());
        assertEquals(pr, wp.getProfessional());
        Professional pr2 = new Professional("Professionista", "2", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        wp.addProfessional(pr2);
        assertEquals(pr2, wp.getProfessional());
        Workout w1 = new Workout(wp, "Gambe");
        Workout w2 = new Workout(wp, "Schiena");
        wp.addWorkout(w1);
        wp.addWorkout(w2);
        Exercise e1 = new Exercise(w1, "panca piana", "---", "...");
        Exercise e2 = new Exercise(w1, "manubri", "---", "...");
        Exercise e3 = new Exercise(w2, "panca inclinata", "---", "...");
        Exercise e4 = new Exercise(w2, "croci", "---", "...");
        assertEquals("panca piana", e1.getTitle());
        assertEquals("---", e1.getDescription());
        assertEquals("...", e1.getDetails());
        e4.editTitle("nulla");
        e4.editDetails("....");
        assertEquals("nulla", e4.getTitle());
        assertEquals("....", e4.getDetails());
        e4.updateExercise("croci", "----", "...");
        assertEquals("croci", e4.getTitle());
        assertEquals("----", e4.getDescription());
        assertEquals("...", e4.getDetails());
        assertEquals(wp, w1.getWorkoutPlan());
        assertEquals("Gambe", wp.getWorkouts().get(0).getTitle());
        w1.setTitle("Braccia");
        wp.updateWorkout(w1);
        assertEquals("Braccia", wp.getWorkouts().get(0).getTitle());
        w1.addExercise(e1);
        w1.addExercise(e2);
        assertEquals("manubri", w1.getExercises().get(1).getTitle());
    }


    @Test
    public void requestandquestionnaireTest(){
        Professional p = new Professional("Professionista", "1", "10-02-1987",
                "prof1", "1234", "Personal Trainer", "workout, diete" );
        Patient pt = new Patient("Paziente", "1", new String[]{"06-02-2022", "81"}, "10-02-1987",
                "paziente1", "1234" );
        Request r = new Request(pt, "dieta", "ciao", p);
        assertEquals(pt, r.getPatient());
        assertEquals("dieta", r.getServices());
        assertEquals("ciao", r.getMessage());
        assertEquals(p, r.getProfessional());
        r.setAccepted(true);
        Questionnaire q = new Questionnaire(pt, p);
        q.addQuestion("1?");
        q.addAnswer("1!");
        assertEquals("1?", q.getQuestions().get(1));
        assertEquals("1!", q.getAnswers().get(1));
        ArrayList<String> Q = new ArrayList<>();
        ArrayList<String> A = new ArrayList<>();
        Q.add("2?");
        A.add("2!");
        q.setAnswers(A);
        q.setQuestions(Q);
        assertEquals("2?", q.getQuestions().get(0));
        assertEquals("2!", q.getAnswers().get(0));
        assertEquals(false, q.isAnswered());
        q.setAnswered(true);
        assertEquals(true, q.isAnswered());
    }

}





