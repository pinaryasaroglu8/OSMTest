package Cucumber.OSM;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OsmTest {

	static WebDriver driver;
	
	String osmId;
	
	@Test
	public void OSMTest() {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\PinarYasaroglu\\Desktop\\Selenium\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://***.***.***.***:***/");
		Scanner sc= new Scanner(System.in);
		
		driver.findElement(By.cssSelector("input.UserNameComponent")).sendKeys("username");	// Enter user name
		driver.findElement(By.cssSelector("input.PasswordComponent")).sendKeys("password");	// Enter password
		driver.findElement(By.cssSelector("input.LoginText")).click();	// Click on Login button
	
		driver.findElement(By.xpath("//*[@id=\"BodyIndex\"]/form/table[1]/tbody/tr/td/p/input[7]")).click();	// Click on the change status radio button
		
		System.out.println("Please enter the Order ID: ");	// Get the Order ID from the user
		osmId= sc.nextLine();
		sc.close();
		
		recurringEvents();
		WebDriverWait w = new WebDriverWait(driver, 10);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.results")));	// Explicit wait for 10 seconds
		
		driver.findElement(By.cssSelector("input[value='/OrderManagement/control/assignOrder']")).click();	// Click on the Assign to User radio button
		Select s = new Select(driver.findElement(By.cssSelector("select[name='AssignToUser']")));	// Drop down menu
		s.selectByVisibleText("osmadmin");	//Chose "osmadmin" from the drop down menu
		driver.findElement(By.xpath("//input[@value='Update']")).click(); // Click on Update Button
		
		recurringEvents();
		
		driver.findElement(By.cssSelector("input[value='/OrderManagement/control/acceptOrder']")).click(); // Click on the Accept Order radio button
		driver.findElement(By.xpath("//input[@value='Update']")).click(); // Click on Update Button
		
		recurringEvents();	
		
		driver.findElement(By.cssSelector("input[value='/OrderManagement/control/completeOrder']")).click();	// Click on the Complete Order radio button
		Select s2 = new Select(driver.findElement(By.cssSelector("select[name='StatusID']")));	// Drop down menu
		s2.selectByVisibleText("Success");	// Chose Success from the drop down menu
		driver.findElement(By.xpath("//input[@value='Update']")).click();	// Click on Update Button
		
		driver.quit();	// Close the browser
		
	}

	public void recurringEvents() {

		Actions a = new Actions(driver); 
		
		driver.findElement(By.xpath("//input[@name='orderIDFilter']")).sendKeys(osmId);	// Enter the Order ID which was typed by the user

		driver.findElement(By.cssSelector("input.button")).click(); // Click on Update Button

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // Wait for 5 seconds to retrieve the order details from the system (Implicit wait)
		
		driver.findElement(By.xpath("//input[@name='move']")).click();
		a.moveToElement(driver.findElement(By.xpath("//input[@name='move']"))).click().build().perform();

	}

}
