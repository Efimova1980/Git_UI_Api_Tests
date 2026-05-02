package dto;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Item {
    private int id;
    private String name;
    private String full_name;
    @JsonProperty("private")
    private boolean isPrivate;


}
