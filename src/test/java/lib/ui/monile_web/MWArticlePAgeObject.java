package lib.ui.monile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePAgeObject extends ArticlePageObject {

  static {
    TITLE = "css:#content h1";
    FOOTER_ELEMENT = "css:footer";
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:#page-actions li #ca-watch.mw-ui-icon-mf-watch";
    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions li #ca-watch.mw-ui-icon-mf-watched watched";

    TITLES_ELEMENTS = "//XCUIElementTypeLink";
    ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{ARTICLE_TITLE}')]";
    NAME_ATTRIBUTE = "name";
    LOGIN_VIEW_CLOSE_BUTTON = "id:places auth close";
    LOGIN_VIEW = "//XCUIElementTypeScrollView";
  }

  public MWArticlePAgeObject(RemoteWebDriver driver) {
    super(driver);
  }
}
