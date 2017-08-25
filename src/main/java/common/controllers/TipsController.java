package common.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import common.entities.Tip;
import common.entities.dto.TipDto;
import common.service.TipService;

@Controller
public class TipsController {

	@Autowired
	private TipService tipService;

	private List<String> sectionsList;

	@RequestMapping("/tips")
	public String printTips(Model model) {

		List<Tip> tips = new ArrayList<Tip>();

		for (Tip tip : tipService.findAll()) {
			tips.add(tip);
			System.out.println(tip.toString());
		}
		model.addAttribute("tips", tips);
		return "tips";
	}

	@RequestMapping(value = "/addtip", method = RequestMethod.GET)
	public String showAddTip(Model model) {

		sectionsList = new ArrayList<String>(Arrays.asList("Git", "Linux", "Maven", "Others"));
		TipDto tipDto = new TipDto();
		model.addAttribute("sectionsList", sectionsList);
		model.addAttribute("tip", tipDto);
		return "addtip";
	}

	@RequestMapping(value = "/addtip", method = RequestMethod.POST)
	public ModelAndView addTip(@ModelAttribute("tip") @Valid TipDto tipDto, BindingResult bindingResult,
			WebRequest webRequest, Errors errors) {

		Tip createdTip = new Tip();

		if (!bindingResult.hasErrors()) {
			createdTip = tipService.createTip(tipDto);
			tipService.save(createdTip);
		}

		if (bindingResult.hasErrors()) {
			return new ModelAndView("addtip", "tip", tipDto);
		} else {
			return new ModelAndView("tipadded", "user", tipDto);
		}
	}

}
