package com.github.guitsilva.rebelsapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {

    @NotNull
    @Valid
    private InventoryDTO request;

    @NotNull
    @Valid
    private InventoryDTO offer;
}
