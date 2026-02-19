# JetpackMvi (TestIdeAiApp)

JetpackMvi è un'applicazione Android dimostrativa avanzata che implementa una gestione completa (CRUD) di "Post" utilizzando le più moderne tecnologie e best practice di sviluppo, con un'architettura **MVI (Model-View-Intent)** pulita e scalabile.

## 🚀 Caratteristiche

- **Architettura MVI**: Gestione dello stato unidirezionale e prevedibile per ogni schermata.
- **CRUD Completo**: Visualizzazione lista, dettaglio, creazione, modifica ed eliminazione di post.
- **Temi Immagini Dinamici**: Ogni post può avere un tema visivo differente (Landscape, Robot, Avatar, Tech, Kitten, Food, Nature, Beard, Nicolas Cage).
- **Gestione Immagini Centralizzata**: Logica di generazione URL astratta tramite `ImageProvider` nel layer di dominio.
- **Offline-First**: I dati vengono salvati localmente in un database Room per essere consultabili anche senza connessione internet.
- **Sincronizzazione Remota**: Integrazione con REST API (JSONPlaceholder) tramite Retrofit.
- **UI Moderna**: Interfaccia sviluppata in Jetpack Compose con Material 3 e componenti dinamici (Dropdown menu, animazioni di caricamento).

## 🛠️ Tech Stack

- **UI**: Jetpack Compose (Material 3)
- **Dependency Injection**: Hilt (Dagger)
- **Database Locale**: Room
- **Networking**: Retrofit & OkHttp
- **Immagini**: Coil
- **Asincronia**: Kotlin Coroutines & Flow
- **Architettura**: Clean Architecture + MVI

## 🏗️ Architettura

Il progetto segue i principi della **Clean Architecture** e del pattern **MVI**:

1.  **Domain Layer**:
    - **Models**: `Post`, `PostTheme`.
    - **Use Cases**: Logica di business (es. `GetPostsUseCase`, `UpdatePostUseCase`).
    - **Utils**: Interfaccia `ImageProvider` per la generazione degli URL delle immagini.
2.  **Data Layer**:
    - **Repository**: Implementazione di `PostRepository` con strategia di caching locale/remoto.
    - **Local**: Database Room, DAO ed Entity.
    - **Remote**: Retrofit API e DTO.
    - **Utils**: `PicsumImageProvider` che implementa la logica multi-tema per le immagini.
3.  **Presentation Layer**:
    - **MVI Pattern**: Ogni schermata ha un `UiState` (stato) e un `Action` (intento dell'utente).
    - **ViewModel**: Gestiscono lo stato e reagiscono alle azioni.
    - **Screens**: Componenti Compose (Home, Detail, Edit).

## 🖼️ Sistema di Temi per le Immagini

L'applicazione utilizza un sistema flessibile per associare immagini ai post senza caricarle manualmente. Grazie al `PostTheme`, l'URL dell'immagine viene generato dinamicamente usando diversi servizi:
- **Lorem Picsum** (Landscape)
- **Robohash** (Robots)
- **i.pravatar.cc** (Avatars)
- **LoremFlickr** (Tech, Kitten, Food, Nature)
- **PlaceBeard** (Beard)
- **PlaceCage** (Nicolas Cage)

## 📁 Struttura del Progetto

```text
it.branjsmo.jetpackmvi/
├── data/
│   ├── local/          # Room database, DAO ed Entities
│   ├── remote/         # Retrofit API e DTO
│   ├── repository/     # Implementazione del repository
│   ├── mappers/        # Conversione DTO/Entity <-> Domain
│   └── util/           # Implementazioni concrete (PicsumImageProvider)
├── domain/
│   ├── model/          # Modelli di dominio (Post, PostTheme)
│   ├── repository/     # Interfacce dei repository
│   ├── usecase/        # Use Case per la logica di business
│   └── util/           # Astrazioni (ImageProvider)
├── presentation/
│   ├── screen/         # Schermate (View), ViewModel, UiState e Actions (MVI)
│   ├── components/     # Componenti UI riutilizzabili
│   ├── navigation/     # Navigazione Compose
│   └── theme/          # Configurazioni Material 3
└── di/                 # Moduli Hilt (Network, Database, Repository, Util)
```

## 🚥 Come iniziare

1. Clonare il repository.
2. Aprire il progetto in Android Studio (Ladybug o superiore).
3. Eseguire la sincronizzazione di Gradle.
4. Avviare l'app su un emulatore o dispositivo fisico.
