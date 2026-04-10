# 🎬 Movie Booking Platform (Cloud-Native Microservices)

## 🚀 Overview

This project is a **cloud-native movie ticket booking platform** designed to support both:

- 🎭 **B2B (Theatre Partners)** – onboard theatres, manage shows
- 🎟️ **B2C (End Customers)** – browse movies, book tickets

The system is built using **Spring Boot + Kubernetes-native architecture**, focusing on scalability, security, and production readiness.

---

## ⚙️ Tech Stack

| Layer              | Technology |
|-------------------|-----------|
| Backend           | Spring Boot |
| Security          | Spring Security + JWT |
| Architecture      | Microservices |
| Cloud             | Kubernetes |
| Gateway           | Spring Cloud Kubernetes |
| Config Mgmt       | ConfigMaps / Secrets |
| Service Discovery | Kubernetes DNS |
| Build Tool        | Maven |
| Java Version      | Java 17 |

---

## 🔐 Security Implementation

### ✅ WHAT

- JWT-based authentication
- Role-Based Access Control (RBAC)
- Stateless session management
- Secure API Gateway validation

### ❓ WHY

- ✔ Stateless → best for microservices scaling
- ✔ No session storage required
- ✔ Works seamlessly across distributed systems
- ✔ Centralized authentication at Gateway

---

## 🌐 API Gateway (Spring Cloud Kubernetes)

### 🔍 WHAT

- Single entry point for all client requests
- Handles:
    - JWT validation
    - Request routing
    - Cross-cutting concerns (filters, logging)

### ❓ WHY

- ✔ Centralized security enforcement
- ✔ Reduces duplication across services
- ✔ Kubernetes-native (avoids Netflix stack)
- ✔ Improves maintainability

---

## 🔑 Identity Service

### 🔍 WHAT

Handles:

- User registration
- Authentication (login)
- JWT token generation
- Role management (RBAC)

### ❓ WHY

- ✔ Decouples authentication from business logic
- ✔ Enables independent scaling
- ✔ Improves system security and maintainability

---

## 📦 Microservices Overview

### 1️⃣ Identity Service
- Authentication & Authorization
- JWT generation & validation
- RBAC implementation

### 2️⃣ Movie Service
- Manage movies
- Theatre onboarding
- Show management

### 3️⃣ Booking Service
- Ticket booking
- Seat allocation
- Booking history

---

## 🧩 Kubernetes-Native Design

### 🔍 WHAT

- Service discovery using Kubernetes DNS
- Configuration via ConfigMaps
- Secrets management for sensitive data
- Gateway deployed as Kubernetes service

### ❓ WHY

- ✔ Eliminates need for Eureka/Config Server
- ✔ Fully cloud-native approach
- ✔ Better scalability and resilience
- ✔ Simplified infrastructure

---
## ▶️ Running the Project

### 🔧 Prerequisites

- Java 17
- Maven
- Docker
- Kubernetes (Minikube / Cluster)

---

### 🏃 Steps to Run

#### 1️⃣ Build all services
mvn clean install


#### 2️⃣ Build Docker images
docker build -t identity-service .
docker build -t api-gateway .
docker build -t movie-service .
docker build -t booking-service

#### 3️⃣ Deploy to Kubernetes
kubectl apply -f k8s/
---

## 🔄 Request Flow
Client → API Gateway → JWT Validation → Route → Microservice

---

## 💡 Key Highlights

- ✅ Kubernetes-native microservices architecture
- ✅ JWT + RBAC security (enterprise standard)
- ✅ API Gateway as edge service
- ✅ Clean separation of concerns
- ✅ Scalable and production-ready design

---

## 🚀 Future Enhancements

- 🔔 Notification Service (Email/SMS)
- 💳 Payment Integration
- 📊 Monitoring (Prometheus + Grafana)
- 📦 CI/CD Pipeline (GitHub Actions)
- ⚡ Caching (Redis)

---
