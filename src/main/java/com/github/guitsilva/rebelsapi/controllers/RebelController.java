package com.github.guitsilva.rebelsapi.controllers;

import com.github.guitsilva.rebelsapi.domain.dtos.LocationDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.RebelDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.TradeDTO;
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

    @GetMapping()
    public ResponseEntity<Page<Rebel>> findAll(Pageable pageable) {
        Page<Rebel> rebels = this.rebelService.findAll(pageable);
        return new ResponseEntity<>(rebels, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Rebel> save(
            @Valid @RequestBody RebelDTO rebelDTO
    ) {
        Rebel savedRebel = this.rebelService.save(rebelDTO);
        return new ResponseEntity<>(savedRebel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<Void> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody LocationDTO newLocationDTO
    ) {
        this.rebelService.updateLocation(id, newLocationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/treasons")
    public ResponseEntity<Void> reportTreason(
            @PathVariable Long id
    ) {
        this.rebelService.reportTreason(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
