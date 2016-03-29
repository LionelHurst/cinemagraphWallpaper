package com.dagtech.cinemagraphwallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class CinemagraphWallpaper extends WallpaperService {
    java.io.InputStream is;
    private Movie mMovie;
    private long mMovieStart;
    private Bitmap backgroundImage;
    private float resizeWidth, resizeHeight;
    private Context context;
    private int resource, w, h;
    private BitmapFactory.Options options;

    @Override
    public Engine onCreateEngine() {
        return new CinemagraphEngine();
    }

    public class CinemagraphEngine extends Engine {

        private final Handler handler = new Handler();
        public boolean running = false;
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }

        };
        private boolean visible = true;


        public CinemagraphEngine() {
            context = getBaseContext();
            SharedPreferences prefs = context.getSharedPreferences(
                    "cinemagraph", 0);
            options = new BitmapFactory.Options();
            options.inPurgeable = true;
            resource = context.getResources().getIdentifier(
                    prefs.getString("pref_picture", "river"), "drawable",
                    "com.dagtech.cinemagraphwallpaper");
            backgroundImage = BitmapFactory.decodeResource(
                    context.getResources(), resource, options);

            is = context.getResources().openRawResource(resource);
            mMovie = Movie.decodeStream(is);
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            w = display.getWidth();
            resizeWidth = (float) ((float) w / (float) backgroundImage
                    .getWidth());
            h = display.getHeight();
            resizeHeight = (float) h / (float) backgroundImage.getHeight();
            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (isNewImage()) {
                updateImage(getBaseContext());
            }
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);

        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            running = true;
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
            running = false;
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    render(canvas);
                }
            } finally {
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                // draw every 17 miliseconds (60 fps)
                handler.postDelayed(drawRunner, 17);
            }

        }

        private void render(Canvas canvas) {
            long now = android.os.SystemClock.uptimeMillis();
            if (mMovieStart == 0) { // first time
                mMovieStart = now;
            }
            if (mMovie != null) {
                int dur = mMovie.duration();
                if (dur == 0) {
                    dur = 1000;
                }
                int relTime = (int) ((now - mMovieStart) % dur);
                mMovie.setTime(relTime);
                canvas.scale(resizeWidth, resizeHeight);
                mMovie.draw(canvas, 0, 0, null);
                // invalidate();
            }
        };

        public void updateImage(Context context) {
            SharedPreferences prefs = context.getSharedPreferences(
                    "cinemagraph", 0);
            resource = context.getResources().getIdentifier(
                    prefs.getString("pref_picture", "river"), "drawable",
                    "com.dagtech.cinemagraphwallpaper");
            backgroundImage = BitmapFactory.decodeResource(
                    context.getResources(), resource, options);
            is = context.getResources().openRawResource(resource);
            mMovie = Movie.decodeStream(is);
            resizeWidth = w / (float) backgroundImage.getWidth();
            resizeHeight = h / (float) backgroundImage.getHeight();
        }

    }

    public boolean isNewImage() {
        SharedPreferences prefs = context
                .getSharedPreferences("cinemagraph", 0);
        if (resource == context.getResources().getIdentifier(
                prefs.getString("pref_picture", "river"), "drawable",
                "com.dagtech.cinemagraphwallpaper")) {
            return false;
        } else {
            return true;
        }

    }

}
