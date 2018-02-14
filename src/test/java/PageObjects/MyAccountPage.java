package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends PageObject {

    @FindBy(xpath = "//a[@title='Orders']")
    private WebElement ordersHistoryBtn;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public void goToHistory() {ordersHistoryBtn.click();
    }
}
