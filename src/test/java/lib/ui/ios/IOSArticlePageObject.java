package lib.ui.ios;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSArticlePageObject extends ArticlePageObject {

  static {
    TITLE = "id:Java (programming language)";
    FOOTER_ELEMENT = "id:View article in browser";
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
    CLOSE_ARTICLE_BUTTON = "id:Back";
    TITLES_ELEMENTS = "//XCUIElementTypeLink";
    ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{ARTICLE_TITLE}')]";
    NAME_ATTRIBUTE = "name";
    LOGIN_VIEW_CLOSE_BUTTON = "id:places auth close";
    LOGIN_VIEW = "//XCUIElementTypeScrollView";
  }

  public IOSArticlePageObject(RemoteWebDriver driver) {
    super(driver);
  }
}
