package stepdef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

	static WebDriver driver;
	Connection conn;
	Statement stmt;

	@Before
	public void beforeScenario() throws ClassNotFoundException, SQLException {
		System.out.println("beforeSecnario()");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "system", "oracle");

		System.out.println("Start Test");

	}

	public static Connection getConnect() throws SQLException {
		return DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "system", "oracle");
	}

	@After
	public void afterScenario() {
		driver.quit();
	}

}
