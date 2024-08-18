package tacos;

import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;

@Controller
@RequestMapping("/register")
@Data
public class RegistrationController {

	
	private UserRepository  userRepo;
	private PasswordEncoder encoder;
	
	public RegistrationController(UserRepository  userRepo,PasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.encoder = encoder;
	}
	
	@GetMapping
	public String registerForm() {
		return "registration";
	}
	
	//@PostMapping
	//public String processRegistration() {
	//	userRepo.save(form.toUser(encoder));
	//	return "redirect:/registration";
	//	return  "registration";
	//}
	
	@PostMapping
	public String home_post(RegistrationForm form) {
		userRepo.save(form.toUser(encoder));
		return "redirect:/login";
		
	}
}
