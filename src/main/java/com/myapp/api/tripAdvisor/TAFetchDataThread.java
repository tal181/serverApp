package com.myapp.api.tripAdvisor;

import com.myapp.Constants;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.category.Category;
import jdk.nashorn.internal.runtime.ECMAException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class TAFetchDataThread implements Callable{

    public static final String SEARCH_BOX= "mainSearch";
    public static final String SEARCH_LOCATION="GEO_SCOPED_SEARCH_INPUT";

    public static final String ATTRACTIONS_LIST="FILTERED_LIST";

    public static final String SUBMIT_HOTELS="SUBMIT_HOTELS";

    public static final int MAX_NUMBER_OF_ATTRACTIONS_FOR_CATEGORY=1;


    String location;

    @Autowired
    TAUtils taUtils;

    public TAFetchDataThread() {
    }
    public HashMap<Category,List<Activity> > call() throws Exception{
        System.out.println("in " + location + "!!!!!!!!!!!!!!!!!!!!!!!!");
        return getCategoryAggregation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static void main(String[] args) throws Exception{
        TAFetchDataThread thread= new TAFetchDataThread();
        HashMap<Category,List<Activity> > a= thread.getCategoryAggregation("New York City, New York");

        for (Map.Entry<Category, List<Activity>> entry : a.entrySet()) {
            try {
                System.out.println("Category is " + entry.getKey());
                System.out.println("value is " + entry.getValue().toString());
            } catch (Exception e) {

            }
        }
    }

    public  void decideBetweenPages(FirefoxDriver driver,String location) throws Exception{
        WebElement ele=null;
        try {
            ele=driver.findElementById(SUBMIT_HOTELS);
        }
        catch (Exception e){

        }
        if(ele!=null){
            WebElement globalAttractions=driver.findElementById("global-nav-attractions");
            globalAttractions.click();
            Thread.sleep(2000);
            WebElement textBox=driver.findElementByClassName("typeahead_input"); //todo need to fix
            setLocation(textBox,driver,location);

            //WebElement thingsToDo=driver.findElementById("SUBMIT_THINGS_TO_DO");
            //thingsToDo.click();
        }
        else{
            //set category
            setCategory(driver);
            //set location
            WebElement searchField=driver.findElement(By.id(SEARCH_LOCATION));
            setLocation(searchField,driver,location);
        }
    }
    public  HashMap<Category,List<Activity> > getCategoryAggregation(String location) throws Exception {
        System.out.println( "getCategoryAggregation " + location + " started !!!!!!!!!!");

        FirefoxDriver driver=initDriver();
        driver.get("https://www.tripadvisor.com");
        Thread.sleep(1000);

        //decide between pages
        decideBetweenPages(driver,location);


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
            //driver.navigate().back();
        }



        System.out.println( "getCategoryAggregation " + location +" finished !!!!!!!!!!");

        driver.quit();
        return categoryAggregation;

    }
    private  String parseCategory(String categoryName){

        String[] tokens = categoryName.split(" ");
        String parsedCategoryName=tokens[0];
        return parsedCategoryName;
    }

    private  Double parseAttractionRating(String attractionRating){
        String[] tokens = attractionRating.split(" ");
        Double parsedAttractionRating= Double.valueOf(tokens[0]) *2;
        return parsedAttractionRating;

    }

    private  void getActivityLocation(FirefoxDriver driver, Activity activity){
        try {
            WebElement locationAddress = driver.findElementByClassName("format_address");
            activity.setAddress(locationAddress.getText().split(": ")[1]);
        }
        catch (Exception e){
            System.out.println("failed to get  Address data " +activity.getActivityName()+e);
        }
        if(activity.getAddress()==null){
            try {
                WebElement locationAddress = driver.findElementByClassName("address");
                activity.setAddress(locationAddress.getText());

            }
            catch (Exception e){
                System.out.println("failed to get  Address data " +activity.getActivityName()+e);
            }
        }
    }
    private void activityPage(FirefoxDriver driver, Activity activity) throws Exception{
        String parentHandle = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
        }
        Thread.sleep(2000);
        getActivityLocation(driver,activity);

        try {
            WebElement suggestedDuration = driver.findElementByClassName("details_wrapper").findElement(By.className("detail"));//need to fix
            String suggestedDurationString=suggestedDuration.getText().split(": ")[1];
            Integer suggestedDurationValue=taUtils.parseSuggestedDuration(suggestedDurationString);
            activity.setSuggestedDuration(suggestedDurationValue);
        }
        catch (Exception e){
            System.out.println("failed to get suggested Duration data " +activity.getActivityName()+e);
            activity.setSuggestedDuration(Constants.DEFAULT_SUGGESTED_DURATION);
        }

        driver.close();
        //Thread.sleep(10000);
        driver.switchTo().window(parentHandle);
    }
    private    List<Activity>  getAttractions(FirefoxDriver driver, String location) throws Exception{
        List<Activity>  attractionsList = new ArrayList<>();
        WebElement attractions=driver.findElement(By.id(ATTRACTIONS_LIST));
        List<WebElement> childs = attractions.findElements(By.className("listing_info"));
        int index=0;
        for (WebElement child :childs ) {
            if( index>MAX_NUMBER_OF_ATTRACTIONS_FOR_CATEGORY){
                return attractionsList;
            }
            try {
                Activity activity = new Activity();
                //tittle
                WebElement title = child.findElement(By.className("listing_title"));
                String titleText=title.getText();
                activity.setActivityName(titleText);
                activity.setLocation(location);
                WebElement activityLink=title.findElement(By.tagName("a"));
                activityLink.click();

                //take data from activity page
                activityPage(driver,activity);
                //driver.close();
                //rating
                WebElement rating = child.findElement(By.className("ui_bubble_rating"));
                String ratingString = rating.getAttribute("alt");
                Double parsedAttractionRating=parseAttractionRating(ratingString);

                //numbers of reviews
                WebElement reviews=child.findElement(By.className("listing_rating"));
                WebElement reviewsLink=reviews.findElement(By.tagName("a"));
                String numberOfReviews=reviewsLink.getText();
                String tempReviews=numberOfReviews.split(" ")[0];
                int numberOfReviewsNumber=Integer.parseInt(tempReviews.replace(",",""));


                activity.setRating(parsedAttractionRating);
                activity.setNumbersOfReviews(numberOfReviewsNumber);

                attractionsList.add(activity);
                index++;
                System.out.println("added activity " +activity.toString());
            } catch (Exception e) {
                System.out.println("failed to get activity ");
            }
        }
       // driver.close();
        return attractionsList;
    }


    private   Map<String,String> buildCategorylist(List<WebElement> childs){
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
    private  void clickOnMoreResaults(WebElement parentElement){

        try{
            WebElement linkClass = parentElement.findElement(By.className("reduced_height"));
            WebElement link = linkClass.findElement(By.className("taLnk"));
            link.click();
        }
        catch (Exception e1){

        }
    }
    private   FirefoxDriver  initDriver(){
        //init
        System.setProperty("webdriver.gecko.driver", "C:/drivers/geckodriver.exe");

        System.setProperty("webdriver.chrome.driver", "C:/drivers/chromedriver.exe");

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-extensions");
//        options.addArguments("--test-type");
//        WebDriver driver = new ChromeDriver(options);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("webdriver.log.browser.file", "/dev/null");
        profile.setPreference("browser.privatebrowsing.autostart", true);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(FirefoxDriver.PROFILE, profile);

        FirefoxDriver driver = new FirefoxDriver(caps);

        //perform navigation to things to do
        return driver;
    }
    private  void setCategory(FirefoxDriver driver) throws Exception{
        WebElement searchField=driver.findElement(By.id(SEARCH_BOX));
        searchField.sendKeys("Things to Do");
        Thread.sleep(1000);
        searchField.sendKeys(Keys.ENTER);
    }
    private  void setLocation( WebElement searchField,FirefoxDriver driver,String location)  throws Exception{
        String[] locationArray=location.split(",");
        searchField.sendKeys(locationArray[0]);
        Thread.sleep(3000);
        searchField.sendKeys(locationArray[1]);
        searchField.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
    }
}
