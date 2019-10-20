package projekti.harjoitus.kirpputori.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

//Productrepository saa CrudRepositoryn palvelut käyttöön (rajapinnan määrittely)
public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByProductName(String productName);
	List<Product> findByProductNameStartingWith(String Haku);

}
