package it.uniroma3.sii.main;


import it.uniroma3.sii.classifier.*;
import it.uniroma3.sii.event.*;
import it.uniroma3.sii.persistence.TweetEntity;
import it.uniroma3.sii.naiveBayes.*;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;

public class App {	
	public static void main(String[] args){
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		NaiveBayes nb = new NaiveBayes();
		NaiveClassifier.classification(nb);
		EventExtractor ee = new EventExtractor();

		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}


			public void onStatus(Status status) {
				User user = status.getUser();
				TweetEntity tweet = new TweetEntity();

				Event e = ee.eventExtraction(status.getText());
				if(ee.isAnEvent()){
										
					String eventString = e.getNome() + ", " + e.getLuogo() + ", " + e.getData();
					String classification = nb.predict(eventString);
					
					System.out.println(eventString);
					System.out.format("The event is about \"%s\".%n", classification);
					System.out.println("TESTO: " + status.getText() + "\n");
					System.out.format("\n");
					

					// gets Username
					String username = (String) user.getScreenName();
					tweet.setUsername(username);

					//String profileLocation = user.getLocation();
					//tweet.setUserLocation(profileLocation);
					//System.out.println(profileLocation);

					int tweetId = (int) status.getId();
					tweet.setId(tweetId);

					String content = status.getText();
					tweet.setText(content);
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
		String keywords[] = {"football, leicester, euro2016, isis, donald trump, syria, obama, athletics, olympics, rio, george bush, adele, music, david guetta, justin bieber, rihanna, prince, beatles, cher, david bowie, madonna, jennifer lopez, metallica, concert, radiohead, shakira, whitney houston"}; // fino a un massimo di 400
		String languages[] = {"en"}; // filtriamo per lingua. 

		fq.track(keywords);
		fq.language(languages);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);

	}
}

