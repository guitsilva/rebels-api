package com.github.guitsilva.rebelsapi.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NameInventoryDTO {

    @NotBlank
    private String name;

    @NotNull
    @Valid
    private InventoryDTO inventory;
}
