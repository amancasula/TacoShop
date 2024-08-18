package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@PostMapping("/")
	public String home_post() {
		//return "home";
		return "redirect:/";
		
	}
}
