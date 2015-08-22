package com.pallot.cms.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CMSController {
	@Autowired
	ResourceLoader loader;

	@Autowired
	Environment env;

	@RequestMapping("/product/info/{product}")
	public String greeting(
			@RequestParam(value = "view", required = false, defaultValue = "writing-implement-brief") String view,
			@PathVariable(value = "product") String product, Model model, HttpServletRequest request)
					throws JsonMappingException, IOException {
		Arrays.asList(env.getActiveProfiles()).stream().forEach(System.out::println);

		Resource dataResource = null;
		dataResource = loader.getResource(productDataLocation(product));
		Object jsonReadIntoMap = null;
		try {
			jsonReadIntoMap = new ObjectMapper().readValue(readContentFrom(dataResource), Object.class);
		} catch (JsonParseException e) {
			throw new LoadDataJsonException(request.getRequestURL().toString(), productDataLocation(product),
					readContentFrom(dataResource), e);
		}

		model.addAttribute("data", jsonReadIntoMap);
		return "template/" + view;
	}

	@RequestMapping("/product/info/error")
	public ModelAndView error(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", request.getAttribute("errorMessage"));
		mav.setViewName("/template/templateError");
		return mav;
	}

	private String productDataLocation(String product) {
		return env.getProperty("com.pallot.cms.data.prefix") + "/" + product
				+ env.getProperty("com.pallot.cms.data.suffix");
	}

	@ExceptionHandler(value = LoadDataJsonException.class)
	public ModelAndView parseException(LoadDataJsonException e) {
		// response.setStatusCode(HttpStatus.BAD_REQUEST);
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", e.getUrl());
		mav.addObject("dataPath", e.getLocation());
		mav.addObject("parseError", e.getCause().getMessage());
		mav.addObject("dataContent", e.getContent());
		mav.setViewName("/template/dataError");
		return mav;
	}

	public String readContentFrom(Resource resource) throws IOException {
		try (InputStream is = resource.getInputStream()) {
			return IOUtils.toString(is);
		}
	}

}