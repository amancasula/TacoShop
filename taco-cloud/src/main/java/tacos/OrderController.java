package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("tacoOrder")
@RequestMapping("/orders")
public class OrderController {
	
	OrderRepository orderRepo;
	
	OrderController(OrderRepository orderRepo){
		
		this.orderRepo = orderRepo;
		
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String String(TacoOrder tacoOrder , SessionStatus sessionStatus) {
		System.out.print("Hello");
		orderRepo.save(tacoOrder);
		return "redirect:/";
		
	}

}
