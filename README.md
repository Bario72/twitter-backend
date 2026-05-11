# Twitter Backend

A Twitter-like social media REST API built with **Spring Boot 4**, **PostgreSQL**, and **Spring Security**.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 4.0.6 |
| Database | PostgreSQL 17 |
| ORM | Hibernate / Spring Data JPA |
| Security | Spring Security + BCrypt |
| Validation | Jakarta Bean Validation |
| Build | Maven |
| Testing | JUnit 5 + Mockito |

## Features

- **User** &mdash; Register, login, profile update (BCrypt hashed passwords)
- **Tweet** &mdash; Create, read, update, delete with ownership checks
- **Comment** &mdash; CRUD on tweets with authorization
- **Like / Dislike** &mdash; Toggle like on tweets (idempotent)
- **Retweet** &mdash; Create and remove with ownership checks
- **Global Exception Handling** &mdash; Consistent JSON error responses (400 / 403 / 404 / 409 / 500)
- **Unit Tests** &mdash; Service layer tests with Mockito

## Architecture

```
src/main/java/com/example/twitter_backend/
├── config/          SecurityConfig, CorsConfig
├── controller/      AuthController, TweetController, CommentController,
│                    LikeController, RetweetController, UserController
├── dto/
│   ├── request/     RegisterRequest, LoginRequest, TweetCreateRequest ...
│   └── response/    UserResponse, TweetResponse, CommentResponse ...
├── entity/          User, Tweet, Comment, Like, Retweet, Role
├── exception/       GlobalExceptionHandler, BadRequestException ...
├── repository/      Spring Data JPA repositories
├── service/         Service interfaces + implementations
└── util/
    ├── mapper/      UserMapper, TweetMapper, CommentMapper
    └── security/    CustomUserDetailsService
```

## Prerequisites

- **Java 17+** &mdash; [Download](https://adoptium.net/)
- **PostgreSQL 15+** &mdash; [Download](https://www.postgresql.org/download/)
- **Maven 3.9+** (or use the included `mvnw` wrapper)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Bario72/twitter-backend.git
cd twitter-backend
```

### 2. Create the database

Open **pgAdmin** or **psql** and run:

```sql
CREATE DATABASE twitter_db;
```

> Hibernate will auto-create the `twitter` schema and all tables on first run (`ddl-auto=update`).

### 3. Configure environment variables

Copy the example file and fill in your values:

```bash
cp .env.example .env
```

Or set them in your IDE's Run Configuration:

| Variable | Example | Description |
|----------|---------|-------------|
| `SERVER_PORT` | `3000` | Tomcat port |
| `CONTEXT_PATH` | `/api` | URL prefix for all endpoints |
| `DB_URL` | `jdbc:postgresql://localhost:5432/twitter_db` | JDBC connection string |
| `DB_USERNAME` | `postgres` | Database user |
| `DB_PASSWORD` | `your_password` | Database password |

**IntelliJ IDEA:** Run &rarr; Edit Configurations &rarr; Environment variables &rarr; paste:

```
SERVER_PORT=3000;CONTEXT_PATH=/api;DB_URL=jdbc:postgresql://localhost:5432/twitter_db;DB_USERNAME=postgres;DB_PASSWORD=your_password
```

### 4. Build & Run

```bash
./mvnw spring-boot:run
```

Or on Windows:

```cmd
mvnw.cmd spring-boot:run
```

You should see:

```
Tomcat started on port 3000 (http) with context path '/api'
```

### 5. Run tests

```bash
./mvnw test
```

## API Endpoints

> Base URL: `http://localhost:3000/api`

### Auth

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Create a new user |
| POST | `/login` | Authenticate (returns user info) |

### Tweets

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/tweet` | Create a tweet |
| GET | `/tweet/findById?id=1` | Get tweet by ID |
| GET | `/tweet/findByUserId?userId=1` | Get user's tweets |
| PUT | `/tweet/{id}` | Update tweet (owner only) |
| DELETE | `/tweet/{id}?userId=1` | Delete tweet (owner only) |

### Comments

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/comment` | Add comment to a tweet |
| PUT | `/comment/{id}` | Update comment |
| DELETE | `/comment/{id}?userId=1` | Delete comment (owner only) |

### Likes

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/like` | Like a tweet |
| POST | `/dislike` | Remove like from a tweet |

### Retweets

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/retweet?userId=1&tweetId=1` | Retweet |
| DELETE | `/retweet/{id}?userId=1` | Remove retweet (owner only) |

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/user/{id}` | Get user profile |
| PUT | `/user/{id}` | Update user profile |

## Example Requests (Postman / cURL)

### Register

```bash
curl -X POST http://localhost:3000/api/register \
  -H "Content-Type: application/json" \
  -d '{
    "nickName": "ahmet",
    "firstName": "Ahmet",
    "lastName": "Yilmaz",
    "email": "ahmet@test.com",
    "password": "1234abcd",
    "bio": "Hello world"
  }'
```

### Create Tweet

```bash
curl -X POST http://localhost:3000/api/tweet \
  -H "Content-Type: application/json" \
  -d '{ "content": "My first tweet!", "userId": 1 }'
```

## Frontend

This API is designed to work with **[twitter-ui](https://github.com/Bario72/twitter-ui)** &mdash; a React + TypeScript frontend.

CORS is pre-configured for `http://localhost:5173` (Vite dev server).

## Database Schema

All tables live in the `twitter` schema:

- `users` &mdash; id, nick_name, first_name, last_name, email, password, bio, created_at, updated_at
- `tweets` &mdash; id, content, user_id, created_at, updated_at
- `comments` &mdash; id, content, user_id, tweet_id, created_at, updated_at
- `likes` &mdash; id, user_id, tweet_id, created_at
- `retweets` &mdash; id, user_id, tweet_id, created_at
- `role` &mdash; id, authority
- `user_roles` &mdash; user_id, role_id

## License

This project is for educational / portfolio purposes.
