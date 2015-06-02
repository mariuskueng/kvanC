# kvanC Übung 2 - Chatroom mit *servlet*
Marius Küng; Yves Buschor

29.05.2015

## Beschreibung

Das file `web.xml` ist im Ordner `WEB-INF` dafür verantwortlich, dass Anfragen an die richtige Java Klasse gesendet werden. Das Programm ist in Tomcat im Ordner `webapps/chat` zu installieren. Tomcat sollte auf den Port 8080 eingestellt sein.

Nach der Installation können Client-Anfragen an `http://localhost:8080/chat/Server` gerichtet werden.

Mögliche Client-Anfragen:

- Der Client *<name>* meldet sich an:

	`?action=addParticipant&name=<name>`
	
- Der Client *<name>* meldet sich ab:

	`?action=removeParticipant&name=<name>`

- Das Thema *<topic>* wird zur Themenliste hinzugefügt:

	`?action=addTopic&topic=<topic>`
	
- Das Thema *<topic>* wird aus derThemenliste entfernt:

	`?action=removeTopic&topic=<topic>`
	
- Die Nachricht *<message>*  wird unter dem Thema *<topic>* gepostet:

	`?action=postMessge&message=<message>&topic=<topic>`

- Der Client erhält die letzten 10 Nachrichten zum Thema *<topic>* :

	`?action=getMessages&topic=<topic>`

- Der Client erhält die letzten 10 Nachrichten zum Thema *<topic>* und zusätzlich aktualisierte Themen- und Teilnehmerlisten:

	`?action=refresh&topic=<topic>`


Beim Start eines Clients wird dieser automatisch angemeldet und die Listen werden aktualisiert.


## Relevante Files:

- web/WEB-INF/web.xml
- src.ch.fhnw.kvanc.chat/servlet/Server.java
- src.ch.fhnw.kvanc.chat/servlet/Client.java
