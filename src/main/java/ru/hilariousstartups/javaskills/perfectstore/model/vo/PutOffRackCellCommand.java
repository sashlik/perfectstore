package ru.hilariousstartups.javaskills.perfectstore.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Команда снять товар с полки")
public class PutOffRackCellCommand {

    @Schema(description = "Id полки", required = true)
    private Integer rackCellId;

}
