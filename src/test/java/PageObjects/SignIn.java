package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignIn extends PageObject {

    @FindBy(id = "email")
    private WebElement emailFld;

    @FindBy(id = "passwd")
    private WebElement passwordFld;

    @FindBy(id = "SubmitLogin")
    private WebElement submitBtn;

    @FindBy(css = "a.login")
    private WebElement signInBtn;

    @FindBy(css = "a.logout")
    private WebElement signOutBtn;

    public SignIn(WebDriver driver) {
        super(driver);
    }

    public void doLogin(String email, String password) {
        emailFld.clear();
        emailFld.sendKeys(email);
        passwordFld.clear();
        passwordFld.sendKeys(password);
        submitBtn.click();
    }

    public void doLogout() {
        signOutBtn.click();
    }

    public void goTo() {
        signInBtn.click();
    }
}
