package lib.ui.monile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePAgeObject extends ArticlePageObject {

  static {
    TITLE = "css:#content h1";
    FOOTER_ELEMENT = "css:footer";
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:#page-actions li #ca-watch.mw-ui-icon-mf-watch";
    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#ca-watch.mw-ui-icon-mf-watched.watched";
    TITLES_ELEMENTS = "a > h3";
    ARTICLE_BY_TITLE_TPL = "xpath://li[contains(@title, '{ARTICLE_TITLE}')]";
    LOGIN_VIEW = ".view-border-box.visible.animated";
  }

  public MWArticlePAgeObject(RemoteWebDriver driver) {
    super(driver);
  }
}
