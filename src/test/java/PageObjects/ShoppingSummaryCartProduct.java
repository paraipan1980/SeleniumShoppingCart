package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingSummaryCartProduct extends PageObject {

    private WebElement product;

    public ShoppingSummaryCartProduct(WebDriver driver, int index) {
        super(driver);
        product = driver.findElements(By.cssSelector("tr.cart_item")).get(index);
    }

    public String getName() {
        return product.findElement(By.cssSelector("p.product-name")).getText();
    }

    public String getDescription() {
        return product.findElement(By.cssSelector("td.cart_description")).getText();
    }

    public String getQuantity() {
        return product.findElement(By.cssSelector("input.cart_quantity_input")).getAttribute("value");
    }

    public String getPrice() {
        return product.findElement(By.cssSelector("td.cart_unit")).getText();
    }

    public String getTotalPrice() {
        return product.findElement(By.cssSelector("td.cart_total")).getText();
    }
}
