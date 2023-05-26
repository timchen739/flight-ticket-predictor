## Project Name
Flight Ticket Predictor

### Description
This project aims to demonstrate the test strategies outlined in the [Data Science Play Book](https://docs.google.com/document/d/1gIAOjFwkNDfNYc1Bq2zI-kbGIXl4ciLFc4MYwhoxSiY/edit#heading=h.j7ixztvhnv4p) by implementing a series of tests for the Data Model

The test scenarios are as follows:

1. **Zero Dollar Lowest Prices**: The Model should **NOT** produce any result with zero dollar lowest prices
2. **Specific Routes Testing**: Run the model against certain routes(which could represent the most searched routes in a real life scenario) and ensure there are no instances of zero dollar lowest prices.
3. **Random Routes Testing**: Select routes randomly and execute the Data Model against these routes, verifying that there are no occurrences of zero dollar lowest prices.
4. **Logging and Rerunning**: Log any failed routes from the randomized testing and, if necessary, rerun the Data Model against those routes to replicate the results.

### Usage
All major tests for the model are located in the `TheDataModelTest.java` file.


***Note:***
- The model has been simulated by providing fabricated outputs based on the given scenario. The primary objective is to showcase the testing strategy rather than creating a comprehensive data model.
