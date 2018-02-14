package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductsPage extends PageObject {

    @FindBy(css = "li.ajax_block_product")
    private List<WebElement> products;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void quickViewProduct(int index) {
        WebElement selectedProduct = products.get(index);

        // mouse hover over product
        Actions actions = new Actions(driver);
        actions.moveToElement(selectedProduct).build().perform();

        WebElement quickViewLink = selectedProduct.findElement(By.cssSelector("a.quick-view"));
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait
                .until(ExpectedConditions.elementToBeClickable(quickViewLink))
                .click();
    }
}
