package com.epam.ta.factory;

import com.epam.ta.model.AbstractPage;
import com.epam.ta.page.LoginPage;
import com.epam.ta.page.MailPage;
import org.openqa.selenium.WebDriver;

public class PageFactory {

    public static <T extends AbstractPage> T getPage(Class<T> pageClass, WebDriver driver) {
        if (pageClass.equals(LoginPage.class)) {
            return (T) new LoginPage(driver);
        } else if (pageClass.equals(MailPage.class)) {
            return (T) new MailPage(driver);
        } else {
            throw new IllegalArgumentException("Unsupported page class: " + pageClass.getName());
        }
    }
}
