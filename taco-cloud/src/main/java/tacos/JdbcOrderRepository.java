package tacos;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private JdbcOperations jdbcOperations; 
	
	JdbcOrderRepository(JdbcOperations jdbcOperations){
		
		this.jdbcOperations = jdbcOperations;
	}
	@Override
	//@Transactional
	public TacoOrder save(TacoOrder order) {
		// TODO Auto-generated method stub
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into Taco_Order(delivery_name,delivery_street, delivery_city, delivery_state, delivery_zip,cc_number,cc_expiration,cc_cvv, placed_at) values(?,?,?,?,?,?,?,?,?)",Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.TIMESTAMP);
		pscf.setReturnGeneratedKeys(true);
		order.setPlacedAt(new Date());
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(List.of(order.getDeliveryName(),order.getDeliveryStreet(),order.getDeliveryCity(),order.getDeliveryState(),order.getDeliveryZip(),order.getCcNumber(),order.getCcExpiration(),order.getCcCVV(),order.getPlacedAt())); 
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);
		long orderId = keyHolder.getKey().longValue();
		order.setId(orderId);
		
		List<Taco> tacos = order.getTacos();
	    
		int  i = 0;
		
		for(Taco taco : tacos) {
			saveTaco(orderId, i++ ,taco);
		}
		
		return order;
		
	}

	
	Taco saveTaco(long orderId, int orderKey, Taco taco) {
		
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into Taco(name, taco_order, taco_order_key,created_at) values(?,?,?,?)",Types.VARCHAR,Types.BIGINT,Types.BIGINT,Types.TIMESTAMP);
		pscf.setReturnGeneratedKeys(true);
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(List.of(taco.getName(),orderId,orderKey,taco.getCreatedAt()));
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);
		long tacoId = keyHolder.getKey().longValue();
		taco.setId(tacoId);
		
		saveIngredientRefs(tacoId,taco.getIngredients());
		
		return taco;
	
		
	}
	
	private void saveIngredientRefs(long tacoId, List<Ingredient> ingredientRefs) {
		int key =0;
		for(Ingredient ingredient : ingredientRefs) {
			jdbcOperations.update("insert into Ingredient_Ref values(?,?,?)",ingredient.getId(),tacoId, key++);
		}
	}
	
}
