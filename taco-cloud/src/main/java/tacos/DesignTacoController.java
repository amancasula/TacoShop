package tacos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	
	
	DesignTacoController(IngredientRepository ingredientRepo){
		this.ingredientRepo = ingredientRepo;
	}
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		
/*		List<Ingredient> ingredints = List.of(new Ingredient("FLTO", "Flour Tortilla",Type.WRAP),
				new Ingredient("COTO","Corn Tortilla",Type.WRAP),
				new Ingredient("GRBF","Ground Beef",Type.PROTIEN),
				new Ingredient("CARN","Carnitas", Type.PROTIEN),
				new Ingredient("TMTO","Diced Tomato",Type.VEGGIES),
				new Ingredient("LETC","lettuce",Type.VEGGIES),
				new Ingredient("CHED","CHeddar", Type.CHEESE),
				new Ingredient("SLSA","Salsa",Type.SAUSE));        */
		
		Iterable<Ingredient> ingredients = ingredientRepo.findAll();
		List<Ingredient> ingredientsL = (List)ingredients;
		
		
		Type[] types = Type.values();
		for(Type type :types) {
			model.addAttribute(type.toString().toLowerCase(),ingredientsL.stream().filter(x ->x.getType().equals(type)).collect(Collectors.toList()));
			
		}
		model.addAttribute("myKEY", "myVAL");
	}
	
	@ModelAttribute(name ="taco")
	public Taco taco() {
		return new Taco();
	}

	
	@ModelAttribute(name ="tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}
	
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	@PostMapping
	public String processTaco(Taco taco, @ModelAttribute  TacoOrder tacoOrder) {
		tacoOrder.addTaco(taco);
		System.out.println(taco.getName()+"  "+taco.getIngredients());
		return "redirect:/orders/current";
	}
}

