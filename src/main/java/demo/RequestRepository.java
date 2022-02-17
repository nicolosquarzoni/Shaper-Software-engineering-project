package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends CrudRepository<Request, Long> {



        Optional<Request> findById(long id);
        List<Request> findByPatient(Patient patient);
        List<Request> findByProfessional(Professional professional);
        List<Request> findByPatientAndAccepted(Patient patient, boolean accepted);
        List<Request> findByProfessionalAndAccepted(Professional professional, boolean accepted);
        List<Request> findByProfessionalAndPatientAndAccepted(Professional professional, Patient patient, boolean accepted);


    }


