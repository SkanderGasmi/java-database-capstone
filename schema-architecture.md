# Smart Clinic Management System – Architecture Design

## Architecture Overview

The Smart Clinic Management System follows a **three-tier architecture** that separates the application into Presentation, Application, and Data layers. This design improves scalability, maintainability, and deployment flexibility. Each tier can be independently developed, tested, and scaled without directly impacting the others.

Spring Boot is used as the core backend framework because it simplifies development while enforcing best practices. The system supports both **Spring MVC with Thymeleaf** for server-rendered dashboards and **RESTful APIs** for scalable client-server communication. Data is stored using a dual-database approach with **MySQL** for structured relational data and **MongoDB** for flexible document-based data such as prescriptions.

---

## Architecture Diagram

Below is a simplified architecture diagram illustrating how requests flow through the Smart Clinic Management System and how different technologies interact across the layers.

![Smart Clinic Architecture Diagram](architecture-diagram.png)

> **Note:**  
> Place the architecture image file (for example, `architecture-diagram.png`) in the root of your repository or update the path accordingly.

---

## Three-Tier Architecture Layers

### 1. Presentation Tier
- Thymeleaf templates for **Admin Dashboard** and **Doctor Dashboard**
- REST API consumers such as appointment modules, patient dashboards, and external clients
- Communicates with the backend using HTTP requests

### 2. Application Tier
- Spring Boot backend
- MVC Controllers for server-rendered views
- REST Controllers for JSON-based APIs
- Service layer containing business logic, validations, and workflow coordination

### 3. Data Tier
- **MySQL** for structured relational data:
  - Patient
  - Doctor
  - Appointment
  - Admin
- **MongoDB** for flexible, document-based data:
  - Prescription records

---

## Numbered Flow of Data and Control

1. The user accesses the application through a browser or REST API client.
2. Requests are routed to either Thymeleaf MVC controllers or REST controllers based on the endpoint.
3. Controllers validate input and delegate processing to the service layer.
4. The service layer applies business rules and coordinates workflows.
5. The service layer interacts with repositories for data access.
6. JPA repositories communicate with MySQL, while MongoDB repositories handle prescription documents.
7. Data is bound to application models and returned as either rendered HTML views or JSON responses.

---

## Key Benefits of This Architecture

- Clean separation of concerns
- Support for both web dashboards and API-based clients
- Flexible data storage using relational and document databases
- Easy containerization with Docker
- Seamless integration with CI/CD pipelines

---

## Conclusion

The Smart Clinic Management System architecture follows Spring Boot best practices and the three-tier model. By combining MVC, REST APIs, and dual databases, the system provides a robust, scalable, and production-ready foundation suitable for modern healthcare applications.
