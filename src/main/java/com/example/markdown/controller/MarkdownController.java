package com.example.markdown.controller;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class MarkdownController {

    @GetMapping("/view/{page}")
    public String markdownView(@PathVariable("page") String page, Model model) throws Exception {

        String markdownValueForm = getMarkdownValueForm(page);

        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdownValueForm);
        HtmlRenderer renderer = HtmlRenderer.builder().build();

        model.addAttribute("contents", renderer.render(document));

        return "view";
    }

    public String getMarkdownValueForm(String page) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        ClassPathResource classPathResource = new ClassPathResource("static/markdown/" + page);

        BufferedReader br = Files.newBufferedReader(Paths.get(classPathResource.getURI()));
        br.lines().forEach(line -> stringBuilder.append(line).append("\n"));

        return stringBuilder.toString();
    }
}
