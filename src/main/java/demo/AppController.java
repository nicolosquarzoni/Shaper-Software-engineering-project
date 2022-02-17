package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private RequestRepository requestRepository;

    @RequestMapping("/") //home page with repository creation for tests
    public String homepage(){
        optionRepository.deleteAll();
        dietRepository.deleteAll();
        exerciseRepository.deleteAll();
        workoutRepository.deleteAll();
        workoutPlanRepository.deleteAll();
        professionalRepository.deleteAll();
        patientRepository.deleteAll();
        if(!professionalRepository.findByUserName("Nicolo").isPresent()) {
            professionalRepository.save(new Professional("Nicolo", "Squarzoni", "10/04/1992", "userName",
                    "password", "Personal trainer", "diete, workouts"));
            professionalRepository.save(new Professional("Professional", "1", "10/09/1992", "Professional1",
                    "Professional1", "Dietologist", "dietee"));
            patientRepository.save(new Patient("Paziente", "1", new String[]{"07/01/2022", "82"}, "10/10/1987", "paziente1",
                    "paziente1"));
            patientRepository.save(new Patient("Paziente", "2", new String[]{"11/01/2022", "70"}, "01/12/1967", "paziente2",
                    "paziente2"));
            Professional a = professionalRepository.findAll().iterator().next();
            a.addPatient(patientRepository.findAll().iterator().next());
            Patient b = patientRepository.findAll().iterator().next();
            b.addProfessional(professionalRepository.findAll().iterator().next());
            a.addReview(b.getId(), 5);
            patientRepository.save(b);
            professionalRepository.save(a);
            dietRepository.save(new Diet(b, a, new String("Dieta di Paziente"), new String("Aumento massa muscolare")));
            Diet c = dietRepository.findAll().iterator().next();
            optionRepository.save(new Option(c, new String("Giorno on")));
            optionRepository.save(new Option(c, new String("Giorno off")));
            optionRepository.save(new Option(c, new String("Giorno e basta")));
            for (Option o : optionRepository.findAll()) {
                o.addMeal(new String[]{"Colazione", "Yogurt greco"});
                o.addMeal(new String[]{"Pranzo", "Salmone e riso"});
                o.addMeal(new String[]{"Spuntino", "mela e mandorle"});
                c.addOption(o);
                optionRepository.save(o);
            }
            c.addPatient(b);
            c.addProfessional(a);
            dietRepository.save(c);
            workoutPlanRepository.save(new WorkoutPlan(b, a, new String("Allenamnto di Paziente"), new String("Aumento massa muscolare")));
            WorkoutPlan e = workoutPlanRepository.findAll().iterator().next();
            workoutRepository.save(new Workout(e, new String("Giorno 1")));
            Workout f = workoutRepository.findAll().iterator().next();
            exerciseRepository.save(new Exercise(f, new String("Petto"), new String("**PPPP***"), new String("AAA")));
            Exercise g = exerciseRepository.findAll().iterator().next();
            f.addExercise(g);
            e.addPatient(b);
            e.addProfessional(a);
            e.addWorkout(f);
            workoutPlanRepository.save(e);
            workoutRepository.save(f);
            patientRepository.save(b);
        }
        return "homePage";
    }

    @RequestMapping("/home") //home page without repository creation
    public String home(){

        return "homePage";
    }

    @RequestMapping("/notfound")
    public String notfound(){
        return "notfound";
    }


    @RequestMapping("/signin") //sign in page. manages exeptions
    public String signin(
            @RequestParam(name="error", required=false) String error, Model model){
        if (error != null) {
            String errorr = error;
            model.addAttribute("error", errorr);
        }
        return "signin";
    }

    @RequestMapping("/accountlogin" ) //logs the user in and redirect to personal home page. manages exeptions
    public String accountlogin(
            @RequestParam(name="userName", required=false) String username,
            @RequestParam(name="password", required=false) String password,
            Model model, RedirectAttributes redirectAttributes){
        if (username != null && password != null){
            Optional<Professional> pr = professionalRepository.findByUserName(username);
            Optional<Patient> pt = patientRepository.findByUserName(username);
            if (pr.isPresent()){
                String pw = pr.get().getPassword();
                if (pw.equals(password)){
                    redirectAttributes.addAttribute("profid", pr.get().getId());
                    return "redirect:/profhome";
                }
                else {
                    redirectAttributes.addAttribute("error", "Username or Password not valid");
                    return "redirect:/signin";
                }

            }
            else if (pt.isPresent()){
                String pw = pt.get().getPassword();
                if (pw.equals(password)){
                    redirectAttributes.addAttribute("patientid", pt.get().getId());
                    return "redirect:/pathome";
                }
                else {
                    redirectAttributes.addAttribute("error", "Username or Password not valid");
                    return "redirect:/signin";
                }
            }
            else {
                redirectAttributes.addAttribute("error", "Username or Password not valid");
                return "redirect:/signin";
            }
        }
        else{
            redirectAttributes.addAttribute("error", "Username or Password not valid");
            return "redirect:/signin";

        }


    }

    @RequestMapping("/profhome") //home page for professionals
    public String profhome(
            @RequestParam(name="profid", required=true) Long profid, Model model){


            List<Request> requests = new LinkedList<>();
            List<Patient> patients = new LinkedList<>();
            Professional professional = professionalRepository.findById(profid).get();
            for (Request r: requestRepository.findByProfessionalAndAccepted(professional, false)){
            requests.add(r);

             }
            for (Patient pt: professional.getPatients()){
            patients.add(pt);

             }
            model.addAttribute("professional", professional);
            model.addAttribute("requests", requests);
            model.addAttribute("patients", patients);
            return "profhome";
    }


    @RequestMapping("/pathome") //home page for patients
    public String pathome(
            @RequestParam(name="patientid", required=true) long patientid, Model model){
        List<Diet> diets = new LinkedList<>();
        List<WorkoutPlan> workoutplans = new LinkedList<>();
        List<Professional> professionals = new LinkedList<>();
        List<Request> requests = new LinkedList<>();
        Patient patient = patientRepository.findById(patientid).get();
        for (Diet d: dietRepository.findByPatient(patient)){
            diets.add(d);
        }
        for (WorkoutPlan w: workoutPlanRepository.findByPatient(patient)){
            workoutplans.add(w);
        }
        for (Professional pr: patient.getProfessionals()){
            professionals.add(pr);

        }
        for (Request r: requestRepository.findByPatientAndAccepted(patient, false)){
            requests.add(r);

        }
        model.addAttribute("patient", patient);
        model.addAttribute("diets", diets);
        model.addAttribute("workoutplans", workoutplans );
        model.addAttribute("professionals", professionals );
        model.addAttribute("requests", requests);
        return "pathome";
    }

    @RequestMapping("/patientrequest") //creates a request to send to the professional by a patient
    public String patientrequest(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="profid", required=true) long profid,
            @RequestParam(name="services", required=false) String services,
            @RequestParam(name="message", required=false) String message, Model model, RedirectAttributes redirectAttributes){
        requestRepository.save(new Request(patientRepository.findById(patientid).get(), services, message,
                professionalRepository.findById(profid).get()));
        redirectAttributes.addAttribute("patientid", patientid);
        return "redirect:/searchprof";
    }

    @RequestMapping("/showrequest") //display the patient request to the professional
    public String showrequest(
            @RequestParam(name="reqid", required=true) long reqid,
            Model model, RedirectAttributes redirectAttributes){
            Request request = requestRepository.findById(reqid).get();
        model.addAttribute("patient", request.getPatient());
        model.addAttribute("professional", request.getProfessional());
        model.addAttribute("request", request);
        model.addAttribute("questionnairesel", "0");
        return "/showrequest";
    }

    @RequestMapping("/sendquestionnaire") //creates and send the questionnaire to the patient
    public String sendquestionnaire(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="profid", required=true) long profid,
            @RequestParam(name="submit", required=true) String submit,
            @RequestParam(name="question", required=false) String[] questions,
            @RequestParam(name="reqid", required=true) long reqid,
            Model model, RedirectAttributes redirectAttributes) {
        Professional professional = professionalRepository.findById(profid).get();
        Patient patient = patientRepository.findById(patientid).get();
        Request request = requestRepository.findById(reqid).get();
        if (submit.equals("Accept Request and create questionnaire")) {
            Questionnaire q = new Questionnaire(patient, professional);
            q.addQuestion("");
            model.addAttribute("patient", request.getPatient());
            model.addAttribute("professional", request.getProfessional());
            model.addAttribute("request", request);
            model.addAttribute("questionnairesel", "1");
            model.addAttribute("questionnaire", q);
            return "/showrequest";
        } else if (submit.equals("Add Question")) {
            Questionnaire q = new Questionnaire(patient, professional);
            ArrayList<String> Aquestions = new ArrayList<>();
            for (String question : questions) {
                Aquestions.add(question);
            }
            Aquestions.add("");
            q.setQuestions(Aquestions);
            model.addAttribute("patient", request.getPatient());
            model.addAttribute("professional", request.getProfessional());
            model.addAttribute("request", request);
            model.addAttribute("questionnairesel", "1");
            model.addAttribute("questionnaire", q);
            return "/showrequest";
        } else if (submit.equals("Send Questionnaire")) {
            Questionnaire q = new Questionnaire(patient, professional);
            ArrayList<String> Aquestions = new ArrayList<>();
            ArrayList<String> emptyanswers = new ArrayList<>();
            for (String question : questions) {
                Aquestions.add(question);
                emptyanswers.add("");
            }
            q.setQuestions(Aquestions);
            q.setAnswers(emptyanswers);
            questionnaireRepository.save(q);
            request.setAccepted(true);
            requestRepository.save(request);
            patient.addProfessional(professional);
            patientRepository.save(patient);
            professional.addPatient(patient);
            professionalRepository.save(professional);
            redirectAttributes.addAttribute("profid", professional.getId());

            return "redirect:/profhome";
        }
        else{
            return "/error";
        }
    }


    @RequestMapping("/sendfilledquestionnaire") //fills the questionnaire with the patient's answers and saves it in the repository
    public String sendfilledquestionnaire(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="questionnaireid", required=true) long questionnaireid,
            @RequestParam(name="answer", required=true) String[] answers,
            Model model, RedirectAttributes redirectAttributes) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireid).get();
        ArrayList<String> Aanswers = new ArrayList<>();
        for (String answer : answers) {
            Aanswers.add(answer);
        }
        questionnaire.setAnswers(Aanswers);
        questionnaire.setAnswered(true);
        questionnaireRepository.save(questionnaire);
        redirectAttributes.addAttribute("patientid", patientid);
        return "redirect:/pathome";
    }


    @RequestMapping("/viewfilledquestionnaire") //displays the filled questionnaire
    public String viewfilledquestionnaire(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="profid", required=true) long profid,
            Model model) {
        Patient patient = patientRepository.findById(patientid).get();
        Professional professional = professionalRepository.findById(profid).get();
        model.addAttribute("patientid", patientid);
        model.addAttribute("profid", profid);
        if (questionnaireRepository.findByProfessionalAndPatient(professional, patient).iterator().hasNext()) {
            Questionnaire questionnaire = questionnaireRepository.findByProfessionalAndPatient(professional, patient).iterator().next();
            model.addAttribute("questionnaire", questionnaire);
        }
        else{
            model.addAttribute("questionnaire", "1");
        }
        Questionnaire questionnaire = questionnaireRepository.findByProfessionalAndPatient(professional, patient).iterator().next();
        model.addAttribute("patientid", patientid);
        model.addAttribute("profid", profid);
        model.addAttribute("questionnaire", questionnaire);
        return "viewfilledquestionnaire";
    }

    @RequestMapping("/newaccount") //new account create page with general information
    public String newaccount(
            @RequestParam(name="error", required=false) String error, Model model){
        if (error != null) {
            String errorr = error;
            model.addAttribute("error", errorr);
        }
        return "newaccount";
    }

    @RequestMapping("/newaccountcreate" ) // second new account create page dedicated for professional and patient profiles
    public String newaccountcreate(
            @RequestParam(name="firstName", required=false) String firstname,
            @RequestParam(name="lastName", required=false) String lastname,
            @RequestParam(name="dateofbirth", required=false) String dateofbirth,
            @RequestParam(name="userName", required=false) String username,
            @RequestParam(name="account", required=true) String account,
            @RequestParam(name="password", required=false) String password,
            @RequestParam(name="repeatpassword", required=false) String rpassword,
            Model model, RedirectAttributes redirectAttributes
            ){
        if (firstname != null && lastname != null && dateofbirth != null && username != null && account!= null && password != null &&
        rpassword != null && password.equals(rpassword)){
             if (account.equals("pro")){
                Professional pr = new Professional(firstname, lastname, dateofbirth, username,
                        password, new String(""), new String(""));
                professionalRepository.save(pr);
                redirectAttributes.addAttribute("id", pr.getId());
                return "redirect:/newaccountprof";
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                System.out.println(formatter.format(date));
                String[] detailweight = new String[]{formatter.format(date),new String("")};
                Patient pt = new Patient(firstname, lastname, detailweight, dateofbirth, username, password);
                patientRepository.save(pt);
                redirectAttributes.addAttribute("id", pt.getId());
                redirectAttributes.addAttribute("account", account);
                return "redirect:/newaccountpatient";
            }
        }
        else{
            redirectAttributes.addAttribute("error", "Form not correctly filled, try again");
            return "redirect:/newaccount";

        }


    }

    @RequestMapping("/newaccountpatient") //displays the second form for new account patient
    public String newaccountpatient(@RequestParam(name="id", required=false) String id,
                                    @RequestParam(name="account", required=false) String account,
                                    @RequestParam(name="error", required=false) String error,
                                    Model model){
        model.addAttribute("id", id);
        model.addAttribute("account", account);
        model.addAttribute("error", error);
        return "newaccountpatient";
    }

    @RequestMapping("/newaccountprof") //displays the second form for new account professional
    public String newaccountprof(@RequestParam(name="id", required=false) String id,
                                 @RequestParam(name="account", required=false) String account,
                                 @RequestParam(name="error", required=false) String error,
                                 Model model){
        model.addAttribute("id", id);
        model.addAttribute("account", account);
        model.addAttribute("error", error);
        return "newaccountprof";
    }

    @RequestMapping("/newaccountcreatepr") //saves the new profile in the repository
    public String newaccountcreatepr(
            @RequestParam(name="id", required=false) Long id,
            @RequestParam(name="profession", required=false) String profession,
            @RequestParam(name="services", required=false) String services,
            Model model, RedirectAttributes redirectAttributes){
        if (profession != null && services != null){
                Optional<Professional> pr = professionalRepository.findById(id);
                pr.get().updateProfession(profession);
                pr.get().updateServices(services);
                professionalRepository.save(pr.get());
                return "redirect:/signin";
            }
        else {
            redirectAttributes.addAttribute("error", "Form not correctly filled, try again");
            return "redirect:/newaccountprof";

        }


    }

    @RequestMapping("/newaccountcreatepatient") //saves the new profile in the repository
    public String newaccountcreatepatient(
            @RequestParam(name="id", required=true) Long id,
            @RequestParam(name="weight", required=false) String weight,
            Model model, RedirectAttributes redirectAttributes){
        if (weight != null){
            Optional<Patient> pt = patientRepository.findById(id);
            pt.get().getWeight().get(0)[1] = weight;
            patientRepository.save(pt.get());
            return "redirect:/signin";
        }
        else {  redirectAttributes.addAttribute("error", "Form not correctly filled, try again");
                return "redirect:/newaccountpatient";

                }


    }


    @RequestMapping("/showdiet") //show the details of the selected diet
    public String showdiet(
            @RequestParam(name="dietid", required=true) long dietid,
            @RequestParam(name="patientid", required=true) long patient,
            @RequestParam(name="profid", required=false) long profid,

            Model model) {
        List<Option> options = new LinkedList<>();
        Diet diet = dietRepository.findById(dietid);
        for (Option o: optionRepository.findByDiet(diet)){
            options.add(o);
        }
        if (profid == patient)
        {
            model.addAttribute("options", options);
            model.addAttribute("patient", patientRepository.findById(patient).get());
            model.addAttribute("diet", diet);

            return "showdiet";
        }
        else
        {
            //model.addAttribute("meals", options);
            model.addAttribute("patient", patientRepository.findById(patient).get());
            model.addAttribute("diet", diet);
            model.addAttribute("profid", profid);
            return "showdietprof";
        }

    }

    @RequestMapping("/updateoption") // updates details of the diet (options) in the repository
    public String updateoption( @RequestParam String submit,
                              @RequestParam Long optionid,
                                @RequestParam String[] meal0 ,
                                @RequestParam String[] meal1 ,
                                @RequestParam String title ,
                              Model model, RedirectAttributes redirectAttributes) {

            ArrayList<String[]> meals = new ArrayList<String[]>();
            for (int i = 0; i < meal0.length; i++) {
                meals.add(new String[]{meal0[i],meal1[i]});
            }
            Option option = optionRepository.findById(optionid).get();
            Diet diet = option.getDiet();
            option.setMeals(meals);

            option.setTitle(title);
            diet.updateOption(option);
        if (submit.equals("Add Meal")) {
            option.addMeal(new String[]{"",""});
            diet.updateOption(option);
            model.addAttribute("diet", diet);
            model.addAttribute("patient", diet.getPatient());
            model.addAttribute("profid", diet.getProfessional().getId());

            return "showdietprof";

        }
        else {
            optionRepository.save(option);
            redirectAttributes.addAttribute("dietid", diet.getId());
            redirectAttributes.addAttribute("patientid", diet.getPatient().getId());
            redirectAttributes.addAttribute("profid", diet.getProfessional().getId());

            return "redirect:/showdiet";

        }


    }

    @RequestMapping("/updatediet") //updates the diet in the repository
    public String updatediet( @RequestParam String goal,
                              @RequestParam String title,
                              @RequestParam String submit,
                              @RequestParam Long dietid,
                              Model model, RedirectAttributes redirectAttributes) {
        Diet diet = dietRepository.findById(dietid).get();
        if (submit.equals("Add Option")) {
            ArrayList<String[]> meals = new ArrayList<String[]>();
            meals.add(new String[]{"",""});
            meals.add(new String[]{"",""});
            Option newoption = new Option(diet,"");
            newoption.setMeals(meals);
            optionRepository.save(newoption);
            diet.addOption(newoption);
            diet.setTitle(title);
            diet.setGoal(goal);
            model.addAttribute("diet", diet);
            model.addAttribute("patient", diet.getPatient());
            model.addAttribute("profid", diet.getProfessional().getId());
            return "showdietprof";

        }
        else {
            diet.setTitle(title);
            diet.setGoal(goal);
            dietRepository.save(diet);
            redirectAttributes.addAttribute("dietid", diet.getId());
            redirectAttributes.addAttribute("patientid", diet.getPatient().getId());
            redirectAttributes.addAttribute("profid", diet.getProfessional().getId());

            return "redirect:/showdiet";

        }


    }

    @RequestMapping("/showwoplan") //shows deatils of the selected workoutplan
    public String showwoplan(
            @RequestParam(name="woplanid", required=true) long woplan,
            @RequestParam(name="patientid", required=false) long patient,
            @RequestParam(name="profid", required=false) long profid,

            Model model) {
        List<Workout> workouts = new LinkedList<>();
        Optional<WorkoutPlan> workoutPlan = workoutPlanRepository.findById(woplan);
        for (Workout w: workoutRepository.findByWorkoutPlan(workoutPlan.get())){
            workouts.add(w);
        }
        if (patient == profid) {
            model.addAttribute("workouts", workouts);
            model.addAttribute("patient", patientRepository.findById(patient).get());
            model.addAttribute("woplan", workoutPlan.get());

            return "showwoplan";
        }
        else {
            model.addAttribute("woplan", workoutPlan.get());
            return "showwoplanprof";
        }

    }

    @RequestMapping("/updateexercise") //updates details of the workout plan (exercise)
    public String updateoption( @RequestParam String submit,
                                @RequestParam Long workoutid,
                                @RequestParam String[] extitle ,
                                @RequestParam String[] descr ,
                                @RequestParam String[] details ,
                                @RequestParam String title ,
                                @RequestParam (name = "exid") Long[] exid,
                                Model model, RedirectAttributes redirectAttributes) {

        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        Workout workout = workoutRepository.findById(workoutid).get();

        if (submit.equals("Add Exercise")) {
            for (int i = 0; i < exid.length; i++) {
                Optional<Exercise>  e = exerciseRepository.findById(exid[i]);
                if(e.isPresent()) {
                    Exercise ex = e.get();
                    ex.updateExercise(extitle[i], descr[i], details[i]);
                    exercises.add(ex);
                }
                else {
                    Exercise ex = new Exercise(workout, extitle[i], descr[i], details[i]);
                    ex.setId(exid[i]);
                    exercises.add(ex);
                }
            }
            WorkoutPlan workoutPlan = workout.getWorkoutPlan();
            workout.setExercises(exercises);
            workout.setTitle(title);
            workoutPlan.updateWorkout(workout);
            Exercise e = new Exercise(workout, "", "", "");
            e.setId(exid[exid.length-1]+1);
            workout.addExercise(e);
            workoutPlan.updateWorkout(workout);
            model.addAttribute("woplan", workoutPlan);
            model.addAttribute("patient", workoutPlan.getPatient());
            model.addAttribute("profid", workoutPlan.getProfessional().getId());

            return "showwoplanprof";

        } else {
            for (int i = 0; i < exid.length; i++) {
                Optional<Exercise>  e = exerciseRepository.findById(exid[i]);
                if(e.isPresent()) {
                    Exercise ex = e.get();
                    ex.updateExercise(extitle[i], descr[i], details[i]);
                    exerciseRepository.save(ex);
                    exercises.add(ex);
                }
                else {
                    Exercise ex = new Exercise(workout, extitle[i], descr[i], details[i]);
                    exerciseRepository.save(ex);
                    exercises.add(ex);
                }
            }
            WorkoutPlan workoutPlan = workout.getWorkoutPlan();
            workout.setExercises(exercises);
            workout.setTitle(title);
            workoutRepository.save(workout);
            workoutPlan.updateWorkout(workout);
            workoutPlanRepository.save(workoutPlan);
            redirectAttributes.addAttribute("woplanid", workoutPlan.getId());
            redirectAttributes.addAttribute("patientid", workoutPlan.getPatient().getId());
            redirectAttributes.addAttribute("profid", workoutPlan.getProfessional().getId());

            return "redirect:/showwoplan";

        }
    }

    @RequestMapping("/updatewoplan") //updates details of the workout plan
    public String updatewoplan( @RequestParam String goal,
                              @RequestParam String title,
                              @RequestParam String submit,
                              @RequestParam Long woplanid,
                              Model model, RedirectAttributes redirectAttributes) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(woplanid).get();
        if (submit.equals("Add Workout")) {
            ArrayList<Exercise> exercises = new ArrayList<>();
            Workout newworkout = new Workout(workoutPlan, "");
            workoutRepository.save(newworkout);
            Exercise e1 = new Exercise(newworkout, "", "", "");
            exerciseRepository.save(e1);
            exercises.add(e1);
            Exercise e2 = new Exercise(newworkout, "", "", "");
            exerciseRepository.save(e2);
            exercises.add(e2);
            newworkout.setExercises(exercises);
            workoutRepository.save(newworkout);
            workoutPlan.addWorkout(newworkout);
            workoutPlan.setTitle(title);
            workoutPlan.setGoal(goal);
            model.addAttribute("woplan", workoutPlan);
            model.addAttribute("patient", workoutPlan.getPatient());
            model.addAttribute("profid", workoutPlan.getProfessional().getId());
            return "showwoplanprof";

        }
        else {
            workoutPlan.setTitle(title);
            workoutPlan.setGoal(goal);
            workoutPlanRepository.save(workoutPlan);
            redirectAttributes.addAttribute("woplanid", workoutPlan.getId());
            redirectAttributes.addAttribute("patientid", workoutPlan.getPatient().getId());
            redirectAttributes.addAttribute("profid", workoutPlan.getProfessional().getId());

            return "redirect:/showwoplan";

        }


    }


        @RequestMapping("/showoption") //shows the option deatils of a specific diet
    public String showoption(
            @RequestParam(name="iddiet", required=true) long diet,
            @RequestParam(name="idoption", required=true) long option,

            Model model) {
        List<String[]> meals = new LinkedList<>();
        for (String[] m: optionRepository.findById(option).getMeals()){
            meals.add(m);
        }
        if (!meals.isEmpty())
        {
            model.addAttribute("meals", meals);
            model.addAttribute("patient", dietRepository.findById(diet).getPatient());
            model.addAttribute("diet", dietRepository.findById(diet));

            return "showoption";
        }
        else
        {
            return "redirect:/notfound";
        }

    }

    @RequestMapping("/showworkout") //shows the workouts details of a specific workout plan
    public String showworkout(
            @RequestParam(name="idwoplan", required=true) long woplan,
            @RequestParam(name="idworkout", required=true) long workout,

            Model model) {
        List<Exercise> exercises = new LinkedList<>();
        for (Exercise e: workoutRepository.findById(workout).getExercises()){
            exercises.add(e);
        }
        if (!exercises.isEmpty())
        {
            model.addAttribute("exercises", exercises);
            model.addAttribute("patient", workoutPlanRepository.findById(woplan).get().getPatient());
            model.addAttribute("woplan", workoutPlanRepository.findById(woplan).get());

            return "showworkout";
        }
        else
        {
            return "redirect:/notfound";
        }

    }

    @RequestMapping("/adddiet") //creates a new empty diet
    public String adddiet(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="profid", required=true) long profid,
            RedirectAttributes redirectAttributes, Model model) {
        Patient patient = patientRepository.findById(patientid).get();
        Professional professional = professionalRepository.findById(profid).get();
        Diet diet = new Diet(patient, professional, "New Diet", "");
        dietRepository.save(diet);
        Option option = new Option(diet, "");
        optionRepository.save(option);
        option.addMeal(new String[]{"", ""});
        diet.addOption(option);
        optionRepository.save(option);
        dietRepository.save(diet);
        redirectAttributes.addAttribute("patientid", patientid);
        redirectAttributes.addAttribute("requesterid", profid);
        return "redirect:/patprofile";
    }

    @RequestMapping("/addwoplan") //creates a new empty workout plan
    public String addwoplan(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="profid", required=true) long profid,
            RedirectAttributes redirectAttributes, Model model) {
        Patient patient = patientRepository.findById(patientid).get();
        Professional professional = professionalRepository.findById(profid).get();
        WorkoutPlan workoutPlan = new WorkoutPlan(patient, professional, "New Workout Plan", "");
        workoutPlanRepository.save(workoutPlan);
        Workout workout = new Workout(workoutPlan, "");
        workoutRepository.save(workout);
        Exercise exercise = new Exercise(workout, "", "", "");
        exerciseRepository.save(exercise);
        Exercise exercise1 = new Exercise(workout, "", "", "");
        exerciseRepository.save(exercise1);
        workout.addExercise(exercise);
        workout.addExercise(exercise1);
        workoutPlan.addWorkout(workout);
        workoutRepository.save(workout);
        workoutPlanRepository.save(workoutPlan);
        redirectAttributes.addAttribute("patientid", patientid);
        redirectAttributes.addAttribute("requesterid", profid);
        return "redirect:/patprofile";
    }

    @RequestMapping("/patprofile") //show deatails of a patient profile
    public String patprofile(
            @RequestParam(name="patientid", required=true) long patientid,
            @RequestParam(name="requesterid", required=true) long requesterid,


            Model model) {
        Patient patient = patientRepository.findById(patientid).get();


        if (patientid == requesterid){

            List<String[]> weights = new LinkedList<>();
            List<Professional> professionals = new LinkedList<>();
            for (String[] w : patient.getWeight()) {
                weights.add(w);
            }
            for (Professional pr : patient.getProfessionals()) {
                professionals.add(pr);
            }
            model.addAttribute("patient", patient);
            model.addAttribute("weights", weights);
            model.addAttribute("professionals", professionals);


            return "patprofileprivate";
        }
        else {
            Professional professional = professionalRepository.findById(requesterid).get();
            List<Diet> diets = new LinkedList<>();
            List<WorkoutPlan> workoutplans = new LinkedList<>();
            List<String[]> weights = new LinkedList<>();
            for (String[] w : patient.getWeight()) {
                weights.add(w);
            }
            for (Diet d: dietRepository.findByPatientAndProfessional(patient, professional)){
                diets.add(d);
            }
            for (WorkoutPlan w: workoutPlanRepository.findByPatientAndProfessional(patient, professional)){
                workoutplans.add(w);
            }
            model.addAttribute("diets", diets);
            model.addAttribute("workoutplans", workoutplans );
            model.addAttribute("professional", professional);
            model.addAttribute("patient", patient);
            model.addAttribute("weights", weights);
            return "patprofileprofessional";
        }
    }

    @RequestMapping("/profprofile") //shows deatils of a professional profile
    public String profprofile(
            @RequestParam(name="profid", required=true) long profid,
            @RequestParam(name="requesterid", required=true) long reqid,
            @RequestParam(name="FirstName", required=false) String FirstName,
            @RequestParam(name="LastName", required=false) String LastName,
            @RequestParam(name="profession", required=false) String profession,
            @RequestParam(name="services", required=false) String services,
            @RequestParam(name="my", required=false) String my,
            Model model) {
        Professional professional = professionalRepository.findById(profid).get();
        if (profid == reqid){
            model.addAttribute("professional", professional);
            return "profprofileprivate";
        }
        else if (my != null && my.equals("1")) {
            Patient patient = patientRepository.findById(reqid).get();
            model.addAttribute("professional", professional);
            model.addAttribute("patient", patient);
            if (questionnaireRepository.findByProfessionalAndPatient(professional, patient).iterator().hasNext()) {
                List<Questionnaire> questionnaires = questionnaireRepository.findByProfessionalAndPatient(professional, patient);
                model.addAttribute("questionnaire", questionnaires.iterator().next());
            }
            else{
                model.addAttribute("questionnaire", "1");
            }
            model.addAttribute("professional", professional);
            model.addAttribute("patient", patient);
            return "myprofprofilepublic";
        }
        else{
            model.addAttribute("professional", professional);
            model.addAttribute("patient", patientRepository.findById(reqid).get());
            return "profprofilepublic";
        }
    }

    @RequestMapping("/editprofile") //edits details of a specific profile
    public String editprofile(
            @RequestParam(name="id", required=true) long id,
            @RequestParam(name="firstname", required=false) String FirstName,
            @RequestParam(name="lastname", required=false) String LastName,
            @RequestParam(name="dateofb", required=false) String dateofb,
            @RequestParam(name="username", required=false) String username,
            @RequestParam(name="password", required=false) String password,
            @RequestParam(name="profession", required=false) String profession,
            @RequestParam(name="services", required=false) String services,
            @RequestParam(name="weight1", required=false) String weight1,
            @RequestParam(name="weight2", required=false) String weight2,
            Model model, RedirectAttributes redirectAttributes) {
        Optional<Patient> patient = patientRepository.findById(id);
        Optional<Professional> professional = professionalRepository.findById(id);
        if (patient.isPresent()){
            Patient pt = patient.get();
            pt.setFirstName(FirstName);
            pt.setLastName(LastName);
            pt.setDateOfBirth(dateofb);
            pt.setUserName(username);
            pt.setPassword(password);
            if (!weight1.equals("") && !weight2.equals("")) {
                pt.addWeight(new String[]{weight1, weight2});
            }
            patientRepository.save(pt);
            redirectAttributes.addAttribute("patientid", pt.getId());
            return "redirect:/pathome";
        }
        else if (professional.isPresent()) {
            Professional pr = professional.get();
            pr.setFirstName(FirstName);
            pr.setLastName(LastName);
            pr.setDateOfBirth(dateofb);
            pr.setUserName(username);
            pr.setPassword(password);
            pr.setProfession(profession);
            pr.setServices(services);
            professionalRepository.save(pr);
            redirectAttributes.addAttribute("profid", pr.getId());
            return "redirect:/profhome";
        }
        else {
            return "redirect:/error";
        }
    }




    @RequestMapping("/searchprof") //redirects to the page to search a professional in the repository
    public String searchprof(
            @RequestParam(name="patientid", required=true) Long patientid,
            @RequestParam(name="professionals", required=false) List<Professional> professionals,
            @RequestParam(name="FirstName", required=false) String FirstName,
            @RequestParam(name="LastName", required=false) String LastName,
            @RequestParam(name="profession", required=false) String profession,
            @RequestParam(name="services", required=false) String services,

            Model model) {
            model.addAttribute("patient", patientRepository.findById(patientid).get());
            model.addAttribute("professionals", professionals);
            model.addAttribute("FirstName", FirstName);
            model.addAttribute("LastName", LastName);
            model.addAttribute("profession", profession);
            model.addAttribute("services", services);

            return "searchprof";
        }

    @RequestMapping("/searchprofpost") //seraches the professional based on the input fields and return the list
    public String searchprofpost(
            @RequestParam(name="patientid", required=true) Long patientid,
            @RequestParam(name="FirstName", required=false) String FirstName,
            @RequestParam(name="LastName", required=false) String LastName,
            @RequestParam(name="profession", required=false) String profession,
            @RequestParam(name="services", required=false) String services,
            Model model, RedirectAttributes redirectAttributes) {
        List<Professional> professionals1 = new LinkedList<>();
        FirstName.trim();
        List<Professional> professionals2 = new LinkedList<>();
        LastName.trim();
        List<Professional> professionals3 = new LinkedList<>();
        profession.trim();
        List<Professional> professionals4 = new LinkedList<>();
        services.trim();
        if (!LastName.equals(" ")){

            for (Professional pr: professionalRepository.findByLastNameIgnoreCase(LastName)){
                professionals1.add(pr);
            }
        }
        else {
            for (Professional pr: professionalRepository.findAll()){
                professionals1.add(pr);
            }

        }
        if (!FirstName.equals(" ")){
            professionals2 = professionalRepository.findByFirstNameIgnoreCase(FirstName);
        }
        else{
            for (Professional pr: professionalRepository.findAll()){
                professionals2.add(pr);
            }

        }
        if (!profession.equals(" ")){


            for (Professional pr: professionalRepository.findByProfessionContainingIgnoreCase(profession)){

                    professionals3.add(pr);
            }

        }
        else{
            for (Professional pr: professionalRepository.findAll()){
                professionals3.add(pr);
            }

        }
        String[] cleanservices = services.split("\\s*,\\s*");
        if (!services.equals(" ")){

            for (Professional pr: professionalRepository.findAll()){
                boolean b = true;
                for (String s: cleanservices) {
                    if (!pr.getServices().toLowerCase().contains(s.toLowerCase()))
                        b = false;
                    break;
                }
                if (b){
                    professionals4.add(pr);
                }
            }

        }
        else{
            for (Professional pr: professionalRepository.findAll()){
                professionals4.add(pr);
            }

        }
        List<Professional> professionals = new LinkedList<>(professionals1);
        professionals.retainAll(professionals2);
        professionals.retainAll(professionals3);
        professionals.retainAll(professionals4);

        redirectAttributes.addAttribute("patientid", patientid);
        redirectAttributes.addAttribute("professionals", professionals);
        redirectAttributes.addAttribute("FirstName", FirstName);
        redirectAttributes.addAttribute("LastName", LastName);
        redirectAttributes.addAttribute("profession", profession);
        redirectAttributes.addAttribute("services", services);


        return "redirect:/searchprof";
    }

}