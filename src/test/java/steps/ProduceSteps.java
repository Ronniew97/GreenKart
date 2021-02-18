package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.ProducePage;
import util.BrowserFactory;

public class ProduceSteps {
	WebDriver driver;
	ProducePage produce;
	String[] items = {"Cauliflower","Tomato","Beans"};
	
	@Given("user is on the GreenKart home page")
	public void user_is_on_the_green_kart_home_page() {
		driver = BrowserFactory.startBrowser();
		produce = PageFactory.initElements(driver, ProducePage.class);
		produce.validateGreenKartLogoIsDisplayed();
	}
		
	@When("user clicks add to cart button on produce he wants")
	public void user_clicks_add_to_cart_button_on_produce_he_wants() {
		produce.addItemToCart(items);
	}
	
	@When("user clicks the cart logo")
	public void user_clicks_the_cart_logo() {
		produce.clickCartButton();
	}
	
	@Then("user should see the correct items names")
	public void user_should_see_the_correct_items_names() {
		produce.validateCartItems(items);
	}
	
	@Then("user should see correct prices")
	public void user_should_see_correct_prices() {
	    produce.validateCartItemPrices();
	    produce.validateThatTotalCartPriceIsCorrect();
	}

}
