package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends PageObject {

    @FindBy(id = "total_product")
    private WebElement totalProducts;

    @FindBy(id = "total_shipping")
    private WebElement totalShipping;

    @FindBy(id = "total_price_without_tax")
    private WebElement totalPriceWithoutTax;

    @FindBy(id = "total_tax")
    private WebElement totalTax;

    @FindBy(id = "total_price")
    private WebElement total;

    @FindBy(css = "a.standard-checkout")
    private WebElement summaryProceedToCheckoutBtn;

    @FindBy(xpath = "//button[@name='processAddress']")
    private WebElement addressProceedToCheckoutBtn;

    @FindBy(xpath = "//button[@name='processCarrier']")
    private WebElement shippingProceedToCheckoutBtn;

    @FindBy(id = "cgv")
    private WebElement termsCheckbox;

    @FindBy(css = "a.bankwire")
    private WebElement payByBankWireBtn;

    @FindBy(css = "#cart_navigation button")
    private WebElement confirmOrderBtn;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingSummaryCartProduct getProduct(int index) {
        return new ShoppingSummaryCartProduct(driver, index);
    }

    public int getNumberOfProducts() {
        return driver.findElements(By.cssSelector("tr.cart_item")).size();
    }

    public String getTotalProductsPrice() {
        return totalProducts.getText();
    }

    public String getTotalShippingPrice() {
        return totalShipping.getText();
    }

    public String getTotalPriceWithoutTax() {
        return totalPriceWithoutTax.getText();
    }

    public String getTotalTax() {
        return totalTax.getText();
    }

    public String getTotal() {
        return total.getText();
    }

    public void summaryProceedToCheckout() {
        summaryProceedToCheckoutBtn.click();
    }

    public void addressProceedToCheckout() {
        addressProceedToCheckoutBtn.click();
    }
    public void shippingProceedToCheckout() {
        shippingProceedToCheckoutBtn.click();
    }

    public void acceptTerms() {
        termsCheckbox.click();
    }

    public void payByBankWire() {
        payByBankWireBtn.click();
    }

    public void confirmOrder() {
        confirmOrderBtn.click();
    }
}
