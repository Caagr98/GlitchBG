package c98.glitchbg;

import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;

public class GlitchWallpaperService extends GLWallpaperService {
	public class EBEngine extends GLWallpaperService.GLEngine {
		@Override public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			
			setEGLContextClientVersion(2);
			setRenderer(new GlitchRenderer());
			surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		}
	}
	
	@Override public Engine onCreateEngine() {
		return new EBEngine();
	}
}
