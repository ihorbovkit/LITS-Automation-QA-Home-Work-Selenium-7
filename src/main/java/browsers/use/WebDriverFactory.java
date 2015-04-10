package browsers.use;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * A factory that returns a singleton of WebDriver object.
 */
public class WebDriverFactory {

	private static final String CHROME = "chrome";
	private static final String FIREFOX = "firefox";
	private static final String IE = "IE";
	private static final String OPERA = "opera";

	private static WebDriver webDriver;
	private static DesiredCapabilities dc;

	private WebDriverFactory() {

	}

	/**
	 * Gets the single instance of WebDriverFactory.
	 *
	 * @param browser the browser set in properties
	 * @return single instance of WebDriverFactory
	 * @throws Exception the exception of invalid browser property
	 */
	public static WebDriver getInstance(String browser) throws Exception {
		if (webDriver == null) {
			if (CHROME.equals(browser)) {
				
				setChromeDriver();
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				// set some options
				DesiredCapabilities dc = DesiredCapabilities.chrome();
				dc.setCapability(ChromeOptions.CAPABILITY, options);

				webDriver = new ChromeDriver(dc);
				
			} else if (FIREFOX.equals(browser)) {
				FirefoxProfile fp = new FirefoxProfile();
				dc = DesiredCapabilities.firefox();
				dc.setCapability(FirefoxDriver.PROFILE, fp);
				
				webDriver = new FirefoxDriver(dc);
				
			} else if (IE.equals(browser)) {
				System.setProperty("webdriver.ie.driver",
						".//src/main/resources/drivers/IE/IEDriverServer1.exe");
				webDriver = new InternetExplorerDriver();
			}else if (OPERA.equals(browser)) {
				/*OPERA is not supported yet*/
				throw new Exception("Opera browser is not supported yet...");
				//webDriver = new OperaDriver();
			}else
				throw new Exception("Invalid browser property set in configuration file");

		}

		return webDriver;
	}

	/**
	 * Kill driver instance.
	 * @throws Exception 
	 */
	public static void killDriverInstance() throws Exception {
		webDriver.close();
		webDriver.quit();
		webDriver = null;
	}

	/**
	 * Sets the chrome driver path for specific OS.
	 *
	 * @throws Exception the exception
	 */
	private static void setChromeDriver() throws Exception {
		String osName = System.getProperty("os.name").toLowerCase();
		StringBuffer chromeBinaryPath = new StringBuffer(
				"src/main/resources/drivers/");

		if (osName.startsWith("win")) {
			chromeBinaryPath.append("chrome-win/chromedriver.exe");
		} else if (osName.startsWith("lin")) {
			chromeBinaryPath.append("chrome-lin/chromedriver");
		} else if (osName.startsWith("mac")) {
			chromeBinaryPath.append("chrome-mac/chromedriver");
		} else
			throw new Exception("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.chrome.driver",
				chromeBinaryPath.toString());
	}

}