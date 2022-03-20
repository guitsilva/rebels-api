package com.github.guitsilva.rebelsapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    @NotNull
    @Min(value = 0)
    private Double traitorsPercentage;

    @NotNull
    @Min(value = 0)
    private Double rebelsPercentage;

    @NotNull
    @Valid
    private AvgInventoryDTO rebelsAverageInventory;

    @NotNull
    @Min(value = 0)
    private Long traitorsLostPoints;
}
