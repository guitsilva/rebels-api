package com.github.guitsilva.rebelsapi.domain.mappers;

import com.github.guitsilva.rebelsapi.domain.dtos.InventoryDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.LocationDTO;
import com.github.guitsilva.rebelsapi.domain.dtos.RebelDTO;
import com.github.guitsilva.rebelsapi.entities.Inventory;
import com.github.guitsilva.rebelsapi.entities.Location;
import com.github.guitsilva.rebelsapi.entities.Rebel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface MapStructMapper {
    LocationDTO locationToLocationDTO(Location location);

    @Mapping(target = "id", ignore = true)
    Location locationDTOToLocation(LocationDTO locationDTO);

    InventoryDTO inventoryToInventoryDTO(Inventory inventory);

    @Mapping(target = "id", ignore = true)
    Inventory inventoryDTOToInventory(InventoryDTO inventoryDTO);

    RebelDTO rebelToRebelDTO(Rebel rebel);

    @Mapping(target = "id", ignore = true)
    Rebel rebelDTOToRebel(RebelDTO rebelDTO);

    List<RebelDTO> rebelsToRebelDTOs(List<Rebel> rebels);

    @Mapping(target = "id", ignore = true)
    List<Rebel> rebelDTOsToRebels(List<RebelDTO> rebelDTOs);
}
