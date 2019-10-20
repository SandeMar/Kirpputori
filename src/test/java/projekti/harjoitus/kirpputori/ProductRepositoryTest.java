package projekti.harjoitus.kirpputori;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import projekti.harjoitus.kirpputori.domain.Product;
import projekti.harjoitus.kirpputori.domain.ProductRepository;



@RunWith(SpringRunner.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductRepositoryTest {
	@Autowired
	 ProductRepository repository;
	@Test
	public void findByProductNameShouldReturnProduct() {
		 List<Product> products = repository.findByProductNameStartingWith("Ken");
		  
	        assertThat(products).hasSize(1);
	}
	       
	    }