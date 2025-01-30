<p align="center"><h1 align="center"><code>❯ OneClickBooking</code></h1></p>
<p align="center">
	<em>🌐 **OneClickBooking: Secure Scalability in a Single Click!**

Embrace the power of open-source with our robust, scalable, and secure application built on Kotlin, Spring Boot, and a myriad of essential dependencies. Experience the ease of one-click booking services, backed by data persistence, RESTful web services, and containerized testing.</em>
</p>
<p align="center">
	<!-- local repository, no metadata badges. --></p>
<p align="center">Built with the tools and technologies:</p>
<p align="center">
	<img src="https://img.shields.io/badge/JetBrains-000000.svg?style=default&logo=JetBrains&logoColor=white" alt="JetBrains">
	<img src="https://img.shields.io/badge/GitHub-181717.svg?style=default&logo=GitHub&logoColor=white" alt="GitHub">
	<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=default&logo=Gradle&logoColor=white" alt="Gradle">
	<img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=default&logo=MySQL&logoColor=white" alt="MySQL">
	<img src="https://img.shields.io/badge/Docker-2496ED.svg?style=default&logo=Docker&logoColor=white" alt="Docker">
	<img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=default&logo=Kotlin&logoColor=white" alt="Kotlin">
</p>
<br>

##  Table of Contents

