# Weather Application

## How to run

1. Install all dependencies using maven
2. Start the application and provide the environment variable **API_URL**
    - _Option 1_: Supply the variable at runtime
    - _Option 2_: Updating the fallback value of the property _weather.api.url_
      in [application.properties](src/main/resources/application.properties)
3. The application will start on http://localhost:8080

## API Key
> All requests to the api must be made with a header **api-key: secret-api-key**
The api key is not real and is only included for demo purposes. You can [find the hardcoded value here](src/main/java/com/example/weather/app/service/ApiKeyValidator.java).

## System Requirements

- Java 21 installed

## Available endpoints

| HTTP Method | URL                                                                      |
|-------------|--------------------------------------------------------------------------|
| GET         | /api/weather/stations                                                    |
| GET         | /api/weather/station/_{id}_/latest-hour                                  |
| GET         | /api/weather/station/_{id}_/latest-day                                   |
| GET         | /api/weather/station/_{id}_/latest-months                                |
| GET         | /api/weather/station/_{id}_/filter?from=_{yyyy-MM-dd}_&to=_{yyyy-MM-dd}_ |

### Date formats
The dates should be formatted as yyy-MM-dd. For example, 2025-01-01.

## Terminology

| What       | Description                                                                                    |
|------------|------------------------------------------------------------------------------------------------|
| Parameter  | One "data type" such as wind or temperature and is represented by a number in the external api |
| Data Point | Data point is what I call one measurement which is a combination of date, value and quality    |



