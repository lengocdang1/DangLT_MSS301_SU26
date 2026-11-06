package DE180613.MSBrand_DE180613.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandWatchesResponse {
    private Integer brandId;
    private String brandName;
    private Integer totalWatches;
    private List<Integer> watchIds;
}

