package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

  protected static String
          FOLDER_BY_NAME_TPL,
          ARTICLE_BY_TITLE_TPL,
          REMOVE_FROM_SAVED_BUTTON,
          NOTIFICATION;

  /* TEMPLATES METHODS */
  private static String getFolderXpathByName(String nameOfFolder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
  }

  private static String getSavedArticleXpathByTitle(String articleTitle) {
    return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
  }

  private static String getRemoveButtonByTitle(String articleTitle) {
    return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
  }
  /* TEMPLATES METHODS */

  public MyListsPageObject (RemoteWebDriver driver) {
    super(driver);
  }

  public void openFolderByName(String nameOfFolder) {
    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    this.waitForElementAndClick(
            folderNameXpath,
            "Cannot find folder by name " + nameOfFolder,
            5);
  }

  public void swipeByArticleToDelete(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(articleXpath,
            "Articles titled " + articleTitle + " are not listed.",
            5);
    if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
      removeArticleFromListInApp(articleXpath);
    } else {
      removeArticleFromListInMW(articleTitle);
    }
    this.waitForArticleToDisappearByTitle(articleTitle);
  }

  private void removeArticleFromListInApp(String articleXpath) {
    this.swipeElementToLeft(
            articleXpath,
            "Cannot find saved article");
    if (Platform.getInstance().isIOS()) {
      this.clickElementToTheRightUpperCorner(articleXpath,
              "Cannot find saved article");
    }
  }

  private void removeArticleFromListInMW(String articleTitle) {
    String removeLocator = getRemoveButtonByTitle(articleTitle);
    this.waitForElementAndClick(
            removeLocator,
            "Cannot click button to remove article from saved",
            10);
    waitForElementNotPresent(
            NOTIFICATION,
            "Notification still present",
            15);
    driver.navigate().refresh();
  }

  public void waitForArticleToDisappearByTitle(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementNotPresent(
            articleXpath,
            "Saved article still present with title " + articleTitle,
            15);
  }

  public void waitForArticleToAppearByTitle(String articleTitle) {
    String articleXpath = getSavedArticleXpathByTitle(articleTitle);
    this.waitForElementPresent(
            articleXpath,
            "Cannot find saved article " + articleTitle,
            15);
  }

  public void removeSavedArticleFromFolder(String nameOfFolder, String articleTitle) {
    if (Platform.getInstance().isAndroid()) {
      openFolderByName(nameOfFolder);
    }
    swipeByArticleToDelete(articleTitle);
  }
}