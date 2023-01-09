#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
Feature: verify database
Background:
 Given user hit the url "http://databasetesting.s3-website-us-west-2.amazonaws.com/"
 Scenario: verify if the dropdown values are populated correctly from hrschema
When the values retrived  from database
Then it should match with the values on the application

Scenario: Validate if the data is correctly populated from hr_schema
When total number of departments present in each city extract from hrscema
Then it should match with cities present on application

Scenario: Validate the details with hr.schema
When  retrived data of third heighest salary employee from  hrscema
Then  the data should match with application 