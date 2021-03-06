package lib.ui;

import lib.Platform;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract public class ArticlePageObject extends MainPageObject {

  protected static String
          TITLE,
          FOOTER_ELEMENT,
          OPTIONS_BUTTON,
          OPTIONS_ADD_TO_MY_LIST_BUTTON,
          OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
          ADD_TO_MY_LIST_OVERLAY,
          MY_LIST_NAME_INPUT,
          MY_LIST_OK_BUTTON,
          CLOSE_ARTICLE_BUTTON,
          TITLES_ELEMENTS,
          ARTICLE_BY_TITLE_TPL,
          FOLDER_TO_SAVE_BY_NAME_TPL,
          CREATE_FOLDER_TO_SAVE_BUTTON,
          ARTICLE_CONTAINER,
          TEXT_VIEW_ELEMENT,
          TEXT_ATTRIBUTE,
          NAME_ATTRIBUTE,
          LOGIN_VIEW_CLOSE_BUTTON,
          LOGIN_VIEW;

  /* TEMPLATES METHODS */
  private static String getArticleXpathByName(String articleTitle) {
    return ARTICLE_BY_TITLE_TPL.replace("{ARTICLE_TITLE}", articleTitle);
  }

  private static String getFolderXpathByName(String nameOfFolder) {
    return FOLDER_TO_SAVE_BY_NAME_TPL.replace("{NAME_OF_FOLDER}", nameOfFolder);
  }
  /* TEMPLATES METHODS */

  public ArticlePageObject(RemoteWebDriver driver) {
    super(driver);
  }

  public WebElement waitForTitleElement() {
    return this.waitForElementPresent(
            (TITLE),
            "Cannot find article title on page!",
            15);
  }

  public String getArticleTitle() {
    WebElement titleElement = waitForTitleElement();
    if (Platform.getInstance().isAndroid()) {
      return titleElement.getAttribute(TEXT_ATTRIBUTE);
    } else if (Platform.getInstance().isIOS()) {
      return titleElement.getAttribute(NAME_ATTRIBUTE);
    } else {
      return titleElement.getText();
    }
  }

  public String getTitleOfSavedArticle(String articleTitle) {
    if (Platform.getInstance().isAndroid() || Platform.getInstance().isMV()) {
      return getArticleTitle();
    } else {
      String articleXpath = getArticleXpathByName(articleTitle);
      return waitForElementAndGetAttribute(
              articleXpath,
              NAME_ATTRIBUTE,
              "Cannot find name attribute for article " + articleTitle,
              5);
    }
  }

  public void swipeToFooter() {
    if (Platform.getInstance().isAndroid()) {
      this.swipeUpToFindElement(
              FOOTER_ELEMENT,
              "Cannot the end of article",
              40);
    } else if (Platform.getInstance().isIOS()) {
      this.swipeUpTitleElementAppear(
              FOOTER_ELEMENT,
              "Cannot the end of article",
              40);
    } else {
      this.scrollWebPageTitleElementNotVisible(
              FOOTER_ELEMENT,
              "Cannot the end of article",
              40);
    }
  }

  public void addArticleToMyList(String nameOfFolder) {
    selectMoreOptions();
    selectAddToReadingList();
    selectGotIt();
    fillFolderNameInput(nameOfFolder);
  }

  public void closeArticle() {
    if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
      this.waitForElementAndClick(
              CLOSE_ARTICLE_BUTTON,
              "Cannot close article, cannot find X link",
              5);
    } else {
      System.out.println("Method closeArticle() does nothing for platform "
              + Platform.getInstance().getPlatformVar());
    }
  }

  public List<WebElement> listOfFoundArticles() {
    if (Platform.getInstance().isAndroid()) {
      return By.id(TITLES_ELEMENTS).findElements(driver);
    } else if (Platform.getInstance().isIOS()) {
      return By.xpath(TITLES_ELEMENTS).findElements(driver);
    } else {
      waitForElementPresent(
              "css:" + TITLES_ELEMENTS,
              "No results found",
              5);
      return By.cssSelector(TITLES_ELEMENTS).findElements(driver);
    }
  }

  public void initFolderCreation() {
    this.waitForElementAndClick(
            CREATE_FOLDER_TO_SAVE_BUTTON,
            "Cannot find a button to create a new folder",
            5);
  }

  public void selectMoreOptions() {
    this.waitForElementAndClick(
            OPTIONS_BUTTON,
            "Cannot find button to open article options",
            5);
  }

  public void selectAddToReadingList() {
    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find options to add article to reading list",
            5);
  }

  public void selectGotIt() {
    this.waitForElementAndClick(
            ADD_TO_MY_LIST_OVERLAY,
            "Cannot find 'Got it' tip overlay",
            5);
  }

  public void fillFolderNameInput(String nameOfFolder) {
    this.waitForElementAndClear(
            MY_LIST_NAME_INPUT,
            "Cannot find input to set name of articles folder",
            5);
    this.waitForElementAndSendKeys(
            MY_LIST_NAME_INPUT,
            nameOfFolder,
            "Cannot put text into article folder input",
            5);
    this.waitForElementAndClick(
            MY_LIST_OK_BUTTON,
            "Cannot press OK button",
            5);
  }

  public void addArticlesToMySaved(String login, String password) {
    if (Platform.getInstance().isMV()) {
      this.removeArticleFromSavedIfItAdded();
    }
    saveArticle();
    if (Platform.getInstance().isMV() && isPresentLoginView()) {
      AuthorizationPageObject auth = new AuthorizationPageObject(driver);
      auth.signIn(login, password);
      removeArticleFromSavedIfItAdded();
      saveArticle();
    }
    if (Platform.getInstance().isIOS() && isPresentLoginView()) {
      closeLoginView();
    }
  }

  private void closeLoginView() {
    waitForElementAndClick(
            LOGIN_VIEW_CLOSE_BUTTON,
            "Cannot find login view close button",
            5);
  }

  private void saveArticle() {
    waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "Cannot find option to add article to reading list",
            5);
  }

  public void removeArticleFromSavedIfItAdded() {
    if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
      this.waitForElementAndClick(
              OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
              "Cannot click button to remove an article from saved",
              1);
      this.waitForElementPresent(
              OPTIONS_ADD_TO_MY_LIST_BUTTON,
              "Cannot find button to add an article to saved list after removing it from this list before",
              5);
    }
  }

  public boolean isPresentLoginView() {
    if (Platform.getInstance().isIOS()) {
      List elements = driver.findElements(By.xpath(LOGIN_VIEW));
      return elements.size() > 0;
    } else {
      List elements = driver.findElements(By.cssSelector(LOGIN_VIEW));
      return elements.size() > 0;
    }
  }

  public List<WebElement> listOfArticleElements() {
    return driver.findElements(By.id(ARTICLE_CONTAINER));
  }

  public void createNewFolderToSave(String nameOfFolder) {
    initFolderCreation();
    fillFolderNameInput(nameOfFolder);
  }

  public void checkIfArticleHasTitle() {
    assertElementPresent((TITLE), "The article has no title");
  }

  public void findArticleInSearch(String searchQuery) {
    SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine(searchQuery);
  }

  public void selectFolderToSave(String nameOfFolder) {
    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    this.waitForElementAndClick(
            (folderNameXpath),
            "Cannot find folder " + nameOfFolder,
            5);
  }

  public ArrayList<String> articles() {
    List<String> searchingResults = new ArrayList<>();
    List<WebElement> elements = listOfFoundArticles();
    for (WebElement element : elements) {
      if (Platform.getInstance().isAndroid()) {
        String text = element.getAttribute(TEXT_ATTRIBUTE);
        searchingResults.add(text);
      } else if (Platform.getInstance().isIOS()) {
        String text = element.getAttribute(NAME_ATTRIBUTE);
        searchingResults.add(text);
      } else {
        String text = element.getText();
        searchingResults.add(text);
      }
    }
    return new ArrayList<String>(searchingResults);
  }

  public String saveAnyFoundArticle(String searchQuery, String nameOfFolder, String login, String password) {
    findArticleInSearch(searchQuery);
    if (Platform.getInstance().isIOS()) {
      SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
      searchPageObject.clickNavigationBar();
    }
    String articleTitle = selectRandomArticle("No results found for " + searchQuery);
    selectArticleByTitle(articleTitle);
    if (Platform.getInstance().isAndroid()) {
      addArticleToReadingList(nameOfFolder);
    } else {
      addArticlesToMySaved(login, password);
    }
    if (!Platform.getInstance().isMV()) {
      NavigationUI navigationUI = NavigationUIFactory.get(driver);
      navigationUI.comeBack();
    }
    return articleTitle;
  }

  public void selectArticleByTitle(String articleTitle) {
    String articleTitleXpath = getArticleXpathByName(articleTitle);
    this.waitForElementAndClick(
            (articleTitleXpath),
            "Cannot find '" + articleTitle + "' article in search",
            5);
  }

  public void addArticleToReadingList(String nameOfFolder) {
    selectMoreOptions();
    selectAddToReadingList();
    if (isFirstFolderCreation()) {
      selectGotIt();
      fillFolderNameInput(nameOfFolder);
    } else {
      selectFolder(nameOfFolder);
    }
  }

  public boolean isFirstFolderCreation() {
    List elements = driver.findElements(By.id(ADD_TO_MY_LIST_OVERLAY));
    return (elements.size() > 0);
  }

  public void selectFolder(String nameOfFolder) {
    if (folderAlreadyCreated(nameOfFolder)) {
      selectFolderToSave(nameOfFolder);
    } else {
      createNewFolderToSave(nameOfFolder);
    }
  }

  public String selectRandomArticle(String errorMessage) {
    List articles = articles();
    if (articles.size() > 0) {
      Random random = new Random();
      return (String) articles.get(random.nextInt(articles.size()));
    } else {
      throw new AssertionError(errorMessage);
    }
  }

  public boolean folderAlreadyCreated(String nameOfFolder) {
    String folderNameXpath = getFolderXpathByName(nameOfFolder);
    List elements = driver.findElements(
            By.xpath(folderNameXpath));
    return elements.size() > 0;
  }

  public ArrayList<String> articlesWithDescription() {
    List<String> searchingResults = new ArrayList<>();
    List<WebElement> elements = listOfArticleElements();
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.xpath(TEXT_VIEW_ELEMENT));
      String articleTitle = cells.get(0).getAttribute(TEXT_ATTRIBUTE);
      if (cells.size() < 2) {
        throw new AssertionError("The article entitled '" + articleTitle + "' has no description.");
      }
      String description = cells.get(1).getAttribute(TEXT_ATTRIBUTE);
      searchingResults.add(articleTitle);
      searchingResults.add(description);
    }
    return new ArrayList<String>(searchingResults);
  }
}