package com.github.guitsilva.rebelsapi.services;

import com.github.guitsilva.rebelsapi.domain.Role;
import com.github.guitsilva.rebelsapi.domain.dtos.InventoryDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.LocationDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.RebelDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.TradeDTO;
import com.github.guitsilva.rebelsapi.domain.mappers.MapStructMapper;
import com.github.guitsilva.rebelsapi.entities.Inventory;
import com.github.guitsilva.rebelsapi.entities.Rebel;
import com.github.guitsilva.rebelsapi.exceptions.InvalidTradeException;
import com.github.guitsilva.rebelsapi.exceptions.RebelNotFoundException;
import com.github.guitsilva.rebelsapi.exceptions.RebelOverwriteException;
import com.github.guitsilva.rebelsapi.repositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class RebelService {

    private final RebelRepository rebelRepository;
    private final MapStructMapper mapStructMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RebelService(
            RebelRepository rebelRepository,
            MapStructMapper mapStructMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.rebelRepository = rebelRepository;
        this.mapStructMapper = mapStructMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<Rebel> findAll(Pageable pageable) {
        return this.rebelRepository.findAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Rebel save(RebelDTO rebelDTO) {
        Optional<Rebel> optionalSameNameRebel = this.rebelRepository.findByName(rebelDTO.getName());

        if (optionalSameNameRebel.isPresent()) {
            throw new RebelOverwriteException("rebel name already taken");
        }

        String encodedPassword = this.passwordEncoder.encode(rebelDTO.getPassword());

        Rebel newRebel = this.mapStructMapper.rebelDTOToRebel(rebelDTO);
        newRebel.setPassword(encodedPassword);
        newRebel.setRole(Role.REBEL);

        return this.rebelRepository.save(newRebel);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REBEL')")
    public Rebel updateLocation(Long id, LocationDTO newLocationDTO) {
        Rebel rebel = this.rebelRepository.findById(id).orElseThrow(
                () -> new RebelNotFoundException("rebel with id = " + id + " not found")
        );

        rebel.setLocation(this.mapStructMapper.locationDTOToLocation(newLocationDTO));

        return this.rebelRepository.save(rebel);
    }

    @PreAuthorize("hasRole('REBEL')")
    public Rebel reportTreason(Long id) {
        Rebel rebel = this.rebelRepository.findById(id).orElseThrow(
                () -> new RebelNotFoundException("rebel with id = " + id + " not found")
        );

        int treasons = rebel.getTreasons();
        rebel.setTreasons(++treasons);

        return this.rebelRepository.save(rebel);
    }

    @PreAuthorize("hasRole('REBEL')")
    @Transactional
    public void trade(Long requestingRebelId, Long offeringRebelId, TradeDTO tradeDTO) {
        if (Objects.equals(requestingRebelId, offeringRebelId)) {
            throw new InvalidTradeException("requesting and offering parties cannot be equal");
        }

        Rebel requestingRebel = this.rebelRepository.findById(requestingRebelId).orElseThrow(
                () -> new RebelNotFoundException("rebel with id = " + requestingRebelId + " not found")
        );

        Rebel offeringRebel = this.rebelRepository.findById(offeringRebelId).orElseThrow(
                () -> new RebelNotFoundException("rebel with id = " + offeringRebelId + " not found")
        );

        if (requestingRebel.getTreasons() >= 3 || offeringRebel.getTreasons() >= 3) {
            throw new InvalidTradeException("requesting and offering parties cannot be traitors");
        }

        if (!this.isValidTradePoints(tradeDTO)) {
            throw new InvalidTradeException("request and offer points diverge");
        }

        Inventory request = this.mapStructMapper.inventoryDTOToInventory(tradeDTO.getRequest());
        Inventory offer = this.mapStructMapper.inventoryDTOToInventory(tradeDTO.getOffer());

        this.addToInventory(requestingRebel.getInventory(), request);
        this.subtractFromInventory(requestingRebel.getInventory(), offer);

        this.addToInventory(offeringRebel.getInventory(), offer);
        this.subtractFromInventory(offeringRebel.getInventory(), request);

        if (!this.isValidInventory(requestingRebel.getInventory())) {
            throw new InvalidTradeException("requesting party cannot fulfill trade");
        }

        if (!this.isValidInventory(offeringRebel.getInventory())) {
            throw new InvalidTradeException("offering party cannot fulfill trade");
        }

        this.rebelRepository.save(requestingRebel);
        this.rebelRepository.save(offeringRebel);
    }

    private boolean isValidTradePoints(TradeDTO tradeDTO) {
        InventoryDTO request = tradeDTO.getRequest();
        InventoryDTO offer = tradeDTO.getOffer();

        int requestPoints =
                request.getWeapons() * 4 +
                        request.getAmmo() * 3 +
                        request.getWater() * 2 +
                        request.getFood();

        int offerPoints =
                offer.getWeapons() * 4 +
                        offer.getAmmo() * 3 +
                        offer.getWater() * 2 +
                        offer.getFood();

        return requestPoints == offerPoints;
    }

    private void subtractFromInventory(Inventory inventoryA, Inventory inventoryB) {
        inventoryA.setWeapons(inventoryA.getWeapons() - inventoryB.getWeapons());
        inventoryA.setAmmo(inventoryA.getAmmo() - inventoryB.getAmmo());
        inventoryA.setWater(inventoryA.getWater() - inventoryB.getWater());
        inventoryA.setFood(inventoryA.getFood() - inventoryB.getFood());
    }

    private void addToInventory(Inventory inventoryA, Inventory inventoryB) {
        inventoryA.setWeapons(inventoryA.getWeapons() + inventoryB.getWeapons());
        inventoryA.setAmmo(inventoryA.getAmmo() + inventoryB.getAmmo());
        inventoryA.setWater(inventoryA.getWater() + inventoryB.getWater());
        inventoryA.setFood(inventoryA.getFood() + inventoryB.getFood());
    }

    private boolean isValidInventory(Inventory inventory) {
        if (inventory.getWeapons() < 0) {
            return false;
        }

        if (inventory.getAmmo() < 0) {
            return false;
        }

        if (inventory.getWater() < 0) {
            return false;
        }

        return inventory.getFood() >= 0;
    }
}
