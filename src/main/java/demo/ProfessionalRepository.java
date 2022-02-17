package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalRepository extends CrudRepository<Professional, Long> {



    public Optional<Professional> findById(long id);
    public Optional<Professional> findByUserName(String userName);
    public List<Professional> findByLastName(String lastName);
    public List<Professional> findByFirstName(String firstName);
    public List<Professional> findByLastNameIgnoreCase(String lastName);
    public List<Professional> findByFirstNameIgnoreCase(String firstName);
    public List<Professional> findByProfessionContainingIgnoreCase(String profession);
    }


