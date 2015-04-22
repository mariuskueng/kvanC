# kvanC Übung 1 — Chatroom mit *socket*

Marius Küng, Yves Buschor

Klasse 4iCa

------

Für die Implementation des Chatrooms mit socket haben wir neben den vorgegebenen Files folgenden Code implementiert:

## socket/server/

### `Server.java`

Dieses File startet den Server. Dazu wird ein neuer `ServerSocket` am definierten `SERVER_PORT` 1235 instanziert und ein Thread mit einem `connectionListener` für diesen ServerSocket gestartet.

### `ConnectionListener.java`

Der ConnectionListener führt eine Liste mit den verschiedenen Clients, welche jeweils durch einen ConnectionHandler repräsentiert sind.

### `ConnectionHandler.java`

Der ConnectionHandler initialisiert einen `inputStream` und `outputStream` für jeden Client und startet einen Thread, womit auf Eingaben über diesen Stream gewartet wird.

Die Methode `processMessage` verarbeitet den Message-String, welcher vom Client empfangen wird. Der String wird in die einzelnen Bestandteile aufgespalten und die entsprechende Aktion eingeleitet.

## socket/client/

### `Client`

Die main-Methode erzeugt einen neuen Client gemäss den Argumenten, welche über das GUI mitgereicht werden. Auf der Client-Seite wird nun ebenfalls ein Socket erzeugt sowie ein `inputStream`, welcher an eine Instanz von `ClientMessageReceiver` weitergereicht wird  und ein `outputStream`, welcher an eine Instanz von `ClientMessageSender` weitergegeben wird.

Nach der Instanziierung wird der Client zum Chatroom hinzugefügt und eine Liste von Topics und Teilnehmern abgerufen.

### `ClientMessageReceiver`

Der ClientMessageReceiver beobachtet den inputStream auf Nachrichten, welche vom Server an den Client gesendet werden. Sobald eine Nachricht auf diesem Stream eintrifft, wird sie mittels der `processMessage`-Methode verarbeitet. Dies sind primär Prozesse, welche auf dem GUI des Client ausgeführt werden.

### `ClientMessageSender`

Der ClientMessageSender stellt dem Client verschiedene Methoden zur Verfügung, welche die Message-Strings zusammenfügen und mittels `outputStream` an den Server gesendet werden.