# springboot-spring-integration-with-redis
An example to display communication between microservices using spring integration and redis queues

The project consists of Producer, Consumer, and the messaging framework.

Download and import all the three projects.

Perform a Maven install on Messaging-library project (*important)

Install redis (see https://redis.io/ for download and installation steps) in your machine and start redis (redis-server)

Redis provides you a key-value in memory data structure, which can be used a queue, database, or cache. In this example we are going to use redis as a queue for its faster message processing abilities

Start both producer and consumer as regular springboot applications

send a post request from PostMan

http://localhost:8080/producer/sendPost

use the below json request

{
	"id": "12345",
	"firstName": "Sam",
	"lastName": "William",
	"age": "12",
	"gender": "male"
}

you can monitor the queues in the redis through command prompt (use redis-cli monitor)

This is a simple example explaining how to communicate between microservices using spring integration and redis queues. It will give the same request as output, but you can configure and use it.
