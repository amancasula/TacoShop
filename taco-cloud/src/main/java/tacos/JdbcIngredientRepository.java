package tacos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	JdbcIngredientRepository(JdbcTemplate jdbcTemplate){
		
		this.jdbcTemplate  = jdbcTemplate;
	}

	@Override
	public Iterable<Ingredient> findAll() {
//	public List<Ingredient> findAll() {

		// TODO Auto-generated method stub
		return jdbcTemplate.query("select id, name,type from Ingredient",  this::mapRowToIngredient);
		
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		// TODO Auto-generated method stub
		List<Ingredient>  results  =   jdbcTemplate.query("select id, name,type from Ingredient where id=?",  this::mapRowToIngredient, id);
		
		if (results.isEmpty()){
		return Optional.empty();
		}
		else {
			return  Optional.of(results.get(0));
		}
		
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		// TODO Auto-generated method stub
		
		
		jdbcTemplate.update("insert into Ingredient values(?,?,?)", ingredient.getId(),ingredient.getName(),ingredient.getType());
		
		return ingredient;
	}

	private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException{
		
		return new Ingredient(row.getString("id"),row.getString("name"),Ingredient.Type.valueOf(row.getString("type")));
	}

}
