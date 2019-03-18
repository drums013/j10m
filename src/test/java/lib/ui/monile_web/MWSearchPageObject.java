package lib.ui.monile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

  static {
    SEARCH_INIT_ELEMENT = "css:button#searchIcon";
    SEARCH_INPUT = "css:form > input[type='search']";
    SEARCH_CANCEL_BUTTON = "css:button.cancel";
    SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')]" +
            "[contains(text(), '{SUBSTRING}')]";
    SEARCH_RESULT_ELEMENT = "css: ul.page-list > li.page-summary";
    SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
    SEARCH_INPUT_PLACEHOLDER = "id:org.wikipedia:id/search_src_text";
    SEARCH_PROGRESS_BAR = "id:org.wikipedia:id/search_progress_bar";
    SEARCH_RESULTS_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[android.widget.TextView[@text='{ARTICLE_TITLE}']" +
            " and android.widget.TextView[@text='{ARTICLE_DESCRIPTION}']]";
    ARTICLE_BY_TITLE_TPL = "xpath://li[contains(@title, '{ARTICLE_TITLE}')]";
  }

  public MWSearchPageObject(RemoteWebDriver driver) {
    super(driver);
  }
}
