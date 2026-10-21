<div align="center">

<br/>

```
                                             ██╗███╗   ███╗███████╗
                                             ██║████╗ ████║██╔════╝
                                             ██║██╔████╔██║███████╗
                                             ██║██║╚██╔╝██║╚════██║
                                             ██║██║ ╚═╝ ██║███████║
                                             ╚═╝╚═╝     ╚═╝╚══════╝
```

# Information Management System
### Bangladesh Army University of Engineering & Technology

> *Replacing scattered WhatsApp threads with a structured, role-based academic tool — built in Java for the people who keep classes running.*

<br/>

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=black)
![Material 3](https://img.shields.io/badge/UI-Material%203-6750A4?style=flat-square&logo=materialdesign&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active-success?style=flat-square)

<br/>

</div>

---

## 🎯 The Problem

Class Representatives at BAUET were managing everything through group chats — attendance, assignments, absence reports, test counts — all buried in an endless scroll of unstructured messages. **IMS fixes that.**

---

## ✨ Features

<table>
<tr>
<td width="50%">

### 👤 Role-Based Access
Two distinct user types, two focused dashboards. Students and Class Representatives see what they need. The HOD and authority users get the full picture.

</td>
<td width="50%">

### 📋 Attendance Tracking
Mark daily attendance per course through a dynamic student checklist. No more counting names in a chat.

</td>
</tr>
<tr>
<td width="50%">

### 📸 Assignment Upload
Capture or pick images and push them to Firebase as Base64. Submissions stay organized — not lost in someone's DMs.

</td>
<td width="50%">

### 📊 Class Test Counter
Track exactly how many class tests have been conducted per course. Clean, persistent, auditable.

</td>
</tr>
<tr>
<td width="50%">

### 🚫 Absence Reporting
Log absences with course code, student ID, and reason in one structured flow. No ambiguity.

</td>
<td width="50%">

### 📁 Student List Manager
Dynamically add or remove students with ID and name. The source of truth for your cohort.

</td>
</tr>
</table>

---

## 🏗️ Architecture

```
IMS/
└── src/main/java/com/app/testlogin/
    │
    ├── 🔐 Auth Layer
    │   └── MainActivity.java          # Login + role routing
    │
    ├── 📱 Dashboards
    │   ├── page.java                  # Student / CR dashboard
    │   └── autormain.java             # Authority / HOD dashboard
    │
    ├── 📋 Attendance Module
    │   ├── attendence.java            # Mark attendance
    │   └── attenden_list.java         # View attendance records
    │
    ├── 📸 Assignment Module
    │   ├── Assignment.java            # Upload assignment images
    │   └── static_asi.java            # Submission status view
    │
    ├── 🧪 Academic Records
    │   ├── CT.java                    # Class test counter
    │   ├── absent.java                # Submit absence reason
    │   └── absentlist.java            # View absence records
    │
    └── 🛠️ Utilities
        ├── list.java                  # Student list manager
        ├── chatbox.java               # Chat interface (WIP)
        └── threedays.java             # 3-day absent tracker (WIP)
```

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Language** | Java (Android SDK) | Core application logic |
| **UI Framework** | Material 3 + CardView + AppCompat | Native Android UI |
| **Local Storage** | SharedPreferences | Offline-first data persistence |
| **Cloud Database** | Firebase Realtime Database | Synced remote data |
| **File Storage** | Firebase Storage (Base64) | Assignment image uploads |
| **Build System** | Gradle | APK compilation & dependency management |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- Java 11+
- A Firebase project with Realtime Database and Storage enabled

### Setup

```bash
# Clone the repository
git clone https://github.com/ironer201/IMS-BAUET.git

# Open in Android Studio
# File → Open → select the project folder

# Add your Firebase config
# Place google-services.json in /app/
```

Then sync Gradle and run on an emulator or physical device.

> **Pre-built APK** is included in the repo root for quick testing without a build setup.

---

## 📱 Screenshots

*Coming soon — UI walkthrough of CR and HOD dashboards.*

---

## 🗺️ Roadmap

- [x] Role-based login system
- [x] Attendance marking + records
- [x] Assignment image upload
- [x] Class test counter
- [x] Absence reporting
- [x] Student list management
- [ ] In-app chat (chatbox.java — in progress)
- [ ] 3-day absence alert system (threedays.java — in progress)
- [ ] Push notifications for HOD alerts
- [ ] Export attendance to PDF/CSV

---

## 👥 Team

| Role | Name |
|------|------|
| **Developer** | [Md. Zarif Noor](https://github.com/ironer201) |
| **UI/UX Designer** |  [Jannatul Naima Moon ](https://github.com/DarkSideScript) |
| **Contributor** *(CT Logic)* | Jihad Hasan Jwel |

---

## 📄 License

This project is developed for **BAUET** as an academic utility tool.  
Feel free to reference the architecture or contribute — open an issue or PR.

---

<div align="center">

*Built with care for every CR who ever had to scroll back 300 messages to find an absence report.*

**⭐ Star this repo if it helped you or sparked an idea.**

</div>
