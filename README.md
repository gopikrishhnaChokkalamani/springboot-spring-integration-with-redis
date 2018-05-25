# springboot-spring-integration-with-redis
An example to display communication between microservices using spring integration and redis queues

The project consists of Producer, Consumer, and the messaging framework.

Download and import all the three projects.

Perform a Maven install on Messaging-library project (*important)

Install redis in your machine and start redis (redis-server)

Start both producer and consumer as regular springboot applications

send a post request from PostMan of the producer,

http://localhost:8080/producer/sendPost
{
	"id": "12345",
	"firstName": "Sam",
	"lastName": "William",
	"age": "12",
	"gender": "male"
}

you can monitor the queues in the redis through command prompt (use redis-cli monitor)

This is a simple example explaining how to communicate between microservices using spring integration and redis queues. It will give the same request as output, but you can configure and use it.
