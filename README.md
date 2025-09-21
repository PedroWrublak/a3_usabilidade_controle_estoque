## A3 Usabilidade Controle de Estoque

## 🧑‍🏫 Students
- Pedro Vinicius Wrublak - 10724263009 - PedroWrublak
- Allex Maia - 10724261257 - AllexMaia
- Nicolas Evertom Duarte da Silva - 10724263203 - NicolasDuarte497
- Guilherme Custodio Capote - 10724269158 - Capote7
- Andrey Wesley Bastos de As - 10724268990 - AndreyWBS

## 📜 Description
This is a Java Web project for managing stock control. It uses JSP for the front-end and Java classes for business logic, with a connection to a MySQL database. The project structure includes source code, web resources, and build configurations.

## 📂 Project Structure
a3_usabilidade_controle_estoque/
│
├─ build/web/        # Compiled build of the web application
├─ nbproject/        # NetBeans project configuration files
├─ src/              # Java source files (models, DAOs, etc.)
├─ web/              # JSP pages and web resources (HTML, CSS, JS)
└─ build.xml         # Ant build file for compiling and deploying the project

## 🛠 Requirements
- Java JDK 17+
- Apache Tomcat 9+ (or any compatible servlet container)
- MySQL 8+
- NetBeans IDE (optional, recommended for easy build and deployment)
- MySQL Connector/J library added to the project

## 🚀 Features
  1. Clone the repository
    git clone https://github.com/PedroWrublak/a3_usabilidade_controle_estoque.git
    cd a3_usabilidade_controle_estoque

  2. Configure MySQL
     Create a database named controle_estoque (or your preferred name).
     Import the required tables (you should have a SQL file or create them manually in MySQL).

  3. Set up database connection
     Edit your connection file (usually under src/dao/ or a Connection.java class) with your MySQL credentials

  4. Build and deploy the project
     Using NetBeans:
     - Open the project.
     - Right-click → Clean and Build.
     - Right-click → Run.

  5. Access the application;
     Open your browser and navigate to:
     http://localhost:8080/<project_name_saved>/index.jsp

Notes
- Ensure that the MySQL Connector/J .jar file is added to your project libraries.
- Keep consistent line endings (LF vs CRLF) to avoid Git warnings.
- JSP files in the web/ folder are the main entry points for the web application.

License
- This project is open-source and free to use for educational purposes.

     

