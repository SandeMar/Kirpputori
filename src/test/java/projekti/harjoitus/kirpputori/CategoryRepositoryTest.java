package projekti.harjoitus.kirpputori;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import projekti.harjoitus.kirpputori.domain.Category;
import projekti.harjoitus.kirpputori.domain.CategoryRepository;
import projekti.harjoitus.kirpputori.domain.Product;





@RunWith(SpringRunner.class)

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CategoryRepositoryTest {
	@Autowired
	 CategoryRepository crepository;
	@Test
	public void findByNameShouldReturnCategory() {
		List<Category> categories = crepository.findByName("Tarvikkeet");
		  
        assertThat(categories).hasSize(1);
	}
	       
	    }