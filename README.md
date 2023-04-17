# spring-reactive-firestore
> The aim of this project is to demonstrate the Reactive Firestore implementation with Spring Boot in conjunction with
> Spring Webflux.
> 
> _Coded by [atromilen](https://github.com/atromilen)_

## About technology

** Spring Cloud GCP Starter **
Allow us to connect our Spring app with GCP resources using GCP Application Default Credentials (ADC), a very 
comfortable way to start session in our applications once the SDK is set correctly. 

**Spring Cloud GCP Starter Data Firestore**

This dependency allow us to extend from FirestoreReactiveRepository in order to get an interface that will provide basic
crud operations, very similar to ReactiveCRUDRepository provided by R2DBC. Also, this dependency provides Firestore and 
FirestoreTemplate clients to adapt our code to different situations, in special when FirestoreReactiveRepository 
interface are not enough. All the documentation can be found 
[here](https://cloud.spring.io/spring-cloud-static/spring-cloud-gcp/1.2.0.RC1/reference/html/#spring-data-reactive-repositories-for-cloud-firestore).

**Spring WebFlux**

This reactive-stack web framework was added later in version 5.0 to support fully non-blocking Reactive Streams back 
pressure. Spring WebFlux internally uses [Project Reactor](https://projectreactor.io/) and its publisher implementations 
[Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) and 
[Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html).

## Prerequisites
Make you sure you have installed the following software:
- JDK 11.
- GCP Account created.
- SDK CLI installed in your machine (gcloud cli). A brief guide about installation and configuration can be found 
[here](https://gist.github.com/atromilen/48195382877b02b2b1798aee7e5999ef).

## Getting Started

