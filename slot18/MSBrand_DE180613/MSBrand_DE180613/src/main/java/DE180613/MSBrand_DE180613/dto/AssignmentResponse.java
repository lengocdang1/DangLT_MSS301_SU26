package DE180613.MSBrand_DE180613.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponse {
    private Integer watchId;
    private Integer brandId;
    private Integer previousBrandId;
    private String status;
}

