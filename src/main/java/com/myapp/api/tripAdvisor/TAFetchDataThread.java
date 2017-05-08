package com.myapp.api.tripAdvisor;

import com.myapp.domain.activity.Activity;
import com.myapp.domain.category.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Tal on 27/04/2017.
 */
@Component
public class TAFetchDataThread implements Callable{

    public static final String SEARCH_BOX= "mainSearch";
    public static final String SEARCH_LOCATION="GEO_SCOPED_SEARCH_INPUT";

    public static final String ATTRACTIONS_LIST="FILTERED_LIST";

    public static final int MAX_NUMBER_OF_ATTRACTIONS_FOR_CATEGORY=1;


    String location;

    public TAFetchDataThread() {
    }
    public HashMap<Category,List<Activity> > call() throws Exception{
        return getCategoryAggregation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static HashMap<Category,List<Activity> > getCategoryAggregation(String location) throws Exception {
        System.out.println( "getCategoryAggregation " + location + " started !!!!!!!!!!");

        FirefoxDriver driver=initDriver();
        driver.get("https://www.tripadvisor.com");
        Thread.sleep(1000);

        //set category
        setCategory(driver);
        //set location
        setLocation(driver,location);
        //submmit
        //summitSearchLocation(driver);
        Thread.sleep(8000);
        WebElement attractions = driver.findElement(By.className("filter_list_0"));
        List<WebElement> childs = attractions.findElements(By.tagName("div"));

        //check for more
        clickOnMoreResaults(attractions);

        Thread.sleep(5000);
        // get cat names and link to attractions
        Map<String,String> categoriesList=buildCategorylist(childs);

        //build aggregation of category with attractions and rating
        HashMap<Category,List<Activity> > categoryAggregation=
                new HashMap<Category,List<Activity>>();

        for (Map.Entry<String, String> entry : categoriesList.entrySet())
        {
            driver.get(entry.getValue());
            List<Activity> attractionsList=getAttractions(driver,location);
            categoryAggregation.put(new Category(entry.getKey()),attractionsList);
        }



        System.out.println( "getCategoryAggregation " + location +" finished !!!!!!!!!!");

        driver.quit();
        return categoryAggregation;

    }
    private static String parseCategory(String categoryName){

        String[] tokens = categoryName.split(" ");
        String parsedCategoryName=tokens[0];
        return parsedCategoryName;
    }

    private static Double parseAttractionRating(String attractionRating){
        String[] tokens = attractionRating.split(" ");
        Double parsedAttractionRating= Double.valueOf(tokens[0]) *2;
        return parsedAttractionRating;

    }

    private  static  List<Activity>  getAttractions(FirefoxDriver driver, String location){
        List<Activity>  attractionsList = new ArrayList<>();
        WebElement attractions=driver.findElement(By.id(ATTRACTIONS_LIST));
        List<WebElement> childs = attractions.findElements(By.className("listing_info"));
        int index=0;
        for (WebElement child :childs ) {
            if( index>MAX_NUMBER_OF_ATTRACTIONS_FOR_CATEGORY){
                return attractionsList;
            }
            try {
                //tittle
                WebElement title = child.findElement(By.className("listing_title"));
                String titleText=title.getText();

                //rating
                WebElement rating = child.findElement(By.className("ui_bubble_rating"));
                String ratingString = rating.getAttribute("alt");
                Double parsedAttractionRating=parseAttractionRating(ratingString);

                //numbers of reviews
                WebElement reviews=child.findElement(By.className("listing_rating"));
                WebElement reviewsLink=reviews.findElement(By.tagName("a"));
                String numberOfReviews=reviewsLink.getText();
                int numberOfReviewsNumber=Integer.parseInt(numberOfReviews.split(" ")[0]);

                Activity activity = new Activity();
                activity.setActivityName(titleText);
                activity.setRating(parsedAttractionRating);
                activity.setNumbersOfReviews(numberOfReviewsNumber);
                activity.setLocation(location);
                attractionsList.add(activity);
                index++;
                System.out.println("added activity " +activity.toString());
            } catch (Exception e) {
                System.out.println("failed to get activity ");
            }
        }
        return attractionsList;
    }
    private static  Map<String,String> buildCategorylist(List<WebElement> childs){
        Map<String,String> categoriesList= new HashMap<>();
        for (WebElement child :childs) {
            try {
                WebElement link = child.findElement(By.tagName("a"));
                String linkUrl=link.getAttribute("href");
                String cat = link.getText();
                System.out.println("added category " +cat);
                String parsedCategory=parseCategory(cat);
                categoriesList.put(parsedCategory,linkUrl);
            } catch (Exception e) {
                System.out.println("failed to get category ");
            }
        }
        return categoriesList;
    }
    private static void clickOnMoreResaults(WebElement parentElement){

        try{
            WebElement linkClass = parentElement.findElement(By.className("reduced_height"));
            WebElement link = linkClass.findElement(By.className("taLnk"));
            link.click();
        }
        catch (Exception e1){

        }
    }
    private static  FirefoxDriver  initDriver(){
        //init
        System.setProperty("webdriver.gecko.driver", "C:/drivers/geckodriver.exe");

        System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-extensions");
//        options.addArguments("--test-type");
//        WebDriver driver = new ChromeDriver(options);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("webdriver.log.browser.file", "/dev/null");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.PROFILE, profile);

        FirefoxDriver driver = new FirefoxDriver(caps);

        //perform navigation to things to do
        return driver;
    }
    private static void setCategory(FirefoxDriver driver) throws Exception{
        WebElement searchField=driver.findElement(By.id(SEARCH_BOX));
        searchField.sendKeys("Things to Do");
        Thread.sleep(1000);
        searchField.sendKeys(Keys.ENTER);
    }
    private static void setLocation(FirefoxDriver driver,String location)  throws Exception{
        WebElement searchField1=driver.findElement(By.id(SEARCH_LOCATION));
        String[] locationArray=location.split(",");
        searchField1.sendKeys(locationArray[0]);
        Thread.sleep(3000);
        searchField1.sendKeys(locationArray[1]);
        searchField1.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
    }
}
