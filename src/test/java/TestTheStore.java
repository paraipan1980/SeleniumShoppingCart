import PageObjects.*;
import PageObjects.MyAccount.OrdersHistory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestTheStore {

    private WebDriver driver;

    private void captureScreenshot(String name) {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("target/screenshots/" + name + ".png"));
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("http://automationpractice.com");
    }

    @AfterMethod
    public void tearDown(ITestResult result){
        if (ITestResult.FAILURE == result.getStatus()) {
            captureScreenshot(result.getName());
        }

        driver.quit();
    }

    @Test
    public void test1HappyPath() {
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("window.scrollBy(0,600)", "");

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.quickViewProduct(1);

        QuickView quickView = new QuickView(driver);

        quickView.selectSize("M");
        quickView.addToCart();

        quickView.continueShopping();

        productsPage.quickViewProduct(2);
        quickView = new QuickView(driver);

        quickView.addToCart();

        quickView.proceedToCheckout();

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        Assert.assertEquals(shoppingCartPage.getNumberOfProducts(), 2, "The number of shopping items");

        ShoppingSummaryCartProduct product1 = shoppingCartPage.getProduct(0);
        ShoppingSummaryCartProduct product2 = shoppingCartPage.getProduct(1);

        // View the basket and confirm that the items are of the size you selected
        Assert.assertTrue(product1.getDescription().contains("Size : M"), "The size for 1st product");
        Assert.assertTrue(product2.getDescription().contains("Size : S"), "The size for 2nd product");
        // that their prices are correct
        Assert.assertEquals(product1.getPrice(), "$27.00", "The price for 1st product");
        Assert.assertEquals(product2.getPrice(), "$26.00", "The price for 2nd product");
        // that Total Products is the sum of the two items
        Assert.assertEquals(shoppingCartPage.getTotalProductsPrice(), "$53.00", "The Total Products price");
        // that ‘Total’ equals the Total Products + Shipping.
        String totalPriceWithoutTax = shoppingCartPage.getTotalPriceWithoutTax().replace("$", "");
        String totalProductsPrice = shoppingCartPage.getTotalProductsPrice().replace("$", "");
        String totalShippingPrice = shoppingCartPage.getTotalShippingPrice().replace("$", "");
        Assert.assertEquals(Float.parseFloat(totalPriceWithoutTax),
                Float.parseFloat(totalProductsPrice) + Float.parseFloat(totalShippingPrice),
                "‘Total’ equals the Total Products + Shipping");

        shoppingCartPage.summaryProceedToCheckout();

        SignIn signIn = new SignIn(driver);
        signIn.doLogin("borza.laurentiu@gmail.com", "BJSSTest");

        shoppingCartPage.addressProceedToCheckout();

        shoppingCartPage.acceptTerms();
        shoppingCartPage.shippingProceedToCheckout();

        shoppingCartPage.payByBankWire();
        shoppingCartPage.confirmOrder();

        signIn.doLogout();
    }

    @Test(dependsOnMethods = {"test1HappyPath"})
    public void test2ReviewOrderAndAddMessage() {
        SignIn signIn = new SignIn(driver);
        signIn.goTo();
        signIn.doLogin("borza.laurentiu@gmail.com", "BJSSTest");

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.goToHistory();

        OrdersHistory ordersHistory = new OrdersHistory(driver);
        ordersHistory.openOrder(1); // the first order in the table
        String comment = "This order is awesome";
        ordersHistory.addComment(comment);
        String messages = ordersHistory.getMessagesTableContent();

        Assert.assertTrue(messages.contains(comment), "The comment should be displayed in the messages: " + messages);
    }

    @Test(dependsOnMethods = {"test2ReviewOrderAndAddMessage"})
    public void test3CaptureImages() {
        SignIn signIn = new SignIn(driver);
        signIn.goTo();
        signIn.doLogin("borza.laurentiu@gmail.com", "BJSSTest");

        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.goToHistory();

        OrdersHistory ordersHistory = new OrdersHistory(driver);
        ordersHistory.openOrder(1); // the first order in the table

        String messages = ordersHistory.getMessagesTableContent();

        Assert.assertTrue(messages.contains("This comment should not exist in the Messages table"),
                "The comment should be displayed in the messages: " + messages);

        signIn.doLogout();
    }
}
