module jmp.cloud.service.impl {
    requires jmp.dto;
    requires transitive jmp.service.api;
    exports com.epam.service.impl;
    provides com.epam.service.ServiceInterface with com.epam.service.impl.ServiceImpl;
}