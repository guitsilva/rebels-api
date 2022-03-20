package com.github.guitsilva.rebelsapi.repositories;

import com.github.guitsilva.rebelsapi.entities.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
    List<Rebel> findByName(String name);
}
