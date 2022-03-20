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
public class AvgInventoryDTO {

    @NotNull
    @Min(value = 0)
    private Double weapons;

    @NotNull
    @Min(value = 0)
    private Double ammo;

    @NotNull
    @Min(value = 0)
    private Double water;

    @NotNull
    @Min(value = 0)
    private Double food;
}
