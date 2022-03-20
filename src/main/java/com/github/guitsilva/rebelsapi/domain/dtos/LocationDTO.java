package com.github.guitsilva.rebelsapi.domain.dtos;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    @NotBlank
    private String name;

    @NotNull
    @Min(value = 0)
    @Max(value = 90)
    private Integer latitude;

    @NotNull
    @Min(value = 0)
    @Max(value = 90)
    private Integer longitude;
}
