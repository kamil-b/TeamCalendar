package common.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

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

	@Autowired
	private UserService userService;
	private static final String PROJECT_PATH = "\\src\\main\\resources\\static\\images\\users\\";
	private static final String UPLOAD_FOLDER = System.getProperty("user.dir") + PROJECT_PATH;
	private static final String DEFAULT_AVATAR_PATH = System.getProperty("user.dir") + PROJECT_PATH + "user.png";

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getUploadPage(Model model, Principal principal) {
		model.addAttribute("username", principal.getName());
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			Principal principal) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Nothing to upload. Please select an image.");
			return "redirect:/upload";
		}

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_FOLDER + principal.getName() + "_avatar.png");
			Files.write(path, bytes);

			User user = userService.findByName(principal.getName());
			user.setImage(bytes);
			userService.save(user);

			redirectAttributes.addFlashAttribute("message", "You successfully uploaded avatar icon");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/upload";

	}

	@RequestMapping(value = "/{username}/avatar", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getQRImage(@PathVariable("username") String username, Principal principal) {
		System.out.println(username);
		byte[] bytes = userService.findByName(username).getImage();

		if (bytes == null || bytes.length == 0) {
			try {
				bytes = Files.readAllBytes(new File(DEFAULT_AVATAR_PATH).toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

}
