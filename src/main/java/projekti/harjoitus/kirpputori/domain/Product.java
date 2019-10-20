package projekti.harjoitus.kirpputori.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
//jokaisella tietokantaan tallennnettavalla luokalla tulee olla annotaatio @Entity, sekä @id annotaatiolla merkattu atribuutti, joka toimii
// tietokantataulun ensisijaisena avaimena. 
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String productName;
	private String color;
	private int size;
	@Min(1)
	private int price;
	//Tämä taulu on "owner"
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="categoryid")//nimetään foreing key-linkki categoryn ja productin välillä
	private Category category;//tyyppi tälle Columnille on private ja Category, koska linkki on category objektiin JPA:ssa.
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="imageid")//nimetään foreing key-linkki filemodelin ja productin välillä
	private ImageModel imagemodel;
	
public Product() {}


public Product(String productName, String color, int size, int price, Category category, ImageModel imagemodel) {
super();
this.productName = productName;
this.color = color;
this.size = size;
this.price = price;
this.category = category;
this.imagemodel = imagemodel;

}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public String getColor() {
	return color;
}

public void setColor(String color) {
	this.color = color;
}

public int getSize() {
	return size;
}

public void setSize(int size) {
	this.size = size;
}

public int getPrice() {
	return price;
}

public void setPrice(int price) {
	this.price = price;
}
//===================================================================
public Category getCategory() {
	return category;
}

public void setCategory(Category category) {
	this.category = category;
}
//===================================================================
public void setImageModel(ImageModel imagemodel) {
	this.imagemodel = imagemodel;
}

public ImageModel getImageModel() {
	return imagemodel;
}
//===================================================================

@Override
public String toString() {
	
		return "Product [id=" + id + ", productName=" + productName + ", color=" + color + ", size=" + size + ", price=" + price +", category=" + this.getCategory() + ", imagemodel=" + this.getImageModel() +"]";	
	
	}
}
