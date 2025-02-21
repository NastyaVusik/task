package com.example.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTo of hotel returning to requests")
public class HotelInfoDto {

    @Schema(description = "id of hotel")
    private Long id;
    @Schema(description = "name of hotel")
    private String name;
    @Schema(description = "description of hotel")
    private String description;
    @Schema(description = "address of hotel")
    private String address;
    @Schema(description = "phone of hotel")
    private String phone;
}
