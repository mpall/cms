package com.pallot.cms.controller;

import java.io.IOException;
import java.io.InputStream;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CMSController {
	@Autowired
	ResourceLoader loader;

	@Autowired
	Environment env;

	@RequestMapping("/product/info/{product}")
	public String greeting(
			@RequestParam(value = "view", required = false, defaultValue = "writing-implement-brief") String view,
			@PathVariable(value = "product") String product, Model model)
					throws JsonParseException, JsonMappingException, IOException {
		Resource dataResource = loader.getResource(env.getProperty("com.pallot.cms.data.prefix") + "/" + product + env.getProperty("com.pallot.cms.data.suffix"));
		Object jsonReadIntoMap = new ObjectMapper().readValue(getContentFrom(dataResource), Object.class);
		model.addAttribute("data", jsonReadIntoMap);
		return "template/" + view;
	}

	public String getContentFrom(Resource resource) throws IOException {
		try (InputStream is = resource.getInputStream()) {
			return IOUtils.toString(is);
		}
	}

}