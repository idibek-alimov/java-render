package maryam.dto.order;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class OrderCreateDto {
    private String address;
    private List<ItemCreateDto> items = new ArrayList<>();
}