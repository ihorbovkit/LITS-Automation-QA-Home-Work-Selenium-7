package email.subject.test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import browsers.use.WebDriverFactory;

public class EmailPresentTest {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected boolean emailSearchResult;
	
	@BeforeMethod
	@Parameters ("browserName")
	public void setUp(String browserName) throws Exception {
		/* chose browser */
		driver = WebDriverFactory.getInstance(browserName);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.get("https://mail.google.com/mail/");
	}

	@Test
	public void searchingMailUsingSubject() throws InterruptedException {
		wait = new WebDriverWait(driver, 15);
		/* login to the email */
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='Email']")));
		/* login to the Email */
		driver.findElement(By.xpath("//*[@id='Email']")).clear();
		driver.findElement(By.xpath("//*[@id='Email']")).sendKeys("scop.test1");
		driver.findElement(By.xpath("//*[@id='Passwd']")).clear();
		driver.findElement(By.xpath("//*[@id='Passwd']")).sendKeys("mailtest");
		driver.findElement(By.xpath("//*[@id='signIn']")).click();
		/* searching letter using subject */
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//*[@id='gbqfq']")));
		driver.findElement(By.xpath("//*[@id='gbqfq']")).clear();
		driver.findElement(By.xpath("//*[@id='gbqfq']")).sendKeys("Hello User");
		driver.findElement(By.xpath("//*[@id='gbqfb']")).click();

		/* Checking for correct email */
		/*Assert.assertEquals(checkingSubject("Hello User"), true);*/
		Assert.assertTrue(checkingEmailSubject("Hello User"), "Email was found");
	}

	public Boolean checkingEmailSubject(String subjectText) {
		
		List<WebElement> arList = driver.findElements(By.xpath("//div [@class='y6']/span[@id]"));
		Iterator<WebElement> iterator = arList.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getText().equals(subjectText)){
				emailSearchResult = true;
				break;
				
			} else emailSearchResult = false;
		}
		return emailSearchResult;
	}

	@AfterMethod
	public void tearDown() throws Exception {
		if (driver != null) {
			WebDriverFactory.killDriverInstance();
		}
	}
}
