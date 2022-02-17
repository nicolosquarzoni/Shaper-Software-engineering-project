package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends CrudRepository<WorkoutPlan, Long> {



    Optional<WorkoutPlan> findById(Long id);
    List<WorkoutPlan> findByPatient(Patient patient);
    List<WorkoutPlan> findByProfessional(Professional professional);
    List<WorkoutPlan> findByPatientAndProfessional(Patient patient, Professional professional);


    }


