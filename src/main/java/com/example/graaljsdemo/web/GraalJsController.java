package com.example.graaljsdemo.web;

import com.example.graaljsdemo.service.GraalJsExecutorService;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GraalJsController {

    private static final String SAMPLE_SCRIPT = String.join("\n",
            "const hi = app.sayHello('GraalJS');",
            "const sum = math.add(10, 32);",
            "const area = math.multiply(6, 7);",
            "const welcome = userApi.welcome(userApi.currentUserName());",
            "`${hi} sum=${sum} area=${area} ${welcome}`;"
    );

    private final GraalJsExecutorService graalJsExecutorService;

    public GraalJsController(GraalJsExecutorService graalJsExecutorService) {
        this.graalJsExecutorService = graalJsExecutorService;
    }

    @GetMapping("/")
    public String home(Model model) {
        ScriptForm form = new ScriptForm();
        form.setScript(SAMPLE_SCRIPT);
        model.addAttribute("scriptForm", form);
        model.addAttribute("result", "");
        return "index";
    }

    @PostMapping("/execute")
    public String execute(
            @Valid ScriptForm scriptForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("result", "");
            return "index";
        }

        String result = graalJsExecutorService.execute(scriptForm.getScript());
        model.addAttribute("result", result);
        model.addAttribute("scriptForm", scriptForm);
        return "index";
    }
}
