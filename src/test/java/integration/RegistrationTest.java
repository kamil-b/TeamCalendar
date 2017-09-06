package integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.entities.User;
import common.entities.enums.JobRole;

public class RegistrationTest {
	private static final String REGISTRATION_PAGE_URL = "http://www.localhost:8080/user/registration";
	private static final String HOME_PAGE_URL = "http://www.localhost:8080/home";
	private static final String BASE_URL = "http://www.localhost:8080";
	private static final int TIMEOUT_SECONDS = 10;
	private WebDriver driver;
	private String baseUrl = BASE_URL;
	private User user;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Gecko\\chromedriver2.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(TIMEOUT_SECONDS, TimeUnit.SECONDS);
		user = createFakeUser();
	}

	@Test
	public void registrationAndLogout() {
		driver.get(baseUrl + "/");
		assertEquals(driver.getTitle(), "Team Calendar");
		driver.findElement(By.cssSelector("li > a > span")).click();
		assertEquals(driver.getCurrentUrl(), REGISTRATION_PAGE_URL);

		driver.findElement(By.id("name")).sendKeys(user.getName());
		driver.findElement(By.id("email")).sendKeys(user.getEmail());

		Select jobRoles = new Select(driver.findElement(By.name("role")));
		jobRoles.selectByVisibleText(user.getRole().toString());
		driver.findElement(By.id("password")).sendKeys(user.getPassword());
		driver.findElement(By.id("matchingPassword")).sendKeys(user.getPassword());
		driver.findElement(By.tagName("button")).click();
		assertEquals(driver.getCurrentUrl(), REGISTRATION_PAGE_URL);
		assertEquals(driver.findElement(By.cssSelector("div > h4")).getText(), "You have been registered!");
		driver.findElement(By.linkText("Logout")).click();
		assertTrue(isElementPresent(By.linkText("Login")));

	}

	@Test(dependsOnMethods = { "registrationAndLogout" })
	public void login() {
		driver.get(HOME_PAGE_URL);
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.name("username")).sendKeys(user.getName());
		driver.findElement(By.name("password")).sendKeys(user.getPassword());
		driver.findElement(By.id("login_button")).click();
		assertTrue(isElementPresent(By.linkText("Logout")));

	}

	@Test(dependsOnMethods = { "login" })
	public void addEventsWithoutSendingEmailToSuperior() {
		driver.get(HOME_PAGE_URL);
		driver.findElement(By.cssSelector("div.row >  form > button")).click();
		assertEquals(driver.getCurrentUrl(), "http://www.localhost:8080/user/" + user.getName() + "/events?");
		List<WebElement> elements = driver.findElements(By.className("weekday_table"));
		int index;
		for(WebElement e : elements){
			List<WebElement> radioButtons = e.findElements(By.tagName("label"));
			index = (int)(Math.random() * 4) + 1;
			radioButtons.get(index).click();
		}
		driver.findElement(By.cssSelector("div.container > div > form > div.row > div > button")).click();
		assertEquals(driver.getCurrentUrl(),"http://www.localhost:8080/user/" + user.getName() + "/addevents");
		driver.findElement(By.cssSelector("button.col-sm-10.subbutton.red")).click();
		assertEquals(driver.getCurrentUrl(), HOME_PAGE_URL);
	}

	@Test(dependsOnMethods = { "addEventsWithoutSendingEmailToSuperior" })
	public void logout() {
		driver.get("http://www.localhost:8080/console");
		driver.findElement(By.cssSelector("tr.login > td.login > input.button")).click();
		driver.findElement(By.name("sql")).sendKeys("delete user where name=" + user.getName());
		driver.findElement(By.cssSelector("body > form > input.button")).click();
		driver.findElement(By.linkText("Logout")).click();
		assertTrue(isElementPresent(By.linkText("Login")));
		
		
	}

	@AfterClass(alwaysRun = true)
	public void cleanUp() {
		driver.quit();
	}

	private User createFakeUser() {
		User user = new User(99l, "tester", "password123");
		user.setRole(JobRole.DEVELOPER);
		user.setEmail("tester@testing.com");
		return user;
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

}
