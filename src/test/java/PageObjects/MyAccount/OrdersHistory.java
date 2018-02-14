package PageObjects.MyAccount;

import PageObjects.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrdersHistory extends PageObject {

    @FindBy(id = "order-list")
    private WebElement ordersTable;

    @FindBy(xpath = "//textarea[@name='msgText']")
    private WebElement messageTextarea;

    @FindBy(xpath = "//button[@name='submitMessage']")
    private WebElement submitCommentBtn;

    @FindBy(xpath = "//h3[text()='Messages']/following-sibling::div/table")
    private WebElement messagesTable;

    public OrdersHistory(WebDriver driver) {
        super(driver);
    }

    private WebElement getOrder(int index) {
        return ordersTable.findElements(By.tagName("tr")).get(index);
    }

    public void openOrder(int index) {
        getOrder(index).findElement(By.cssSelector("td.history_link")).click();
    }

    public void addComment(String comment) {
        messageTextarea.clear();
        messageTextarea.sendKeys(comment);
        submitCommentBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.textToBePresentInElement(messagesTable, comment));
    }

    public String getMessagesTableContent() {
        return messagesTable.getText();
    }
}
