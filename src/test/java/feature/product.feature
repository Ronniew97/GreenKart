Feature: Produce

Background:
Given user is on the GreenKart home page

Scenario: user should be able to add multiple items to cart
When user clicks add to cart button on produce he wants
And user clicks the cart logo 
Then user should see the correct items names
And user should see correct prices
