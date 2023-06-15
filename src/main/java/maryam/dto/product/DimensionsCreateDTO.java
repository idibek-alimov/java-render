package maryam.dto.product;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DimensionsCreateDTO {
    private Double length;
    private Double width;
    private Double height;
}
