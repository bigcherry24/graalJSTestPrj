package com.example.graaljsdemo.service;

import java.util.List;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
        return executeWithContext(script);
    }

    public String execute(String script, String executionMode) {
        if ("scriptengine".equalsIgnoreCase(executionMode)) {
            return executeWithScriptEngine(script);
        }
        return executeWithContext(script);
    }

    public String executeWithContext(String script) {
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

    public String executeWithScriptEngine(String script) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
        if (engine == null) {
            return "Execution Error: GraalJS ScriptEngine not found.";
        }

        Bindings bindings = engine.createBindings();
        for (JsBridge bridge : bridges) {
            bindings.put(bridge.getBindingName(), bridge.getBindingTarget());
        }
        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

        try {
            Object result = engine.eval(script);
            if (result == null) {
                return "null";
            }
            return String.valueOf(result);
        } catch (ScriptException e) {
            return "Execution Error: " + e.getMessage();
        }
    }
}
