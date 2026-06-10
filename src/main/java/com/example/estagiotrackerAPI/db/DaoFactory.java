package com.example.estagiotrackerAPI.db;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {
    private final ApplicationContext context;

    public DaoFactory(ApplicationContext context) {
        this.context = context;
    }
}
