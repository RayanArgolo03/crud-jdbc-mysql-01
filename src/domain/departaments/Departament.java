package domain.departaments;

import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
public final class Departament {
    private final Long id;
    private String name;
    private final LocalDateTime creationDate = LocalDateTime.now();
    @Setter
    private LocalDateTime lastUpdateDate;
}
