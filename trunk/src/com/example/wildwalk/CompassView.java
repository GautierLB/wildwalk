package com.example.wildwalk;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.R;

public class CompassView extends View {
	// ~--- fields -------------------------------------------------------------

	private float northOrientation = 0;

	private Paint circlePaint;
	private Paint northPaint;
	private Paint southPaint;

	private Path trianglePath;

	private final int DELAY = 1;// Délais entre chaque image
	private final int DURATION = 1000;// Durée de l'animation

	private float startNorthOrientation;
	private float endNorthOrientation;

	private long startTime;// Heure de début de l’animation (ms)
	private float perCent;// Pourcentage d'évolution de l'animation
	private long curTime;// Temps courant
	private long totalTime;// Temps total depuis le début de l'animation

	private Runnable animationTask = new Runnable() {
		public void run() {
			curTime = SystemClock.uptimeMillis();
			totalTime = curTime - startTime;

			if (totalTime > DURATION) {
				northOrientation = endNorthOrientation%360;
				removeCallbacks(animationTask);
			} else {
				perCent = ((float) totalTime) / DURATION;

				// Animation plus réaliste de l'aiguille
				perCent = (float) Math.sin(perCent*1.5);
				perCent = Math.min(perCent, 1);
				northOrientation = (float) (startNorthOrientation+perCent*(endNorthOrientation-startNorthOrientation));
				postDelayed(this, DELAY);
			}
			invalidate();// repaint de la vue
		}
	};

	// Constructeurs

	// Constructeur par défaut de la vue
	public CompassView(Context context) {
		super(context);
		initView();
	}

	// Constructeur utilisé pour instancier la vue depuis sa déclaration dans un fichier XML
	public CompassView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	// idem au précédant
	public CompassView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		initView();
	}

	// récupérer orientation boussole
	public float getNorthOrientation(){
		return northOrientation;
	}

	// Changer l'orientation de la boussole
	public void setNorthOrientation(float rotation){

		// MAJ de l'orientation ssi elle a changé
		if (rotation != this.northOrientation){
			// Arrêter l'ancienne animation
			removeCallbacks(animationTask);
			this.startNorthOrientation = this.northOrientation;// Position courante
			this.endNorthOrientation = rotation;// Position voulue

			// Sens de rotation de l'aiguille
			if (((startNorthOrientation+180)%360)>endNorthOrientation){
				// Rotation vers la gauche
				if ((startNorthOrientation-endNorthOrientation)>=180){
					endNorthOrientation += 360;
				}
			} else {
				// Rotation vers la droite
				if ((endNorthOrientation-startNorthOrientation)>180){
					startNorthOrientation += 360;
				}
			}
			// Nouvelle animation
			startTime = SystemClock.uptimeMillis();
			postDelayed(animationTask, DELAY);
		}
	}

	// ~--- methods ------------------------------------------------------------

	// Initialisation de la vue
	private void initView() {
		Resources r = this.getResources();

		// Paint pour l'arrière plan de la boussole
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(r.getColor(R.color.holo_blue_light));

		// Paint pour les 2 aiguilles, Nord et Sud
		northPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		northPaint.setColor(r.getColor(R.color.holo_red_dark));
		southPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		southPaint.setColor(r.getColor(R.color.black));

		// Path pour dessiner les aiguilles
		trianglePath = new Path();
	}

	// Permet de définir la taille de la vue. 100x100 si non redéfini
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);

		// Notre vue sera un carré, on garde donc le minimum
		int d = Math.min(measuredWidth, measuredHeight);

		setMeasuredDimension(d, d);
	}

	// Déterminer la taille de la vue
	private int measure(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			result = 200;// taille par défaut
		} else {
			result = specSize;// taille vue parent
		}

		return result;
	}

	// Redessiner la vue
	@Override
	protected void onDraw(Canvas canvas) {
		int centerX = getMeasuredWidth() / 2;
		int centerY = getMeasuredHeight() / 2;

		// On détermine le diamètre du cercle (arrière plan de la boussole)
		int radius = Math.min(centerX, centerY);

		canvas.drawCircle(centerX, centerY, radius, circlePaint);

		
		canvas.save();// On sauvegarde la position initiale du canvas

		
		canvas.rotate(-northOrientation, centerX, centerY);// On tourne le canvas pour que le nord pointe vers le haut

		// on créé une forme triangulaire qui part du centre du cercle et pointe vers le haut
		trianglePath.reset(); // RAZ du path (une seule instance)
		trianglePath.moveTo(centerX, 10);
		trianglePath.lineTo(centerX - 10, centerY);
		trianglePath.lineTo(centerX + 10, centerY);

		// On désigne l'aiguille Nord
		canvas.drawPath(trianglePath, northPaint);

		// On tourne notre vue de 180° pour désigner l'auguille Sud
		canvas.rotate(180, centerX, centerY);
		canvas.drawPath(trianglePath, southPaint);

		// On restaure la position initiale
		canvas.restore();
	}
}
