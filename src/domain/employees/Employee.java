package domain.employees;

import domain.departaments.Departament;
import domain.departaments.Level;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SuperBuilder
public class Employee {
    private final Long id;
    private final String name;
    private final LocalDate birthDate;
    private int age;
    private List<BigDecimal> salaries;
    private final LocalDateTime creationDate = LocalDateTime.now();
    @Setter
    private LocalDateTime lastUpdateDate;
    private Map<Departament, Level> departamentsAndLevels;
}
