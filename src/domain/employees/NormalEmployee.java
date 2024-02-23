package domain.employees;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class NormalEmployee extends Employee {
    private final boolean hasFaculty;
}
