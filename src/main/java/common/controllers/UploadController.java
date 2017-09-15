package common.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entities.User;
import common.repository.UserService;

@Controller
public class UploadController {

	private final static Logger logger = LoggerFactory.getLogger(UploadController.class);
	private static final String PROJECT_PATH = "\\src\\main\\resources\\static\\images\\users\\";
	private static final String DEFAULT_AVATAR_PATH = System.getProperty("user.dir") + PROJECT_PATH + "user.png";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getUploadPage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			Principal principal) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Nothing to upload. Please select an image.").addFlashAttribute("success", false);
			return "redirect:/upload";
		}

		try {
			byte[] bytes = file.getBytes();
			User user = userService.findByName(principal.getName());
			user.setImage(bytes);
			userService.update(user);

			redirectAttributes.addFlashAttribute("message", "You successfully uploaded avatar icon").addFlashAttribute("success", true);
		}
		catch (IOException e) {
			logger.error("There was error uploading avatar icon: ", e);
		}

		return "redirect:/upload";

	}

	/**
	 * Returns user avatar as image
	 * 
	 * @param username
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/avatar/{username}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getQRImage(@PathVariable("username") String username, Principal principal) {
		byte[] bytes = userService.findByName(username).getImage();

		if (bytes == null || bytes.length == 0) {
			try {
				bytes = Files.readAllBytes(new File(DEFAULT_AVATAR_PATH).toPath());
			} catch (IOException e) {
				logger.error("There was error uploading default avatar icon: ", e);
			}

		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

}
