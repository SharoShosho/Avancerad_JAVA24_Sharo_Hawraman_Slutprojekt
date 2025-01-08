# Avancerad_JAVA24_Sharo_Hawraman_Slutprojekt

### **Sammanfattning av hur koden fungerar och används**

Den här applikationen är en enkel **ToDo-lista** som består av en **frontend** byggd med JavaFX och en **backend** skapad med Spring Boot. Frontend och backend kommunicerar via HTTP-anrop. Här är en kort förklaring av hur koden fungerar:

---

#### **Backend**
Backend innehåller en REST API-controller (`TaskController`) som hanterar CRUD-operationer för uppgifter:

1. **Lista alla uppgifter:**  
   Endpointen `/api/tasks` (GET) returnerar en lista över alla uppgifter.

2. **Hämta en specifik uppgift:**  
   Endpointen `/api/tasks/{id}` (GET) returnerar en uppgift baserad på dess ID.

3. **Lägg till en ny uppgift:**  
   Endpointen `/api/tasks` (POST) tar emot en uppgift i JSON-format, genererar ett unikt ID, och lägger till den i en lista.

4. **Uppdatera en uppgift:**  
   Endpointen `/api/tasks/{id}` (PUT) uppdaterar namnet på en befintlig uppgift med hjälp av ID.

5. **Ta bort en uppgift:**  
   Endpointen `/api/tasks/{id}` (DELETE) tar bort en uppgift baserat på dess ID.

Alla dessa operationer hanteras av `taskList`, som simulerar en databas i minnet.

---

#### **Frontend**
Frontend är en JavaFX-applikation som låter användaren interagera med backend via en grafisk användargränssnitt (GUI):

1. **Lägga till en uppgift:**  
   Användaren skriver in ett uppgiftsnamn i ett textfält och klickar på "Add". Applikationen skickar en POST-förfrågan till backend för att lägga till uppgiften.

2. **Redigera en uppgift:**  
   Användaren väljer en uppgift från listan, skriver in ett nytt namn i textfältet, och klickar på "Edit". Applikationen skickar en PUT-förfrågan för att uppdatera uppgiften.

3. **Ta bort en uppgift:**  
   Användaren väljer en uppgift från listan och klickar på "Delete". Applikationen skickar en DELETE-förfrågan till backend för att ta bort uppgiften.

4. **Ladda alla uppgifter:**  
   Vid start (och efter varje operation) skickar applikationen en GET-förfrågan för att hämta och visa alla uppgifter i listan.

---

#### **Hur används applikationen?**
1. Starta backend med Spring Boot. Detta startar en server på `http://localhost:8080/api/tasks`.  
2. Starta frontend via JavaFX.  
3. Använd GUI för att lägga till, redigera, eller ta bort uppgifter, och backend hanterar datalagringen.  

Frontend och backend kommunicerar via HTTP, där JSON används för att skicka och ta emot uppgiftsdata.
