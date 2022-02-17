package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {



        Workout findById(long id);
        List<Workout> findByWorkoutPlan(WorkoutPlan workoutPlan);
    }


