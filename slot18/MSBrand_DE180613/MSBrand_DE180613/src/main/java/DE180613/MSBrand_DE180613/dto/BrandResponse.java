package DE180613.MSBrand_DE180613.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandResponse {
    private Integer brandId;
    private String brandName;
    private String countryOfOrigin;
    private Long watchCount;
}

