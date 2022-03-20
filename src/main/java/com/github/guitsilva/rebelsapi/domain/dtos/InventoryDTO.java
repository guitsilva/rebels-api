package com.github.guitsilva.rebelsapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    @NotNull
    @Min(value = 0)
    private Integer weapons;

    @NotNull
    @Min(value = 0)
    private Integer ammo;

    @NotNull
    @Min(value = 0)
    private Integer water;

    @NotNull
    @Min(value = 0)
    private Integer food;
}
