package jhc.redsniff.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

//For use with twist
public abstract class SeleniumTesterFactory {

	private String browserCode;
	boolean enableNativeEvents;
	boolean javascriptEnabled;
	String htmlUnitBrowserVersion;

	protected WebDriver createDriver() throws AssertionError {
		WebDriver driver;
		if(browserCode.equals("ie"))
		    driver=new InternetExplorerDriver();
		else if(browserCode.equals("firefox")){
		    FirefoxProfile firefoxProfile = getDefaultFirefoxProfile();
		    driver=new FirefoxDriver(firefoxProfile);
		}
		else if(browserCode.equals("chrome"))
		    driver=new ChromeDriver();
		else if(browserCode.equals("htmlunit")){
		    driver =new HtmlUnitDriver(getHtmlUnitBrowserVersion());
		    if(javascriptEnabled)
		    ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
		}
		else {
		    throw new AssertionError("driverMode: " + browserCode + " not recognised");
		}
		return driver;
	}

	@SuppressWarnings("deprecation")
    private BrowserVersion getHtmlUnitBrowserVersion() {
		if (htmlUnitBrowserVersion.equals("FIREFOX_3_6"))
			return BrowserVersion.FIREFOX_3_6;
		else if(htmlUnitBrowserVersion.equals("FIREFOX_10"))
			return BrowserVersion.FIREFOX_10;
		else if(htmlUnitBrowserVersion.equals("FIREFOX_17"))
            return BrowserVersion.FIREFOX_17;
		else if(htmlUnitBrowserVersion.equals("CHROME"))
            return BrowserVersion.CHROME;
		else if(htmlUnitBrowserVersion.equals("INTERNET_EXPLORER_8"))
			return BrowserVersion.INTERNET_EXPLORER_8;
		else
			return BrowserVersion.getDefault();
	}

	private static FirefoxProfile getDefaultFirefoxProfile() {
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			return firefoxProfile;
	}

	public void setBrowser(String browserCode) {
		this.browserCode = browserCode;
	}

	

	public void setEnableNativeEvents(boolean enableNativeEvents) {
		this.enableNativeEvents = enableNativeEvents;
	}

	public void setJavascriptEnabled(boolean javascriptEnabled) {
		this.javascriptEnabled = javascriptEnabled;
	}

	public void setHtmlUnitBrowserVersion(String htmlUnitBrowserVersion) {
		this.htmlUnitBrowserVersion = htmlUnitBrowserVersion;
	}

	public abstract RedsniffWebDriverTester get();

	public void stop() {
		get().quit();
	}

}
