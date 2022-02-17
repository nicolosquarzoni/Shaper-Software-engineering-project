package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {



    public Optional<Patient> findById(long id);
    public Optional<Patient> findByUserName(String userName);
    }


