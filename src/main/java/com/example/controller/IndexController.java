package com.example.controller;

import com.example.config.SampleProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    SampleProperty sampleProperty;

    @RequestMapping("/")
    public ModelAndView index(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.getModel().put("prop1", sampleProperty.getStringProp1());
        mv.getModel().put("map1", sampleProperty.getMapProp());
        return mv;
    }

}