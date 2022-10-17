# Short info on test task

### App usage description

API key must be present in headers in order to submit data.
Default header name: 'tt_token', default value: '12345'. Both can be changed in application.yml

Post request body example:
{
"betId" : "12344",
"number": 50,
"betAmount": 40.5
}

### STP test

There are 2 STP tests. One (RtpServiceTest) is pretty much a unit test running just against service
and the second one (RtpIntegrationTest) is of integration test level using WebTestClient. 
STP is provided as just console output. Tests can be launched separately as:

gradle clean test --tests com.yolo.testtask.domain.service.RtpServiceTest.rtpTest

gradle clean test --tests com.yolo.testtask.rest.RtpIntegrationTest.rtpTest 
