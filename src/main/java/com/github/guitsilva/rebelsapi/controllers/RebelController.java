package com.github.guitsilva.rebelsapi.controllers;

import com.github.guitsilva.rebelsapi.domain.dtos.*;
import com.github.guitsilva.rebelsapi.entities.Rebel;
import com.github.guitsilva.rebelsapi.services.RebelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rebels")
public class RebelController {

    private final RebelService rebelService;

    @Autowired
    public RebelController(RebelService rebelService) {
        this.rebelService = rebelService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Page<Rebel>> findAll(Pageable pageable) {
        Page<Rebel> rebels = this.rebelService.findAll(pageable);
        return new ResponseEntity<>(rebels, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<Rebel> save(
            @Valid @RequestBody RebelDTO rebelDTO
    ) {
        Rebel savedRebel = this.rebelService.save(rebelDTO);
        return new ResponseEntity<>(savedRebel, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id
    ) {
        this.rebelService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/inventory")
    public ResponseEntity<NameInventoryDTO> findInventoryById(
            @PathVariable Long id
    ) {
        NameInventoryDTO nameInventoryDTO = this.rebelService.findInventoryById(id);
        return new ResponseEntity<>(nameInventoryDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REBEL')")
    @PutMapping("/{id}/location")
    public ResponseEntity<Void> updateLocationById(
            @PathVariable Long id,
            @Valid @RequestBody LocationDTO newLocationDTO
    ) {
        this.rebelService.updateLocationById(id, newLocationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('REBEL')")
    @PutMapping("/{id}/treasons")
    public ResponseEntity<Void> reportTreason(
            @PathVariable Long id
    ) {
        this.rebelService.reportTreasonById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('REBEL')")
    @PostMapping("/{id}/trade/{otherRebelId}")
    public ResponseEntity<Void> trade(
            @PathVariable Long id,
            @PathVariable Long otherRebelId,
            @Valid @RequestBody TradeDTO tradeDTO
    ) {
        this.rebelService.trade(id, otherRebelId, tradeDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
