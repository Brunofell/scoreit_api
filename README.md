# 🎬 ScoreIt - Social Media Platform for Entertainment Reviews

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![Next.js](https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white)](https://nextjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)](https://jwt.io/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)

### 🌟 A comprehensive social platform for rating and discovering movies, TV series, and music albums

[Features](#-key-features) • [Tech Stack](#-tech-stack) • [Screenshots](#-screenshots) • [Getting Started](#-getting-started) • [API Docs](#-api-documentation)

</div>

---

## 📖 Table of Contents

- [About the Project](#-about-the-project)
- [Team](#-team)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Screenshots](#-screenshots)
- [System Architecture](#-system-architecture)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Security Features](#-security)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## 🎯 About the Project

**ScoreIt** is a modern, full-stack social media platform designed for entertainment enthusiasts to review, discover, and share their opinions about artistic works across multiple media types. Inspired by platforms like Letterboxd and IMDb, ScoreIt expands the concept to encompass movies, TV series, and music albums in a unified, engaging social experience.

### 💡 The Vision

In today's digital age, entertainment consumption is fragmented across multiple platforms and genres. ScoreIt brings together movie buffs, TV series addicts, and music lovers in one place, creating a vibrant community where users can:

- **Express their opinions** through detailed reviews and ratings
- **Discover new content** through personalized recommendations
- **Connect with like-minded fans** through social features
- **Track their entertainment journey** with custom lists and favorites
- **Engage in discussions** through comments and community interactions

### 🎨 Design Philosophy

ScoreIt is built with three core principles:

1. **User-Centric Design**: Intuitive interface that makes rating and discovering content effortless
2. **Social Integration**: Seamless interaction between users, fostering a vibrant community
3. **Data-Rich Experience**: Leveraging powerful APIs to provide comprehensive content information

---

## 👥 Team

This project was developed as a final course project (TCC) by:

<div align="center">

| Developer | Role                   | Expertise                              |
|-----------|------------------------|----------------------------------------|
| **Bruno Feliciano** | Backend Developer      | Backend Architecture & API Integration |
| **Thiago Rosa** | Database Administrator | Database Design & Security             |
| **Leandro Faria** | Frontend Developer     | UI Design & Interface                  |

</div>

---

## ✨ Key Features

### 🔐 1. Authentication & Account Management

<details>
<summary><b>Click to expand authentication features</b></summary>

- **Secure User Registration**
    - Email validation and verification
    - Password strength requirements
    - Account activation via email token

- **JWT-Based Authentication**
    - Stateless authentication mechanism
    - Secure token generation and validation
    - Automatic token refresh

- **Account Recovery**
    - Password reset via email
    - Email change with verification
    - Account status management

- **Profile Customization**
    - Profile picture upload (multipart/form-data)
    - Bio and personal information editing
    - Privacy settings

</details>

### 👤 2. Rich User Profiles

<details>
<summary><b>Click to expand profile features</b></summary>

- **Comprehensive Profile Pages**
    - Custom profile picture and banner
    - Editable bio and personal information
    - Display of recent activity and reviews
    - Statistics (total reviews, followers, following)

- **Content Showcase**
    - Recent reviews with ratings
    - Favorite movies, series, and albums
    - Custom lists created by the user
    - Achievement badges earned

- **Social Discovery**
    - View other users' profiles
    - Check mutual connections
    - Browse user's public lists and reviews
    - Follow/unfollow functionality

</details>

### ⭐ 3. Advanced Review System

<details>
<summary><b>Click to expand review features</b></summary>

- **Multi-Format Reviews**
    - Numeric rating (0-5 scale)
    - Written review with rich text
    - Spoiler warning flags
    - Review visibility settings

- **Review Management**
    - Create reviews for movies, series, and albums
    - Edit existing reviews
    - Delete reviews (soft delete)
    - View review history

- **Review Discovery**
    - Browse reviews by media
    - Filter by rating, date, or popularity
    - Search reviews by content
    - Sort by various criteria

- **Review Interactions**
    - Comment on reviews
    - Like/unlike reviews
    - Share reviews
    - Report inappropriate content

</details>

### 💬 4. Interactive Comments System

<details>
<summary><b>Click to expand comment features</b></summary>

- **Threaded Discussions**
    - Comment on reviews
    - Reply to other comments (nested structure)
    - View comment threads in hierarchical format

- **Comment Management**
    - Edit your own comments
    - Delete comments (soft delete)
    - Report inappropriate comments

- **Real-Time Interactions**
    - Instant comment updates
    - Notification system for replies
    - Active discussion tracking

</details>

### ❤️ 5. Favorites & Collections

<details>
<summary><b>Click to expand favorites features</b></summary>

- **Favorites System**
    - Add movies, series, or albums to favorites
    - Automatic favorites list creation on signup
    - Quick access to favorite content
    - Remove items from favorites
    - Favorites display on profile

- **Smart Organization**
    - Categorized by media type
    - Sort by date added or rating
    - Search within favorites
    - Export favorites list

</details>

### 📚 6. Custom Lists

<details>
<summary><b>Click to expand custom lists features</b></summary>

- **Create Themed Collections**
    - Build lists around any theme (e.g., "Best Horror Films", "90s Hip-Hop Albums")
    - Add detailed descriptions
    - Mix different media types in one list

- **List Management**
    - Edit list name and description
    - Reorder items within lists
    - Remove items from lists
    - Delete entire lists

- **Sharing & Visibility**
    - Make lists public or private
    - Share lists with the community
    - Browse other users' public lists
    - Collaborate on shared lists (future feature)

</details>

### 🔍 7. Powerful Discovery System

<details>
<summary><b>Click to expand discovery features</b></summary>

- **Advanced Search**
    - **Movies**: Search by title, genre, year, cast
    - **Series**: Filter by title, genre, year, network
    - **Music**: Find albums by name, artist, genre

- **Smart Filters**
    - Genre-based filtering
    - Year/decade filtering
    - Popularity sorting
    - Rating-based recommendations

- **Trending Content**
    - Now playing in theaters
    - Currently airing series
    - New album releases
    - Top-rated content

- **Personalized Recommendations**
    - Based on your ratings
    - Similar to your favorites
    - Followed users' activity
    - Genre preferences

</details>

### 👥 8. Social Networking

<details>
<summary><b>Click to expand social features</b></summary>

- **Follow System**
    - Follow/unfollow users
    - View followers and following lists
    - Check follow status between users
    - Follower/following count statistics

- **Activity Feed**
    - See recent reviews from followed users
    - Discover trending content in your network
    - Activity notifications
    - Personalized content feed

- **User Discovery**
    - Find users by name
    - Browse popular reviewers
    - Discover users with similar tastes
    - Suggested connections

</details>

### 🏆 9. Achievements System

<details>
<summary><b>Click to expand achievements features</b></summary>

- **Unlockable Badges**
    - First review milestone
    - Quantity milestones (10, 50, 100 reviews)
    - Genre-specific achievements
    - Social achievements (followers, comments)

- **Achievement Tracking**
    - Progress bars for each badge
    - Achievement history
    - Rarity indicators
    - Display achievements on profile

- **Gamification Elements**
    - Encourage platform engagement
    - Reward active users
    - Create competitive environment
    - Foster community participation

</details>

### 🛡️ 10. Admin Dashboard

<details>
<summary><b>Click to expand admin features</b></summary>

- **User Management**
    - View all registered users
    - Activate/deactivate accounts
    - Change user roles (admin/user)
    - Edit user information
    - Search and filter users

- **Content Moderation**
    - Review reported content
    - Update report status
    - Delete inappropriate content
    - Manage user reports

- **Analytics & Statistics**
    - User growth over time (monthly metrics)
    - Reviews by date
    - Most popular media
    - Platform engagement metrics
    - Traffic analysis

- **Advanced Features**
    - Bulk operations
    - Export data
    - System health monitoring
    - Activity logs

</details>

### 🤖 11. AI Chatbot Integration

<details>
<summary><b>Click to expand chatbot features</b></summary>

- **Intelligent Assistance**
    - Answer common questions
    - Help with platform navigation
    - Provide content recommendations
    - Troubleshooting support

- **Natural Language Processing**
    - Understand user queries
    - Context-aware responses
    - Multi-turn conversations
    - Personalized interactions

</details>

### 🎨 12. Rich Media Integration

<details>
<summary><b>Click to expand media integration details</b></summary>

- **TMDB API Integration (Movies & TV Series)**
    - High-quality posters and backdrops
    - Comprehensive cast and crew information
    - Trailers and video content
    - Release dates and runtime
    - Genre classification
    - User ratings and popularity scores
    - Similar content recommendations
    - Production companies and countries

- **Spotify API Integration (Music)**
    - Album artwork and cover art
    - Artist information and biographies
    - Complete track listings
    - Genre and style tags
    - Release dates and labels
    - Popularity metrics
    - Artist discography

- **Last.fm API Integration (Additional Music Data)**
    - Extended music metadata
    - Top artists by genre
    - Music trends and charts
    - Additional album information

</details>

---

## 🛠️ Tech Stack

### Frontend Technologies

```
Next.js 14          - React framework with SSR and SSG
TypeScript          - Type-safe JavaScript
Tailwind CSS        - Utility-first CSS framework
```

### Backend Technologies

```
Spring Boot 3.2     - Java application framework
Spring Security     - Authentication and authorization
Spring Data JPA     - Data access layer
Hibernate           - ORM framework
MySQL               - Relational database
Maven               - Dependency management and build tool
Lombok              - Reduce boilerplate code
JWT (jjwt)          - JSON Web Token implementation
JavaMail            - Email functionality
```

### External APIs & Services

```
TMDB API            - Movie and TV series data
Spotify API         - Music and album information
Cloudinary          - Image hosting and optimization (optional)
```

### Development Tools

```
IntelliJ IDEA       - Java IDE
VS Code             - Frontend development
Postman             - API testing and documentation
Git & GitHub        - Version control
MySQL Workbench     - Database management
Docker              - Containerization (optional)
```

---

## 📸 Screenshots

### 🏠 Landing & Authentication

<table>
  <tr>
    <td width="50%">
      <h4>Landing Page</h4>
      <img src=".\prints\scoreit_menu.png" alt="Landing Page" />
      <p><i>Modern landing page with hero section and feature highlights</i></p>
    </td>
    <td width="50%">
      <h4>User Registration</h4>
      <img src=".\prints\cadastro.png" alt="Registration" />
      <p><i>Clean registration form with validation</i></p>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <h4>Login Page</h4>
      <img src=".\prints\login.png" alt="Login" />
      <p><i>Secure login with JWT authentication</i></p>
    </td>
  </tr>
</table>

### 🛡️ Admin Panel

<table>
  <tr>
    <td width="50%">
      <h4>Admin Dashboard</h4>
      <img src=".\prints\admin1.png" alt="Admin Dashboard" />
      <p><i>Comprehensive admin interface for platform management</i></p>
    </td>
    <td width="50%">
      <h4>Analytics & Charts</h4>
      <img src=".\prints\charts.png" alt="Analytics" />
      <p><i>Visual analytics with user growth and engagement metrics</i></p>
    </td>
  </tr>
</table>

### 📝 Reviews & Content

<table>
  <tr>
    <td width="50%">
      <h4>Review Card</h4>
      <img src=".\prints\card1.png" alt="Review Card" />
      <p><i>Elegant review display with user info and ratings</i></p>
    </td>
    <td width="50%">
      <h4>Content Card</h4>
      <img src=".\prints\detalhes1.png" alt="Content Card" />
      <p><i>Rich media cards with poster, title, and quick actions</i></p>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <h4>Spoiler Warning</h4>
      <img src=".\prints\spoiler.png" alt="Spoiler" />
      <p><i>Protected content with spoiler warnings</i></p>
    </td>
  </tr>
</table>

### 💬 Social Features

<table>
  <tr>
    <td width="50%">
      <h4>Comments Section</h4>
      <img src=".\prints\comentarios.png" alt="Comments" />
      <p><i>Threaded comment system for engaging discussions</i></p>
    </td>
    <td width="50%">
      <h4>Activity Feed</h4>
      <img src=".\prints\feeed.png" alt="Feed" />
      <p><i>Social feed with updates from followed users</i></p>
    </td>
  </tr>
</table>

### 🎬 Content Discovery

<table>
  <tr>
    <td width="33%">
      <h4>Movie Filters</h4>
      <img src=".\prints\filtro1.png" alt="Movie Filters" />
      <p><i>Advanced movie search and filtering</i></p>
    </td>
    <td width="33%">
      <h4>Series Filters</h4>
      <img src=".\prints\filtro2.png" alt="Series Filters" />
      <p><i>TV series discovery with multiple filters</i></p>
    </td>
    <td width="33%">
      <h4>Music Filters</h4>
      <img src=".\prints\filtro3.png" alt="Music Filters" />
      <p><i>Album search by genre, artist, or name</i></p>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <h4>Now Playing & Recommendations</h4>
      <img src=".\prints\filmes_em_cartaz_recomendacao.png" alt="Movies" />
      <p><i>Current releases with personalized suggestions</i></p>
    </td>
    <td>
      <h4>Music Platform</h4>
      <img src=".\prints\musica.png" alt="Music" />
      <p><i>Dedicated music section with albums</i></p>
    </td>
  </tr>
</table>

### 👤 User Features

<table>
  <tr>
    <td width="50%">
      <h4>User Profile</h4>
      <img src=".\prints\perfil1.png" alt="Profile" />
      <p><i>Personalized profile with stats and activity</i></p>
    </td>
    <td width="50%">
      <h4>Achievements</h4>
      <img src=".\prints\conquistas.png" alt="Achievements" />
      <p><i>Unlock and display achievement badges</i></p>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <h4>Custom Lists</h4>
      <img src=".\prints\lista.png" alt="Lists" />
      <p><i>Create and manage themed content collections</i></p>
    </td>
  </tr>
</table>

### 🤖 AI Features

<table>
  <tr>
    <td>
      <h4>AI Chatbot</h4>
      <img src=".\prints\chatbot.png" alt="Chatbot" />
      <p><i>Intelligent assistant for user support and guidance</i></p>
    </td>
  </tr>
</table>

---

## 🏗️ System Architecture

### High-Level Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                             │
│                  (Next.js + TypeScript + Tailwind)               │
│                                                                   │
│   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│   │  Pages   │  │Components│  │  Hooks   │  │  Store   │       │
│   └──────────┘  └──────────┘  └──────────┘  └──────────┘       │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP/REST + JWT
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      API GATEWAY LAYER                           │
│                    (Spring Boot REST API)                        │
│                                                                   │
│   ┌──────────────────────────────────────────────────────────┐  │
│   │              Spring Security + JWT Filter                 │  │
│   └──────────────────────────────────────────────────────────┘  │
│                                                                   │
│   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│   │  Auth    │  │ Content  │  │  Social  │  │  Admin   │      │
│   │Controller│  │Controller│  │Controller│  │Controller│      │
│   └──────────┘  └──────────┘  └──────────┘  └──────────┘      │
└─────────────────────────────────────────────────────────────────┘
                              │
                    ┌─────────┼─────────┐
                    ▼         ▼         ▼
┌─────────────────────────────────────────────────────────────────┐
│                       SERVICE LAYER                              │
│                                                                   │
│   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│   │  Member  │  │  Review  │  │Follower  │  │  List    │      │
│   │ Service  │  │ Service  │  │ Service  │  │ Service  │      │
│   └──────────┘  └──────────┘  └──────────┘  └──────────┘      │
│                                                                   │
│   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│   │  Email   │  │   JWT    │  │  Badge   │  │  Report  │      │
│   │ Service  │  │ Service  │  │ Service  │  │ Service  │      │
│   └──────────┘  └──────────┘  └──────────┘  └──────────┘      │
└─────────────────────────────────────────────────────────────────┘
                              │
                    ┌─────────┼─────────┐
                    ▼         ▼         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    REPOSITORY LAYER                              │
│                   (Spring Data JPA)                              │
│                                                                   │
│   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│   │  Member  │  │  Review  │  │Follower  │  │CustomList│      │
│   │   Repo   │  │   Repo   │  │   Repo   │  │   Repo   │      │
│   └──────────┘  └──────────┘  └──────────┘  └──────────┘      │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      DATA LAYER                                  │
│                                                                   │
│              ┌─────────────────────────────┐                     │
│              │       MySQL Database        │                     │
│              │      (Hibernate ORM)        │                     │
│              └─────────────────────────────┘                     │
│                                                                   │
│   Tables: members, reviews, comments, favorites,                 │
│          custom_lists, followers, reports, badges                │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    EXTERNAL SERVICES                             │
│                                                                   │
│   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│   │   TMDB   │  │ Spotify  │  │ Last.fm  │  │  Email   │      │
│   │   API    │  │   API    │  │   API    │  │ Service  │      │
│   └──────────┘  └──────────┘  └──────────┘  └──────────┘      │
└─────────────────────────────────────────────────────────────────┘
```

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 17 or higher**
    - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

- **Node.js 18+ and npm**
    - Download from [nodejs.org](https://nodejs.org/)

- **MySQL 8.0 or higher**
    - Download from [mysql.com](https://dev.mysql.com/downloads/)

- **Maven 3.6+ (usually bundled with IntelliJ IDEA)**
    - Download from [maven.apache.org](https://maven.apache.org/download.cgi)

- **Git**
    - Download from [git-scm.com](https://git-scm.com/)

### 📦 Installation

#### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Brunofell/scoreit_api.git
cd scoreit
```


The frontend will be available at `http://localhost:3000`

**✅ Verify Frontend is Running:**

Open your browser and navigate to `http://localhost:3000`

#### 4️⃣ Getting API Keys

##### TMDB API Key
1. Go to [TMDB](https://www.themoviedb.org/)
2. Create an account or login
3. Navigate to Settings → API
4. Request an API key (free)
5. Copy your API key

##### Spotify API Credentials
1. Go to [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Login with your Spotify account
3. Create a new app
4. Copy your Client ID and Client Secret

##### Last.fm API Key
1. Go to [Last.fm API](https://www.last.fm/api)
2. Create an account or login
3. Create an API account
4. Copy your API key

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Authentication

All protected endpoints require a JWT token in the Authorization header:

```http
Authorization: Bearer <your_jwt_token>
```

---

### 🔐 Authentication Endpoints

#### Register New User

```http
POST /member/post
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "John Doe",
  "birthDate": "1995-06-15",
  "gender": "MALE",
  "email": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

**Response:**
```json
{
  "message": "User registered successfully. Please check your email to activate your account.",
  "memberId": 1
}
```

#### Activate Account

```http
GET /member/confirm?token=<activation_token>
```

**Response:**
```json
{
  "message": "Account activated successfully!"
}
```

#### Login

```http
POST /member/login
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "memberId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "USER"
}
```

#### Forgot Password

```http
POST /api/forgot-password?email=john.doe@example.com
```

**Response:**
```json
{
  "message": "Password reset link sent to your email"
}
```

#### Reset Password

```http
POST /api/reset-password?token=<reset_token>&newPassword=NewPassword123!
```

**Response:**
```json
{
  "message": "Password reset successfully"
}
```

---

### 👤 User Management Endpoints

#### Get Current User

```http
GET /member/get
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "birthDate": "1995-06-15",
  "gender": "MALE",
  "bio": "Movie enthusiast and music lover",
  "profileImage": "https://example.com/profile.jpg",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

#### Update User Profile

```http
PUT /member/update
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "birthDate": "1995-06-15",
  "gender": "MALE",
  "bio": "Updated bio: Love movies, series, and music!",
  "password": "NewPassword123!"
}
```

**Response:**
```json
{
  "message": "Profile updated successfully",
  "member": { /* updated member object */ }
}
```

#### Upload Profile Picture

```http
POST /api/images/upload/{userId}
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Form Data:**
- `file`: Image file (JPG, PNG, max 10MB)

**Response:**
```json
{
  "message": "Image uploaded successfully",
  "imageUrl": "https://example.com/uploads/profile_1.jpg"
}
```

#### Delete Account

```http
DELETE /member/delete/{userId}
Authorization: Bearer <token>
```

**Response:**
```json
{
  "message": "Account deleted successfully"
}
```

---

### ⭐ Review Endpoints

#### Create Review

```http
POST /review/register
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "memberId": 1,
  "mediaId": "550",
  "mediaType": "MOVIE",
  "rating": 4.5,
  "reviewText": "An absolute masterpiece! Fight Club is a thought-provoking film that challenges societal norms.",
  "hasSpoiler": false
}
```

**Response:**
```json
{
  "id": 1,
  "memberId": 1,
  "mediaId": "550",
  "mediaType": "MOVIE",
  "rating": 4.5,
  "reviewText": "An absolute masterpiece!...",
  "hasSpoiler": false,
  "createdAt": "2024-11-18T14:30:00"
}
```

#### Get All Reviews

```http
GET /review/getAllReviews
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 1,
    "member": {
      "id": 1,
      "name": "John Doe",
      "profileImage": "https://example.com/profile.jpg"
    },
    "mediaId": "550",
    "mediaType": "MOVIE",
    "rating": 4.5,
    "reviewText": "An absolute masterpiece!...",
    "hasSpoiler": false,
    "createdAt": "2024-11-18T14:30:00",
    "commentCount": 5
  }
]
```

#### Get Reviews by User

```http
GET /review/getReviewByMemberId/{memberId}
Authorization: Bearer <token>
```

#### Get Reviews by Media

```http
GET /review/getReviewByMediaId/{mediaId}
Authorization: Bearer <token>
```

**Response:** Array of reviews for the specific media

#### Update Review

```http
PUT /review/update
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "id": 1,
  "rating": 5.0,
  "reviewText": "Updated: This is the best film ever made!",
  "hasSpoiler": true
}
```

#### Delete Review

```http
DELETE /review/delete/{reviewId}
Authorization: Bearer <token>
```

**Response:**
```json
{
  "message": "Review deleted successfully"
}
```

---

### 💬 Comment Endpoints

#### Create Comment

```http
POST /comments/create?memberId={memberId}&reviewId={reviewId}&content={comment_text}
Authorization: Bearer <token>
```

**Query Parameters:**
- `memberId`: ID of the user commenting
- `reviewId`: ID of the review being commented on
- `content`: The comment text

**Response:**
```json
{
  "id": 1,
  "memberId": 1,
  "reviewId": 1,
  "content": "Great review! I totally agree.",
  "createdAt": "2024-11-18T15:00:00"
}
```

#### Reply to Comment

```http
POST /comments/create?memberId={memberId}&reviewId={reviewId}&content={reply_text}&parentId={commentId}
Authorization: Bearer <token>
```

#### Get Comments for Review

```http
GET /comments/review/{reviewId}
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 1,
    "member": {
      "id": 2,
      "name": "Jane Smith",
      "profileImage": "https://example.com/jane.jpg"
    },
    "content": "Great review!",
    "createdAt": "2024-11-18T15:00:00",
    "replies": [
      {
        "id": 2,
        "member": {
          "id": 1,
          "name": "John Doe"
        },
        "content": "Thanks!",
        "createdAt": "2024-11-18T15:05:00",
        "replies": []
      }
    ]
  }
]
```

#### Delete Comment

```http
DELETE /comments/{commentId}?memberId={memberId}
Authorization: Bearer <token>
```

---

### ❤️ Favorites Endpoints

#### Add to Favorites

```http
POST /member/favorites/{userId}/{mediaId}/{mediaType}
Authorization: Bearer <token>
```

**Path Parameters:**
- `userId`: User ID
- `mediaId`: Media ID (from TMDB/Spotify)
- `mediaType`: MOVIE, SERIES, or ALBUM

**Response:**
```json
{
  "message": "Added to favorites successfully",
  "favoriteId": 1
}
```

#### Get User Favorites List

```http
GET /member/favoritesList/{userId}
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 1,
    "mediaId": "550",
    "mediaType": "MOVIE",
    "createdAt": "2024-11-18T10:00:00"
  },
  {
    "id": 2,
    "mediaId": "1396",
    "mediaType": "SERIES",
    "createdAt": "2024-11-18T11:00:00"
  }
]
```

#### Get Favorites with Content Details

```http
GET /member/favoritesListContent/{userId}
Authorization: Bearer <token>
```

**Response:** Array with full content details from APIs

#### Remove from Favorites

```http
DELETE /member/favoritesDelete/{userId}/{mediaId}/{mediaType}
Authorization: Bearer <token>
```

#### Check if Media is Favorited

```http
GET /member/is-favorited?memberId={userId}&mediaId={mediaId}
Authorization: Bearer <token>
```

**Response:**
```json
{
  "isFavorited": true
}
```

---

### 📚 Custom Lists Endpoints

#### Create Custom List

```http
POST /customList/register
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "memberId": 1,
  "name": "Best Horror Films of 2024",
  "description": "My favorite horror movies from this year",
  "isPublic": true
}
```

#### Add Content to List

```http
POST /customList/addContent
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "listId": 1,
  "mediaId": "346364",
  "mediaType": "MOVIE"
}
```

#### Get User's Lists

```http
GET /customList/getList/{memberId}
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Best Horror Films of 2024",
    "description": "My favorite horror movies from this year",
    "isPublic": true,
    "itemCount": 15,
    "createdAt": "2024-01-15T10:00:00"
  }
]
```

#### Get List Contents

```http
GET /customList/getContent/{memberId}/{listName}
Authorization: Bearer <token>
```

#### Update List

```http
PUT /customList/update
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "id": 1,
  "name": "Updated List Name",
  "description": "Updated description"
}
```

#### Delete Content from List

```http
DELETE /customList/deleteContent
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "listId": 1,
  "mediaId": "346364",
  "mediaType": "MOVIE"
}
```

#### Delete List

```http
DELETE /customList/delete/{listId}
Authorization: Bearer <token>
```

---

### 👥 Social Endpoints

#### Follow User

```http
POST /followers/follow?followerId={yourId}&followedId={userId}
Authorization: Bearer <token>
```

**Response:**
```json
{
  "message": "Successfully followed user",
  "followId": 1
}
```

#### Unfollow User

```http
POST /followers/unfollow?followerId={yourId}&followedId={userId}
Authorization: Bearer <token>
```

#### Get Followers

```http
GET /followers/{memberId}/followers
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 2,
    "name": "Jane Smith",
    "profileImage": "https://example.com/jane.jpg",
    "followedAt": "2024-10-15T08:30:00"
  }
]
```

#### Get Following

```http
GET /followers/{memberId}/following
Authorization: Bearer <token>
```

#### Check Follow Status

```http
GET /followers/isFollowing?followerId={yourId}&followedId={userId}
Authorization: Bearer <token>
```

**Response:**
```json
{
  "isFollowing": true
}
```

#### Count Followers

```http
GET /followers/{memberId}/countFollowers
Authorization: Bearer <token>
```

**Response:**
```json
{
  "count": 150
}
```

#### Count Following

```http
GET /followers/{memberId}/countFollowing
Authorization: Bearer <token>
```

---

### 🎬 Movie Endpoints

#### Get Movie by ID

```http
GET /movie/id/{movieId}
Authorization: Bearer <token>
```

#### Get Popular Movies

```http
GET /movie/get/page/{pageNumber}
Authorization: Bearer <token>
```

#### Get Now Playing Movies

```http
GET /movie/now/{pageNumber}
Authorization: Bearer <token>
```

#### Get Upcoming Movies

```http
GET /movie/upcoming/{pageNumber}
Authorization: Bearer <token>
```

#### Get Multiple Movies

```http
GET /movie/several?ids=550,680,510
Authorization: Bearer <token>
```

#### Get Movie Details

```http
GET /movie/{movieId}/details
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": 550,
  "title": "Fight Club",
  "overview": "A ticking-time-bomb insomniac...",
  "posterPath": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
  "backdropPath": "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
  "releaseDate": "1999-10-15",
  "voteAverage": 8.4,
  "runtime": 139,
  "genres": [
    {"id": 18, "name": "Drama"}
  ],
  "cast": [
    {
      "id": 287,
      "name": "Brad Pitt",
      "character": "Tyler Durden",
      "profilePath": "/ajNaPmXVVMJFg9GWmu6MJzTaXdV.jpg"
    }
  ]
}
```

#### Search Movies

```http
GET /search/title/{title}
Authorization: Bearer <token>
```

```http
GET /search/genre/{genreId}
Authorization: Bearer <token>
```

```http
GET /search/year/{year}
Authorization: Bearer <token>
```

#### Get Movie Genres

```http
GET /search/genres
Authorization: Bearer <token>
```

---

### 📺 Series Endpoints

#### Get Series by ID

```http
GET /series/get/{seriesId}
Authorization: Bearer <token>
```

#### Get Popular Series

```http
GET /series/get/page/{pageNumber}
Authorization: Bearer <token>
```

#### Get Currently Airing

```http
GET /series/now/{pageNumber}
Authorization: Bearer <token>
```

#### Get Series Details

```http
GET /series/{seriesId}/details
Authorization: Bearer <token>
```

#### Get Season Details

```http
GET /series/{seriesId}/season/{seasonNumber}
Authorization: Bearer <token>
```

#### Search Series

```http
GET /series/search/title/{title}
Authorization: Bearer <token>
```

```http
GET /series/search/genre/{genreId}
Authorization: Bearer <token>
```

```http
GET /series/search/year/{year}
Authorization: Bearer <token>
```

---

### 🎵 Music Endpoints

#### Get New Album Releases

```http
GET /spotify/api/newAlbumReleases?country=US&limit=20&offset=0&sort=desc
Authorization: Bearer <token>
```

#### Get Album by ID

```http
GET /spotify/api/album/{albumId}
Authorization: Bearer <token>
```

#### Get Multiple Albums

```http
GET /spotify/api/albums?ids=album1,album2,album3
Authorization: Bearer <token>
```

#### Search Albums

```http
GET /spotify/api/searchAlbum?query={albumName}&limit=10
Authorization: Bearer <token>
```

#### Get Artist

```http
GET /spotify/api/artist/{artistId}
Authorization: Bearer <token>
```

#### Search Artists

```http
GET /spotify/api/artist/search?query={artistName}&limit=10
Authorization: Bearer <token>
```

#### Get Track

```http
GET /spotify/track/{trackId}
Authorization: Bearer <token>
```

#### Search Tracks

```http
GET /spotify/track/search?query={trackName}&limit=5
Authorization: Bearer <token>
```

#### Get Albums by Genre

```http
GET /lastfm/albums-by-genre/{genre}?page=1&limit=20
Authorization: Bearer <token>
```

---

### 🏆 Achievement Endpoints

#### Get User Badges

```http
POST /member/{memberId}/badges
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "First Review",
    "description": "Write your first review",
    "icon": "🌟",
    "earnedAt": "2024-01-15T10:30:00"
  },
  {
    "id": 2,
    "name": "Movie Buff",
    "description": "Review 50 movies",
    "icon": "🎬",
    "earnedAt": "2024-05-20T15:45:00"
  }
]
```

---

### 🛡️ Admin Endpoints

#### Toggle User Status

```http
PATCH /admin/members/{userId}/toggle
Authorization: Bearer <admin_token>
```

#### Change User Role

```http
PATCH /admin/members/{userId}/role
Authorization: Bearer <admin_token>
```

#### Get All Users

```http
GET /admin/members?page=0&size=20&search={searchTerm}
Authorization: Bearer <admin_token>
```

#### Get User by ID

```http
GET /admin/members/{userId}
Authorization: Bearer <admin_token>
```

#### Update User

```http
PATCH /admin/members/{userId}/update
Authorization: Bearer <admin_token>
```

#### Get All Reports

```http
GET /admin/reports
Authorization: Bearer <admin_token>
```

#### Update Report Status

```http
PATCH /admin/reports/{reportId}/status?status=RESOLVED
Authorization: Bearer <admin_token>
```

#### Delete Report

```http
DELETE /admin/reports/{reportId}
Authorization: Bearer <admin_token>
```

#### Get Statistics

```http
GET /admin/statistic/reviews-by-date
Authorization: Bearer <admin_token>
```

```http
GET /admin/statistic/popular-media
Authorization: Bearer <admin_token>
```

```http
GET /admin/statistic/users-growth
Authorization: Bearer <admin_token>
```

**Response (User Growth):**
```json
[
  {
    "month": "2024-01",
    "count": 150
  },
  {
    "month": "2024-02",
    "count": 200
  }
]
```

---

### 🚨 Report Endpoints

#### Create Report

```http
POST /reports?reporterId={yourId}&reportedId={userId}&reason={reportReason}
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": 1,
  "reporterId": 1,
  "reportedId": 5,
  "reason": "Inappropriate content in reviews",
  "status": "PENDING",
  "createdAt": "2024-11-18T16:00:00"
}
```

---

## 🔐 Security

### Authentication Flow

1. **User Registration**
    - Password hashed using BCrypt
    - Activation email sent with unique token
    - Account inactive until email confirmed

2. **Login**
    - Credentials validated
    - JWT token generated with user details
    - Token expires after 24 hours (configurable)

3. **Protected Requests**
    - Token sent in Authorization header
    - Server validates token signature
    - User information extracted from token

### Security Features

- ✅ **Password Encryption** - BCrypt with salt rounds
- ✅ **JWT Authentication** - Stateless token-based auth
- ✅ **Email Verification** - Account activation required
- ✅ **Role-Based Access Control** - USER and ADMIN roles
- ✅ **SQL Injection Protection** - Parameterized queries
- ✅ **CORS Configuration** - Controlled origin access
- ✅ **Input Validation** - Request body validation
- ✅ **Rate Limiting** - API request throttling (optional)
- ✅ **Secure File Upload** - File type and size validation
- ✅ **Soft Deletes** - Data retention for auditing

---

## 🚀 Future Enhancements

### Planned Features

- 🎮 **Gamification Expansion**
    - More achievement types
    - Leaderboards
    - Streak tracking
    - Level system

- 📱 **Mobile Application**
    - React Native app
    - Push notifications
    - Offline mode

- 🤝 **Social Features**
    - Direct messaging
    - User groups/communities
    - Live watch parties
    - Collaborative lists

- 🎯 **Advanced Recommendations**
    - Machine learning recommendations
    - Collaborative filtering
    - Taste matching with other users
    - Weekly personalized picks

- 📊 **Enhanced Analytics**
    - Personal statistics dashboard
    - Watching/listening habits
    - Year in review
    - Export personal data

- 🌐 **Internationalization**
    - Multi-language support
    - Regional content
    - Currency support

- 🎨 **Customization**
    - Theme customization
    - Layout preferences
    - Custom profile themes

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### How to Contribute

1. **Fork the Repository**
```bash
git clone https://github.com/yourusername/scoreit.git
```

2. **Create a Feature Branch**
```bash
git checkout -b feature/AmazingFeature
```

3. **Make Your Changes**
    - Write clean, documented code
    - Follow existing code style
    - Add tests if applicable

4. **Commit Your Changes**
```bash
git commit -m 'Add some AmazingFeature'
```

5. **Push to Branch**
```bash
git push origin feature/AmazingFeature
```

6. **Open a Pull Request**
    - Describe your changes
    - Reference any related issues
    - Wait for review

### Contribution Guidelines

- **Code Style**: Follow Java and TypeScript best practices
- **Commit Messages**: Use clear, descriptive messages
- **Documentation**: Update README and API docs
- **Testing**: Add unit tests for new features
- **Issues**: Report bugs with detailed information

---

## 📄 License

This project was developed as a final course project (TCC - Trabalho de Conclusão de Curso) for academic purposes.

**Academic Use**: This project is part of our graduation requirements and is freely available for educational purposes.

---

## 📧 Contact

### Project Team

<div align="center">

| Name | Role | Contact |
|------|------|---------|
| **Bruno Feliciano** | Full-Stack Developer | [GitHub](https://github.com/brunofeliciano) • [LinkedIn](#) |
| **Thiago Rosa** | Full-Stack Developer | [GitHub](https://github.com/thiagorosa) • [LinkedIn](#) |
| **Leandro Faria** | Full-Stack Developer | [GitHub](https://github.com/leandrofaria) • [LinkedIn](#) |

</div>

### Get in Touch

- 📧 Email: scoreit.team@example.com
- 🐛 Report Issues: [GitHub Issues](https://github.com/yourusername/scoreit/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/yourusername/scoreit/discussions)

---

## 🙏 Acknowledgments

Special thanks to:

- **Our University** for support and guidance throughout this project
- **TMDB** for providing comprehensive movie and TV data
- **Spotify** for their excellent music API
- **Spring Boot Community** for extensive documentation
- **Next.js Team** for an amazing React framework
- **Open Source Community** for countless libraries and tools

---

## 📊 Project Stats

<div align="center">

![GitHub repo size](https://img.shields.io/github/repo-size/yourusername/scoreit?style=for-the-badge)
![GitHub language count](https://img.shields.io/github/languages/count/yourusername/scoreit?style=for-the-badge)
![GitHub top language](https://img.shields.io/github/languages/top/yourusername/scoreit?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/yourusername/scoreit?style=for-the-badge)

</div>

---

<div align="center">

### ⭐ If you found this project interesting, please consider giving it a star!

**Made with ❤️ by the ScoreIt Team**

[Back to Top](#-scoreit---social-media-platform-for-entertainment-reviews)

</div>