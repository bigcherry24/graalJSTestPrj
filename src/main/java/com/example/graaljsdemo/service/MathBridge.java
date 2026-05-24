package com.example.graaljsdemo.service;

import org.graalvm.polyglot.HostAccess;
import org.springframework.stereotype.Component;

@Component
public class MathBridge implements JsBridge {

    @Override
    public String getBindingName() {
        return "math";
    }

    @Override
    public Object getBindingTarget() {
        return this;
    }

    @HostAccess.Export
    public int add(int left, int right) {
        return left + right;
    }

    @HostAccess.Export
    public int multiply(int left, int right) {
        return left * right;
    }
}
