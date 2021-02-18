package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	public void explicitWait(WebDriver driver, int time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void explicitWait(WebDriver driver, int time, By locator, int number) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.numberOfElementsToBe(locator, number));
	}
	
}
