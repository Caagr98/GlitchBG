package c98.glitchbg;

import static android.opengl.GLES20.*;
import java.nio.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.util.Log;

public class GlitchRenderer implements android.opengl.GLSurfaceView.Renderer {
	@Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		IntBuffer arrayIdBuf = IntBuffer.allocate(1);
		glGenBuffers(1, arrayIdBuf);
		glBindBuffer(GL_ARRAY_BUFFER, arrayIdBuf.get());
		float[] verts = {-1, -1};
		FloatBuffer vertBuf = (FloatBuffer)ByteBuffer.allocateDirect(verts.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(verts).flip();
		glBufferData(GL_ARRAY_BUFFER, verts.length, vertBuf, GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 1, GL_FLOAT, false, 0, vertBuf);
		
		glUseProgram(getProgram());
		glError();
	}
	
	@Override public void onSurfaceChanged(GL10 gl, int width, int height) {}
	
	@Override public void onDrawFrame(GL10 gl) {
		glDrawArrays(GL_POINTS, 0, 1);
		glError();
	}
	
	public void glError() {
		int error;
		while((error = glGetError()) != 0)
			Log.w("EB_GLError", GLU.gluErrorString(error));
	}
	
	private static final String vert = "attribute vec4 pos;void main(){gl_Position = pos;}";
	private static final String frag = "void main(){gl_FragColor = vec4(0,0,0,0);}";
	
	private static int getProgram() {
		int programID = glCreateProgram();
		
		int vid = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vid, vert);
		glCompileShader(vid);
		glAttachShader(programID, vid);
		
		int fid = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fid, frag);
		glCompileShader(fid);
		glAttachShader(programID, fid);
		
		glLinkProgram(programID);
		
		glDeleteShader(vid);
		glDeleteShader(fid);
		
		return programID;
	}
}
