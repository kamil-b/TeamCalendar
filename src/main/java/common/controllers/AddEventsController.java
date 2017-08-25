package common.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.Event;
import common.entities.User;
import common.entities.dto.EventDto;
import common.entities.wraper.EventDtoListForm;
import common.entities.wraper.MailContentWrapper;
import common.service.EmailManager;
import common.service.EventService;
import common.service.UserService;

@Controller
public class AddEventsController {

	private static final int TIMEOUT = 5000;
	private EventDtoListForm eventForm;

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailManager emailManager;

	@RequestMapping(value = "/user/{username}/addevents", method = RequestMethod.GET)
	public String showChangedEvents(@PathVariable("username") String username,
			@ModelAttribute("eventForm") @Valid EventDtoListForm eventForm, Model model, Principal principal,
			RedirectAttributes redirectAttributes) {

		if (!username.equals(principal.getName())) {
			redirectAttributes.addFlashAttribute("reason", "You dont have permision to visit this site !!");
			return "redirect:/error";
		}

		MailContentWrapper content = new MailContentWrapper();
		model.addAttribute("content", content);
		model.addAttribute("eventForm", eventForm);
		this.eventForm = new EventDtoListForm();
		this.eventForm.setEventslist((ArrayList<EventDto>) eventForm.getEventslist());

		return "addevents";

	}

	@RequestMapping(value = "/user/{username}/addevents", method = RequestMethod.POST)
	public String addChangedEvents(Principal principal, @ModelAttribute("content") MailContentWrapper content) {
		User user = userService.findByName(principal.getName());

		// TODO: move code below to eventService !
		List<Event> upadatedEvents = new ArrayList<Event>();
		for (EventDto eventDto : eventForm.getEventslist()) {
			upadatedEvents.add(eventService.updateAndReturnUpdated(eventService.returnEvent(eventDto)));

		}
		eventForm.setEventslist((ArrayList<EventDto>) eventService.returnListOfEventDto(upadatedEvents));
		// end of TODO
		if (content.isSendMail()) {
			String contentOfMessage = prepeareMessageContent(user);
			JavaMailSender mailSender = getJavaMailSender();
			String[] destination = { "kamil.bednarczyk@ericsson.com" };
			emailManager = new EmailManager();
			emailManager.setMailSender(mailSender);
			emailManager.prepeareMessage("NexusTeamApplication", destination, "Approval Request", contentOfMessage);
			emailManager.send();
		}
		return "redirect:/home";
	}

	// @Bean
	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setUsername("teamnexusapplication@gmail.com");
		mailSender.setPassword("neutrinoteam");
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(465);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.ssl.enable", true);
		props.put("mail.debug", true);
		props.put("mail.smtp.connectiontimeout", TIMEOUT);
		props.put("mail.smtp.timeout", TIMEOUT);
		props.put("mail.smtp.writetimeout", TIMEOUT);

		return mailSender;
	}

	private String prepeareMessageContent(User user) {
		StringBuilder contentOfMessage = new StringBuilder();
		contentOfMessage.append("<html><head></head><body>");
		contentOfMessage.append("<p>User: " + user.getName() + "</p>\n");
		contentOfMessage.append("<p>Email: " + user.getEmail() + "</p>\n\n");
		contentOfMessage.append("<p>Is asking for aproval for following events: " + "</p>\n");
		eventForm.getEventslist().forEach(x -> contentOfMessage.append("<p>" + x.getDate() + ": " + x.getEventType()
				+ " <a href=\"http://localhost:8080\\eventstatus\\" + x.getId() + "\">Approval</a></p> " + " \n"));
		return contentOfMessage.toString();
	}

}
