package com.github.guitsilva.rebelsapi.security.details.services;

import com.github.guitsilva.rebelsapi.entities.Rebel;
import com.github.guitsilva.rebelsapi.repositories.RebelRepository;
import com.github.guitsilva.rebelsapi.security.details.data.RebelDataDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RebelDetailsServiceImpl implements UserDetailsService {

    private final RebelRepository rebelRepository;

    @Autowired
    public RebelDetailsServiceImpl(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    @Override
    public RebelDataDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Rebel> rebelOptional = rebelRepository.findByName(username);

        if (rebelOptional.isEmpty()) {
            throw new UsernameNotFoundException(username + "not found");
        }

        return new RebelDataDetails(rebelOptional.get());
    }
}
