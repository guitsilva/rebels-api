package com.github.guitsilva.rebelsapi.repositories;

import com.github.guitsilva.rebelsapi.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {}
