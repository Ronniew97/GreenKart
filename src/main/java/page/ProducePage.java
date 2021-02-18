package page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import util.BasePage;

public class ProducePage extends BasePage{

	WebDriver driver;
	
	public ProducePage(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(how = How.CSS, using = "div.brand.greenLogo") WebElement GREENKART_LOGO;
	@FindBy(how = How.XPATH, using = "//div[@class='product-action']//button") List<WebElement> ADD_TO_CART_BUTTONS;
	@FindBy(how = How.CSS, using = "h4.product-name") List<WebElement> PRODUCT_NAMES;
	@FindBy(how = How.CSS, using = "a.increment") List<WebElement> INCREMENT_BUTTONS;
	@FindBy(how = How.CSS, using = "a.decrement") List<WebElement> DECREMENT_BUTTONS;
	@FindBy(how = How.CSS, using = "input.quantity") List<WebElement> PRODUCE_ITEM_QUANITY;
	@FindBy(how = How.XPATH, using = "//div[@class='product']//p[@class='product-price']") List<WebElement> PRODUCE_PRICES;
	@FindBy(how = How.XPATH, using = "//img[@alt='Cart']") WebElement CART_BUTTON;
	@FindBy(how = How.XPATH, using = "//div[@class='cart-preview active']//p[@class='product-name']") List<WebElement> CART_ITEMS_NAMES;
	@FindBy(how = How.XPATH, using = "//div[@class='cart-preview active']//p[@class='amount']") List<WebElement> CART_ITEMS_PRICES;
	@FindBy(how = How.XPATH, using = "//div[@class='cart-preview active']//p[@class='quantity']") List<WebElement> CART_ITEMS_QUANTITY;
	@FindBy(how = How.XPATH, using = "//td[text()='Price']//following-sibling::td//strong") WebElement TOTAL_PRICE;
	@FindBy(how = How.XPATH, using = "//button[text()='PROCEED TO CHECKOUT']") WebElement CHECKOUT_BUTTON;
	@FindBy(how = How.CSS, using = "input.search-keyword") WebElement SEARCH_BOX;
	@FindBy(how = How.CSS, using = "button.search-button") WebElement SEARCH_BUTTON;

	By CART_ITEMS_LOCATOR = By.xpath("//div[@class='cart-preview active']//li[@class='cart-item']");
	
	public void validateGreenKartLogoIsDisplayed() {
		Assert.assertTrue(GREENKART_LOGO.isDisplayed(),"logo is not displayed");
	}
	
	List<String> producePrice = new ArrayList<String>();
	
	public void addItemToCart(String[] products) {
		int totalProducts = PRODUCT_NAMES.size();
		int counter = 0;
		for(int i=0; i<totalProducts; i++) {
			String[] productName = PRODUCT_NAMES.get(i).getText().split("-");
			String formattedProductName = productName[0].trim();
			
			List<?> productsList = Arrays.asList(products);
			
			if(productsList.contains(formattedProductName)) {
				counter++;
				ADD_TO_CART_BUTTONS.get(i).click();
				producePrice.add(PRODUCE_PRICES.get(i).getText());
				if(counter==productsList.size()) 
				break;
			}
		}
	}
	
	public void clickCartButton() {
		CART_BUTTON.click();
	}
	
	public void validateCartItems(String[] products) {
		explicitWait(driver, 10, CART_ITEMS_LOCATOR, products.length);
		
		List<?> expectedProductsNames = Arrays.asList(products);
		List<String> actualProductsNames = new ArrayList<String>();

		for(int i=0; i<CART_ITEMS_NAMES.size(); i++) {
			String[] cartItems = CART_ITEMS_NAMES.get(i).getText().split("-");
			String formattedCartItems = cartItems[0].trim();
			actualProductsNames.add(formattedCartItems);
		} 
		Assert.assertEquals(actualProductsNames, expectedProductsNames,"product names in shopping cart aren't correct");
	}
	
	public List<String> validateCartItemPrices() {
		List<String> cartPrice = new ArrayList<String>();

		for(int i=0; i<CART_ITEMS_PRICES.size(); i++) {
			String cartItemPrices = CART_ITEMS_PRICES.get(i).getText().trim();
			cartPrice.add(cartItemPrices);
		} 
		
		Assert.assertEquals(cartPrice, producePrice,"produce prices don't match cart prices");
		return cartPrice;
	}
	
	public void validateThatTotalCartPriceIsCorrect() {
		String[] cartPricesString = validateCartItemPrices().toArray(new String[validateCartItemPrices().size()]);
		int[] cartPrices = new int[cartPricesString.length];
		
		 for(int i=0; i<cartPricesString.length; i++) {
	         cartPrices[i] = Integer.parseInt(cartPricesString[i]);
	      }
		 
		int actualTotalPrice = 0; 
		for(int i=0; i<cartPrices.length; i++) {
			actualTotalPrice = actualTotalPrice + cartPrices[i];
		}
		int expectedTotalPrice = Integer.parseInt(TOTAL_PRICE.getText());
		Assert.assertEquals(actualTotalPrice, expectedTotalPrice, "total price is incorrect");
	}
}
