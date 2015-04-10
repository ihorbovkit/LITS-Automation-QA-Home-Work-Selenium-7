package email.subject.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import browsers.use.WebDriverFactory;

public class Test {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected boolean emailSearchResult;
	
	@BeforeMethod
	
	public void setUp() throws Exception {
		/* chose browser */
		driver = WebDriverFactory.getInstance("IE");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.get("https://google.com");
	}
	
	@org.testng.annotations.Test
	public void testGoogle(){
		wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit']")));
		driver.findElement(By.xpath("//input[@type='lst-ib']"));
	}
	@AfterMethod
	public void tearDown() throws Exception{
		WebDriverFactory.killDriverInstance();
	}
}
