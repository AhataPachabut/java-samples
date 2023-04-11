package com.epam.entity;

import java.time.LocalDate;


public record User(
    String name,
    String surname,
    LocalDate birthday
) {
}
