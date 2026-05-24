package com.example.graaljsdemo.web;

import javax.validation.constraints.NotBlank;

public class ScriptForm {

    @NotBlank(message = "JavaScript 코드를 입력해주세요.")
    private String script;

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
