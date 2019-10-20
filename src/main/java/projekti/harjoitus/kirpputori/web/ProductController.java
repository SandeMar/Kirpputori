package projekti.harjoitus.kirpputori.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import projekti.harjoitus.kirpputori.domain.ProductRepository;

import projekti.harjoitus.kirpputori.domain.CategoryRepository;

import projekti.harjoitus.kirpputori.domain.ImageModel;
import projekti.harjoitus.kirpputori.domain.ImageModelRepository;
import projekti.harjoitus.kirpputori.domain.Product;
// käsittelee käyttäjän syötteitä-@Controller kertoo luokan sisältävän palvelimelle tulevien pyyntöjä käsitteleviä metodeja.
@Controller

public class ProductController {
	//Autowired annotaatio antaa olion repository rajanpinnan kautta Controllerin käyttöön.
	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository crepository;
	@Autowired
	private ImageModelRepository frepository;
	
	
	
	//Aloitussivulle
	@GetMapping("aloitus")
	public String aloitus() {
			return "aloitus";
		}
	
	//Login sivulle 
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
	    }

    //Listaa tuotteet
	@GetMapping(value= "/productlist")//HTML-sivun nimi mistä tullaan metodiin
	public String productList(Model model) {
	
		model.addAttribute("products", repository.findAll());
		
        return "productlist";
	}
	// RESTful listaa kaikki tuotteet
    @RequestMapping(value="/products", method = RequestMethod.GET)
    public @ResponseBody List<Product> productListRest() {	
        return (List<Product>) repository.findAll();
    }    

	// RESTful listaa tuotteet id mukaan
    @RequestMapping(value="/product/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Product> findProductRest(@PathVariable("id") Long productId) {	
    	return repository.findById(productId);
    }   

		
	// Palaa lisäyksen jälkeen lisää uuden tuotteen
    @PreAuthorize("hasAuthority('ADMIN')")// vain Admin tunnuksien oikeuksilla voi lisätä tuotteen.
	@RequestMapping(value = "/add", method = RequestMethod.GET)
		    public String addProduct(Model model){
		    	model.addAttribute("product", new Product());
		    	model.addAttribute("categories", crepository.findAll());
		    	model.addAttribute("images", frepository.findAll());
		        return "addproduct";
		        
		    }  
	
    // Tallentaa ja lisää tuotteen
	@RequestMapping(value = "/save", method = RequestMethod.POST)
		    public String save(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model){
		System.out.println("save");
		if (bindingResult.hasErrors()) {
			if (bindingResult.getFieldError().getField().equalsIgnoreCase("price")) {
				System.out.println("price -error haara");
				bindingResult.rejectValue("price", "err.price", "Check price format");// jos hinta ei ole vähintään yksi euro palauttaa errorin.
			} else {
				System.out.println("Jokin muu vika");
			}
			return "redirect:add";// jos tulee väärä hinta palauttaa uudelleen "lisää tuote ikkunan"
			

		}
		        
		System.out.println("mene tallettamaan tuote");
		repository.save(product);
		       // return "redirect:productlist";// Palaa productlist-sivulle.
		return "redirect:productlist";
		    }    
	
	// Poistaa tuotteen
	@PreAuthorize("hasAuthority('ADMIN')")// vain Admin tunnuksien oikeuksilla voi poistaa tuotteen.
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
		    public String deleteProduct(@PathVariable("id") Long productId, Model model) {
		    	repository.deleteById(productId);
  	
		        return "redirect:../productlist";
		 }
	
	//Tuotetta voi muokata 
	
	@PreAuthorize("hasAuthority('ADMIN')")// vain Admin tunnuksien oikeuksilla voi muokata tuotteen.
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
		    public String editProduct(@PathVariable("id") Long Id, Model model) {
		    	model.addAttribute("product", repository.findById(Id));
		    	model.addAttribute("categories", crepository.findAll());
		    	model.addAttribute("images", frepository.findAll());
		    	
		    	return "editproduct";
		    }
		 
		 
		//Etsii tuotteet
		 @RequestMapping(value= "/search1", method = RequestMethod.GET)//userilla ja adminilla oikeudet
	     		public String productsearch(@RequestParam("haku") String Haku, Model model) {
				 System.out.println("Tulosta" + Haku);	
				 model.addAttribute("products", repository.findByProductNameStartingWith(Haku));// haku tapahtuu tuotenimen alkuosalla.
				 		 
			        return "searchlist";             // palauttaa listan listan, jonka tuotteita haettiin.
				}		
		 
		 @GetMapping("/index")
		    public String index() {
		        return "upload";
		  }
	
		 
		 // AVAA tiedostohallinnan ikkunan, josta valitaan kuva
		 @PostMapping("/upload")
		    @PreAuthorize("hasAuthority('ADMIN')")
		    public String imageUpload(@RequestParam("image") MultipartFile image, Model model) {
		    	// Image Base64.getEncoder().encodeToString(file.file)
		    	 //<img  th:src="@{'data:image/jpeg;base64,'+${file.file}}" />
		        if (image.isEmpty()) {
		        	model.addAttribute("msg", "Lataus epäonnistui");
		            return "uploadstatus";
		        }

		        try {
		            ImageModel imageModel = new ImageModel(image.getOriginalFilename(), image.getContentType(), image.getBytes());
		            frepository.save(imageModel);
		            
		     
		            return "redirect:/productlist";
		            //return "editProduct";
		        } catch (IOException e) {
		            e.printStackTrace();
		        }

		        return "uploadstatus";
		    }
		 
		 // Lataa kuvan tietokannasta
		 @GetMapping("/image/{imageid}")
			public ResponseEntity<byte[]> getFile(@PathVariable Long imageid) {
				Optional<ImageModel> fileOptional = frepository.findById(imageid);
				
				if(fileOptional.isPresent()) {
					ImageModel file = fileOptional.get();
					return ResponseEntity.ok()
							.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getImageName() + "\"")
							.body(file.getFile());	
				}
				
				return ResponseEntity.status(404).body(null);
			}    
		 
		
		    
		}

		 
		
		 
		 
	


