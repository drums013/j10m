package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

  protected static String
          MY_LISTS_LINK,
          OPEN_NAVIGATION;

  public NavigationUI(RemoteWebDriver driver) {
    super(driver);
  }

  public void clickMyLists() {
    if (Platform.getInstance().isMV()) {
      this.openNavigation();
      this.tryClickElementWithFewAttempts(
              MY_LISTS_LINK,
              "Cannot find navigation button to My List",
              20);
    } else {
      this.waitForElementAndClick(
              MY_LISTS_LINK,
              "Cannot find navigation button to My List",
              5);
    }
  }

  public void comeBack() {
    driver.navigate().back();
  }

  public void openNavigation() {
    if (Platform.getInstance().isMV()) {
      this.waitForElementAndClick(
              OPEN_NAVIGATION,
              "Cannot find and click open navigation button",
              5);
    } else {
      System.out.println("Method openNavigation() does nothing for platform "
              + Platform.getInstance().getPlatformVar());
    }
  }
}