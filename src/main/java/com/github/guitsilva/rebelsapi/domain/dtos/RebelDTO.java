package com.github.guitsilva.rebelsapi.domain.dtos;

import com.github.guitsilva.rebelsapi.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RebelDTO {

    @NotBlank
    private String name;

    @NotNull
    @Min(value = 0)
    private Integer age;

    @NotNull
    private Gender gender;

    @NotNull
    @Valid
    private LocationDTO location;

    @NotNull
    @Valid
    private InventoryDTO inventory;
}
