package com.github.guitsilva.rebelsapi.services;

import com.github.guitsilva.rebelsapi.domain.dtos.AvgInventoryDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.ReportDTO;
import com.github.guitsilva.rebelsapi.entities.Rebel;
import com.github.guitsilva.rebelsapi.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final RebelRepository rebelRepository;

    @Autowired
    public ReportService(RebelRepository rebelRepository) {
        this.rebelRepository = rebelRepository;
    }

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

        long traitorsCount = this.rebelRepository.findAll().stream().filter(
                (rebel) -> rebel.getTreasons() >= 3
        ).count();

        return (double) traitorsCount / rebelsCount;
    }

    private AvgInventoryDTO getRebelsAverageInventory() {
        List<Rebel> rebels = this.rebelRepository.findAll().stream()
                .filter((rebel) -> rebel.getTreasons() < 3)
                .collect(Collectors.toList());

        long rebelsCount = rebels.size();

        if (rebelsCount == 0) {
            return AvgInventoryDTO.builder()
                    .weapons(0d).ammo(0d).water(0d).food(0d)
                    .build();
        }

        long weaponsCount = rebels.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getWeapons(),
                        Long::sum
                );

        long ammoCount = rebels.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getAmmo(),
                        Long::sum
                );

        long waterCount = rebels.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getWater(),
                        Long::sum
                );

        long foodCount = rebels.stream()
                .reduce(0L,
                        (sum, rebel) -> sum + rebel.getInventory().getFood(),
                        Long::sum
                );

        return AvgInventoryDTO.builder()
                .weapons((double) weaponsCount / rebelsCount)
                .ammo((double) ammoCount / rebelsCount)
                .water((double) waterCount / rebelsCount)
                .food((double) foodCount / rebelsCount)
                .build();
    }

    private long getTraitorsLostPoints() {
        return this.rebelRepository.findAll().stream()
                .filter((rebel) -> rebel.getTreasons() >= 3)
                .reduce(0L, (sum, traitor) -> sum +
                        (traitor.getInventory().getWeapons() * 4L) +
                        (traitor.getInventory().getAmmo() * 3L) +
                        (traitor.getInventory().getWater() * 2L) +
                        traitor.getInventory().getFood(),
                        Long::sum);
    }
}
