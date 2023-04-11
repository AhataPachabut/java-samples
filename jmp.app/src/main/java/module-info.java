module jmp.app {
    requires jmp.dto;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    uses com.epam.bank.BankInterface;
    uses com.epam.service.ServiceInterface;
}