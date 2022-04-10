package com.github.guitsilva.rebelsapi.security.components;

import com.github.guitsilva.rebelsapi.domain.Gender;
import com.github.guitsilva.rebelsapi.domain.Role;
import com.github.guitsilva.rebelsapi.domain.dtos.InventoryDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.LocationDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.RebelDTO;
import com.github.guitsilva.rebelsapi.domain.mappers.MapStructMapper;
import com.github.guitsilva.rebelsapi.entities.Rebel;
import com.github.guitsilva.rebelsapi.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminRunner implements CommandLineRunner {

    private final RebelRepository rebelRepository;
    private final MapStructMapper mapStructMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminRunner(
            RebelRepository rebelRepository,
            MapStructMapper mapStructMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.rebelRepository = rebelRepository;
        this.mapStructMapper = mapStructMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String[] args) {
        InventoryDTO adminInventoryDTO = InventoryDTO.builder()
                .weapons(0)
                .ammo(0)
                .food(0)
                .water(0)
                .build();

        LocationDTO adminLocationDTO = LocationDTO.builder()
                .name("")
                .latitude(0)
                .longitude(0)
                .build();

        RebelDTO adminDTO = RebelDTO.builder()
                .name("admin")
                .age(0)
                .gender(Gender.OTHER)
                .inventory(adminInventoryDTO)
                .location(adminLocationDTO)
                .build();

        String encodedPassword = this.passwordEncoder.encode("admin");

        Rebel admin = this.mapStructMapper.rebelDTOToRebel(adminDTO);
        admin.setPassword(encodedPassword);
        admin.setRole(Role.ADMIN);

        this.rebelRepository.save(admin);
    }
}
