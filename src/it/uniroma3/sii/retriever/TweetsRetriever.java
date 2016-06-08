package it.uniroma3.sii.retriever;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import it.uniroma3.sii.event.*;
import it.uniroma3.sii.persistence.TweetEntity;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;


public final class TweetsRetriever {
	private static final String VERSION = "4.0.1";
	private static final String TITLE = "Twitter4J examples";

	private TweetsRetriever() {
		throw new AssertionError();
	}

	public static String getVersion() {
		return VERSION;
	}



	public static void main(String[] args) throws TwitterException, FileNotFoundException {

		// IL FILE DI CONFIGURAZIONE ï¿½ PRESENTE NEL CLASSPATH, nome: twitter4j.properties  

		// The factory instance is re-useable and thread safe.
		/*Twitter twitter = TwitterFactory.getSingleton();
    Query query = new Query("M5S");
    QueryResult result = twitter.search(query);
    for (Status status : result.getTweets()) {
        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
    }*/

		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		EventExtractor ee = new EventExtractor();

		OutputStream outstream = new FileOutputStream("elenco-tweets.txt");
		Writer output = new OutputStreamWriter(outstream);
		final Writer outputz = new BufferedWriter(output);


		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}


			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}



			@Override
			public void onStatus(Status status) {
				User user = status.getUser();
				TweetEntity tweet = new TweetEntity();
				
				Event e = ee.eventExtraction(status.getText());
				if(ee.isAnEvent()){
					
					System.out.println(e.getNome() + ", " + e.getLuogo() + ", " + e.getData());

					// gets Username
					String username = (String) user.getScreenName();
					tweet.setUsername(username);

					//String profileLocation = user.getLocation();
					//tweet.setUserLocation(profileLocation);
					//System.out.println(profileLocation);

					int tweetId = (int) status.getId();
					tweet.setId(tweetId);
					//System.out.println(tweetId);

					String content = status.getText();
					tweet.setText(content);
					//System.out.println(content +"\n");

					//CONNESSIONE CON POSTGRES E INSERIMENTO DATI
					Connection c = null;
					Statement stmt = null;
					String sql;
					try {
						//outputz.write(content);
						//outputz.write("\n");
						//outputz.flush(); //scriviamo su file il tweet. (sarebbe meglio attualizzare il buffer su file ogni tot tweet e non per ogni tweet). In ogni caso, ogni tweet filtrato verrï¿½ salvato istantaneamente nel nostro file testuale.

						Class.forName("org.postgresql.Driver");
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tweets","postgres", "password");
						c.setAutoCommit(false);
						System.out.println("Opened database successfully");

						stmt = c.createStatement();
						sql = 	"INSERT INTO TWEET_AND_EVENT(ID, TWITTER_USER, TWEET_TEXT, CLASSIFICATION, SOGGETTO, LUOGO, DATA)" +
	                    		"VALUES (" + tweetId + ",'" + username + "','" + (String) content+ "','Sport','" + e.getNome()+ "','" + e.getLuogo() + "','" + e.getData() + "');";		//I tweet vengono inseriti nel db con id del tweet, utente, testo e CLASSE(in questo caso è MUSICA)
	                    //+ "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
	                    //result = stmt.executeQuery("INSERT INTO TWEETS (id,twitter_user,tweet_text) values(' tweetId + "', twitter_user" + username + "', tweet_text" + content);
	                    stmt.executeUpdate(sql);
	       	         	stmt.close();
	                    c.commit();
	       	         	c.close();
						System.out.println("Database closed successfully");

					} catch (Exception ex) {		//(IOException ex)
						System.err.println( ex.getClass().getName()+": "+ ex.getMessage() );
						System.out.println("Errore inserimento dati e/o connessione");
						//Logger.getLogger(Tweets_Retriever.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice sdn) {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

			@Override
			public void onStallWarning(StallWarning sw) {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}

		};

		FilterQuery fq = new FilterQuery();

		//FILTRI:
		String keywords[] = {"leicester, juventus, bayern monaco, uefa euro, olympics, serie a, premier league, football, athletics, claudio ranieri"}; // fino a un massimo di 400
		String languages[] = {"en"}; // filtriamo per lingua.

		fq.track(keywords);
		fq.language(languages);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);

		//twitterStream.sample();

	}
}