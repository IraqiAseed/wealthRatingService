package com.epam.repo;

import com.epam.model.RichPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RichPersonRepository extends JpaRepository<RichPerson, Long> {

    RichPerson findById(long id);
}
