package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class CoreTestCase extends TestCase {

  protected RemoteWebDriver driver;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    driver = Platform.getInstance().getDriver();
    if (driver instanceof RemoteWebDriver) {
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    //Exercise #7
    if (driver instanceof AppiumDriver) {
      AppiumDriver driver = (AppiumDriver) this.driver;
      if (driver.getOrientation().value().equals("landscape")) {
        this.rotateScreenPortrait();
      } else {
        System.out.println("Method getOrientation() does nothing for platform "
                + Platform.getInstance().getPlatformVar());
      }
    }
    this.openWikiWebPageForMobileWeb();
    this.skipWelcomePageForIOSApp();
  }

  @Override
  protected void tearDown() throws Exception {
    driver.quit();
    super.tearDown();
  }

  protected void rotateScreenPortrait() {
    if (driver instanceof AppiumDriver) {
      AppiumDriver driver = (AppiumDriver) this.driver;
      driver.rotate(ScreenOrientation.PORTRAIT);
    } else {
      System.out.println("Method rotateScreenPortrait() does nothing for platform "
              + Platform.getInstance().getPlatformVar());
    }
  }

  protected void rotateScreenLandscape() {
    if (driver instanceof AppiumDriver) {
      AppiumDriver driver = (AppiumDriver) this.driver;
      driver.rotate(ScreenOrientation.LANDSCAPE);
    } else {
      System.out.println("Method rotateScreenLandscape() does nothing for platform "
              + Platform.getInstance().getPlatformVar());
    }
  }

  protected void backgroundApp(int seconds) {
    if (driver instanceof AppiumDriver) {
      AppiumDriver driver = (AppiumDriver) this.driver;
      driver.runAppInBackground(seconds);
    } else {
      System.out.println("Method backgroundApp() does nothing for platform "
              + Platform.getInstance().getPlatformVar());
    }
  }

  protected void openWikiWebPageForMobileWeb() {
    if (Platform.getInstance().isMV()) {
      driver.get("https://en.m.wikipedia.org");
    } else {
      System.out.println("Method openWikiWebPageForMobileWeb() does nothing for platform "
              + Platform.getInstance().getPlatformVar());
    }
  }

  private void skipWelcomePageForIOSApp() {
    if (Platform.getInstance().isIOS()) {
      AppiumDriver driver = (AppiumDriver) this.driver;
      WelcomePageObject welcomePageObject = new WelcomePageObject(driver);
      welcomePageObject.clickSkip();
    }
  }
}