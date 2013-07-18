package com.myfirstapp.domino;

import Android.Tiles.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;

public class TilesPlayerView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener, View.OnClickListener
{   
	/** System params */
	private Context appContext;
	private SurfaceHolder surfaceholder;
	/** Custom_SurfaceView Thread */
	private TilesPlayThread gameThread;


	private DominoApplication app;
	private Paint paint1;
	private Paint paint2;
	/** Button */
	private Button newGame;
	private Button exit;
	private Button stock;

	/** Values for onTouch method */
	private Coord d_old=null;
	private Domino d=null;
	private int tileReleased=-1;
	private int touchedTile=-1;
	private float distx;
	private float disty;

	/** Screen specs */
	private float width,height;
	private int longd,largd;


	private SparseArray<Bitmap> map;
	private int color;
	private boolean transRunning;
	private Coord dep,fin,fun_param,bitm;
	private float yIncr;
	private float xfin,yfin;

	private boolean state_exit;
	private boolean state_newgame;
	private boolean state_stock;

	private boolean transActivated;

	public TilesPlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		setFocusable(true);


		this.app=((DominoApplication)(this.getContext().getApplicationContext()));
		this.appContext=context;
		this.map=new SparseArray<Bitmap>(60);

		paint1=new Paint();
		paint2=new Paint();
		paint2.setColor(Color.BLACK);
		paint2.setStyle(Paint.Style.FILL);
		paint2.setAlpha(50);
	}


	public void surfaceCreated(SurfaceHolder holder) {

		Log.d("testapp","surfaceCreated !");

		/** Thread starting **/
		gameThread = new TilesPlayThread(getHolder(), this);
		gameThread.setRunning(true);
		gameThread.start();


		setOnTouchListener(this);
		this.newGame=app.getNewGameButton();
		this.exit=app.getExitButton();
		this.stock=app.getButtonPioche();

		this.transActivated=app.getTransPref();

		/** refreshing screen size and tile size */
		height=holder.getSurfaceFrame().bottom;
		width=holder.getSurfaceFrame().right;
		getAdjustedDimension(appContext);
		transRunning=false;
		
		Log.d("testapp","transmission des parametres");
		this.app.setConfig((int)width, (int)height, this.longd, this.largd);

	}

	public void reset(Context context) {
		Log.d("testapp","reset");
		this.appContext = context;
	    postInvalidate();
	    
	    this.d_old = null;
	    this.d = null;
	    this.tileReleased = -1;
	    this.touchedTile = -1;
	    
	    Log.d("testapp", "mise � jour des nouveaux parametres");
	    getAdjustedDimension(this.appContext);
	    this.app.setConfig((int)this.width, (int)this.height, this.longd, this.largd);
	}



	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		gameThread.setRunning(false);
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	public void onResume(){

		surfaceholder = getHolder();
		getHolder().addCallback(this);

		//Create and start background Thread
		gameThread = new TilesPlayThread(surfaceholder,this);
		gameThread.setRunning(true);
		gameThread.start();
	}

	// Méthode de View.OnTouchListener
	public boolean onTouch(View view, MotionEvent event) {

		if (app.getGaming() && (! transRunning)) {

			/** Touched point coord */
			Coord c=new Coord(event.getX(),event.getY()); 

			switch(event.getAction()) {

			case MotionEvent.ACTION_DOWN :

				touchedTile=c.newinspectHand(app.getHumanHand()); //search for a tile matching coord c
				Log.d("testapp", "x: "+event.getX() + " y: " + event.getY());
				if (touchedTile != -1) {  //A tile has been touched
					d=app.getHumanHand().get(touchedTile);
					d_old=new Coord(d);
					distx=d_old.getX()-c.getX();
					disty=d_old.getY()-c.getY();    
				} break;

			case MotionEvent.ACTION_MOVE :

				if (d != null) { 
					d.setX(c.getX()+distx);
					d.setY(c.getY()+disty);

				}
				break;

			case MotionEvent.ACTION_UP :

				if (d !=null) {
					
					if (d.intersects(app.getPl().getCoordG())) {
						tileReleased=1;

					}
					else if (d.intersects(app.getPl().getCoordD())) {
						tileReleased=2;
					}

					if (tileReleased != -1) {
						try {
							try {

								app.getA().jeu(tileReleased,touchedTile);

								app.fixHumanHand();
								d=null;
								touchedTile=-1;
								tileReleased=-1;
							}
							catch (TileDoesntMatchException e) {
								d.setCoord(d_old);
								d=null;
								touchedTile=-1;
								tileReleased=-1;
							}
							catch(PartieGagneException e) {
								this.postInvalidate();
								Log.d("Testapp", "exception : partiegagneexception");
								AlertDialog alert = new AlertDialog.Builder(appContext).create();
								alert.setCanceledOnTouchOutside(false);

								if (e.getJoueur().isComputer()) {
									// Log.d("Testapp", "TEST2222");
									alert.setTitle(app.getString(R.string.lostTitle));
									alert.setMessage(app.getString(R.string.lostMessage));
								}
								else {
									alert.setTitle(app.getString(R.string.wonTitle));
									alert.setMessage(app.getString(R.string.wonMessage));
								}

								alert.setButton(DialogInterface.BUTTON_POSITIVE, app.getString(R.string.newGame) ,new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										newGame.performClick();

									}
								});

								alert.setButton(DialogInterface.BUTTON_NEGATIVE, app.getString(R.string.menu),new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										app.setGaming(false);
										exit.performClick();
									}
								});
								alert.show();
							}

							catch (MatchNullException e3) {
								this.postInvalidate();

								AlertDialog alert = new AlertDialog.Builder(appContext).create();
								alert.setCanceledOnTouchOutside(false);
								alert.setTitle( app.getString(R.string.endDrawTitle));
								alert.setMessage( app.getString(R.string.endDrawMessage));
								alert.setButton(DialogInterface.BUTTON_POSITIVE, app.getString(R.string.newGame),new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										app.getNewGameButton().performClick();
									}
								});

								alert.setButton(DialogInterface.BUTTON_NEGATIVE,  app.getString(R.string.menu),new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {

										app.setGaming(false);
										exit.performClick();
									}
								});
								alert.show();
							}

						}
						catch (Exception e) {
							Log.d("Testapp", "exception : e"+e.getMessage());
						}
					}
					else {
						d.setCoord(d_old);
						d=null;
						touchedTile=-1;
						tileReleased=-1;
					}

				}
				break;
			}
			this.postInvalidate();
			return true;
		}
		return false;
	}

	// Méthode de dessin de SurfaceView
	@Override
	public void onDraw(Canvas canvas) {
		if (app.getGaming() && (longd > 0) && (largd> 0)) {
			int i=0;
			int r;
			Domino di;
			Plateau pl = app.getPl();
			ArrayList<Domino> handPlayer=app.getHumanHand();
			Bitmap bitmap;


			if (touchedTile != -1) {
				canvas.drawRect(app.getPl().getCoordG(), paint2);
				canvas.drawRect(app.getPl().getCoordD(), paint2);
			}


			for (i=0;i<pl.getNbDominos();i++) {
				di=pl.getDomino(i);
				r=di.getR();

				if (r == 1) {

					bitmap=rotate(getBitmap(di.getId()),90);
				}
				else if (r == -1) {
					bitmap=rotate(getBitmap(di.getId()),180);
				}
				else if (r == 2) {
					bitmap=getBitmap(di.getIdDouble());
				}
				else {
					bitmap=getBitmap(di.getId());
				} 



				if (di.getFather()==1 && transActivated) {

					if (!transRunning) {

						disable_button();
						fin=di.getCoord();
						if (di.getR()==2) {
							fin.setY(fin.getY()-largd/2);
						}
						dep=new Coord(100,-100);
						xfin=fin.getX();
						yfin=fin.getY();

						bitm = new Coord(dep.getX(),dep.getY());
						yIncr=20;
						fun_param=Coord.translate(dep, fin);
						transRunning=true;
						super.postInvalidate();

					}
					else {
						if (Math.abs(bitm.getY()-yfin) > 25) {



							bitm.setY((bitm.getY()+yIncr));

							bitm.setX(Coord.fun(fun_param.getX(), fun_param.getY(), bitm.getY()));
							canvas.drawBitmap(bitmap,bitm.getX(),bitm.getY(),paint1);
							super.postInvalidate();
						}
						else {
							di.setFather(2);
							transRunning=false;

							enable_button();

							canvas.drawBitmap(bitmap,xfin,yfin,paint1);
							super.postInvalidate();

						}
					}
				}
				else {
					if (di.getR() == 2) {
						canvas.drawBitmap(bitmap,di.getX(),di.getY()-largd/2,paint1);
					}
					else {
						canvas.drawBitmap(bitmap,di.getX(),di.getY(),paint1);
					}


				}


			}

			//Drawing of human's hand
			for (int j=0;j<handPlayer.size();j++) {
				Domino dh =handPlayer.get(j);
				canvas.drawBitmap(getBitmap(dh.getId()),dh.getX(),dh.getY(),paint1);

			}



		}


	}


	public Bitmap getBitmap(int id) {
		if ((map.get(id) != null) && (map.get(id).getPixel(0, 0)!=0)) {    // We have to check a pixel of this bitmap to check its integrety
			return map.get(id);
		}

		Bitmap b=app.getBitmap(id);
		if (id<100) {
			b=scaleToFill(b,longd,largd);
		}
		else {
			b=scaleToFill(b,largd,longd);
		}
		if (color==1) {
			b=changeColor(b,255,255,255);
		}
		map.put(id, b);

		return map.get(id);
	}

	public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int format, int width, int height)
	  {
		Log.d("testapp", "surfaceChanged !");
	    setLayoutParams(new LayoutParams(width, height));
	    reset(this.appContext);
	    refreshSurface();
	  }


	public void onClick(View v) {
		// throw new UnsupportedOperationException("Not supported yet.");
	}


	public Bitmap scaleToFill(Bitmap b, int width, int height) {
		float factorH = height / (float) b.getHeight();
		float factorW = width / (float) b.getWidth();
		float factorToUse = (factorH > factorW) ? factorW : factorH;

		return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factorToUse), (int) (b.getHeight() * factorToUse), true);
	}


	public Bitmap rotate (Bitmap b, int x) {
		Matrix matrix = new Matrix();
		matrix.postRotate(x);
		Bitmap new_b = Bitmap.createBitmap(b,0,0,b.getWidth(),b.getHeight(), matrix, true);
		return new_b;
	}

	public Bitmap changeColor(Bitmap b2, int r,int g, int b) {

		Bitmap bis =b2.copy(b2.getConfig(), true);
		int [] allpixels = new int [ bis.getHeight()*bis.getWidth()];
		int p;

		bis.getPixels(allpixels, 0, bis.getWidth(), 0, 0, bis.getWidth(),bis.getHeight());


		for (int i =0; i<bis.getHeight()*bis.getWidth();i++){
			p=allpixels[i];

			if ((r==255)&&(g==255)&&(b==255)) {
				allpixels[i]=Color.rgb(255-Color.red(p), 255-Color.green(p), 255-Color.blue(p));
			}
			else  {

				if ((Color.red(p) < 200) && (Color.green(p) < 200) && (Color.blue(p) < 200)) {

					allpixels[i]=Color.rgb(255, 0, 0);
				}
				else {
					allpixels[i]=Color.rgb(255, 255, 255);
				}
			}
		}

		bis.setPixels(allpixels, 0, bis.getWidth(), 0, 0, bis.getWidth(), bis.getHeight());
		return bis;
	}

	public void setColor(int c) {
		this.color=c;
	}
	public TilesPlayThread getThread() {
		return gameThread;
	}

	public void setTransRunning(boolean b) {
		transRunning=b;
	}


	public void disable_button() {
		state_exit=exit.isEnabled();
		state_newgame=newGame.isEnabled();
		state_stock=stock.isEnabled();

		exit.setEnabled(false);
		newGame.setEnabled(false);
		stock.setEnabled(false);   
	}

	public void enable_button() {
		exit.setEnabled(state_exit);
		newGame.setEnabled(state_newgame);
		stock.setEnabled(state_stock);   
	}

	public static float convertDpToPixel(float dp, Context context){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		Log.d("testapp", "density : " + metrics.densityDpi);
		return px;
	}
	public static float convertPixelsToDp(float px, Context context){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		Log.d("testapp", "density : " + metrics.densityDpi);
		return dp;
	}

	public void getAdjustedDimension(Context context){
		if (Math.abs((double) (height-width)) < 150) {
			longd=(int) (width/6f);
		}
		else {
			if (height > width) {
				longd=(int) (width/4.45f);
			}
			else {
				longd=(int) (width/7.8f);
			}
		}
		largd = (int) (longd/2f);
	}
	
	public void refreshSurface()
	  {
	    this.map.clear();
	    if (this.app.getGaming())
	    {
	      this.app.surfaceChanged();  /* A supprimer peut etre */
	      Log.d("testapp", "nouveau plateau");
	      Plateau pl = this.app.getPl().fixPl();
	      if (pl != null)
	      {
	        this.app.setPl(pl);
	        this.app.getA().setPl(pl);
	      }
	    }
	  }
}


