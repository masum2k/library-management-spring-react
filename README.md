# Library Management System (SPRING AND REACTJS)

A full-stack web application for managing library operations with role-based access control, built with Spring Boot and React.

## Demo User
- username: superadmin
- password: SuperAdmin123!
- Rol: SUPER_ADMIN

## Live Demo
- **Frontend (React)**: https://library-management-spring-react-1pi.vercel.app/
- **Backend API (Spring Boot)**: Deployed on Railway

## Features

### Authentication & Authorization
- **JWT Token-based Authentication** with secure session management
- **Role-based Access Control** with 4 distinct user levels:
  - `USER`: Basic read access to books
  - `LIBRARIAN`: Can manage books and basic user operations
  - `ADMIN`: Full system access except user role changes
  - `SUPER_ADMIN`: Complete system control including role management

### Book Management
- **CRUD Operations** for book inventory
- **Pagination & Sorting** for large datasets
- **Search Functionality** (Frontend integration ready)
- **Validation** for data integrity

### User Management (Admin Panel)
- **User Role Management** with hierarchical permissions
- **User Status Control** (activate/deactivate accounts)
- **Bulk Operations** for efficient user management
- **System Statistics Dashboard**
- **User Search** by username or email

### Security Features
- **CORS Configuration** for secure cross-origin requests
- **Input Validation** with comprehensive error handling
- **Password Encryption** using BCrypt
- **Method-level Security** annotations
- **JWT Token Validation** middleware

### Admin Dashboard
- Real-time system statistics
- User registration analytics
- Book inventory metrics
- Activity monitoring
  
### DevOps & Deployment
- **Railway** - Backend deployment with PostgreSQL
- **Vercel** - Frontend deployment with CI/CD
- **Environment Variables** for secure configuration
- **Production-ready** CORS configuration

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Books (Role-based access)
- `GET /api/books` - Get all books (paginated)
- `GET /api/books/{id}` - Get book by ID
- `POST /api/books` - Create book (LIBRARIAN+)
- `PUT /api/books/{id}` - Update book (LIBRARIAN+)
- `DELETE /api/books/{id}` - Delete book (LIBRARIAN+)

### Admin Operations
- `GET /api/admin/users` - Get all users (ADMIN+)
- `PUT /api/admin/users/{id}/role` - Change user role (SUPER_ADMIN)
- `PUT /api/admin/users/{id}/status` - Change user status (ADMIN+)
- `GET /api/admin/stats` - Get system statistics (ADMIN+)

## Security Implementation

- **JWT Authentication**: Stateless token-based authentication
- **Role Hierarchy**: Granular permission system
- **CORS Security**: Configured for production domains
- **Input Validation**: Comprehensive data validation
- **Password Security**: BCrypt hashing with salt
- **Method Security**: Annotation-based access control

## Performance Features

- **Pagination**: Efficient data loading for large datasets
- **Lazy Loading**: Optimized resource utilization
- **Connection Pooling**: Database connection optimization
- **Caching**: JWT token validation caching
- **Error Handling**: Comprehensive error management
  
## JAVA 17
- **Java 17 with Spring Boot framework
- **Spring Security 6 for authentication & authorization
- **Spring Data JPA with Hibernate ORM
- **PostgreSQL database with connection pooling
- **JWT (JSON Web Tokens) for stateless authentication
- **Maven for dependency management
- **Lombok for reducing boilerplate code
- **Validation API for comprehensive input validation
  
## REACTJS
- ** JWT Token Secure Storage - HttpOnly cookie implementation
- ** Role-based Component Rendering - Dynamic UI permissions
- ** API Endpoint Protection - Automatic token injection
- ** XSS Protection - React built-in sanitization
- ** Input Validation - Client-side validation with backend sync
- ** Secure Headers - Content Security Policy implementation
