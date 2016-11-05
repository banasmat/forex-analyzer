package com.jorm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello {  
	
	@RequestMapping(value = "/")
	public ModelAndView sayHello() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "Hello, world!");
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping(value = "/user")
	public ModelAndView userPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user");
		return mav;
	}
	
	
}