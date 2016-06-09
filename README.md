# TwitterEventExtractor

*GUIDA ALL'INSTALLAZIONE*
____________________________________________________________________________________________________________________________________
1) Librerie ufficiali
  - JDBC4 Postgresql Driver, Version 9.4-1208 (https://jdbc.postgresql.org/download.html),
  - SLF4J-API (http://www.slf4j.org/download.html),
  - SLF4J-SIMPLE (http://www.slf4j.org/download.html),
  - stanford-corenlp-3.6.0-javadoc.jar (http://stanfordnlp.github.io/CoreNLP/),
  - stanford-corenlp-3.6.0models.jar (http://stanfordnlp.github.io/CoreNLP/),
  - stanford-corenlp-3.6.0-models.jar (http://stanfordnlp.github.io/CoreNLP/),
  - stanford-corenlp-3.6.0-source.jar (http://stanfordnlp.github.io/CoreNLP/),
  - stanford-corenlp-3.6.0.jar (http://stanfordnlp.github.io/CoreNLP/),
  - twitter4j-async-4.0.4.jar (http://twitter4j.org/en/index.html#download),
  - twitter4j-core-4.0.2.jar (http://twitter4j.org/en/index.html#download),
  - twitter4j-stream-4.0.4.jar (http://twitter4j.org/en/index.html#download).

_____________________________________________________________________________________________________________________________________  
2) Configurazione Twitter4J
  - Creare un account su TwitterDev (https://dev.twitter.com/) e registrare l'applicazione.
  - Recuperare le chiavi  CONSUMER_KEY,CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET.
  - Salvare le chiavi di cui sopra nel file twitter4j.properties.
  
_____________________________________________________________________________________________________________________________________
3) Creazione DataSet
  - Creare un database con delle tabelle, una per ogni categoria (es. 'Musica','Politica','Sport'), popolate opportunamente con il 
    dataset di training.
    Nella cartella Dataset vi sono degli esempi di tabelle.
  - Verificare le credenziali per collegarsi a Postgres.

______________________________________________________________________________________________________________________________________
4) Run
  - All'interno della classe App è possibile impostare dei filtri per lingua e parole chiave contenute nei tweet.
  - Run & Enjoy!
  
  
  
PER MAGGIORI INFORMAZIONI:
Avolio Luca             luc.avolio@stud.uniroma3.it
Botticelli Martina      mar.botticelli@stud.uniroma3.it
Buratti Rinaldo         rinaldoburatti@gmail.com
