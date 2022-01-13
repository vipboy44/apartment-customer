package poly.com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalDTO {

	private Integer[] waterNumbers;
	
	private Integer[] electricityNumbers;
}
