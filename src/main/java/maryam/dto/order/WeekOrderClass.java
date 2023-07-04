package maryam.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekOrderClass {
    Integer count;
    Double price;
    Integer day;
    String dayOfTheWeek;
//    Integer getCount();
//    Double getPrice();
//    Integer getDay();
//    String getDayOfTheWeek();
}
