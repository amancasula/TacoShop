package tacos;


import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Taco {

	private String name;
	private List<Ingredient> ingredients;
	private Date createdAt = new Date();
	private long id;
}
