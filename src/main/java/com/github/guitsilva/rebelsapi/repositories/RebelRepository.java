package com.github.guitsilva.rebelsapi.repositories;

import com.github.guitsilva.rebelsapi.entities.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
    Optional<Rebel> findByName(String name);

    @Query(value = "select r from Rebel r where r.treasons >= 3")
    List<Rebel> findAllTraitors();

    @Query(value = "select r from Rebel r where r.treasons < 3")
    List<Rebel> findAllNonTraitors();
}
