package stepdef;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Stepdefinition {
	Connection conn = null;
	Statement stmt = null;
	ResultSet resultSet = null;
	WebDriver driver;
	int rowcount;
	int colcount;
	ArrayList<String> tcity;
	ArrayList<String> datacity;
	String emp_id;
	String emp_name;
	String emp_sal;

	@Given("user hit the url {string}")
	public void user_hit_the_url(String arg1) {
		Hooks.driver.get(arg1);

		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@When("the values retrived  from database")
	public void the_values_retrived_from_database() throws SQLException {
		stmt = Hooks.getConnect().createStatement();
		resultSet = stmt.executeQuery(
				"SELECT D.DEPARTMENT_NAME FROM HR.DEPARTMENTS D, HR.LOCATIONS L WHERE D.LOCATION_ID = L.LOCATION_ID AND L.COUNTRY_ID != 'US' ");
		ResultSetMetaData rsMetaData = resultSet.getMetaData();
		colcount = rsMetaData.getColumnCount();
		System.out.println(colcount);
		while (resultSet.next()) {
			System.out.println(resultSet.getString(1));
			rowcount++;
		}
		System.out.println(rowcount);

	}

	@Then("it should match with the values on the application")
	public void it_should_match_with_the_values_on_the_application() {
		// Hooks.driver.findElement(By.xpath("//select[@id='depart']"));
		WebElement element = Hooks.driver.findElement(By.xpath("//select[@id='depart']"));

		Select s = new Select(element);
		List<WebElement> alldepart = s.getOptions();
		System.out.println(alldepart.size());
		for (WebElement alloptions : alldepart) {
			System.out.println((alloptions.getText()));
		}
		Assert.assertEquals(rowcount, alldepart.size());

	}

	@When("total number of departments present in each city extract from hrscema")
	public void total_number_of_departments_present_in_each_city_extract_from_hrscema() throws SQLException {
		stmt = Hooks.getConnect().createStatement();
		resultSet = stmt.executeQuery(
				"select l.city, count(*) from HR.departments d, hr.locations l where d.location_id = l.location_id group by l.city ");
		ResultSetMetaData rsMetaData = resultSet.getMetaData();
		colcount = rsMetaData.getColumnCount();
		datacity = new ArrayList<String>();
		while (resultSet.next()) {
			System.out.println("From database");
			System.out.print(resultSet.getString(1));
			System.out.print(resultSet.getString(2));
			datacity.add("resultSet.getString(2)");

			rowcount++;
		}
		System.out.println("rowcount " + rowcount);
		System.out.println(rowcount);
		System.out.println(datacity);

	}

	@Then("it should match with cities present on application")
	public void it_should_match_with_cities_present_on_application() {
		List<WebElement> uirows = Hooks.driver
				.findElements(By.xpath("//table[@class='adap-table']/tbody//tr//child::td[2]"));
		tcity = new ArrayList<String>();
		System.out.println("no: of rows " + uirows);
		Assert.assertEquals(rowcount, uirows);
		for (WebElement row : uirows) {
			tcity.add(row.getText());

		}
		System.out.println(tcity);
		datacity.removeAll(tcity);
		System.out.println(datacity);
		Assert.assertEquals(datacity.get(0), "Seattle");

	}

	@When("retrived data of third heighest salary employee from  hrscema")
	public void retrived_data_of_third_heighest_salary_employee_from_hrscema() throws SQLException {
		stmt = Hooks.getConnect().createStatement();
		resultSet = stmt.executeQuery("select * from ("
				+ "select 'Employee_id:'||e.employee_id, 'Name:'||e.first_name||' '||e.last_name employee_name,'Salary:'||e.salary,dense_rank() over(order by salary desc) r from hr.employees e "
				+ ") data where data.r = 3");

		ResultSetMetaData rsMetaData = resultSet.getMetaData();
		colcount = rsMetaData.getColumnCount();
		while (resultSet.next()) {
			System.out.println("From database");
			String emp_id = resultSet.getString(1);
			String emp_name = resultSet.getString(2);
			String emp_sal = resultSet.getString(3);

			System.out.print(emp_id);
			System.out.print(emp_name);
			System.out.print(emp_sal);

			rowcount++;
		}
		System.out.println("rowcount " + rowcount);

	}

	@Then("the data should match with application")
	public void the_data_should_match_with_application() {
		String uiemp_id = Hooks.driver.findElement(By.xpath("//table[@class='salary_employee']/tbody/tr/td[1]"))
				.getText();
		Assert.assertEquals(uiemp_id, "Employee_id:" + emp_id);
		String uiemp_name = Hooks.driver.findElement(By.xpath("//table[@class='salary_employee']/tbody/tr/td[2]"))
				.getText();
		Assert.assertEquals(uiemp_name, "Employee_name:" + emp_name);
		String uiemp_sal = Hooks.driver.findElement(By.xpath("//table[@class='salary_employee']/tbody/tr/td[3]"))
				.getText();
		Assert.assertEquals(uiemp_sal, "Employee_sal:" + emp_sal);

		// Write code here that turns the phrase above into concrete actions

	}

}
