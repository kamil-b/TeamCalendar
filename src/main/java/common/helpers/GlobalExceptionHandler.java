package common.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.controllers.AdministrationPageController;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MultipartException.class)
	public String handleError1(MultipartException e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("message", e.getMostSpecificCause().getMessage()).addFlashAttribute("success",
				false);
		return "redirect:/upload";
	}
}
