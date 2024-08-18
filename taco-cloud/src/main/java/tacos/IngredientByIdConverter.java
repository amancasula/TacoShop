package tacos;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import tacos.Ingredient;
import tacos.Ingredient.Type;

@Component
public class IngredientByIdConverter implements Converter<String,Ingredient> {
	
/*	HashMap<String, Ingredient> igmap = new HashMap<>();
	
	public IngredientByIdConverter() {
		
		igmap.put("FLTO",new Ingredient("FLTO", "Flour Tortilla",Type.WRAP));
		igmap.put("COTO",new Ingredient("COTO","Corn Tortilla",Type.WRAP));
		igmap.put("GRBF",new Ingredient("GRBF","Ground Beef",Type.PROTIEN));
		igmap.put("CARN",new Ingredient("CARN","Carnitas", Type.PROTIEN));
		igmap.put("TMTO",new Ingredient("TMTO","Diced Tomato",Type.VEGGIES));
		igmap.put("LETC",new Ingredient("LETC","lettuce",Type.VEGGIES));
		igmap.put("CHED",new Ingredient("CHED","CHeddar", Type.CHEESE));
		igmap.put("SLSA",new Ingredient("SLSA","Salsa",Type.SAUSE));
		
	} 
	
	public Ingredient convert(String id) {
		
		return igmap.get(id);

}  */
	
	IngredientRepository ingredientRepo;
	
	@Autowired
	IngredientByIdConverter(IngredientRepository ingredientRepo){
		
		this.ingredientRepo = ingredientRepo;
		
	}
	
public Ingredient convert(String id) {
		
		return ingredientRepo.findById(id).orElse(null);

} 
	
}	
