# business_customer_onboarding_system

This github repository includes both frontEnd & backEnd for customer onboarding system.

For Backend : 
There are few db configuration needed to run app.
Execute the "schema.sql" file
Steps to run : 
1. ` mvn clean install `
2. `mvn spring-boot:run`


For FrontEnd : 
1. `npm install`
2. `ng serve`

For Kafka
1. `bin/zookeeper-server-start.sh config/zookeeper.properties`
2. `bin/kafka-server-start.sh config/server.properties`
3. `bin/kafka-topics.sh --create --topic application-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1`
4. 
# Demo Screenshot

<img width="1266" alt="Screenshot 2025-07-08 at 4 31 53 PM" src="https://github.com/user-attachments/assets/0a7431b8-d24e-44d7-a115-2bf57fb3920a" />

<img width="1170" alt="Screenshot 2025-07-08 at 4 33 02 PM" src="https://github.com/user-attachments/assets/b9ce165c-114f-42ca-95e9-85d9c4e04646" />
<img width="1210" alt="Screenshot 2025-07-08 at 4 34 02 PM" src="https://github.com/user-attachments/assets/c9f7e986-1b59-4336-991b-63d66c60c8f1" />
<img width="1260" alt="Screenshot 2025-07-08 at 4 34 30 PM" src="https://github.com/user-attachments/assets/999e4d67-5f9b-4dc8-a838-58f5b606bee3" />
<img width="820" alt="Screenshot 2025-07-08 at 4 35 14 PM" src="https://github.com/user-attachments/assets/550d6f5c-77a2-4ac8-a912-c141880806d8" />



