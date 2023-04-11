package com.epam.entity;

import java.time.LocalDate;

public record Subscription(
    BankCard bankCard,
    LocalDate startDate
) {
}
