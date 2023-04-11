module jmp.cloud.bank.impl {
    requires jmp.dto;
    requires transitive jmp.bank.api;
    exports com.epam.bank.impl;
    provides com.epam.bank.BankInterface with com.epam.bank.impl.BankImpl;
}