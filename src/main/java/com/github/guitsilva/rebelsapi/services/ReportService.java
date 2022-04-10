package com.github.guitsilva.rebelsapi.services;

import com.github.guitsilva.rebelsapi.domain.dtos.AvgInventoryDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.ReportDTO;
import com.github.guitsilva.rebelsapi.entities.Rebel;
import com.github.guitsilva.rebelsapi.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final RebelRepository rebelRepository;

    @Autowired
    public ReportService(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ReportDTO generateReport() {
        return ReportDTO.builder()
                .traitorsPercentage(this.getTraitorsPercentage())
                .rebelsPercentage(1 - this.getTraitorsPercentage())
                .rebelsAverageInventory(this.getRebelsAverageInventory())
                .traitorsLostPoints(this.getTraitorsLostPoints())
                .build();
    }

    private double getTraitorsPercentage() {
        long rebelsCount = this.rebelRepository.count();

        if (rebelsCount == 0) {
            return 0d;
        }

        long traitorsCount = this.rebelRepository.findAllTraitors().size();

        return (double) traitorsCount / rebelsCount;
    }

    private AvgInventoryDTO getRebelsAverageInventory() {
        List<Rebel> nonTraitors = this.rebelRepository.findAllNonTraitors();

        long nonTraitorsCount = nonTraitors.size();

        if (nonTraitorsCount == 0) {
            return AvgInventoryDTO.builder()
                    .weapons(0d).ammo(0d).water(0d).food(0d)
                    .build();
        }

        long weaponsCount = nonTraitors.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getWeapons(),
                        Long::sum
                );

        long ammoCount = nonTraitors.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getAmmo(),
                        Long::sum
                );

        long waterCount = nonTraitors.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getWater(),
                        Long::sum
                );

        long foodCount = nonTraitors.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getFood(),
                        Long::sum
                );

        return AvgInventoryDTO.builder()
                .weapons((double) weaponsCount / nonTraitorsCount)
                .ammo((double) ammoCount / nonTraitorsCount)
                .water((double) waterCount / nonTraitorsCount)
                .food((double) foodCount / nonTraitorsCount)
                .build();
    }

    private long getTraitorsLostPoints() {
        return this.rebelRepository.findAllTraitors().stream()
                .reduce(0L, (sum, traitor) -> sum +
                                (traitor.getInventory().getWeapons() * 4L) +
                                (traitor.getInventory().getAmmo() * 3L) +
                                (traitor.getInventory().getWater() * 2L) +
                                traitor.getInventory().getFood(),
                        Long::sum);
    }
}
