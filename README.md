# notification-app

Basic implementation of CQRS(Command Query Responsibility Separation] )

Framework:
- Lombok
- Hibernate

Services:
- Kafka
- PostgresSQL


# CQRS: 	Command Query Responsibility Separation

Separate the domain model and its persistence to handle write and read operations.

- Command: 		An intent to mutate the state of the domain model
- Aggregator:		Logically grouping different entities by binding them to an aggregate root
- Projection: 	Representing Domain Objects in different shapes and structures ( Read-only )
- Query: 			A query is an intent to get data
- Domain events: 	A specific change in the state of the domain model at a specific point of time

Pros:
- Separate domain models appropriate for write and read operations
- Don't have to create a complex domain model supporting Read AND Writes
- Select repositories that are individually suited for handling the complexities of the read and write operations
- Complements event-based programming models in a distributed architecture by providing a separation of concerns as well as simpler domain models

Cons:
- Only a complex domain model can benefit from the added complexity of this pattern
- Code duplication to some
- Separate repositories lead to problems of consistency
- Difficult to keep the write and read repositories in perfect sync always
- Settle for eventual consistency