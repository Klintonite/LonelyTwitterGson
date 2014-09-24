package ca.ualberta.cs.lonelytwitter.data;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.renderscript.Type;
import android.util.Log;
import ca.ualberta.cs.lonelytwitter.Tweet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonFileDataManager implements IDataManager {
	
	private Gson gson;
	private static final String FILENAME = "gsonfile.sav";
	
private Context ctx;
	
	public GsonFileDataManager(Context ctx) {
		this.ctx = ctx;
	}
	

	public ArrayList<Tweet> loadTweets() {
		ArrayList<Tweet> lts = new ArrayList<Tweet>();

		try {
			BufferedReader fis = new BufferedReader(new InputStreamReader(ctx.openFileInput(FILENAME)));
			String line;
			StringBuffer fileContent = new StringBuffer();
			
			while ((line = fis.readLine()) != null) {
				fileContent.append(line); 
			}
			
			Type collectionType = (Type) new TypeToken<Collection<Tweet>>(){}.getType();
			lts = gson.fromJson(fileContent.toString(), (java.lang.reflect.Type) collectionType);

		} catch (Exception e) {
			Log.i("LonelyTwitter", "Error casting");
			e.printStackTrace();
		} 

		return lts;
	}

	public void saveTweets(List<Tweet> lts) {
		try {
			FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			String json = gson.toJson(lts);
			fos.write(json.getBytes());
			fos.close();
			
			Log.i("Persistance", "Saved" + json);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
