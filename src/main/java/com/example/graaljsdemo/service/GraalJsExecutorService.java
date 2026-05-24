package com.example.graaljsdemo.service;

import java.util.List;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.springframework.stereotype.Service;

@Service
public class GraalJsExecutorService {

    private final List<JsBridge> bridges;

    public GraalJsExecutorService(List<JsBridge> bridges) {
        this.bridges = bridges;
    }

    public String execute(String script) {
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.EXPLICIT)
                .allowHostClassLookup(className -> false)
                .allowCreateThread(false)
                .option("engine.WarnInterpreterOnly", "false")
                .build()) {
            for (JsBridge bridge : bridges) {
                context.getBindings("js").putMember(bridge.getBindingName(), bridge.getBindingTarget());
            }
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
