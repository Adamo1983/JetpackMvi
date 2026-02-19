# TestIdeAiApp

TestIdeAiApp è un'applicazione Android dimostrativa che implementa una gestione completa (CRUD) di "Post" utilizzando le più moderne tecnologie e best practice di sviluppo.

## 🚀 Caratteristiche

- **CRUD Completo**: Visualizzazione lista, dettaglio, creazione, modifica ed eliminazione di post.
- **Offline-First**: I dati vengono salvati localmente in un database Room per essere consultabili anche senza connessione internet.
- **Sincronizzazione Remota**: Integrazione con REST API (JSONPlaceholder) tramite Retrofit.
- **Architettura Pulita (Clean Architecture)**: Separazione netta tra Data, Domain e Presentation layer.
- **UI Moderna**: Interfaccia interamente sviluppata in Jetpack Compose con supporto al tema chiaro/scuro e Material 3.

## 🛠️ Tech Stack

- **Linguaggio**: Kotlin
- **UI**: Jetpack Compose
- **Dependency Injection**: Hilt (Dagger)
- **Database Locale**: Room
- **Networking**: Retrofit & OkHttp
- **Asincronia**: Coroutines & Flow
- **JSON Parsing**: Moshi
- **Image Loading**: Coil
- **Navigazione**: Compose Navigation

## 🏗️ Architettura

Il progetto segue i principi della **Clean Architecture**:

1.  **Domain Layer**: Contiene i modelli di business (`Post`), le interfacce dei repository e gli Use Case (es. `GetPostsUseCase`, `CreatePostUseCase`). È indipendente da librerie esterne.
2.  **Data Layer**: Implementa le interfacce del repository. Gestisce la logica di caching tra il database locale (Room) e il network (Retrofit). Include anche i DTO e i Mapper.
3.  **Presentation Layer**: Implementa la UI utilizzando il pattern MVVM. Gli stati sono gestiti tramite `StateFlow`.
    - `navigation`: Gestisce il NavHost e le rotte.
    - `screen`: Contiene le diverse schermate (Home, Detail, Create, Edit) e i relativi ViewModel.
    - `theme`: Definisce i colori, la tipografia e il tema dell'app.

## 📁 Struttura del Progetto

```text
it.innovactors.testideaiapp/
├── data/
│   ├── local/          # Room database, DAO ed Entities
│   ├── remote/         # Retrofit API e DTO
│   ├── repository/     # Implementazione del repository
│   └── mappers/        # Funzioni di conversione modelli
├── domain/
│   ├── model/          # Modelli di dominio
│   ├── repository/     # Interfacce dei repository
│   └── usecase/        # Logica di business specifica
├── presentation/
│   ├── navigation/     # NavGraph e rotte
│   ├── screen/         # Schermate e ViewModel (Home, Detail, Create, Edit)
│   └── theme/          # Configurazioni Material 3
└── di/                 # Moduli Hilt per la Dependency Injection
```

## 🚥 Come iniziare

1. Clonare il repository.
2. Aprire il progetto in Android Studio (Ladybug o superiore).
3. Eseguire la sincronizzazione di Gradle.
4. Avviare l'app su un emulatore o dispositivo fisico.
