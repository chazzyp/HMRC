package tests;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class HMRC_Parvez {
	
		//Generate a random email using a number between 1 and 500,000
		static Random rand = new Random();
		static int n = rand.nextInt(500000);
		static String email = n + "@email.com"; //Something like 65421@email.com
		private static WebDriver driver;
	  
	  
		//Tests driven by main method
		public static void main(String[] args) throws InterruptedException 

		{
			setUp();
			createNewAccountAndLogIn(); //Test 1 + 2
			expensiveDressAddedToCart(); //Test 3
			logOutLogInCheckBasket(); //Test 4		
			tearDown();

		}

	  @Before
	  public static void setUp()
	  {
		//Chrome driver setup
		//YOU WILL NEED TO CHANGE THE PATH HERE...
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\cparv\\eclipse-workspace\\Drivers\\chromedriver.exe"); 
	    driver = new ChromeDriver();
	    //Wait code
	    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);   
	  }

	
	  //Test 1 and Test 2 - Create new account and log in
	private static void createNewAccountAndLogIn()
	  {
		//Navigate to page
	    driver.get("http://automationpractice.com/index.php");
	    driver.manage().window().maximize();
	    
	    //Sign-Up via Sign-In link
	    driver.findElement(By.linkText("Sign in")).click();
	    driver.findElement(By.id("email_create")).click();
	    driver.findElement(By.id("email_create")).clear();
	    
	    //Email must be unique - so increment the numbers by 1
	    driver.findElement(By.id("email_create")).sendKeys(email);
	    driver.findElement(By.xpath("//button[@id='SubmitCreate']/span")).click();

	    //Complete form credentials
	    driver.findElement(By.id("id_gender1")).click();
	    driver.findElement(By.id("customer_firstname")).click();
	    driver.findElement(By.id("customer_firstname")).clear();
	    driver.findElement(By.id("customer_firstname")).sendKeys("Charles");
	    driver.findElement(By.id("customer_lastname")).click();
	    driver.findElement(By.id("customer_lastname")).clear();
	    driver.findElement(By.id("customer_lastname")).sendKeys("Parvez");
	    driver.findElement(By.xpath("//form[@id='account-creation_form']/div/div[5]")).click();
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("passwd")).click();
	    driver.findElement(By.id("passwd")).clear();
	    driver.findElement(By.id("passwd")).sendKeys("12345");
	    
	    //Complete form address
	    driver.findElement(By.id("address1")).click();
	    driver.findElement(By.id("address1")).clear();
	    driver.findElement(By.id("address1")).sendKeys("100 High Road");
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys("Atlanta");
	    driver.findElement(By.id("id_state")).click();
	    new Select(driver.findElement(By.id("id_state"))).selectByVisibleText("Georgia");
	    driver.findElement(By.id("id_state")).click();
	    driver.findElement(By.id("postcode")).click();
	    driver.findElement(By.id("postcode")).clear();
	    driver.findElement(By.id("postcode")).sendKeys("00000");
	    driver.findElement(By.xpath("//form[@id='account-creation_form']/div[2]/p[9]")).click();
	    
	    //Complete form telephone
	    driver.findElement(By.id("phone")).click();
	    driver.findElement(By.id("phone")).clear();
	    driver.findElement(By.id("phone")).sendKeys("00000000000");
	    driver.findElement(By.id("phone_mobile")).click();
	    driver.findElement(By.id("phone_mobile")).clear();
	    driver.findElement(By.id("phone_mobile")).sendKeys("00000000000");
	    
	    //Submit Form
	    driver.findElement(By.xpath("//form[@id='account-creation_form']/div[4]")).click();
	    driver.findElement(By.xpath("//button[@id='submitAccount']/span")).click();
	    driver.findElement(By.xpath("//div[@id='center_column']/p")).click();

	    
	    //Assert test 1 and 2
	    if((driver.getPageSource().contains("Charles Parvez"))) {
	    	   System.out.println("Test 1 = Passed");
	    	   System.out.println("Test 2 = Passed");
	    	}else {
	    		System.out.println("Test 1 = Failed");
	    		System.out.println("Test 2 = Failed");
	    	}
	}

	  //Test 3 - Place most expensive dress in cart
		private static void expensiveDressAddedToCart() throws InterruptedException 
	  {
		  	//Click expensive dress
		    driver.findElement(By.xpath("(//a[contains(text(),'Dresses')])[5]")).click();

		    //Hover over image
		    Actions builder = new Actions(driver);
		    WebElement element = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[2]/div/div[1]/div/a[1]/img"));
		    builder.moveToElement(element).build().perform();
		    
		    //Click Add to cart button    
		    driver.findElement(By.xpath("//div[@id='center_column']/ul/li[2]/div/div[2]/div[2]/a/span")).click();
		    Thread.sleep(2000); //Wait to load
		        
		    driver.findElement(By.xpath("//div[@id='layer_cart']/div/div[2]/h2")).click();
		    Thread.sleep(2000); //Wait to load
		    
		  //Assert test 3
		    if((driver.getPageSource().contains("There is 1 item in your cart."))) {
		    	   System.out.println("Test 3 = Passed");
		    	}else {
		    		System.out.println("Test 3 = Failed");
		    	}

		    driver.findElement(By.xpath("//div[@id='layer_cart']/div/div[2]/div[4]/a/span")).click();

		   //Sign out
		    driver.findElement(By.linkText("Sign out")).click();
		}
	  
	  //Test 4 - Log back in and check for dress in cart (This fails)
		private static void logOutLogInCheckBasket() throws InterruptedException
	  {
		//Log back in again
		    driver.findElement(By.linkText("Sign in")).click();
		    driver.findElement(By.id("email")).click();
		    driver.findElement(By.id("email")).clear();
		    driver.findElement(By.id("email")).sendKeys(email);
		    driver.findElement(By.id("passwd")).click();
		    driver.findElement(By.id("passwd")).clear();
		    driver.findElement(By.id("passwd")).sendKeys("12345");
		    driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();
		    
		    //Go to shopping cart and check for dress
		    driver.findElement(By.xpath("//header[@id='header']/div[3]/div/div/div[3]/div/a/b")).click();
		    Thread.sleep(3000);
		    
			  //Assert test 4
		    if((driver.getPageSource().contains("Your shopping cart contains: 1 Product"))) {
		    	   System.out.println("Test 4 = Passed");
		    	}else {
		    		System.out.println("Test 4 = Failed");
		    	}
		  }
			

	  public static void tearDown()
	  {
		  String timeStamp = new SimpleDateFormat("dd/MM/YYYY HH:mm").format(Calendar.getInstance().getTime());
		  System.out.println("\n" + "Test Run: " + timeStamp );
			driver.quit(); //Close browser
	  }
	  
	  
}