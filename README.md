# Healthfuelness

Important:
-
- URL-ul pentru tot proiectul Healthfuelness: https://github.com/BiancaTripa/Healthfuelness.git
- URL-ul pentru "Healthfuelness: Aplicație mobilă pentru monitorizarea sănătății", partea
poiectul Healthfuelness, proiectată și implementată de Bianca-Cristina Tripa:
- URL-ul pentru "Healthfuelness: Sistem de senzori pentru monitorizarea factorilor de mediu ce influențează sănătatea", partea poiectul Healthfuelness, proiectată și implementată de Cristina-Andreea Urucu și care se poate găsi pe brench-ul „Arduino”: https://github.com/BiancaTripa/Healthfuelness/tree/Arduino (cu codul sursă pentru Arduino: https://github.com/BiancaTripa/Healthfuelness/blob/Arduino/Arduino/Aprox_Complet/Arduino_code.ino)

Pentru a instala Android IDE și Arduino IDE și a compila și lansa aplicația, urmați pașii de mai jos:

Instalarea Android IDE:
-

1. Accesați site-ul oficial Android Studio: https://developer.android.com/studio.

2. Faceți clic pe butonul "Descărcare Android Studio" pentru a începe descărcarea fișierului de instalare.
  
3. Odată ce descărcarea este completă, executați fișierul de instalare.
  
4. În timpul procesului de instalare, acceptați acordul de licență și selectați opțiunile potrivite pentru configurația dvs. (puteți lăsa setările implicite dacă nu sunteți sigur).
   
5. După instalare, deschideți Android Studio. Ați fi întâmpinat cu un ecran de bun venit și opțiuni pentru crearea unui nou proiect.

6. Accesați pagina de proiect GitHub pe care doriți să o clonați. Faceți clic pe butonul "Clone or download" și copiați URL-ul repo-ului.

7. În ecranul de bun venit, faceți clic pe "Check out project from Version Control" și selectați "Git" din lista derulantă.

8. În câmpul URL, introduceți URL-ul repo-ului GitHub pe care l-ați copiat anterior. Alegeți un director local pentru a salva proiectul clonat.

9. Faceți clic pe butonul "Clone" pentru a începe procesul de clonare. Android Studio va descărca toate fișierele de pe repo-ul GitHub și va crea un proiect local în directorul ales.

10. Așteptați ca Android Studio să finalizeze clonarea repo-ului GitHub și să configureze proiectul local.

11. După finalizarea clonării, Android Studio va deschide proiectul într-o fereastră nouă. Verificați fișierul `build.gradle` pentru a vă asigura că aveți toate dependențele necesare și configurațiile de compilare. După aceea, faceți clic pe butonul "Run" (butonul verde cu un triunghi) pentru a construi și rula aplicația pe un dispozitiv virtual sau dispozitiv real conectat.
    
Instalarea Arduino IDE:
-

1. Accesați site-ul oficial Arduino: https://www.arduino.cc/en/software.
   
2. Faceți clic pe butonul "Download the Arduino IDE" pentru a începe descărcarea fișierului de instalare.
   
3. Odată ce descărcarea este completă, executați fișierul de instalare.
   
4. În timpul procesului de instalare, acceptați acordul de licență și selectați opțiunile potrivite pentru configurația dvs. (puteți lăsa setările implicite dacă nu sunteți sigur).
   
5. După instalare, deschideți Arduino IDE. Veți fi întâmpinat cu o fereastră goală și o structură de bază pentru proiectul Arduino.
   
6. Accesați pagina de proiect GitHub care conține biblioteca sau codul sursă pe care doriți să îl importați în Arduino IDE.

5. Pe pagina de proiect GitHub, faceți clic pe butonul "Code" și selectați opțiunea "Download ZIP" pentru a descărca întregul cod sursă al proiectului în format ZIP. Salvați fișierul ZIP într-un loc accesibil pe computer.

6. În Arduino IDE, selectați "Sketch" din meniul principal, apoi "Include Library" și "Add .ZIP Library". Alegeți fișierul ZIP pe care l-ați descărcat anterior și faceți clic pe "Accept" pentru a importa biblioteca în Arduino IDE.

7. După ce ați importat biblioteca, extrageți conținutul fișierului ZIP descărcat într-un folder pe computer.

8. În Arduino IDE, selectați "File" din meniul principal și apoi "Open". Navigați către folderul în care ați extras codul sursă și selectați fișierul cu extensia ".ino" (fișierul principal al proiectului Arduino).

9. Verificați și configurați setările specifice proiectului în funcție de cerințele bibliotecii și ale codului sursă importat. Aceasta poate include selectarea plăcii de dezvoltare corecte, portul serial și altele. Puteți accesa aceste setări din meniul "Tools" în Arduino IDE.

10. Conectați placa Arduino la computer utilizând un cablu USB și asigurați-vă că ați selectat portul serial corect în setările Arduino IDE. Apoi, faceți clic pe butonul "Upload" (săgeata în sus) pentru a compila și încărca codul pe placa Arduino.
