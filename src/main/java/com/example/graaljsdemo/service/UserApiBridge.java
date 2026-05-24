package com.example.graaljsdemo.service;

import org.graalvm.polyglot.HostAccess;
import org.springframework.stereotype.Component;

@Component
public class UserApiBridge implements JsBridge {

    @Override
    public String getBindingName() {
        return "userApi";
    }

    @Override
    public Object getBindingTarget() {
        return this;
    }

    @HostAccess.Export
    public String currentUserName() {
        return "demo-user";
    }

    @HostAccess.Export
    public String welcome(String name) {
        String safeName = name == null || name.trim().isEmpty() ? currentUserName() : name.trim();
        return "Welcome, " + safeName + "!";
    }
}
