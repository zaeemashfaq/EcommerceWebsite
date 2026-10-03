-- One schema per microservice (database-per-service pattern)
CREATE DATABASE IF NOT EXISTS productservice;
CREATE DATABASE IF NOT EXISTS userservice;
CREATE DATABASE IF NOT EXISTS paymentservice;
CREATE DATABASE IF NOT EXISTS notificationservice;
GRANT ALL PRIVILEGES ON productservice.* TO 'serviceAdmin'@'%';
GRANT ALL PRIVILEGES ON userservice.* TO 'serviceAdmin'@'%';
GRANT ALL PRIVILEGES ON paymentservice.* TO 'serviceAdmin'@'%';
GRANT ALL PRIVILEGES ON notificationservice.* TO 'serviceAdmin'@'%';
FLUSH PRIVILEGES;
