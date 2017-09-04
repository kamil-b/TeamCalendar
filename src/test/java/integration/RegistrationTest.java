package integration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import common.entities.User;
import common.entities.enums.JobRole;

public class RegistrationTest {
	private WebDriver driver;
	private String baseUrl;
	private User user;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Gecko\\chromedriver2.exe");
		driver = new ChromeDriver();
		baseUrl = "www.localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		user = createFakeUser();
	}

	@Test
	public void registrationAndLogout() {
		driver.get(baseUrl + "/");
		assertEquals(driver.getTitle(), "Team Calendar");
		driver.findElement(By.cssSelector("li > a > span")).click();
		assertEquals(driver.getCurrentUrl(), "http://www.localhost:8080/user/registration");

		driver.findElement(By.id("name")).sendKeys(user.getName());
		driver.findElement(By.id("email")).sendKeys(user.getEmail());

		Select jobRoles = new Select(driver.findElement(By.name("role")));
		jobRoles.selectByVisibleText(user.getRole().toString());
		driver.findElement(By.id("password")).sendKeys(user.getPassword());
		driver.findElement(By.id("matchingPassword")).sendKeys(user.getPassword());
		driver.findElement(By.tagName("button")).click();
		assertEquals(driver.getCurrentUrl(), "http://www.localhost:8080/user/registration");
		assertEquals(driver.findElement(By.cssSelector("div > h4")).getText(), "You have been registered!");
		driver.findElement(By.linkText("Logout")).click();
		assertTrue(isElementPresent(By.linkText("Login")));

	}

	@Test(dependsOnMethods = { "registrationAndLogout" })
	public void loginAndLogout() throws InterruptedException {
		driver.get(baseUrl + "/home");
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.name("username")).sendKeys(user.getName());
		driver.findElement(By.name("password")).sendKeys(user.getPassword());
		driver.findElement(By.id("login_button")).click();
		assertTrue(isElementPresent(By.linkText("Logout")));
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
