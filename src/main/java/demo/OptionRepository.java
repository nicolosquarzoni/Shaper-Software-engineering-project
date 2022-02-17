package demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OptionRepository extends CrudRepository<Option, Long> {



        Option findById(long id);
        List<Option> findByDiet(Diet diet);
    }


