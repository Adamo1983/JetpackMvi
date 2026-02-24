# JetpackMvi - Android App

JetpackMvi è un'applicazione Android dimostrativa che implementa l'architettura **MVI (Model-View-Intent)** utilizzando le più moderne tecnologie di sviluppo Android. L'app permette di gestire un elenco di post, interagendo con un'API remota e gestendo lo stato in modo reattivo.

## 📱 Funzionalità dell'App

L'applicazione offre un'esperienza completa per la gestione di contenuti (CRUD):

1.  **Visualizzazione Elenco (Home):** All'avvio, l'app recupera una lista di post da un servizio REST esterno. 
2.  **Dettaglio Post:** Cliccando su un post, l'utente può visualizzarne il contenuto completo e l'immagine dedicata.
3.  **Gestione Multimediale Avanzata:** In fase di creazione post, l'utente può scegliere l'immagine tramite tre modalità:
    *   **Fotocamera:** Scatto di una foto in tempo reale tramite integrazione CameraX.
    *   **Galleria:** Selezione di un'immagine esistente dal dispositivo tramite Photo Picker.
    *   **Generazione Dinamica:** Se non viene fornita un'immagine locale, l'app ne genera una automaticamente tramite provider esterni (Picsum, Robohash, etc.) in base alla categoria scelta (Landscape, Food, Tech, etc.).
4.  **Creazione Post:** Form interattivo per l'aggiunta di nuovi contenuti con anteprima dell'immagine selezionata.
5.  **Modifica Post:** L'utente può modificare i post esistenti aggiornando titolo e contenuto.
6.  **Eliminazione Post:** Funzionalità per rimuovere un post sia dall'interfaccia che (simulando) dal server.
7.  **Persistenza e Preferenze:** Utilizzo di DataStore per il salvataggio di impostazioni locali.

## 🛠️ Stack Tecnologico

L'app è costruita seguendo le migliori pratiche di "Modern Android Development" (MAD):

### Core & UI
*   **Kotlin**: Linguaggio di programmazione principale.
*   **Jetpack Compose**: Toolkit moderno per la creazione di interfacce utente dichiarative.
*   **Material 3**: Ultima versione del design system di Google.
*   **Navigation Compose**: Gestione della navigazione type-safe.

### Architettura
*   **MVI (Model-View-Intent)**: Gestione dello stato basata su flussi unidirezionali (UDF).
*   **Clean Architecture**: Separazione in Data, Domain e Presentation layer.
*   **Hilt (Dagger)**: Dependency Injection.

### Gestione Dati & Network
*   **Retrofit & OkHttp**: Chiamate API REST.
*   **Kotlinx Serialization**: Parsing JSON.
*   **Room Database**: Persistenza locale.
*   **DataStore**: Gestione preferenze.
*   **Coroutines & Flow**: Programmazione asincrona e reattiva.

### Librerie di Supporto
*   **CameraX**: Per l'acquisizione di foto direttamente dall'app.
*   **Coil**: Caricamento immagini asincrono da URL o URI locali.
*   **Accompanist**: Gestione dei permessi di sistema (es. Fotocamera).

## 🏗️ Struttura del Progetto

Il progetto è organizzato in pacchetti che riflettono la Clean Architecture:
*   `data/`: Implementazione repository, database e API.
*   `domain/`: Modelli, interfacce e Use Case.
*   `presentation/`: UI (Compose), ViewModel (MVI) e temi.
