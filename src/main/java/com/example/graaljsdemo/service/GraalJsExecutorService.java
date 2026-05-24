package com.example.graaljsdemo.service;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

@Service
public class GraalJsExecutorService {

    private final AppBridge appBridge = new AppBridge();

    public String execute(String script) {
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.EXPLICIT)
                .allowHostClassLookup(className -> false)
                .allowCreateThread(false)
                .option("engine.WarnInterpreterOnly", "false")
                .build()) {
            context.getBindings("js").putMember("app", appBridge);
            Value result = context.eval("js", script);
            if (result == null || result.isNull()) {
                return "null";
            }
            if (result.isString()) {
                return result.asString();
            }
            return result.toString();
        } catch (PolyglotException e) {
            if (e.isSyntaxError()) {
                return "Syntax Error: " + e.getMessage();
            }
            return "Execution Error: " + e.getMessage();
        }
    }

    public static class AppBridge {

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
}
