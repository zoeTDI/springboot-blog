package org.example.springbootblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 18324
 */
@Controller
public class Test {
    @RequestMapping("/toIndex")
    public String hello(Model model, @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        String message = "You just create Spring Boot Example successfully";
        model.addAttribute("name", name);
        model.addAttribute("message", message);
        return "index.html";
    }
}
