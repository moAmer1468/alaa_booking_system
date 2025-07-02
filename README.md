# 🏥 Doctor Appointment Booking System (Full Stack)

## 🔧 Technologies Used

- **Frontend:** React.js
- **Backend:** Java + Spring Boot (RESTful API)
- **Database:** H2 (in-memory) for development, MySQL for production
- **Security:** Spring Security with JWT (JSON Web Token)
- **Build Tools:** Maven
- **Other Tools:** Lombok, JPA/Hibernate, jsPDF (for PDF generation), Postman (API testing)

---

## 📋 Project Overview

The Doctor Appointment Booking System is a full-stack web application designed to help doctors and clinic assistants manage patient records, schedule appointments, and generate printable daily reports. It is intended to be deployed inside clinics — particularly in rural or local areas — without requiring an internet connection.

This application offers a simple, intuitive interface and can be hosted on a local machine or small clinic server.

---

## 🔐 Core Features

### 👨‍⚕️ User Management

- Role-based access (`DOCTOR`, `ASSISTANT`, `ADMIN`)
- Secure login using Spring Security & JWT
- Assistants can manage appointments and patients
- Doctors can view and print schedules

### 👥 Patient Management

- Add, update, and delete patient profiles
- Store medical history and contact details
- Search and filter patients

### 📅 Appointment Booking

- Schedule appointments for specific doctors
- Set date, time, and notes
- Appointment status: `PENDING`, `COMPLETED`, `CANCELLED`
- Calendar view and list view available

### 🧾 Reporting

- Daily report generation of all appointments
- Download or print PDF reports for clinic use
- Track completed visits and patient activity

---

## 🗃️ Database Structure (Simplified)

### Tables

- `users`: authentication and roles
- `patients`: patient records
- `appointments`: appointment bookings with status
- `roles` (optional): role-based access mapping

---

## 🧱 Backend Modules

- `model`: contains all entity classes
- `repository`: JPA interfaces for DB access
- `service`: business logic and security handlers
- `controller`: REST API endpoints
- `security`: JWT filters, token generation, authentication
- `config`: Spring configurations and CORS setup

---

## 📲 REST API Endpoints (Example)

| Method | Endpoint             | Description                 |
| ------ | -------------------- | --------------------------- |
| POST   | `/api/auth/login`    | Login and receive JWT token |
| GET    | `/api/patients`      | Get all patients            |
| POST   | `/api/patients`      | Add a new patient           |
| GET    | `/api/appointments`  | Get all appointments        |
| POST   | `/api/appointments`  | Create a new appointment    |
| GET    | `/api/reports/daily` | Daily printable report      |

---

## 🖨️ Printing & PDF

- Uses `jsPDF` and `html2canvas` (on the frontend) to generate printable reports
- Doctors can view or print appointments by day
- Reports are simple and clean, ready for clinic use

---

## 🖥️ Hosting & Deployment

- **Development:** Can run locally on any machine (with Java and Node.js)
- **Production:** Can be deployed to a local server or LAN-connected PC inside the clinic
- No internet connection required for main features (except cloud sync or notifications if needed)

---

## 🧠 Why This System?

- Helps small clinics digitize their appointment workflow
- Makes scheduling and patient tracking easier and more reliable
- Saves time and paper with automatic reporting
- Designed for **simplicity**, **offline usage**, and **real-world clinic needs**

---

## 🚀 Future Improvements

- Patient notification system (WhatsApp or SMS)
- Admin dashboard for monitoring overall activity
- Export data to Excel
- Mobile view support
- Cloud sync (optional)

---

## ✅ Final Notes

This system is fully modular and scalable — more features like prescriptions, file attachments, or AI-based scheduling can be added easily. It’s a powerful base for building any medical booking or patient management solution using **React + Java Spring Boot**.

# alaa_booking_system
