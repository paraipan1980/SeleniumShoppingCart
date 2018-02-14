package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class QuickView extends PageObject {

    private final By quickViewFrameLocator = By.cssSelector("iframe.fancybox-iframe");

    @FindBy(id = "group_1")
    private WebElement sizeDropdownElement;

    @FindBy(id = "add_to_cart")
    private WebElement addToCartBtn;

    @FindBy(css = "span.continue.btn")
    private WebElement continueBtn;

    @FindBy(xpath = "//a[@title='Proceed to checkout']")
    private WebElement proceedToCheckoutBtn;

    public QuickView(WebDriver driver) {
        super(driver);
        switchToQuickViewFrame();
    }

    private void switchToQuickViewFrame() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.switchTo().frame(wait.until(ExpectedConditions.visibilityOfElementLocated(quickViewFrameLocator)));
    }

    public void selectSize(String size) {
        Select sizeDropdown = new Select(sizeDropdownElement);
        sizeDropdown.selectByVisibleText(size);
    }

    public void addToCart() {
        addToCartBtn.click();
        driver.switchTo().defaultContent();

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(quickViewFrameLocator));

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void continueShopping() {
        continueBtn.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOf(continueBtn));
    }

    public void proceedToCheckout() {
        proceedToCheckoutBtn.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOf(proceedToCheckoutBtn));
    }
}
