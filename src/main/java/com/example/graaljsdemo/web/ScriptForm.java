package com.example.graaljsdemo.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ScriptForm {

    @NotBlank(message = "JavaScript 코드를 입력해주세요.")
    private String script;

    private String executionMode = "context";

    @Min(value = 1, message = "반복 횟수는 1 이상이어야 합니다.")
    @Max(value = 10000, message = "반복 횟수는 10000 이하여야 합니다.")
    private int benchmarkIterations = 200;

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(String executionMode) {
        this.executionMode = executionMode;
    }

    public int getBenchmarkIterations() {
        return benchmarkIterations;
    }

    public void setBenchmarkIterations(int benchmarkIterations) {
        this.benchmarkIterations = benchmarkIterations;
    }
}
