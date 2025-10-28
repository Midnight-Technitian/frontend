# Midnight Technician

![midnight_technician logo](https://img.shields.io/badge/Mitnight%20Tecnician-Open%20Source-purple?style=for-the-badge&logo=java)

![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0--M3-brightgreen?style=flat-square&logo=spring)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)
![Build Status](https://img.shields.io/badge/Build-Passing-success?style=flat-square)

**Midnight Technician** is a comprehensive management system developed by Glabay Studios for tech repair and service companies. 
This web-based platform enables efficient management of customer service requests, employee operations, and device repair/service tracking across a wide range of technologies.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview
Midnight Technician is designed for tech repair and service environments where customers need support for various devices including:
- Desktop computers and laptops
- Tablets and smartphones
- Gaming consoles
- Servers and enterprise equipment

The system provides separate portals for customers and employees, ensuring streamlined operations and excellent customer service.

---
## Features
### Customer Dashboard
- **Profile Management**: Register and maintain customer profiles
- **Device Registration**: Add and manage information about devices needing service
- **Service Requests**: Submit new repair/service tickets
- **Ticket Tracking**: Monitor the status of ongoing service requests
- **Service History**: View the complete history of past tickets and services
### Employee Dashboard
- **Time Tracking**: Log and track work hours
- **Employee Information**: View personal details, start date, current role, and role history
- **Dashboard**: Overview of current workload and tasks
### Ticket Dashboard
- **Ticket Management**: Access and manage customer service requests
- **Dashboard**: Overview of current workload and tasks

---
## Tech Stack
| Category      | Technology      | Version  |
|---------------|-----------------|----------|
| **Backend**   | Spring Boot     | 4.0.0-M3 |
| **Framework** | Jakarta EE      | Latest   |
| **Database**  | Postgres SQL    | Latest   |
| **Security**  | Spring Security | 6        |
| **Build**     | Gradle          | 9.1.0+   |
| **Language**  | Java            | JDK 25   |

---
# Getting Started
1. Clone the repositories
    ```shell
    git clone https://github.com/Midnight-Technitian/frontend.git
    ```
2. Set up the database
    - Configure application.properties with your DB credentials
    - Configure your environment variables
    - Backend Project -> Services Module:
      - resources/data.sql to initialize the Postgres database
    - Connect to MongoDB from your Intellij Datasource panel
      - Create a new connection with the following details:
        - url: `mongodb://M_T_USER:M_T_PASSWORD@localhost:27042/midnight-technician?authSource=admin`
      - Create the databases in the MongoDB shell:
        - Use the command `use midnight-employee` to create the database
            - Use the command `db.createCollection("employee")` to create the collection
            - Use the command `db.createCollection("midnight_wmployee_sequences")` to create the collection
        - Use the command `use midnight-ticketing` to create the database
            - Use the command `db.createCollection("service_ticket")` to create the collection
            - Use the command `db.createCollection("midnight_technician_sequences")` to create the collection
3. Setting up the environment variables
    - Review the `.env.example` file for the required token names
    - Navigate to https://sentry.io/auth/login/ and on your dashboard create a new project
    - Create the project name as `midnight-technician` for the application conventions
    - From here you can find your AUTH_TOKEN, and DNS you need
    - For the SENTRY_ENVIRONMENT leave this as `development`
4. Running the Website:
    - Inside the Services View in Intellij, Click the + icon to add Spring Boot configuration
    - Run the website
    - Navigate to http://localhost to view the website

---
## Contributing
Contributions are welcome! Please clone the repository, make a new Branch, and create a pull request with your changes. 
Ensure your code meets the project's coding standards and includes proper documentation.

1. Clone the repository.
2. Create your feature branch:
    ```shell
        git checkout -b feature/AmazingFeature
    ```
3. Commit your changes:
    ```shell
        git commit -m 'Add some AmazingFeature'
    ```
4. Push to the branch:
    ```shell
        git push origin feature/AmazingFeature
    ```
5. Open a pull request.

---
### Contribution Guidelines
- Follow the existing code style and conventions
- Write clear, descriptive commit messages
- Add tests for new features when applicable
- Update documentation as needed
- Ensure your code builds without errors

### 📋 Contribution Checklist
- Fork and clone the repository
- Create a feature branch: `git checkout -b feature/amazing-feature`
- Make your changes following our coding standards
- Test thoroughly
- Commit: `git commit -m 'Add: Amazing feature'`
- Push: `git push origin feature/amazing-feature`
- Open a Pull Request

### 🎯 Code Standards
- ✅ Use meaningful variable and method names
- ✅ Follow Java naming conventions
- ✅ Leverage Lombok annotations appropriately
- ✅ Write comprehensive JavaDoc comments for public APIs

---
## Contributing
We welcome contributions to Midnight Technician! 
Follow these steps to contribute:

### How to Contribute
1. **Fork the Repository**
   - Click the "Fork" button at the top right of this repository
   - This creates a copy of the repository in your GitHub account

2. **Clone Your Fork**
    ```shell
      git clone https://github.com/YOUR-USERNAME/frontend.git
    ```
3. **Set Up the Original Repository as Upstream**
    ```shell
      git remote add upstream https://github.com/Midnight-Technitian/frontend.git
    ```
4. **Create a Feature Branch**
    ```shell
      git checkout -b feature/your-amazing-feature
    ```
5. **Make Your Changes**
    - Write your code following our coding standards
    - Test your changes thoroughly
    - Add documentation where necessary

6. **Commit Your Changes**
    ```shell
      git add .
      git commit -m "Add: Brief description of your feature"
    ```
7. **Push to Your Fork**
    ```shell
      git push origin feature/your-amazing-feature
    ```
8. **Create a Pull Request**
    - Go to your fork on GitHub
    - Click "New pull request"
    - Select the base repository
    - Select base branch: `main` (or the default branch)
    - Select your feature branch from your fork
    - Fill out the PR template with:
        - **Title**: Clear, descriptive title
        - **Description**: What changes you made and why
        - **Testing**: How you tested the changes
        - **Screenshots**: If applicable for UI changes

---
### Pull Request Guidelines
- **Before Creating a PR:**
    - Ensure your fork is up to date with the main repository
    - Run tests and verify everything works
    - Follow the existing code style and conventions

- **PR Description Should Include:**
    - What problem does this solve?
    - What changes were made?
    - How to test the changes?
    - Any breaking changes or special considerations?

- **After Submitting:**
    - Be responsive to feedback and review comments
    - Make requested changes promptly
    - Keep the PR focused and avoid unrelated changes

---
### Keeping Your Fork Updated
```shell
    # Fetch the latest changes from the original repository
    git fetch upstream
    
    # Switch to your main branch
    git checkout main
    
    # Merge the changes from upstream
    git merge upstream/main
    
    # Push the updates to your fork
    git push origin main
```

---
## Usage
### 🧑‍💼 Customer Portal
- **👤 Profile Management**: Register and maintain customer profiles
- **📱 Device Registration**: Add and manage information about devices needing service
- **🎫 Service Requests**: Submit new repair/service tickets
- **📊 Ticket Tracking**: Monitor the status of ongoing service requests
- **📚 Service History**: View the complete history of past tickets and services

### 👩‍💻 Employee Portal
- **⏰ Time Tracking**: Log and track work hours
- **ℹ️ Employee Information**: View personal details, start date, current role, and role history
- **🎯 Ticket Management**: Access and manage customer service requests
- **📈 Dashboard**: Overview of current workload and tasks

### 🔧 Device & Service Management
- **💻 Multi-Device Support**: Handle various device types from computers to gaming consoles
- **🔍 Service Tracking**: Monitor diagnostics, repairs, backups, and hardware upgrades
- **✅ Status Management**: Real-time updates on service progress

---
## License
This project is licensed under the MIT License—see the [LICENSE](LICENSE) file for details.

For more information about the MIT License, visit: https://opensource.org/licenses/MIT

---
## Acknowledgements
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Lombok](https://projectlombok.org/)
- [Bootstrap](https://getbootstrap.com/)
- [Font Awesome](https://fontawesome.com/)
- [jQuery](https://jquery.com/)
- [Chart.js](https://www.chartjs.org/)

---
### 🏢 **Developed by Glabay Studios**
**Made with ❤️ and coffee for the tech repair community**