- [ Overview](#-overview)
- [ Features](#-features)
- [ Project Structure](#-project-structure)
  - [ Project Index](#-project-index)
- [ Getting Started](#-getting-started)
  - [ Prerequisites](#-prerequisites)
  - [ Installation](#-installation)
  - [ Usage](#-usage)
  - [ Testing](#-testing)

---

##  Overview

**"OneClickBooking" - A Scalable, Secure, and Open-Source Spring Boot Application Built with Kotlin:

Solve complex booking workflows effortlessly! Our OneClickBooking application addresses the need for a robust, RESTful web service solution. Key features include AOP, Data JPA, Security, Validation, Web, Redis, JSON tools, Lombok, Actuator, and Testcontainers, ensuring seamless data persistence and containerized testing.

Targeting developers and businesses seeking efficient booking systems, this project offers MySQL database connectivity, JUnit testing frameworks, and adheres to the Apache License v2.0. The Gradle build configuration, settings, and startup scripts streamline development and deployment, making OneClickBooking an ideal choice for your next project!

---

##  Features

|      | Feature         | Summary       |
| :--- | :---:           | :---          |
| ⚙️  | **Architecture**  | The project is built using a Spring Boot application with Kotlin as the primary language. It utilizes various dependencies such as AOP, Data JPA, Security, Validation, Web, Redis, JSON tools, Lombok, Actuator, and Testcontainers. MySQL database connectivity and JUnit testing frameworks are also included. The project aims to build a robust, scalable, and secure application with support for data persistence, RESTful web services, and containerized testing. |
| 🔩 | **Code Quality**  | The code quality is maintained using best practices in Kotlin programming language. The project follows clean coding principles and adheres to the SOLID principles. The codebase is well-structured with clear naming conventions, comments, and documentation. |
| 📄 | **Documentation** | The primary language for documentation is Kotlin. The project includes a total of 183 Kotlin files (`.kt`), 2 Gradle files (`build.gradle`, `settings.gradle`), 3 YAML files (`docker-compose.yaml`, `application.yaml`, `application-test.yaml`), and 9 SQL files. The project also uses Gradle as the package manager for managing dependencies. |
| 🔌 | **Integrations**  | The project integrates with Docker for containerization, using the `docker-compose.yaml` file in the source directory's resources. It also utilizes various libraries such as FasterXML, JetBrains, Jackson, Mockito, Com, Github, SpringFramework, org, NimbusDS, Boot, Kotlin Security, Testcontainers, MySQL, and module dependencies. |
| 🧩 | **Modularity**    | The project is modular in nature, with a clear separation of concerns between different components. Each module has its specific functionality, ensuring maintainability and scalability. |
| 🧪 | **Testing**       | Testing is performed using JUnit testing frameworks within the project. Gradle commands are used to run tests (`gradle test`). |
| 🛡️ | **Security**      | The project includes security-related dependencies such as Spring Security. |

---

##  Project Structure

```sh
└── /
    ├── build.gradle
    ├── gradle
    │   └── wrapper
    ├── gradlew
    ├── gradlew.bat
    ├── settings.gradle
    └── src
        ├── main
        └── test
```


###  Project Index
<details open>
	<summary><b><code>/</code></b></summary>
	<details> <!-- __root__ Submodule -->
		<summary><b>__root__</b></summary>
		<blockquote>
			<table>
			<tr>
				<td><b><a href='/build.gradle'>build.gradle</a></b></td>
				<td>- This Gradle build configuration sets up a Spring Boot application using Kotlin and various dependencies such as AOP, Data JPA, Security, Validation, Web, Redis, JSON tools, Lombok, Actuator, and Testcontainers<br>- It also includes MySQL database connectivity and JUnit testing frameworks<br>- The project aims to build a robust, scalable, and secure application with support for data persistence, RESTful web services, and containerized testing.</td>
			</tr>
			<tr>
				<td><b><a href='/gradlew.bat'>gradlew.bat</a></b></td>
				<td>- This Gradle startup script for Windows initiates and configures the execution of the Gradle build tool within a project<br>- It sets default JVM options, locates the Java executable, defines the classpath, and launches the Gradle command with specified arguments to manage and compile the project's source code<br>- The script adheres to the Apache License v2.0 and is part of a larger open-source project structure.</td>
			</tr>
			<tr>
				<td><b><a href='/settings.gradle'>settings.gradle</a></b></td>
				<td>- The 'settings.gradle' file within the OneClickBooking project establishes its unique name and identity, serving as a foundation for Gradle build configuration<br>- This setup facilitates efficient management of dependencies, tasks, and other aspects essential to the project's development and deployment.</td>
			</tr>
			</table>
		</blockquote>
	</details>
	<details> <!-- src Submodule -->
		<summary><b>src</b></summary>
		<blockquote>
			<details>
				<summary><b>main</b></summary>
				<blockquote>
					<details>
						<summary><b>kotlin</b></summary>
						<blockquote>
							<details>
								<summary><b>source</b></summary>
								<blockquote>
									<details>
										<summary><b>code</b></summary>
										<blockquote>
											<details>
												<summary><b>oneclickbooking</b></summary>
												<blockquote>
													<table>
													<tr>
														<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/OneClickBookingApplication.kt'>OneClickBookingApplication.kt</a></b></td>
														<td>- The provided Kotlin file, `OneClickBookingApplication.kt`, serves as the entry point for the OneClickBooking application<br>- It initializes and runs the Spring Boot application, orchestrating the overall functionality of the system, which facilitates one-click booking services<br>- This architecture allows seamless integration with various backend services, databases, and APIs to provide a user-friendly booking experience.</td>
													</tr>
													</table>
													<details>
														<summary><b>advice</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/advice/LoggingAroundAdvice.kt'>LoggingAroundAdvice.kt</a></b></td>
																<td>- The provided Kotlin file, `LoggingAroundAdvice`, is an aspect-oriented programming (AOP) implementation within the OneClickBooking project<br>- It logs the execution time of methods in the controller layer by wrapping them with a timing mechanism<br>- This logging helps monitor and optimize the performance of the application's critical operations.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>annotation</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/annotation/AccountOwnerOrAdmin.kt'>AccountOwnerOrAdmin.kt</a></b></td>
																<td>- In the OneClickBooking project, this annotation (AccountOwnerOrAdmin) is used to restrict access to specific functions within the codebase<br>- It ensures that only users with the 'ROLE_ADMIN' or those whose ID matches the authenticated user's ID can execute these functions<br>- This mechanism helps maintain data integrity and security by enforcing appropriate permissions for account-related operations.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/annotation/AdminOnly.kt'>AdminOnly.kt</a></b></td>
																<td>- In the OneClickBooking project, the AdminOnly annotation is utilized to restrict access to specific functions within the codebase<br>- This annotation ensures that only users with the 'ADMIN' role are allowed to execute these functions, thereby maintaining a secure and controlled environment for administrative tasks.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/annotation/BookingOwnerOrAdmin.kt'>BookingOwnerOrAdmin.kt</a></b></td>
																<td>- The `BookingOwnerOrAdmin` annotation, located within the OneClickBooking project structure, is a security measure that ensures only booking owners or administrators can access certain functions in the system<br>- This annotation utilizes Spring Security's PreAuthorize feature to check if the user is either the owner of a specific booking or an administrator before granting access.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/annotation/ImageRelatedEntityAuthorOrAdmin.kt'>ImageRelatedEntityAuthorOrAdmin.kt</a></b></td>
																<td>- In the OneClickBooking project, this Kotlin annotation file defines two custom annotations, `ImageRelatedEntityAuthorOrAdmin` and `ImageRelatedEntityAuthorOrAdminById`<br>- These annotations are used to secure functions by checking if the user is either the entity author or an admin for image-related entities<br>- The `authAnnotationService` is responsible for determining access rights based on the provided parameters, ensuring proper data security within the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/annotation/ReviewOwnerOrAdmin.kt'>ReviewOwnerOrAdmin.kt</a></b></td>
																<td>- In the OneClickBooking project, this Kotlin annotation, `ReviewOwnerOrAdmin`, is utilized to enforce access control on review functions<br>- It ensures that only the owner of a review or an admin can execute these functions at runtime, enforcing security and maintaining data integrity.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>auth</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/auth/CustomAuthenticationToken.kt'>CustomAuthenticationToken.kt</a></b></td>
																<td>- CustomAuthenticationToken class within the OneClickBooking project facilitates custom authentication by extending the UsernamePasswordAuthenticationToken provided by Spring Security<br>- This class allows for additional data, such as user ID, to be associated with an authenticated principal, enhancing security and user management functionality in the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/auth/JwtAuthenticationSuccessHandler.kt'>JwtAuthenticationSuccessHandler.kt</a></b></td>
																<td>- In the OneClickBooking project, the `JwtAuthenticationSuccessHandler` class is responsible for generating and sending JWT tokens upon successful authentication<br>- This custom handler creates access and refresh tokens based on user details and granted authorities, then sends them as a response to the client<br>- The overall architecture ensures secure authentication and authorization in the application.</td>
															</tr>
															</table>
															<details>
																<summary><b>filter</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/auth/filter/BearerTokenFilter.kt'>BearerTokenFilter.kt</a></b></td>
																		<td>- The provided Kotlin file, `BearerTokenFilter`, is a part of the authentication module within the OneClickBooking project<br>- It validates and verifies JWT (JSON Web Token) tokens in HTTP requests to ensure secure access to protected resources<br>- By checking token signature, expiration time, and type, it sets the appropriate security context for authenticated users, enhancing overall system security.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/auth/filter/JwtAuthenticationFilter.kt'>JwtAuthenticationFilter.kt</a></b></td>
																		<td>- The `JwtAuthenticationFilter` class serves as a security layer within the OneClickBooking project architecture<br>- It validates user authentication during login requests, utilizing JWT (JSON Web Token) for secure token-based authentication<br>- Upon successful validation, it hands over control to the `JwtAuthenticationSuccessHandler`, which processes the request further<br>- In case of an error or failure, it delegates to the `SimpleUrlAuthenticationFailureHandler`<br>- This filter ensures secure access to the API by validating user credentials before granting access.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
														</blockquote>
													</details>
													<details>
														<summary><b>config</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/config/CustomTimeStampDiffFunction.kt'>CustomTimeStampDiffFunction.kt</a></b></td>
																<td>- CustomTimeStampDiffFunction is a Hibernate extension that customizes the timestamp difference calculation in the MySQL database within the OneClickBooking project architecture<br>- This function allows for flexible and precise time difference calculations, enhancing the system's ability to handle complex date-related operations efficiently.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/config/MessageResolverConfig.kt'>MessageResolverConfig.kt</a></b></td>
																<td>- Configures the MessageResolver within the OneClickBooking application using Spring Framework<br>- This setup allows for dynamic message handling, enabling flexible and customizable communication across various services in the system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/config/MessageSourceConfig.kt'>MessageSourceConfig.kt</a></b></td>
																<td>- Configures message resource handling within the OneClickBooking project architecture<br>- The provided Kotlin file, MessageSourceConfig.kt, sets up a Spring bean (messageSource()) to manage application messages, reloading them from the classpath ("classpath:Messages")<br>- This facilitates internationalization and localization of the application's text resources.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/config/RedisCacheConfig.kt'>RedisCacheConfig.kt</a></b></td>
																<td>- The RedisCacheConfig.kt file within the oneclickbooking project is a configuration class that sets up a caching system using Spring and Redis<br>- This caching system allows for data persistence across sessions, improving application performance by reducing database queries and increasing response times<br>- The Jackson2JsonRedisSerializer and RedisSerializationContext are used to serialize and deserialize the cached data in JSON format.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/config/SecurityConfig.kt'>SecurityConfig.kt</a></b></td>
																<td>- The provided Kotlin file, `SecurityConfig`, configures the security settings for the OneClickBooking application<br>- It sets up authentication and authorization, including password encoding, CORS configuration, and filter chain management<br>- Additionally, it enables method-level security checks (pre-post) and establishes stateless session management<br>- This ensures secure access to the application's resources while allowing for seamless user interactions.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>controller</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/BookingController.kt'>BookingController.kt</a></b></td>
																<td>- Manages the booking system within the OneClickBooking application by handling HTTP requests related to bookings<br>- It provides endpoints for creating, retrieving, filtering, and updating bookings, as well as deleting them with appropriate access controls (admin-only or booking owner)<br>- The BookingController class interacts with the BookingService to perform these operations.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/EmployeeController.kt'>EmployeeController.kt</a></b></td>
																<td>- The `EmployeeController.kt` file within the OneClickBooking project is responsible for handling HTTP requests related to employees<br>- It provides endpoints for retrieving employee details, filtering employees based on provided criteria, and getting all employees<br>- This class interacts with the EmployeeService to fetch and process data, ultimately returning responses in a structured format.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/ImageController.kt'>ImageController.kt</a></b></td>
																<td>- The `ImageController.kt` file is a crucial component of the OneClickBooking project architecture<br>- It manages image-related operations, such as retrieval, creation, and deletion, for various entities within the system<br>- By interacting with the ImageService, it enables efficient handling of images associated with different parent entities, ensuring seamless integration of visual content across the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/ReviewController.kt'>ReviewController.kt</a></b></td>
																<td>- The `ReviewController.kt` file within the OneClickBooking project manages and handles RESTful API endpoints related to user reviews<br>- It enables creating, retrieving, updating, and deleting reviews, as well as filtering review summaries based on specific criteria<br>- This class interacts with the ReviewService for actual data processing and validation, ensuring a seamless interaction between users and the review system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/ScheduleController.kt'>ScheduleController.kt</a></b></td>
																<td>- The `ScheduleController.kt` file within the OneClickBooking project manages API endpoints related to scheduling services<br>- It receives requests from clients, validates them, and delegates processing to the ScheduleService class<br>- The outcome is a scheduled response in the desired format, enabling seamless interaction with the scheduling system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/ServicePointController.kt'>ServicePointController.kt</a></b></td>
																<td>- The `ServicePointController.kt` file within the OneClickBooking project is responsible for handling HTTP requests related to Service Points<br>- It interacts with the ServicePointService to retrieve specific Service Point details or a list of all Service Points, and returns these data as responses in a RESTful API format<br>- This facilitates easy access to Service Point information for other parts of the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/TreatmentController.kt'>TreatmentController.kt</a></b></td>
																<td>- The `TreatmentController.kt` file within the OneClickBooking project manages API endpoints related to treatments<br>- It retrieves treatment data from the service layer and returns it as a response in various formats, such as individual treatments or all available treatments grouped by service point<br>- This facilitates easy access to treatment information for users and other parts of the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/controller/UserController.kt'>UserController.kt</a></b></td>
																<td>- The `UserController.kt` file within the OneClickBooking project manages user-related operations<br>- It provides RESTful endpoints to register, retrieve, update, and delete users<br>- Additionally, it handles user authentication through refresh tokens, ensuring secure access to user data<br>- This class interacts with the UserService for business logic and JwtService for token management.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>dto</b></summary>
														<blockquote>
															<details>
																<summary><b>other</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/other/FilterCriteria.kt'>FilterCriteria.kt</a></b></td>
																		<td>- The `FilterCriteria` class and associated enums in the provided file serve to define flexible filtering criteria within the OneClickBooking application<br>- These filters can be applied across various entities such as bookings, reviews, and employees, based on keys like employee, service point, date, user, status, etc<br>- The operation parameter allows for a range of comparison types, enabling users to customize their searches effectively.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/other/FilterDto.kt'>FilterDto.kt</a></b></td>
																		<td>- The `FilterDto` class within the OneClickBooking project facilitates filtering and sorting data<br>- It accepts a list of filter criteria, an operation (AND or OR), and a sort option (ASC, DESC, or closest date)<br>- This enables users to customize their search results effectively, enhancing usability and efficiency in the application.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/other/UserCredentialsDto.kt'>UserCredentialsDto.kt</a></b></td>
																		<td>- The `UserCredentialsDto` class within the OneClickBooking project serves to encapsulate user authentication data, including email, password, and roles<br>- This data transfer object facilitates secure communication between client and server during login processes, ensuring proper user authorization in the system.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
															<details>
																<summary><b>request</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/ImageCreateDto.kt'>ImageCreateDto.kt</a></b></td>
																		<td>- The provided Kotlin file, `ImageCreateDto`, is a data transfer object (DTO) used within the OneClickBooking project to handle image upload requests<br>- It accepts an image file and associated metadata such as parent entity type and ID, facilitating seamless integration of user-uploaded images into the system's data model.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/RefreshTokenRequestDto.kt'>RefreshTokenRequestDto.kt</a></b></td>
																		<td>- In the OneClickBooking project architecture, the `RefreshTokenRequestDto` class serves as a data transfer object (DTO) for incoming refresh token requests<br>- It accepts and validates a refresh token string, facilitating secure user authentication and session management within the application.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/ReviewCreateDto.kt'>ReviewCreateDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the `ReviewCreateDto` class serves as a data transfer object (DTO) for creating review requests<br>- It accepts user input in the form of rating, text comment, and booking ID, which are then used to create new reviews within the system<br>- This facilitates seamless communication between the client and server, ensuring efficient handling of user feedback on bookings.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/ReviewUpdateDto.kt'>ReviewUpdateDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the `ReviewUpdateDto` class serves to encapsulate user input when updating a review<br>- It accepts two properties: rating and text, each with specific constraints<br>- This data transfer object (DTO) facilitates efficient communication between the client and server by ensuring that only validated data is processed during review updates.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/ScheduleRequestDto.kt'>ScheduleRequestDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the ScheduleRequestDto class serves as a data transfer object (DTO) for incoming requests related to scheduling treatments<br>- It accepts a FilterDto and an Int representing the treatmentId, facilitating efficient processing of scheduling-related operations within the system.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/UserCreateDto.kt'>UserCreateDto.kt</a></b></td>
																		<td>- This Kotlin data class, `UserCreateDto`, serves as a request model for user registration within the OneClickBooking project architecture<br>- It validates and structures user input data such as name, surname, email, and password according to specific constraints, ensuring secure and consistent data format<br>- The default constructor provides a basic user instance for testing purposes.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/UserUpdateDto.kt'>UserUpdateDto.kt</a></b></td>
																		<td>- This Kotlin data class, `UserUpdateDto`, is utilized within the OneClickBooking project to receive user update requests<br>- It validates and structures user input data such as name, surname, email, password, and old password, ensuring compliance with specified length, format, and uniqueness rules<br>- The validation includes checks for email domain uniqueness, password complexity, and requirement of an old password during certain updates.</td>
																	</tr>
																	</table>
																	<details>
																		<summary><b>booking</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/booking/BookingCreateDto.kt'>BookingCreateDto.kt</a></b></td>
																				<td>- The `BookingCreateDto` class within the OneClickBooking project facilitates the creation of new bookings by accepting required parameters such as date, service point ID, employee ID, and treatment ID<br>- This data is validated and structured according to specific rules before being processed further in the system, ensuring seamless booking operations.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/booking/BookingUpdateDto.kt'>BookingUpdateDto.kt</a></b></td>
																				<td>- The `BookingUpdateDto` class within the OneClickBooking project facilitates updating booking details asynchronously<br>- It accepts updates for date, service point ID, employee ID, and treatment ID, ensuring data integrity through Jakarta Validation constraints<br>- This class is a crucial component in maintaining flexibility and efficiency within the overall booking management system architecture.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/request/booking/BookingUpdateStatusDto.kt'>BookingUpdateStatusDto.kt</a></b></td>
																				<td>- This Kotlin data class, `BookingUpdateStatusDto`, is a part of the OneClickBooking project's request DTO layer<br>- It serves to encapsulate and validate updates to the booking status in the system, allowing for efficient and consistent handling of booking status changes within the application architecture.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
															<details>
																<summary><b>response</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/AccessTokenResponseDto.kt'>AccessTokenResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the AccessTokenResponseDto class serves as a data transfer object (DTO) for handling access tokens in responses<br>- It encapsulates an access token string, facilitating secure and efficient communication between client and server applications within the system.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/EmployeeResponseDto.kt'>EmployeeResponseDto.kt</a></b></td>
																		<td>- The `EmployeeResponseDto` class within the OneClickBooking project is designed to structure and return employee data as a response from an API call<br>- It includes essential attributes such as ID and username, with an optional description field<br>- This simplified representation of employee data facilitates easy consumption by client applications in a consistent format.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/ImageResponseDto.kt'>ImageResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the ImageResponseDto class is a data transfer object (DTO) responsible for handling image responses<br>- It encapsulates an image's unique identifier, binary data, associated entity type, and parent ID, facilitating efficient communication between services and ensuring proper data association within the system.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/ReviewResponseDto.kt'>ReviewResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project architecture, the `ReviewResponseDto` class serves as a data transfer object (DTO) for review responses<br>- It encapsulates essential information about a customer's review of a booking, including the rating, date, text, and user details<br>- Additionally, it can optionally include employee details if the reviewed service was provided by an employee<br>- This DTO facilitates efficient communication between different components of the application, ensuring seamless data exchange for displaying reviews to users.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/ReviewSummaryResponseDto.kt'>ReviewSummaryResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the `ReviewSummaryResponseDto` class is utilized to consolidate and structure review data<br>- It aggregates a list of individual review details (via `ReviewResponseDto`), calculates the average rating, and tallies the total number of reviews received for an item or service<br>- This information is then returned as a response to clients seeking an overview of the ratings and feedback associated with their search results.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/ScheduleResponseDto.kt'>ScheduleResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the ScheduleResponseDto class within the `src\main\kotlin\source\code\oneclickbooking\dto\response` package is designed to encapsulate a list of available time slots (LocalDateTime objects) as response data<br>- This facilitates efficient communication between services and presentation layers regarding scheduling information, enhancing the overall user experience by providing a clear view of free time slots for booking purposes.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/ServicePointResponseDto.kt'>ServicePointResponseDto.kt</a></b></td>
																		<td>- The `ServicePointResponseDto` class within the OneClickBooking project is designed to structure and return service point data as a response<br>- It encapsulates essential information about each service point, including its unique identifier, name, location, email, and phone number, facilitating efficient communication between services and users in the system.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/TreatmentResponseDto.kt'>TreatmentResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project architecture, the TreatmentResponseDto class serves to structure and convey treatment details as a data transfer object (DTO)<br>- This DTO encapsulates essential information about each treatment, including its unique identifier, name, description, price, and duration<br>- By using this DTO, the application can efficiently exchange treatment data between different components while maintaining consistency and clarity.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/UserResponseDto.kt'>UserResponseDto.kt</a></b></td>
																		<td>- In the OneClickBooking project, the UserResponseDto class within the `src\main\kotlin\source\code\oneclickbooking\dto\response` package is designed to structure and return user data as a response object<br>- This class encapsulates essential user information such as ID, name, surname, and email for efficient handling and communication between application layers.</td>
																	</tr>
																	</table>
																	<details>
																		<summary><b>booking</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/booking/BookingDetailedResponseDto.kt'>BookingDetailedResponseDto.kt</a></b></td>
																				<td>- The provided Kotlin file, `BookingDetailedResponseDto`, is part of a larger OneClickBooking project architecture<br>- It defines a data transfer object (DTO) for detailed booking responses, encapsulating information about the booking's ID, date, status, associated user, service point, treatment, and review<br>- This DTO facilitates efficient communication between different components within the system by providing a standardized format for exchanging booking-related data.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/booking/BookingResponseDto.kt'>BookingResponseDto.kt</a></b></td>
																				<td>- The `BookingResponseDto` class within the OneClickBooking project is responsible for constructing and returning structured data responses for booking operations<br>- It encapsulates essential booking details such as ID, date, status, user, service point, employee (optional), treatment (optional), and review (optional) in a convenient format, facilitating efficient communication between client and server.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>innerDtos</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/dto/response/innerDtos/all.kt'>all.kt</a></b></td>
																				<td>- In the OneClickBooking project, this Kotlin file defines data transfer objects (DTOs) for various entities within the system<br>- The UserDetails, EmployeeDetails, and ServicePointDetails classes encapsulate essential attributes of users, employees, and service points respectively, facilitating efficient data exchange between different components of the application.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
														</blockquote>
													</details>
													<details>
														<summary><b>event</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/event/ReviewEvent.kt'>ReviewEvent.kt</a></b></td>
																<td>- In the OneClickBooking project architecture, the `ReviewEvent.kt` file serves as a custom event class that encapsulates review data and extends the Spring's `ApplicationEvent`<br>- This event is triggered when a new review is submitted within the system, facilitating efficient handling of user feedback across various components in the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/event/ReviewListener.kt'>ReviewListener.kt</a></b></td>
																<td>- The `ReviewListener` class within the OneClickBooking project is a Spring component that listens for ReviewEvents and manages cache eviction<br>- It ensures that when a review event occurs, any cached data related to the reviewed item is removed from memory, promoting efficient data retrieval in real-time scenarios.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>exception</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/FileProcessingException.kt'>FileProcessingException.kt</a></b></td>
																<td>- In the OneClickBooking project, the `FileProcessingException` class serves as a custom exception type to handle errors that occur during file processing tasks<br>- This helps maintain application stability by ensuring proper error reporting and handling within the system architecture.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/GlobalExceptionHandler.kt'>GlobalExceptionHandler.kt</a></b></td>
																<td>Manages error handling across the OneClickBooking application by providing customized responses to various exceptions, ensuring user-friendly error messages and appropriate HTTP status codes are returned.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/InvalidFilterKeyException.kt'>InvalidFilterKeyException.kt</a></b></td>
																<td>- In the OneClickBooking project, the `InvalidFilterKeyException` class serves as a custom exception to handle invalid filter key inputs<br>- This helps maintain data integrity and ensures consistent user experience by providing error messages when incorrect filter keys are used in queries.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/InvalidFilterOperationException.kt'>InvalidFilterOperationException.kt</a></b></td>
																<td>- In the OneClickBooking project, the `InvalidFilterOperationException` class serves as a custom exception to handle invalid filter operations within the application<br>- This helps maintain data integrity and ensures consistent user experience by providing meaningful error messages when unexpected filter operation scenarios occur.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/InvalidRefreshTokenException.kt'>InvalidRefreshTokenException.kt</a></b></td>
																<td>- In the OneClickBooking project, this Kotlin exception class, InvalidRefreshTokenException, is utilized to handle and manage exceptions related to invalid refresh tokens<br>- This helps maintain secure and reliable user sessions within the application architecture.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/JwtAuthenticationException.kt'>JwtAuthenticationException.kt</a></b></td>
																<td>- In the OneClickBooking project, this Kotlin class, `JwtAuthenticationException`, serves as a custom exception to handle authentication errors related to JWT (JSON Web Token) in the application's security layer<br>- It leverages Spring Security's AuthenticationException and MessageResolverHolder for localization purposes, ensuring user-friendly error messages are displayed when authentication fails.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/LocalizedException.kt'>LocalizedException.kt</a></b></td>
																<td>- In the OneClickBooking project architecture, the LocalizedException class serves as a custom exception handling mechanism<br>- It allows for error messages to be localized and dynamic by utilizing a MessageResolverHolder, which retrieves messages based on provided keys and arguments<br>- This facilitates internationalization and improves user experience by providing error messages in the appropriate language.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/LocalizedIllegalArgument.kt'>LocalizedIllegalArgument.kt</a></b></td>
																<td>- In the OneClickBooking project, the `LocalizedIllegalArgument` class serves as a custom exception handling mechanism to manage errors with localized messages<br>- This facilitates user-friendly error reporting across various languages and regions, enhancing the overall user experience.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/exception/RecordNotFoundException.kt'>RecordNotFoundException.kt</a></b></td>
																<td>- In the OneClickBooking project, this Kotlin class, `RecordNotFoundException`, is designed to handle exceptions when a requested record cannot be found within the system<br>- It leverages an external utility, `ExceptionMessages`, to localize error messages, ensuring user-friendly and consistent error reporting across the application.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>mapper</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/BookingMapper.kt'>BookingMapper.kt</a></b></td>
																<td>- The `BookingMapper.kt` file within the OneClickBooking project is responsible for converting data between business entities (Bookings, Users, ServicePoints, etc.) and Data Transfer Objects (DTOs)<br>- This mapping facilitates seamless communication between the application's layers, ensuring consistent data representation throughout the system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/EmployeeMapper.kt'>EmployeeMapper.kt</a></b></td>
																<td>- The `EmployeeMapper.kt` file within the OneClickBooking project is a utility class that facilitates data transformation between the Employee model and its corresponding response DTO (Data Transfer Object)<br>- This mapping ensures seamless communication between the application's business logic layer and the presentation layer, enhancing the overall system architecture by providing a clean and consistent interface for data exchange.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/ImageMapper.kt'>ImageMapper.kt</a></b></td>
																<td>- The `ImageMapper.kt` file within the OneClickBooking project is responsible for converting image data between different formats<br>- It translates MultipartFile objects, typically received from client requests, into Image entities to be stored in the database<br>- Conversely, it maps Image objects retrieved from the database into ImageResponseDto format, which is returned to the client<br>- This facilitates seamless communication between the application's frontend and backend while handling image data efficiently.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/ReviewMapper.kt'>ReviewMapper.kt</a></b></td>
																<td>- The `ReviewMapper.kt` file within the OneClickBooking project is responsible for converting data between domain models and DTOs (Data Transfer Objects)<br>- It maps review entities to response DTOs, allowing the application to return structured review information in a user-friendly format<br>- Additionally, it handles the creation of new reviews by mapping incoming DTOs to review entities, ensuring proper data validation and association with related booking and user details.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/ServicePointMapper.kt'>ServicePointMapper.kt</a></b></td>
																<td>- The `ServicePointMapper.kt` file within the OneClickBooking project is responsible for transforming complex ServicePoint objects into simpler, API-friendly ServicePointResponseDto objects<br>- This facilitates efficient data exchange between the application and external services, enhancing the overall system's flexibility and maintainability.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/TreatmentMapper.kt'>TreatmentMapper.kt</a></b></td>
																<td>- The `TreatmentMapper.kt` file within the OneClickBooking project is responsible for transforming complex Treatment objects into simpler, API-friendly response DTOs (Data Transfer Objects)<br>- This mapping facilitates efficient communication between the application and external services by providing a consistent data structure.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/mapper/UserMapper.kt'>UserMapper.kt</a></b></td>
																<td>- The `UserMapper.kt` file within the OneClickBooking project is responsible for converting data between different formats, primarily between DTOs (Data Transfer Objects) and User entities<br>- It maps incoming request data to User objects for creation or updates, and vice versa for response generation<br>- Additionally, it transforms User objects into UserCredentialsDto for authentication purposes, and handles role mapping as well.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>model</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/Booking.kt'>Booking.kt</a></b></td>
																<td>- In the OneClickBooking project, this Kotlin class `Booking` represents a booking entity within the system<br>- It stores details such as date, status, associated user, service point, employee, treatment, and review<br>- The purpose of this model is to manage reservations for services offered by the application, enabling users to schedule appointments with specific employees or service points.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/Employee.kt'>Employee.kt</a></b></td>
																<td>- The `Employee.kt` file within the OneClickBooking project defines an Employee entity, which is a model representing staff members<br>- This class establishes relationships with various other entities such as bookings, availabilities, service points, and treatments<br>- The purpose of this code is to manage employee data, including their unique identifiers, usernames, descriptions, and associations with related resources in the system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/EmployeeAvailability.kt'>EmployeeAvailability.kt</a></b></td>
																<td>- In the OneClickBooking project, the EmployeeAvailability model class defines an entity that represents an employee's working hours on specific days of the week<br>- This data is persisted in a database table and is associated with an employee through a many-to-one relationship<br>- The purpose of this model is to manage employee schedules efficiently, enabling seamless booking operations within the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/Image.kt'>Image.kt</a></b></td>
																<td>- The provided Kotlin file defines an `Image` entity model within the OneClickBooking project<br>- This model is used to store and manage binary image data associated with various entities such as Reviews, Employees, or Service Points<br>- The Image class includes a unique identifier, image data in byte array format, parent type (Review, Employee, or Service Point), and parent ID for referencing the related entity instance<br>- This model facilitates efficient storage and retrieval of images within the OneClickBooking application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/Review.kt'>Review.kt</a></b></td>
																<td>- In the OneClickBooking project, the `Review.kt` file defines a model class representing customer reviews<br>- This model is annotated as an Entity and mapped to the 'review' table in the database<br>- Each review includes a rating, date, text, and a one-to-one relationship with a Booking<br>- The Review class also provides default and custom constructors for creating new reviews, ensuring data integrity through validation constraints and unique identifiers<br>- These customer reviews help maintain a record of user experiences and contribute to the overall quality assessment of the services offered by OneClickBooking.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/Role.kt'>Role.kt</a></b></td>
																<td>- In the OneClickBooking project, the Role.kt file defines a model class representing user roles within the system<br>- The Role entity is annotated as a database table and can take on two predefined values: USER or ADMIN<br>- This class also establishes a many-to-many relationship with the User entity, indicating that multiple users can have the same role and each role can be associated with multiple users.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/ServicePoint.kt'>ServicePoint.kt</a></b></td>
																<td>- The `ServicePoint.kt` file within the OneClickBooking project defines a ServicePoint entity, which represents a physical location offering services<br>- This class is annotated as a JPA (Java Persistence API) entity and mapped to the 'service_point' table in the database<br>- Each ServicePoint has attributes like name, location, email, and phone number<br>- It also maintains a list of associated bookings and employees through one-to-many relationships<br>- This class serves as the foundation for managing service points within the OneClickBooking application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/ServicePointEmployee.kt'>ServicePointEmployee.kt</a></b></td>
																<td>- The `ServicePointEmployee.kt` file within the OneClickBooking project defines a model class that represents an association between a Service Point and an Employee<br>- This class is used to establish relationships between service locations and their staff members, enabling efficient management of employee assignments across various service points in the system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/Treatment.kt'>Treatment.kt</a></b></td>
																<td>- In the OneClickBooking project, the Treatment.kt file defines a data model for treatments offered by the service<br>- Each treatment is associated with a unique identifier, name, description, price, and duration<br>- The class also maintains relationships with bookings and employees, enabling tracking of which treatments are being booked and who provides them<br>- This model facilitates efficient management and organization of available treatments within the system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/model/User.kt'>User.kt</a></b></td>
																<td>- This Kotlin file defines the User model within the OneClickBooking project architecture<br>- It represents a user entity with properties such as name, surname, email, password, roles, and bookings<br>- The User class is annotated as an Entity and mapped to the 'user' table in the database<br>- The purpose of this code is to manage user data, including authentication and authorization details, enabling users to interact with the OneClickBooking system effectively.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>repository</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/BookingRepository.kt'>BookingRepository.kt</a></b></td>
																<td>- The `BookingRepository.kt` file is a Spring Data JPA repository that manages booking data within the OneClickBooking application<br>- It provides methods to query, retrieve, and update bookings based on specific criteria such as service point ID, employee ID, date, and status<br>- Additionally, it offers functionality for marking expired pending bookings as completed<br>- This repository is a crucial component in ensuring seamless booking operations within the OneClickBooking system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/EmployeeAvailabilityRepository.kt'>EmployeeAvailabilityRepository.kt</a></b></td>
																<td>- The `EmployeeAvailabilityRepository` interface, located within the OneClickBooking project structure, serves as a data access layer to manage EmployeeAvailability records persistently<br>- This repository leverages Spring Data JPA to interact with the underlying database, enabling efficient handling of employee availability schedules for seamless service booking in the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/EmployeeRepository.kt'>EmployeeRepository.kt</a></b></td>
																<td>- The `EmployeeRepository` interface, located within the OneClickBooking project, is a Spring Data JPA repository that manages interactions with the Employee entity in the database<br>- It provides methods to retrieve employees based on specific criteria such as service point ID and treatment ID, or by employee ID along with associated data like availabilities, service point associations, and treatments<br>- This facilitates efficient querying and retrieval of employee information within the OneClickBooking application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/ImageRepository.kt'>ImageRepository.kt</a></b></td>
																<td>- The `ImageRepository.kt` file within the OneClickBooking project is a data access layer that interacts with the database to manage image entities<br>- It provides methods for retrieving images associated with specific parent types and IDs, facilitating efficient image management in the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/ReviewRepository.kt'>ReviewRepository.kt</a></b></td>
																<td>- The `ReviewRepository.kt` file within the OneClickBooking project serves as a data access layer for managing review entities<br>- By implementing Spring Data JPA interfaces, it facilitates CRUD operations on reviews and provides additional query capabilities through the JpaSpecificationExecutor interface<br>- This repository is an integral part of the application's architecture, enabling users to submit, retrieve, and manage reviews related to various services offered by OneClickBooking.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/RoleRepository.kt'>RoleRepository.kt</a></b></td>
																<td>- The `RoleRepository.kt` file within the OneClickBooking project is a repository interface that manages database operations related to Role entities<br>- It enables efficient retrieval and manipulation of role data, including finding roles by their names, adhering to the JPA (Java Persistence API) standards for Spring-based applications<br>- This facilitates secure user authentication and authorization within the OneClickBooking system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/ServicePointEmployeeRepository.kt'>ServicePointEmployeeRepository.kt</a></b></td>
																<td>- The `ServicePointEmployeeRepository` interface, located within the OneClickBooking project structure, serves as a data access layer to manage ServicePointEmployee entities using Spring Data JPA<br>- This abstraction facilitates efficient CRUD operations on ServicePointEmployees in the application's database.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/ServicePointRepository.kt'>ServicePointRepository.kt</a></b></td>
																<td>- The `ServicePointRepository` interface, located within the OneClickBooking project structure, serves to manage interactions with ServicePoints, a key entity in the system<br>- It leverages Spring Data JPA to efficiently retrieve and manipulate ServicePoint data, including associated EmployeeAssociations and their respective Treatments<br>- This repository enables seamless access to comprehensive ServicePoint information for various use cases within the OneClickBooking application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/TreatmentRepository.kt'>TreatmentRepository.kt</a></b></td>
																<td>- The `TreatmentRepository` interface, located within the OneClickBooking project structure, is a data access layer that facilitates interaction with the Treatment database entity in the application<br>- It enables retrieval of all treatments associated with a specific service point by querying the database using Spring Data JPA and Hibernate<br>- This allows for efficient management and organization of treatment records within the OneClickBooking system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/repository/UserRepository.kt'>UserRepository.kt</a></b></td>
																<td>- The `UserRepository.kt` file within the OneClickBooking project is a data access layer that manages user entities<br>- It leverages Spring Data JPA to interact with the database, providing methods like finding users by email and checking if a user exists by email address<br>- This repository ensures secure and efficient handling of user data in the system.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>service</b></summary>
														<blockquote>
															<details>
																<summary><b>declaration</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/EmployeeService.kt'>EmployeeService.kt</a></b></td>
																		<td>- The `EmployeeService.kt` file within the OneClickBooking project serves as an interface to manage employee data<br>- It provides methods for retrieving specific employees, listing all employees, and filtering employees based on provided criteria using a FilterDto object<br>- This interface is part of the service layer in the application's architecture, enabling seamless interaction with the underlying data storage system.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/ServicePointService.kt'>ServicePointService.kt</a></b></td>
																		<td>- The `ServicePointService.kt` file within the OneClickBooking project defines an interface for a service that manages ServicePoints<br>- It provides methods to retrieve individual ServicePoint details (get) and a list of all ServicePoints (getAll), thereby enabling seamless access to service locations in the application architecture.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/TreatmentService.kt'>TreatmentService.kt</a></b></td>
																		<td>- The `TreatmentService` interface, located within the OneClickBooking project structure, defines a set of methods to interact with treatment data<br>- It enables retrieval of individual treatments, all available treatments, and treatments specific to a service point, ultimately facilitating seamless management and access to treatment information.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/UserService.kt'>UserService.kt</a></b></td>
																		<td>- The `UserService` interface, located within the OneClickBooking project structure, defines a set of operations to manage user data<br>- It allows for creating, deleting, updating, and retrieving user information as well as finding a user's ID by their email address, thereby enabling seamless user interaction within the application.</td>
																	</tr>
																	</table>
																	<details>
																		<summary><b>booking</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/booking/BookingMappingResolver.kt'>BookingMappingResolver.kt</a></b></td>
																				<td>- The `BookingMappingResolver` interface in the OneClickBooking project maps database model entities to their respective DTO (Data Transfer Object) counterparts<br>- This facilitates seamless data exchange between service layers and the presentation layer, ensuring consistent and efficient handling of user requests related to bookings.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/booking/BookingService.kt'>BookingService.kt</a></b></td>
																				<td>- The `BookingService` interface in the OneClickBooking project manages booking operations<br>- It allows creating, updating, deleting, and modifying the status of bookings<br>- Additionally, it provides methods to retrieve specific bookings or a list of filtered bookings, enhancing the user's ability to interact with the booking system efficiently.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>image</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/image/ImageService.kt'>ImageService.kt</a></b></td>
																				<td>- The `ImageService` interface within the OneClickBooking project manages image operations<br>- It allows for creating, deleting, and retrieving images, as well as fetching all or the first image associated with a specific parent entity type and ID<br>- This service facilitates seamless integration of media content in the overall application architecture.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>review</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/review/ReviewMappingResolver.kt'>ReviewMappingResolver.kt</a></b></td>
																				<td>- The `ReviewMappingResolver` interface, located within the OneClickBooking service, is responsible for mapping booking IDs to corresponding Booking objects<br>- This facilitates efficient retrieval and management of bookings based on their unique identifiers, enhancing the overall functionality of the OneClickBooking system.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/review/ReviewService.kt'>ReviewService.kt</a></b></td>
																				<td>- The `ReviewService` interface in the OneClickBooking project manages user reviews<br>- It allows creation, updating, deletion, and retrieval of individual reviews, as well as listing all reviews<br>- Additionally, it offers a feature to filter and summarize reviews based on specific criteria, enhancing the user experience by providing a more personalized and organized review section.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>schedule</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/schedule/ScheduleService.kt'>ScheduleService.kt</a></b></td>
																				<td>- The `ScheduleService.kt` file within the OneClickBooking project is an interface that defines methods for handling schedule-related operations<br>- It enables external components to interact with the scheduling service, primarily by requesting schedules based on specific parameters and receiving responses in a structured format<br>- This interface plays a crucial role in managing appointments or reservations within the overall system architecture.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/schedule/ScheduleUtilsService.kt'>ScheduleUtilsService.kt</a></b></td>
																				<td>- The `ScheduleUtilsService` interface within the OneClickBooking project defines a set of methods to manage employee schedules and generate potential booking slots<br>- It takes into account employee availability, treatment durations, and overlapping bookings to ensure efficient scheduling<br>- This service is instrumental in maintaining a well-organized calendar for employees and streamlining the booking process for clients.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>util</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/util/JsonPatchService.kt'>JsonPatchService.kt</a></b></td>
																				<td>The `JsonPatchService` interface within the OneClickBooking project facilitates applying JSON patches to objects of various types, ensuring seamless data updates and modifications in a flexible manner, contributing to the overall efficiency and adaptability of the system.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/util/TimeProviderService.kt'>TimeProviderService.kt</a></b></td>
																				<td>- The `TimeProviderService` interface, located within the OneClickBooking project structure, is designed to provide the current LocalDateTime across the application<br>- This abstraction allows for flexible time management and potential future enhancements in the system's scheduling or time-based operations.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/declaration/util/ValidationService.kt'>ValidationService.kt</a></b></td>
																				<td>- The `ValidationService` interface, located within the OneClickBooking project structure, serves as a contract for validation logic across various data transfer objects (DTOs)<br>- By implementing this interface, different services can ensure incoming data adheres to expected formats and rules, thereby maintaining data integrity throughout the application.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
															<details>
																<summary><b>implementation</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/EmployeeServiceImpl.kt'>EmployeeServiceImpl.kt</a></b></td>
																		<td>- The `EmployeeServiceImpl` class in the OneClickBooking project is responsible for managing employee data<br>- It interacts with the repository to retrieve, create, and filter employee records based on user input<br>- The service utilizes caching annotations to optimize performance and provides methods for retrieving individual employees, all employees, or filtered lists of employees<br>- This class is a crucial part of the project's employee management functionality.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/ServicePointServiceImpl.kt'>ServicePointServiceImpl.kt</a></b></td>
																		<td>- The provided Kotlin file, `ServicePointServiceImpl`, is a part of the OneClickBooking project's service layer implementation<br>- It retrieves and manages Service Point data from the database, providing two main functionalities: fetching a specific Service Point by its ID and returning all available Service Points as a list<br>- These functions are annotated with Spring's caching mechanism to optimize performance.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/TreatmentServiceImpl.kt'>TreatmentServiceImpl.kt</a></b></td>
																		<td>- In the OneClickBooking project, the `TreatmentServiceImpl` class is a Spring service implementation that manages treatments within the system<br>- It interacts with the TreatmentRepository to retrieve and manipulate treatment data, converting them into response DTOs using the TreatmentMapper for API communication<br>- This service enables efficient retrieval of individual treatments or all treatments associated with a specific service point.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/UserServiceImpl.kt'>UserServiceImpl.kt</a></b></td>
																		<td>- The `UserServiceImpl` file within the OneClickBooking project is responsible for managing user accounts<br>- It handles user creation, deletion, and updates, including patch-based updates using JSON Merge Patch<br>- Additionally, it ensures password security by hashing passwords before saving them in the database<br>- Furthermore, it integrates with other services such as RoleRepository and ValidationService to maintain user roles and validate incoming data.</td>
																	</tr>
																	</table>
																	<details>
																		<summary><b>booking</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/booking/BookingMappingResolverImpl.kt'>BookingMappingResolverImpl.kt</a></b></td>
																				<td>- This Kotlin file, `BookingMappingResolverImpl`, is a component that implements the `BookingMappingResolver` service within the OneClickBooking project<br>- It resolves references to various entities (User, ServicePoint, Employee, Treatment) and their associated DTOs (Data Transfer Objects), ensuring data consistency across the application by fetching the required records from their respective repositories or throwing exceptions if not found.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/booking/BookingServiceImpl.kt'>BookingServiceImpl.kt</a></b></td>
																				<td>- 1<br>- This Java class defines a `Booking` service that manages the booking of appointments for a specific service point, employee, and treatment<br>- It validates the availability of the employee on the specified date and time, ensuring no conflicts with other bookings<br>- The class also checks if the employee is authorized to provide the specified treatment and if they are associated with the given service point.

2<br>- The `Booking` service uses a repository for data persistence, employing the Spring Data JPA annotations such as `@Repository`, `@Id`, and `@Entity`<br>- It also utilizes helper methods like `findById()`, `orElseThrow()`, and custom exceptions (e.g., `RecordNotFoundException`) to handle potential database issues.

3<br>- The class follows best practices for naming conventions, using descriptive method names, and adhering to Java coding standards<br>- It also demonstrates good object-oriented programming principles by encapsulating related functionality within the same class.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/booking/BookingUpdater.kt'>BookingUpdater.kt</a></b></td>
																				<td>- The `BookingUpdater` service in the OneClickBooking project is a scheduler that periodically updates the status of bookings<br>- It marks expired bookings as completed, ensuring timely management and organization of reservations within the system<br>- This contributes to the overall efficiency and reliability of the booking process.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>image</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/image/ImageServiceImpl.kt'>ImageServiceImpl.kt</a></b></td>
																				<td>- The `ImageServiceImpl.kt` file is a service implementation class within the OneClickBooking project<br>- It manages image resources, allowing creation, deletion, retrieval, and listing of images associated with specific parent entities<br>- This service interacts with the ImageRepository to persist images in the database and provides responses in a DTO format for API consumption.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>review</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/review/ReviewMappingResolverImpl.kt'>ReviewMappingResolverImpl.kt</a></b></td>
																				<td>- The `ReviewMappingResolverImpl` class within the OneClickBooking project is a Spring component that resolves bookings by their ID, ensuring data consistency between the review and booking services<br>- It leverages the BookingRepository to fetch the requested booking or throw an exception if not found.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/review/ReviewServiceImpl.kt'>ReviewServiceImpl.kt</a></b></td>
																				<td>- The `ReviewServiceImpl` class manages the creation, update, deletion, and retrieval of user reviews within the OneClickBooking application<br>- It interacts with the ReviewRepository to persist changes in the database and utilizes Spring's caching mechanism to optimize performance<br>- Additionally, it validates input data and publishes events when a review is created or updated.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>schedule</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/schedule/ScheduleServiceImpl.kt'>ScheduleServiceImpl.kt</a></b></td>
																				<td>- 1<br>- This Java class defines a service that handles booking requests for a specific service point and treatment<br>- It retrieves the necessary data from the database, checks for valid input, and generates potential time slots based on employee availability<br>- The generated slots are then filtered to exclude any already booked times.

2<br>- To improve this code, consider adding error handling for cases where no employees or service points are found for a given treatment or service point ID respectively<br>- Additionally, you could optimize the performance of the method that generates potential time slots by using efficient data structures and algorithms<br>- Lastly, consider implementing unit tests to ensure the correctness and robustness of your code.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/schedule/ScheduleUtilsServiceImpl.kt'>ScheduleUtilsServiceImpl.kt</a></b></td>
																				<td>- In the OneClickBooking project, the ScheduleUtilsServiceImpl class is a Spring component that generates potential booking slots based on employee availability and schedules, taking into account existing bookings to avoid double-booking<br>- It also rounds up start times to the nearest increment (in minutes) for efficient scheduling.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>util</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/AuthAnnotationService.kt'>AuthAnnotationService.kt</a></b></td>
																				<td>- The `AuthAnnotationService` class in the provided file is a utility service within the OneClickBooking project that validates user permissions for various entities (bookings, reviews, images)<br>- It ensures only the owner or an administrator can perform certain actions on specific resources, such as editing or deleting their own reviews or bookings<br>- This service leverages the `AuthorizationUtil` class to check user roles and access rights, thereby maintaining data integrity and security within the application.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/BeanProvider.kt'>BeanProvider.kt</a></b></td>
																				<td>- The `BeanProvider.kt` file within the OneClickBooking project serves as a utility class, leveraging Spring's ApplicationContext to provide access to application beans<br>- This facilitates dependency injection and simplifies service interaction across the project architecture.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/JsonPatchServiceImpl.kt'>JsonPatchServiceImpl.kt</a></b></td>
																				<td>- In the OneClickBooking project, the `JsonPatchServiceImpl` class serves to apply JSON patches to objects within the system<br>- This functionality enables dynamic updates of data structures in a flexible and efficient manner, facilitating seamless integration with various services that require real-time modifications.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/JwtService.kt'>JwtService.kt</a></b></td>
																				<td>- This Kotlin file, `JwtService.kt`, is a crucial component of the OneClickBooking project's authentication service<br>- It implements a JWT (JSON Web Token) service responsible for creating and verifying access and refresh tokens<br>- These tokens are used to authenticate users and manage their sessions securely within the application<br>- The service also handles token expiration, signature verification, and converting tokens into Authentication objects for further processing.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/MessageResolver.kt'>MessageResolver.kt</a></b></td>
																				<td>The `MessageResolver` class within the OneClickBooking service implementation utilizes Spring's MessageSource to localize and format application messages, ensuring seamless multilingual support across the project architecture.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/TimeProviderServiceImpl.kt'>TimeProviderServiceImpl.kt</a></b></td>
																				<td>- In the OneClickBooking project, the TimeProviderServiceImpl class is a Spring-managed service that provides the current system time as LocalDateTime objects<br>- This service facilitates synchronization and coordination across various components within the application by ensuring they operate with consistent timestamps.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/service/implementation/util/ValidationServiceImpl.kt'>ValidationServiceImpl.kt</a></b></td>
																				<td>- The `ValidationServiceImpl` class within the OneClickBooking project ensures data integrity by validating input data structures (DTOs) against defined constraints before processing them further<br>- This is achieved using Jakarta Validation, a powerful tool for performing validation checks in Spring applications, thereby preventing potential errors and maintaining data consistency throughout the system.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
														</blockquote>
													</details>
													<details>
														<summary><b>specification</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/specification/BookingSpecification.kt'>BookingSpecification.kt</a></b></td>
																<td>- The `BookingSpecification.kt` file is a key component of the OneClickBooking project's architecture, defining custom filters for booking data retrieval<br>- It leverages Spring Data JPA specifications to dynamically construct SQL queries based on user-defined criteria such as employee, service point, date, user, and status<br>- This allows users to filter bookings efficiently and effectively, enhancing the overall user experience.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/specification/EmployeeSpecification.kt'>EmployeeSpecification.kt</a></b></td>
																<td>- This Kotlin file, `EmployeeSpecification.kt`, is a part of the OneClickBooking project's architecture<br>- It defines a custom JPA Specification for filtering Employee entities based on provided criteria such as service point or treatment<br>- By leveraging this specification, the application can efficiently search and retrieve employees that match specific filters, enhancing user experience in managing their workforce.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/specification/GenericSpecificationHelper.kt'>GenericSpecificationHelper.kt</a></b></td>
																<td>- This Kotlin utility object, `GenericSpecificationHelper`, is designed to construct complex search criteria within the OneClickBooking project<br>- It simplifies filtering data by providing methods to build predicates based on various properties such as entity IDs, dates, text fields, and statuses<br>- The goal is to enable flexible and efficient querying of the database for improved user experience.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/specification/ReviewSpecification.kt'>ReviewSpecification.kt</a></b></td>
																<td>- The `ReviewSpecification.kt` file is a custom JPA Specification implementation within the OneClickBooking project, which enables dynamic filtering and sorting of review data based on various criteria such as employee, service point, user, or text content<br>- This class leverages the GenericSpecificationHelper to build complex predicates for each filter key, ensuring flexible and efficient query construction.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/specification/SpecificationBuilder.kt'>SpecificationBuilder.kt</a></b></td>
																<td>- The `SpecificationBuilder` class in the provided file is a utility that constructs complex search and sort specifications for data retrieval within the OneClickBooking project<br>- It takes user filters, sorts the results based on specified criteria, and applies additional sorting options such as date ascending, descending, or closest to the current time<br>- This class enables flexible and efficient data querying in the application.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/specification/SpecificationFactory.kt'>SpecificationFactory.kt</a></b></td>
																<td>- The `SpecificationFactory.kt` file within the OneClickBooking project serves as a factory for creating custom JPA specifications based on provided filter criteria<br>- This abstraction enables dynamic and flexible query construction, enhancing the application's ability to handle diverse data filtering needs efficiently.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>utils</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/utils/AuthorizationUtil.kt'>AuthorizationUtil.kt</a></b></td>
																<td>- The `AuthorizationUtil` class within the OneClickBooking project is a utility object that manages authentication context and checks user roles (owner, admin) to enforce access control on protected resources<br>- It ensures only authorized users can perform certain actions based on their role or ownership of specific entities.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/utils/ExceptionMessages.kt'>ExceptionMessages.kt</a></b></td>
																<td>- This Kotlin file, `ExceptionMessages.kt`, within the OneClickBooking project, defines a collection of constant error messages to be used across the application<br>- These messages cater to various validation and exception scenarios, ensuring consistent and user-friendly error handling throughout the system.</td>
															</tr>
															<tr>
																<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/utils/UserDetailsBuilder.kt'>UserDetailsBuilder.kt</a></b></td>
																<td>- In the OneClickBooking project architecture, the `UserDetailsBuilder` function within the `src\main\kotlin\source\code\oneclickbooking\utils\UserDetailsBuilder.kt` file is responsible for constructing user details based on provided UserCredentialsDto objects<br>- This construction follows the UserDetails interface from Spring Security, enabling seamless integration with authentication mechanisms in the application.</td>
															</tr>
															</table>
														</blockquote>
													</details>
													<details>
														<summary><b>validation</b></summary>
														<blockquote>
															<details>
																<summary><b>email</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/email/UniqueEmailDomain.kt'>UniqueEmailDomain.kt</a></b></td>
																		<td>- The `UniqueEmailDomain` annotation within the OneClickBooking project ensures unique email domains during data validation<br>- This promotes data integrity by preventing duplicate email addresses with different usernames from being accepted, thereby enhancing system security and user experience.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/email/UniqueEmailValidator.kt'>UniqueEmailValidator.kt</a></b></td>
																		<td>- The `UniqueEmailValidator` class within the OneClickBooking project ensures unique email addresses across user accounts by validating against existing emails in the UserRepository<br>- This helps maintain data integrity and prevents duplicate email registrations.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
															<details>
																<summary><b>password</b></summary>
																<blockquote>
																	<details>
																		<summary><b>digits</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/digits/PasswordDigitsDomain.kt'>PasswordDigitsDomain.kt</a></b></td>
																				<td>- Enforces password validation rules focusing on digit presence within the OneClickBooking application's source code<br>- This annotation, PasswordDigitsDomain, is used to validate input fields and ensures that they contain a specified number of digits, improving security by preventing weak passwords.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/digits/PasswordDigitsValidator.kt'>PasswordDigitsValidator.kt</a></b></td>
																				<td>- In the OneClickBooking project, the PasswordDigitsValidator class ensures password security by validating if a provided password contains digits<br>- This is part of the broader validation system within the source code, enforcing strong password policies to protect user accounts.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>lowercase</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/lowercase/PasswordLowercaseDomain.kt'>PasswordLowercaseDomain.kt</a></b></td>
																				<td>Enforces password lowercase validation within the OneClickBooking project architecture, ensuring user input adheres to a specific format for improved security and consistency.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/lowercase/PasswordLowercaseValidator.kt'>PasswordLowercaseValidator.kt</a></b></td>
																				<td>- In the OneClickBooking project, this Kotlin file (PasswordLowercaseValidator) enforces password validation rules by ensuring that a password contains at least one lowercase letter<br>- This is part of the broader security measures within the application to maintain user account integrity and protect sensitive data.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>oldpassword</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/oldpassword/EmailChangeRequireOldPassword.kt'>EmailChangeRequireOldPassword.kt</a></b></td>
																				<td>- The provided Kotlin annotation `EmailChangeRequireOldPassword` serves as a validation constraint within the OneClickBooking project architecture<br>- This constraint ensures that when a user attempts to change their email, they must provide the old password for security purposes<br>- This helps maintain account integrity and protect against unauthorized access during sensitive operations.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/oldpassword/EmailChangeRequireOldPasswordValidator.kt'>EmailChangeRequireOldPasswordValidator.kt</a></b></td>
																				<td>- The provided Kotlin file, `EmailChangeRequireOldPasswordValidator`, is a part of the OneClickBooking project's validation layer<br>- It validates user update requests during email change operations, ensuring that the old password is provided to maintain security and integrity of user data<br>- This helps prevent unauthorized access or data manipulation when modifying user email addresses.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/oldpassword/PasswordChangeRequireOldPassword.kt'>PasswordChangeRequireOldPassword.kt</a></b></td>
																				<td>- Enforces password change validation in the OneClickBooking application<br>- The provided Kotlin annotation, `PasswordChangeRequireOldPassword`, is used to ensure that a user's new password must match their old one during password change operations, enhancing security and user experience.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/oldpassword/PasswordChangeRequireOldPasswordValidator.kt'>PasswordChangeRequireOldPasswordValidator.kt</a></b></td>
																				<td>- Validates the old password during user update process in OneClickBooking application, ensuring a secure password change by checking if the provided new password matches the old one<br>- This is part of the broader validation layer within the source code structure.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>special</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/special/PasswordSpecialDomain.kt'>PasswordSpecialDomain.kt</a></b></td>
																				<td>- Enforces password complexity within the OneClickBooking application by annotating relevant fields with `PasswordSpecialDomain`<br>- This ensures user-entered passwords meet specific requirements, enhancing security and adherence to best practices.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/special/PasswordSpecialValidator.kt'>PasswordSpecialValidator.kt</a></b></td>
																				<td>- The provided Kotlin file, `PasswordSpecialValidator`, is a part of the OneClickBooking validation module<br>- It enforces password complexity rules within the project architecture by validating if the entered password contains special characters other than letters and numbers<br>- This ensures user-entered passwords meet a minimum level of security.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>uppercase</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/uppercase/PasswordUppercaseDomain.kt'>PasswordUppercaseDomain.kt</a></b></td>
																				<td>Enforces password uppercase validation within the OneClickBooking project architecture, ensuring user-entered passwords contain at least one uppercase character to enhance security standards.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/main/kotlin/source/code/oneclickbooking/validation/password/uppercase/PasswordUppercaseValidator.kt'>PasswordUppercaseValidator.kt</a></b></td>
																				<td>- In the OneClickBooking project architecture, the `PasswordUppercaseValidator.kt` file serves to enforce password validation rules<br>- Specifically, it ensures that a user's password contains at least one uppercase letter, contributing to the overall security of the system by enforcing strong password policies.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
														</blockquote>
													</details>
												</blockquote>
											</details>
										</blockquote>
									</details>
								</blockquote>
							</details>
						</blockquote>
					</details>
					<details>
						<summary><b>resources</b></summary>
						<blockquote>
							<table>
							<tr>
								<td><b><a href='/src/main/resources/application.yaml'>application.yaml</a></b></td>
								<td>- Configures the OneClickBooking application, setting up a server on port 8000, using MySQL as the database, Redis for caching, and Hibernate for ORM<br>- It also enables logging of SQL queries and sets a maximum file size limit for multipart requests<br>- The shared key and connection details are defined for JWS and datasource respectively<br>- DevTools livereload is disabled.</td>
							</tr>
							<tr>
								<td><b><a href='/src/main/resources/create-schema.sql'>create-schema.sql</a></b></td>
								<td>- This SQL script initializes the database schema for an application managing employee schedules, service points, treatments, bookings, users, and roles<br>- It defines tables for employees, their availability, images, roles, service points, treatment offerings, employee-treatment associations, users, bookings, reviews, and user-role associations<br>- The aim is to store and manage data related to a service-oriented business with staff, services, customers, and bookings.</td>
							</tr>
							<tr>
								<td><b><a href='/src/main/resources/docker-compose.yaml'>docker-compose.yaml</a></b></td>
								<td>- Configures a Docker environment for running a Redis server as part of the project architecture<br>- The 'docker-compose.yaml' file specifies version 3.8, defines a service named 'redis-server', and sets up an image from the latest Redis container<br>- It assigns a unique container name, opens port 6379 for access, ensures the server always restarts upon failure, and links it to the project's network<br>- This setup enables seamless Redis integration within the project's Dockerized environment.</td>
							</tr>
							<tr>
								<td><b><a href='/src/main/resources/insert-data.sql'>insert-data.sql</a></b></td>
								<td>- The provided code file, `src\main\resources\insert-data.sql`, is a MySQL script used for initializing the database schema and populating it with data within our project, OneClickBooking<br>- This script sets up the necessary tables, columns, and relationships, as well as inserts sample data into these tables<br>- By doing so, it ensures that the application has a consistent and functional database environment upon deployment or restart<br>- The overall purpose of this file is to facilitate efficient and reliable data management within our OneClickBooking project architecture.</td>
							</tr>
							<tr>
								<td><b><a href='/src/main/resources/instructions'>instructions</a></b></td>
								<td>- The provided file outlines instructions for setting up and configuring a Redis container within the project's Docker environment, as well as managing database connections, schema creation, data insertion, and image data population using SQL scripts or MySQL Workbench<br>- The project architecture leverages Docker Compose, Spring Boot, Hibernate, and MySQL for efficient and scalable application development.</td>
							</tr>
							<tr>
								<td><b><a href='/src/main/resources/oneclickbooking_image.sql'>oneclickbooking_image.sql</a></b></td>
								<td>- The provided SQL file, `src\main\resources\oneclickbooking_image.sql`, is a MySQL database schema dump for the project "oneclickbooking"<br>- This script is used to create and populate the 'images' table within the 'oneclickbooking' database<br>- It serves as an integral part of the application, enabling the storage and retrieval of image data related to various functionalities in the oneclickbooking system<br>- The images could be product images, user profile pictures, or any other visual content that enhances the user experience within the application<br>- This table is a crucial component of the overall project architecture, ensuring seamless integration of visual elements into the platform's workings.</td>
							</tr>
							</table>
						</blockquote>
					</details>
				</blockquote>
			</details>
			<details>
				<summary><b>test</b></summary>
				<blockquote>
					<details>
						<summary><b>kotlin</b></summary>
						<blockquote>
							<details>
								<summary><b>source</b></summary>
								<blockquote>
									<details>
										<summary><b>code</b></summary>
										<blockquote>
											<details>
												<summary><b>oneclickbooking</b></summary>
												<blockquote>
													<table>
													<tr>
														<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/OneClickBookingApplicationTests.kt'>OneClickBookingApplicationTests.kt</a></b></td>
														<td>- The provided Kotlin test file, `OneClickBookingApplicationTests`, serves to validate the functionality of the OneClickBooking application built using Spring Boot<br>- It ensures that the application behaves as intended across various scenarios, maintaining a seamless user experience in the booking process.</td>
													</tr>
													</table>
													<details>
														<summary><b>integration</b></summary>
														<blockquote>
															<table>
															<tr>
																<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/TestConfiguration.kt'>TestConfiguration.kt</a></b></td>
																<td>- This Kotlin file, `TestConfiguration.kt`, within the OneClickBooking project, serves to set up test environments by providing essential beans such as a NoOpCacheManager and a custom TimeProviderService<br>- The TimeProviderService is configured to return a fixed LocalDateTime (January 16th, 2025, 9:00 AM) during tests, ensuring consistent time-related behavior across test runs<br>- This facilitates reliable testing of the OneClickBooking application's functionality.</td>
															</tr>
															<tr>
																<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/Utils.kt'>Utils.kt</a></b></td>
																<td>- The provided Kotlin file, `Utils.kt`, is a utility module within the OneClickBooking project that enhances functionality across various integration points<br>- It offers methods to set user contexts with different roles (User or Admin), calculates the closest date for a desired day, and generates SQL queries for creating bookings<br>- This versatility helps streamline interactions between components in the OneClickBooking system.</td>
															</tr>
															</table>
															<details>
																<summary><b>annotation</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/annotation/SqlSetup.kt'>SqlSetup.kt</a></b></td>
																		<td>- The provided Kotlin annotation file, `SqlSetup.kt`, is a part of the OneClickBooking project's test integration layer<br>- It serves to prepare and clean up the database before and after each test method execution, ensuring consistent test data across different tests<br>- This is achieved by utilizing the Spring Framework's @Sql annotation, which references SQL scripts for inserting and removing data from the database.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
															<details>
																<summary><b>controller</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/EmployeeControllerTest.kt'>EmployeeControllerTest.kt</a></b></td>
																		<td>- 1<br>- This test class `EmployeeFilterTest` contains multiple test cases to verify the functionality of the employee filter API.
2<br>- The tests cover various scenarios such as valid and invalid filter keys, operations, and data options.
3<br>- Additionally, it checks if the correct number of employees are returned based on the provided filters.
4<br>- Lastly, it ensures that an empty list is returned when no employees match the given filters.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/ImageControllerTest.kt'>ImageControllerTest.kt</a></b></td>
																		<td>- 1<br>- This test class `ImageControllerTest` is a JUnit test for the `ImageController` class<br>- It tests various CRUD (Create, Read, Update, Delete) operations on images associated with different entities such as `REVIEW`, `SERVICE_POINT`.

2<br>- The test cases cover scenarios like creating an image when the user is the author or not, deleting an image when the user is the author or not, and checking for appropriate HTTP status codes in each scenario.

3<br>- Additionally, it tests for situations where the requested image does not exist, ensuring that a `404 Not Found` error is returned in such cases<br>- The test class also includes logging to help debug any issues that may arise during testing.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/ScheduleControllerTest.kt'>ScheduleControllerTest.kt</a></b></td>
																		<td>- The provided code file, `ScheduleControllerTest.kt`, is a part of the OneClickBooking project and resides within the integration test suite in Kotlin<br>- This specific test case is designed to verify the functionality of the Schedule Controller, which manages scheduling operations in the application.

The test checks various scenarios such as creating, updating, and deleting schedules, ensuring that the correct responses are returned and data integrity is maintained<br>- It leverages Spring Boot Test annotations for setting up the test environment, including mocking the HTTP requests and responses, and utilizing Spring Security for testing authenticated operations.

Additionally, it uses Testcontainers to set up a database container for testing purposes, and SQL statements to prepare the test data and verify the results<br>- The test also makes use of custom Utils functions like `createBookingSql` and `getClosestDateForDay`.

In summary, this code file is an integral part of the OneClickBooking project's test suite, ensuring that the Schedule Controller works as intended within the overall application architecture.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/ServicePointControllerTest.kt'>ServicePointControllerTest.kt</a></b></td>
																		<td>- This Kotlin test file, located within the integration controller package of the OneClickBooking project, validates the ServicePointController's API endpoints<br>- It ensures that GET requests to retrieve a specific service point, all service points, or an empty list of service points return the expected responses<br>- The tests are executed with Spring Boot and MockMvc, and utilize Testcontainers for database setup.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/TreatmentControllerTest.kt'>TreatmentControllerTest.kt</a></b></td>
																		<td>- This Kotlin test file, located within the integration controller package of the OneClickBooking project, validates the TreatmentController's API endpoints<br>- It ensures that GET requests to retrieve a specific treatment, all treatments, or treatments by service point return expected responses, including status codes and data structure<br>- The tests are executed using Spring Boot Test and MockMvc, with test containers for database setup.</td>
																	</tr>
																	</table>
																	<details>
																		<summary><b>booking</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/booking/BookingControllerDeleteTest.kt'>BookingControllerDeleteTest.kt</a></b></td>
																				<td>- This Kotlin test file, `BookingControllerDeleteTest`, is part of the OneClickBooking project's integration layer<br>- It validates the DELETE operation on the Booking API controller, ensuring that bookings can be deleted by an admin user, while denying access for non-admin users or when attempting to delete a non-existent booking<br>- The test setup includes creating and dropping the database schema using Testcontainers and Spring Boot Test configurations.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/booking/BookingControllerGetTest.kt'>BookingControllerGetTest.kt</a></b></td>
																				<td>- This Kotlin test file is part of the OneClickBooking project, an integration test for the BookingController class within the Spring Boot application<br>- It verifies various GET requests to retrieve booking data, ensuring correct responses are returned when bookings exist or do not exist, and handling invalid input (e.g., non-numeric IDs)<br>- The test setup includes database operations using JdbcTemplate and Testcontainers for containerized testing environments.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/booking/BookingControllerPatchTest.kt'>BookingControllerPatchTest.kt</a></b></td>
																				<td>- 1<br>- This Java file contains test cases for a PATCH /api/bookings/{id} endpoint.
2<br>- The tests cover various scenarios such as invalid requests, employee availability, and booking conflicts.
3<br>- The tests ensure that the API returns appropriate HTTP status codes in different situations, enforcing business rules and data integrity.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/booking/BookingControllerPostTest.kt'>BookingControllerPostTest.kt</a></b></td>
																				<td>- The provided code file, `BookingControllerPostTest.kt`, is a part of the OneClickBooking project, specifically within the integration testing suite for the booking controller in the Kotlin module<br>- This test class validates the functionality and behavior of the booking API endpoints under various scenarios, ensuring they function as intended when integrated with other components such as the database and security mechanisms.

The overall architecture of the OneClickBooking project is a web application that allows users to make bookings for various services, such as hotels, flights, or rental cars<br>- The project uses Spring Boot for its back-end framework, Testcontainers for managing test containers, and JUnit Jupiter for writing tests.

The `BookingControllerPostTest` specifically focuses on testing the POST requests to the booking API endpoints, ensuring that data is correctly processed, validated, and stored in the database<br>- The test class leverages Spring's MockMvc for sending HTTP requests and verifying their responses, as well as various annotations like `@SpringBootTest`, `@AutoConfigureMockMvc`, and `@WithMockUser` to set up the testing environment and simulate user authentication.

Additionally, the test class uses SQL annotations to prepare the database with predefined data before each test and clean it up afterwards, ensuring that tests run in isolation<br>- The project also employs ActiveProfiles to switch between different database configurations during testing.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>review</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/review/ReviewControllerDeleteTest.kt'>ReviewControllerDeleteTest.kt</a></b></td>
																				<td>- This Kotlin test file, `ReviewControllerDeleteTest`, is part of the OneClickBooking project's integration layer<br>- It verifies the functionality of the review deletion API endpoints in the controller<br>- The tests ensure that a user can delete their own reviews, return a 404 error when attempting to delete non-existent reviews, and that users without authorization cannot delete reviews<br>- Additionally, an admin user is able to delete any review.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/review/ReviewControllerGetTest.kt'>ReviewControllerGetTest.kt</a></b></td>
																				<td>- This Kotlin test file, `ReviewControllerGetTest`, is part of the OneClickBooking project's integration layer<br>- It validates the functionality of the REVIEW API controller by performing various GET requests and verifying the expected responses<br>- The tests cover scenarios such as retrieving a specific review, handling non-existent reviews, invalid input (non-numeric ID), and fetching all reviews<br>- This ensures the correct data is returned to users when interacting with the review section of the OneClickBooking application.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/review/ReviewControllerPatchTest.kt'>ReviewControllerPatchTest.kt</a></b></td>
																				<td>- This Kotlin test file, `ReviewControllerPatchTest`, is part of the OneClickBooking project's integration layer<br>- It tests the functionality of updating reviews in the application by simulating various scenarios such as user authentication, review existence, and illegal field updates<br>- The tests ensure that the review can be updated when the user is either the author or an admin, and returns appropriate error codes for non-existent reviews, unauthorized users, and illegal fields.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/review/ReviewControllerPostTest.kt'>ReviewControllerPostTest.kt</a></b></td>
																				<td>- 1<br>- The provided Java code is a test class for an API endpoint that handles booking and review operations<br>- It includes tests for creating, retrieving, updating, and deleting bookings, as well as tests for creating reviews associated with completed bookings.

2<br>- The test class also includes validation checks for invalid requests, such as missing or incomplete data, and checks for business rules like a booking's status (e.g., only allowing reviews for completed bookings).

3<br>- Overall, the code demonstrates good organization, clear naming conventions, and adherence to best practices for writing testable and maintainable Java code<br>- Keep up the great work!</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>user</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/user/UserControllerDeleteTest.kt'>UserControllerDeleteTest.kt</a></b></td>
																				<td>- This Kotlin test file, `UserControllerDeleteTest`, is part of the OneClickBooking project's integration layer<br>- It tests the functionality of user deletion in the application's REST API controller<br>- The test verifies that a user can be deleted when the user is either an AccountOwner or Admin, and returns appropriate error codes for unauthorized users or non-existing users<br>- This ensures secure and efficient data management within the OneClickBooking system.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/user/UserControllerGetTest.kt'>UserControllerGetTest.kt</a></b></td>
																				<td>- This Kotlin test file, located within the UserControllerGetTest.kt, is part of an integration test suite for a Spring Boot application called OneClickBooking<br>- The purpose of this specific test is to verify the functionality of the GET /api/users/{id} endpoint in various user roles: AccountOwner, Admin, and non-AccountOwner or Admin<br>- It ensures that the correct user data is returned based on the role of the user making the request, with appropriate error responses for unauthorized users.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/user/UserControllerPatchTest.kt'>UserControllerPatchTest.kt</a></b></td>
																				<td>- This test file contains a series of tests for the UserController's PATCH method, which handles updating user information such as email, password, and account status<br>- The tests cover various scenarios like successful updates, invalid requests, incorrect old passwords, and more<br>- The code uses Spring Boot, JPA, and PasswordEncoder for handling user authentication and encryption.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/controller/user/UserControllerPostTest.kt'>UserControllerPostTest.kt</a></b></td>
																				<td>- 1<br>- The provided Java code is a UserController class that handles various REST API endpoints related to user authentication and authorization, such as login, refresh token, and checking token validity.

2<br>- It uses JWT (JSON Web Tokens) for authentication and MAC (Message Authentication Code) for signing the tokens<br>- The code also includes tests for different scenarios like expired or tampered tokens, missing claims, and invalid signatures.

3<br>- This code is part of a larger application that likely involves user management in a web-based system<br>- It demonstrates good practices for handling authentication and authorization in Java using JWT and MAC.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
															<details>
																<summary><b>dto</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/dto/UserCreateDtoValidationTest.kt'>UserCreateDtoValidationTest.kt</a></b></td>
																		<td>- This Kotlin test file, `UserCreateDtoValidationTest`, within the OneClickBooking project, validates the UserCreateDto object using JUnit and Jakarta Validation<br>- It ensures that user data adheres to specified constraints during integration testing<br>- Specifically, it checks for unique email addresses, password complexity, and other validation rules defined in the UserCreateDto class.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/dto/UserUpdateDtoValidationTest.kt'>UserUpdateDtoValidationTest.kt</a></b></td>
																		<td>- This Kotlin test file, `UserUpdateDtoValidationTest`, within the OneClickBooking project, validates the UserUpdateDTO object using JUnit and Mockito<br>- It ensures that the data passed to update a user adheres to specific rules such as requiring old password for password change, email uniqueness, and proper usage of EmailChangeRequireOldPassword and PasswordChangeRequireOldPassword annotations.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
															<details>
																<summary><b>localization</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/integration/localization/LocalizationTest.kt'>LocalizationTest.kt</a></b></td>
																		<td>- 1<br>- The provided Kotlin code tests the localization functionality of an application by checking that error messages are displayed correctly in different languages (English, Russian, and Polish).
2<br>- It uses a `LocalizationTest` class with a companion object containing a logger to log information during testing.
3<br>- The test cases cover both default validation and custom validation scenarios for user registration, ensuring that appropriate error messages are displayed based on the user's chosen language.
4<br>- The code demonstrates how to handle different languages in an application by using the `Locale` class to set the desired language for displaying error messages.
5<br>- It also shows how to use the Spring framework's `@WithMockUser` and `@SqlSetup` annotations to create a test environment with a mock user and pre-populated database.
6<br>- The code is well-structured, easy to understand, and follows best practices for writing tests in Kotlin<br>- It could be further improved by adding more test cases to cover additional scenarios or edge cases.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
														</blockquote>
													</details>
													<details>
														<summary><b>unit</b></summary>
														<blockquote>
															<details>
																<summary><b>mapper</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/BookingMapperTest.kt'>BookingMapperTest.kt</a></b></td>
																		<td>- This test file, `BookingMapperTest.kt`, is part of the OneClickBooking project and serves to validate the functionality of the `BookingMapper` class<br>- It ensures that data transfer objects (DTOs) are correctly mapped to and from corresponding models, as well as updated within the model<br>- The mapping process involves resolving related entities such as User, ServicePoint, Employee, and Treatment using a separate dependency, `BookingResolver`<br>- This test suite helps maintain data consistency throughout the application.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/EmployeeMapperTest.kt'>EmployeeMapperTest.kt</a></b></td>
																		<td>- The `EmployeeMapperTest` file within the OneClickBooking project is a unit test that verifies the correct mapping of an Employee object to its corresponding response DTO (Data Transfer Object) using the EmployeeMapper class<br>- This ensures data consistency and integrity in the application's data exchange processes.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/ImageMapperTest.kt'>ImageMapperTest.kt</a></b></td>
																		<td>- The provided Kotlin test file, `ImageMapperTest.kt`, is part of the OneClickBooking project's unit testing suite<br>- It tests the ImageMapper class, which is responsible for converting between Image model objects and their corresponding DTOs (Data Transfer Objects)<br>- This mapping ensures seamless communication between service layers and data storage in the application architecture<br>- The test verifies that the ImageMapper correctly maps an Image object to its response DTO and vice versa, maintaining essential data integrity throughout the process.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/ReviewMapperTest.kt'>ReviewMapperTest.kt</a></b></td>
																		<td>- This test file, `ReviewMapperTest.kt`, is part of the OneClickBooking project and focuses on validating the functionality of the `ReviewMapper` class<br>- The `ReviewMapper` is responsible for converting data transfer objects (DTOs) to domain models and vice versa, ensuring seamless communication between service layers<br>- Specifically, it tests the mapping of `ReviewCreateDto`, `ReviewUpdateDto`, and `Review` objects, as well as updating existing `Review` fields based on the provided DTOs<br>- This helps maintain data consistency and integrity within the OneClickBooking application.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/ServicePointMapperTest.kt'>ServicePointMapperTest.kt</a></b></td>
																		<td>- The provided Kotlin test file, `ServicePointMapperTest.kt`, is part of the OneClickBooking project's unit testing suite<br>- It tests the functionality of the ServicePointMapper class, which converts a ServicePoint object into a ServicePointResponseDto for API response purposes<br>- This ensures data consistency and proper representation when transferring data between models and DTOs within the application architecture.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/TreatmentMapperTest.kt'>TreatmentMapperTest.kt</a></b></td>
																		<td>- In the OneClickBooking project, this Kotlin test file (TreatmentMapperTest) verifies the correct mapping of Treatment objects to their corresponding response DTOs using the TreatmentMapper class<br>- This ensures data consistency and proper transformation between models in the application architecture.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/mapper/UserMapperTest.kt'>UserMapperTest.kt</a></b></td>
																		<td>- This Kotlin test file, `UserMapperTest`, within the OneClickBooking project, verifies the correct mapping of various data transfer objects (DTOs) to and from the User model using the `UserMapper`<br>- The mapper ensures consistent data representation across different layers of the application, handling conversions between DTOs and the User entity<br>- This includes hashing passwords during creation, returning user details in a response format, updating user fields based on an update DTO, and mapping users to UserCredentialsDto for authentication purposes.</td>
																	</tr>
																	</table>
																</blockquote>
															</details>
															<details>
																<summary><b>service</b></summary>
																<blockquote>
																	<table>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/ImageServiceTest.kt'>ImageServiceTest.kt</a></b></td>
																		<td>- This test file, `ImageServiceTest.kt`, is part of the OneClickBooking project and focuses on testing the ImageServiceImpl class<br>- It verifies that the service correctly handles creating, deleting, and retrieving images, as well as handling exceptions when images are not found<br>- The tests use Mockito for mocking dependencies and JUnit 5 for test assertions<br>- This ensures the correct functionality of the image management system within the OneClickBooking application.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/ScheduleServiceTest.kt'>ScheduleServiceTest.kt</a></b></td>
																		<td>- This test suite contains three JUnit tests for a scheduling system<br>- The first test checks if the system can correctly find available slots when there is only one employee<br>- The second test verifies that the system can find available slots when there are multiple employees, and it also ensures that a slot is kept if another employee is free in the multiple employees scenario<br>- The third test checks if the system can handle an existing booking during the search for available slots.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/ServicePointServiceTest.kt'>ServicePointServiceTest.kt</a></b></td>
																		<td>- This Kotlin test file, `ServicePointServiceTest.kt`, is part of the OneClickBooking project and serves to validate the functionality of the ServicePointServiceImpl class<br>- It tests various scenarios such as retrieving a service point by ID, handling cases where the service point is not found, and getting all service points<br>- The test suite uses Mockito for dependency injection and JUnit 5 for testing.</td>
																	</tr>
																	<tr>
																		<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/UserServiceTest.kt'>UserServiceTest.kt</a></b></td>
																		<td>- 1<br>- This Kotlin file contains test functions for the UserService class.
2<br>- The tests cover various scenarios such as getting a user, loading a user by username, and checking if a user exists by email or ID.
3<br>- Additionally, it checks for exceptions when a user is not found, validation fails, or incorrect input (null username) is provided.</td>
																	</tr>
																	</table>
																	<details>
																		<summary><b>booking</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/booking/BookingMappingResolverTest.kt'>BookingMappingResolverTest.kt</a></b></td>
																				<td>- This test file within the OneClickBooking project verifies the functionality of the BookingMappingResolverImpl class<br>- It ensures that the resolver correctly maps database IDs to their corresponding objects (User, ServicePoint, Employee, Treatment) and their respective detailed representations<br>- The tests also cover edge cases where the required object is not found in the database, triggering a RecordNotFoundException<br>- This helps maintain data integrity and consistency within the OneClickBooking application.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/booking/BookingServiceTest.kt'>BookingServiceTest.kt</a></b></td>
																				<td>- This test file, `BookingServiceTest.kt`, is part of the OneClickBooking project and serves to validate the functionality of the BookingServiceImpl class<br>- It tests various scenarios such as updating, deleting, getting a single booking, and fetching all bookings<br>- The test cases ensure that the service correctly handles non-existent bookings, updates, deletions, and retrievals<br>- This helps maintain the integrity and reliability of the OneClickBooking system's booking management functionality.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>review</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/review/ReviewMappingResolverTest.kt'>ReviewMappingResolverTest.kt</a></b></td>
																				<td>- This Kotlin test file, `ReviewMappingResolverTest`, is part of the OneClickBooking project<br>- It tests the functionality of the `ReviewMappingResolverImpl` class, which is responsible for resolving bookings by their IDs from the repository<br>- The test verifies that the correct booking is returned when found and throws a RecordNotFoundException when the booking is not available in the repository<br>- This ensures seamless integration between the service layer and the repository layer in the OneClickBooking application.</td>
																			</tr>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/review/ReviewServiceTest.kt'>ReviewServiceTest.kt</a></b></td>
																				<td>- 1<br>- This Kotlin file contains a class defining various methods for handling CRUD operations on a `Review` entity<br>- The class uses dependency injection to interact with the `Repository`, `Mapper`, and other services.

2<br>- The class includes tests for creating, updating, deleting, and retrieving reviews, as well as testing for edge cases such as non-existent reviews or invalid states of associated entities (e.g., a booking that is not completed).

3<br>- The tests use the `whenever` method to set up expectations for method calls on mocked objects, and the `assertThrows` function to verify exceptions are thrown in certain scenarios<br>- The test results are compared using functions like `assertEquals`.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																	<details>
																		<summary><b>utils</b></summary>
																		<blockquote>
																			<table>
																			<tr>
																				<td><b><a href='/src/test/kotlin/source/code/oneclickbooking/unit/service/utils/BeanProviderTest.kt'>BeanProviderTest.kt</a></b></td>
																				<td>- The `BeanProviderTest.kt` file is a unit test within the OneClickBooking project that ensures the correct behavior of the BeanProvider class, which interacts with Spring's ApplicationContext to retrieve and manage application beans<br>- This test verifies that the BeanProvider successfully retrieves a bean when it exists and throws an exception when the requested bean is not found in the ApplicationContext.</td>
																			</tr>
																			</table>
																		</blockquote>
																	</details>
																</blockquote>
															</details>
														</blockquote>
													</details>
												</blockquote>
											</details>
										</blockquote>
									</details>
								</blockquote>
							</details>
						</blockquote>
					</details>
					<details>
						<summary><b>resources</b></summary>
						<blockquote>
							<table>
							<tr>
								<td><b><a href='/src/test/resources/application-test.yaml'>application-test.yaml</a></b></td>
								<td>- Configures a test database environment for the OneClickBooking project using Testcontainers and MySQL 8.0.30<br>- The provided YAML file sets up a JPA data source, enabling automated schema creation via 'create-schema.sql' script, and enables debugging features like SQL logging and comments<br>- This setup ensures consistent and isolated testing conditions for the application.</td>
							</tr>
							</table>
							<details>
								<summary><b>testcontainers</b></summary>
								<blockquote>
									<details>
										<summary><b>general-data</b></summary>
										<blockquote>
											<table>
											<tr>
												<td><b><a href='/src/test/resources/testcontainers/general-data/insert-data.sql'>insert-data.sql</a></b></td>
												<td>- This SQL script populates the database with initial data for a service management application<br>- It includes employee details, their availability, service points, service point employees, treatments, treatment employees, users, roles, bookings, and reviews<br>- The data serves as a foundation for testing various functionalities within the application.</td>
											</tr>
											<tr>
												<td><b><a href='/src/test/resources/testcontainers/general-data/remove-data.sql'>remove-data.sql</a></b></td>
												<td>- This SQL script within the project structure serves to reset and clean the database by deleting all data from various tables (user, service_point, treatment, etc.) and then reinitializing their AUTO_INCREMENT counters<br>- This operation is crucial for maintaining a fresh test environment during integration testing in our application.</td>
											</tr>
											</table>
										</blockquote>
									</details>
									<details>
										<summary><b>schema</b></summary>
										<blockquote>
											<table>
											<tr>
												<td><b><a href='/src/test/resources/testcontainers/schema/create-schema.sql'>create-schema.sql</a></b></td>
												<td>- This SQL script defines the schema for a database used in an application, including tables for employees, service points, treatments, bookings, reviews, roles, and users<br>- The tables are interconnected through foreign keys, enabling data relationships between entities such as employee-service point, user-role, and treatment-employee<br>- This schema facilitates the storage and management of data related to a service-oriented platform with user authentication and role-based access control.</td>
											</tr>
											<tr>
												<td><b><a href='/src/test/resources/testcontainers/schema/drop-schema.sql'>drop-schema.sql</a></b></td>
												<td>- This SQL script within the testcontainers schema directory initiates a clean slate by dropping all tables and foreign keys defined in the project architecture<br>- This action is crucial for ensuring consistent testing scenarios, as it eliminates any residual data that may have been left over from previous tests or application states.</td>
											</tr>
											</table>
										</blockquote>
									</details>
									<details>
										<summary><b>user-data</b></summary>
										<blockquote>
											<table>
											<tr>
												<td><b><a href='/src/test/resources/testcontainers/user-data/insert-user.sql'>insert-user.sql</a></b></td>
												<td>- This SQL file within the test resources of our project structure is used to seed initial user data during testing<br>- It creates three users with different roles (USER, ADMIN, and a user with both USER and ADMIN roles)<br>- This setup ensures consistent test scenarios across various user permissions levels in our application.</td>
											</tr>
											<tr>
												<td><b><a href='/src/test/resources/testcontainers/user-data/remove-user.sql'>remove-user.sql</a></b></td>
												<td>- This SQL script within the 'remove-user' resource of the testcontainers folder serves to clean and reset the user database in the project<br>- It deletes specific users and their associated roles, then resets the auto-increment IDs for both tables, ensuring a fresh state for subsequent tests.</td>
											</tr>
											</table>
										</blockquote>
									</details>
								</blockquote>
							</details>
						</blockquote>
					</details>
				</blockquote>
			</details>
		</blockquote>
	</details>
</details>

---
##  Getting Started

###  Prerequisites

Before getting started with , ensure your runtime environment meets the following requirements:

- **Programming Language:** Kotlin
- **Package Manager:** Gradle
- **Container Runtime:** Docker


###  Installation

Install  using one of the following methods:

**Build from source:**

1. Clone the  repository:
```sh
❯ git clone ../
```

2. Navigate to the project directory:
```sh
❯ cd 
```

3. Install the project dependencies:


**Using `gradle`** &nbsp; [<img align="center" src="https://img.shields.io/badge/Kotlin-0095D5.svg?style={badge_style}&logo=kotlin&logoColor=white" />](https://kotlinlang.org/)

```sh
❯ gradle build
```


**Using `docker`** &nbsp; [<img align="center" src="https://img.shields.io/badge/Docker-2CA5E0.svg?style={badge_style}&logo=docker&logoColor=white" />](https://www.docker.com/)

```sh
❯ docker build -t / .
```




###  Usage
Run  using the following command:
**Using `gradle`** &nbsp; [<img align="center" src="https://img.shields.io/badge/Kotlin-0095D5.svg?style={badge_style}&logo=kotlin&logoColor=white" />](https://kotlinlang.org/)

```sh
❯ gradle run
```


**Using `docker`** &nbsp; [<img align="center" src="https://img.shields.io/badge/Docker-2CA5E0.svg?style={badge_style}&logo=docker&logoColor=white" />](https://www.docker.com/)

```sh
❯ docker run -it {image_name}
```


###  Testing
Run the test suite using the following command:
**Using `gradle`** &nbsp; [<img align="center" src="https://img.shields.io/badge/Kotlin-0095D5.svg?style={badge_style}&logo=kotlin&logoColor=white" />](https://kotlinlang.org/)

```sh
❯ gradle test
```
