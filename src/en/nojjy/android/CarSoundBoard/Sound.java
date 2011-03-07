package en.nojjy.android.CarSoundBoard;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;

public class Sound extends Activity {
	
	// Globals
	
	private int MAX_BPP = 8;
	private int MIN_BPP = 4;
	private int MAX_PAGES = 3;
	
	private int numPages = 1;
	private int numBPP = 6;
	private int curPage = 1;
	
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundHash;
	private List<String> soundArray;
	private MediaPlayer mediaPlayer = null;
	
	private String soundFileName = "CarSoundBoard.dat";
	
	private boolean firstRun = true;
	private Context context;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = getBaseContext();

        initGraphics();
        
        if (firstRun)
        {
        	soundHash = new HashMap<Integer, Integer>();
        	initSounds();
        }
    }
    
    
    /** Graphics Routines **/
    /*************************************************************************************/
    public void initGraphics() {
    	boolean horizontal = getOrientation();
    	int rows = 0;
    	int cols = 0;
    	Integer num = (curPage - 1) * numBPP + 1;
    	final Integer startNum = num;
    	int pageButtons = 0;
    	
    	if (horizontal)
    	{
    		rows = 2;
    		cols = numBPP/rows;
    	}
    	else
    	{
    		cols = 2;
    		rows = numBPP/cols;
    	}
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(cols);
        ImageAdapter adapter = new ImageAdapter(this);
        
        // TODO Configure adapter to show only the proper images
        
        gridview.setAdapter(adapter);    
        gridview.setOnItemClickListener(new OnItemClickListener() {        
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {            
				playSound(position + startNum);
				//Toast.makeText(Sound.this, "" + position, Toast.LENGTH_SHORT).show();        
        	}   
        });
        
        final Button btnStop = (Button)this.findViewById(R.id.btnStop);
		btnStop.setVisibility(View.INVISIBLE);
        btnStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer != null && mediaPlayer.isPlaying())
	        	{
	        		mediaPlayer.stop();
	        		btnStop.setVisibility(View.INVISIBLE);
	        	}
			}
        });
        
        // TODO Connect page buttons
        
        showPageButtons (pageButtons);
    }
    
    public boolean getOrientation() {
    	// TODO Write code to get orientation
    	/* First, get the Display from the WindowManager */  
    	Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();   
    	  
    	/* Now we can retrieve all display-related infos */  
    	int width = display.getWidth();   
    	int height = display.getHeight();   
    	int orientation =  display.getOrientation();  
    	if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    public void showPageButtons (int b)
    {
    	if (b == 1 || b == 3)
    	{
    		// show Left Button
    		ImageButton btnPrev = (ImageButton)this.findViewById(R.id.btnPrevPage);
    		btnPrev.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		// hide Left Button
    		ImageButton btnPrev = (ImageButton)this.findViewById(R.id.btnPrevPage);
    		btnPrev.setVisibility(View.INVISIBLE);
    	}
    	if (b == 2 || b == 3)
    	{
    		// show Right Button
    		ImageButton btnNext = (ImageButton)this.findViewById(R.id.btnNextPage);
    		btnNext.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		// hide Right Button
    		ImageButton btnNext = (ImageButton)this.findViewById(R.id.btnNextPage);
    		btnNext.setVisibility(View.INVISIBLE);    		
    	}
    }
    /**\Graphics Routines **/
    /*************************************************************************************/
    

    /** Sound Routines **/
    /*************************************************************************************/
    
    public void initSounds()
    {
    	//mediaPlayer = MediaPlayer.create(context, 0);
    	// Loop through all files
    	//soundArray.add(arg0)
    	
//    	for (int i = 1; i <= soundArray.size(); i++)
//    	{
//    		 soundHash.put(i, soundPool.load(soundArray.get(i), 1));
//    	}
    	soundHash.put(1, R.raw.ateam);
    	soundHash.put(2, R.raw.batman);
    	soundHash.put(3, R.raw.captainplanet);
    	soundHash.put(4, R.raw.ghostbusters);
    	soundHash.put(5, R.raw.inspectorgadget);
    	soundHash.put(6, R.raw.jamesbond);
    	soundHash.put(7, R.raw.pinkpanther);
    	soundHash.put(8, R.raw.tmnt);
    	//writeSounds();
    }
    
    public void writeSounds()
    {
    	// Write the soundArray out to a file
    	
    	// open file
    	
    	for (int i = 0; i < soundArray.size(); i++)
    	{
    		 // print FILE "$i,$soundArray.get($i)\n"
    	}
    	
    	// close file
    }
    
    public void playSound(int sound) {
    	

		final Button btnStop = (Button)this.findViewById(R.id.btnStop);
		
    	if (mediaPlayer != null && mediaPlayer.isPlaying())
    	{
    		mediaPlayer.stop();
    		mediaPlayer.release();
    	}
		mediaPlayer = MediaPlayer.create(context, soundHash.get(sound));
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				mediaPlayer = null;
				btnStop.setVisibility(View.INVISIBLE);
			}
		});
	    mediaPlayer.start();
		btnStop.setVisibility(View.VISIBLE);
    }
    /** Sound Routines **/
    /*************************************************************************************/
    
   
}