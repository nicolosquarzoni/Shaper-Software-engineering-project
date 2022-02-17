package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {



    Optional<Questionnaire> findById(Long id);
    List<Questionnaire> findByProfessionalAndPatient(Professional professional, Patient patient);


    }


