
# Green Shadow Farm Management System

Green Shadow is a comprehensive farm management system designed to streamline agricultural operations. The platform provides tools for managing fields, crops, equipment, staff, vehicles, and monitoring logs. With robust role-based access control and an intuitive interface, it enhances farm productivity and decision-making.

---

## 📑 API Documentation

Explore the detailed API documentation for Green Shadow using the following link:  
[Green Shadow API Documentation](https://documenter.getpostman.com/view/36195888/2sAYBYe9g4)

---

## 🚀 Features

### **Core Functionalities**
- **Field Management**: Add, update, and monitor field details with images and crop data.
- **Crop Management**: Track crop types, growth stages, and their association with fields.
- **Staff Management**: Manage staff roles, assignments, and responsibilities.
- **Equipment Management**: Allocate and track farm equipment with lifecycle insights.
- **Vehicle Management**: Maintain records of vehicles assigned for agricultural operations.
- **Monitoring Logs**: Record observations and updates related to fields, crops, and equipment.
- **Role-Based Access Control**: Permissions tailored to roles such as Manager, Administrator, and Scientist.

### **Additional Highlights**
- User-friendly SPA (Single Page Application) interface.
- Integration with a robust Spring Boot backend.
- Secure JWT-based authentication and authorization.
- Relational data modeling with MySQL.

---

## 🛠️ Tech Stack

### **Frontend**
- **HTML5, CSS3, JavaScript**  
- **Frameworks**: Bootstrap for responsive design  
- **AJAX & Fetch API**: For seamless API integration  

### **Backend**
- **Spring Boot**: REST API development with Hibernate for ORM  
- **MySQL**: Database management  
- **JWT**: Secure authentication  

---

## 📋 Installation Guide

### Prerequisites  
1. **Java Development Kit (JDK)** (version 17 or above).  
2. **MySQL Server**.  
3. **Postman** (optional, for API testing).  

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/pavithraniperera/Green-shadow-Project.git
   cd green-shadow
   ```
2. Configure the database in the `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/greenshadow
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. Access the application at `http://localhost:3000`.

---

## 🔐 Role-Based Permissions

| Role           | Permissions                                                                                  |
|-----------------|----------------------------------------------------------------------------------------------|
| **MANAGER**     | Full access to CRUD operations for all entities.                                            |
| **ADMINISTRATOR** | Cannot edit crop data, field data, or monitoring logs related to crop details.              |
| **SCIENTIST**   | Cannot modify staff, vehicle, or equipment data.                                            |

---

## 🗃️ Database Structure

The database design ensures efficient relationships between entities such as:
- **Fields and Crops**: Many-to-Many relationship.  
- **Staff and Vehicles**: One-to-Many relationship.  
- **Fields and Equipment**: One-to-Many relationship.  
- **Monitoring Logs**: Tracks updates across multiple entities.

---

## 📄 API Highlights

### Base URL
`http://localhost:8080/greenShadow/api/v1/`

### Sample Endpoints
- **User Signup**: `POST /auth/signup`  
- **Get All Fields**: `GET /fields`  
- **Add New Crop**: `POST /crops`  
- **Update Equipment**: `PUT /equipments/{equipmentId}`  

For full details, refer to the [API Documentation](https://documenter.getpostman.com/view/36195888/2sAYBYe9g4).


---

## 📃 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## 🌟 Acknowledgments

Special thanks to all  mentors for their guidance and support.

---

**Green Shadow - Revolutionizing Farm Management**
