package projekti.harjoitus.kirpputori.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Category {

	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		private long categoryid;
		private String name;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")// linkki product ja catecoryn välillä.
		private List<Product> products;// tämä on lista, koska categorylla voi olla monta tuotetta.
		
		public Category() {
		super();
		}
		
		public Category(String name) {
			
			this.name = name;
		}
		
		public long getCategoryid() {
			return categoryid;
		}
		
		public void setCategoryid(long categoryid) {
			this.categoryid = categoryid;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}

		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}

		@Override
		public String toString() {
			return "Category [categoryid=" + categoryid + ", name=" + name + "]";
		}
	}



