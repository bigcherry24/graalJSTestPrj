package com.example.graaljsdemo.service;

import org.graalvm.polyglot.HostAccess;
import org.springframework.stereotype.Component;

@Component
public class AppBridge implements JsBridge {

    @Override
    public String getBindingName() {
        return "app";
    }

    @Override
    public Object getBindingTarget() {
        return this;
    }

    @HostAccess.Export
    public String sayHello(String name) {
        String safeName = name == null || name.trim().isEmpty() ? "guest" : name.trim();
        return "Hello, " + safeName + "!";
    }

    @HostAccess.Export
    public int calc(int left, int right) {
        return left + right;
    }
}
