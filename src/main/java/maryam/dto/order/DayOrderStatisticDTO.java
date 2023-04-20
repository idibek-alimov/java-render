package maryam.dto.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public interface DayOrderStatisticDTO {
     Integer getCount();
     Double getPrice();
     String getTime();
}
