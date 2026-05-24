package com.example.graaljsdemo.service;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

@Service
public class GraalJsExecutorService {

    public String execute(String script) {
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.NONE)
                .allowHostClassLookup(className -> false)
                .allowCreateThread(false)
            .option("engine.WarnInterpreterOnly", "false")
                .build()) {
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
}
