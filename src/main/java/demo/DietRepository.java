package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DietRepository extends CrudRepository<Diet, Long> {



        Diet findById(long id);
        List<Diet> findByPatient(Patient patient);
        List<Diet> findByProfessional(Professional professional);
        List<Diet> findByPatientAndProfessional(Patient patient, Professional professional);
    }


