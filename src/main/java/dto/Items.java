package dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Items {
    private int total_count;
    private List<Item> items;
}
