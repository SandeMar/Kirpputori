package projekti.harjoitus.kirpputori.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;


@Entity
public class ImageModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long imageid;
	private String imageName;
	private String mimeType;
	private String base64str;

	@Lob
	private byte[] file;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "imagemodel")
	private List<Product> products;
	
	public ImageModel() {		 
		super();		
	}
	
	public ImageModel(String imageName, String mimeType, byte[] file) {
		
		this.imageName = imageName;
		this.mimeType = mimeType;
		this.file = file;
	}

	public long getImageid() {
		return imageid;
	}

	public void setImageid(long imageid) {
		this.imageid = imageid;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getFile() {	
		return file;	}

	public void setFile(byte[] file) {		
		this.file = file;	
		}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	@Override
	public String toString() {
		return "ImageModel [base64str=" + base64str + ", file=" + file + ", imageid=" + imageid +", imageName=" + imageName + ", mimeType=" + mimeType + "]";
	
	}

	
	
	

}
