package utils;

import org.openqa.selenium.WebDriver;

public class WindowSwitcher {
    public static void switchToNewWindow(WebDriver webDriver, String originalWindow) {
        webDriver.getWindowHandles()
                .stream()
                .filter(handle -> !handle.equals(originalWindow))
                .findFirst()
                .ifPresent(handle -> webDriver.switchTo().window(handle));
    }

}
