package ru.job4j.accidents.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {

    @EqualsAndHashCode.Include
    private Integer id;

    private String name;
}
