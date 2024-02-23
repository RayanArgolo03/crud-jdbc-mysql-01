package domain.employees;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class SuperiorEmployee extends Employee {
    private int workExperience;
}
