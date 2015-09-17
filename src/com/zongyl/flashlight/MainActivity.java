package com.zongyl.flashlight;

import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	private Button btn;

	private Camera camera;

	boolean open = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn = (Button) findViewById(R.id.btn_flashlight);
		btn.setOnClickListener(new Click());
	}
	
	class Click implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_flashlight:
				if(open){
					btn.setText("open");
					turnLightOn(camera, false);
					camera.setPreviewCallbackWithBuffer(null);
					camera.release();
					camera = null;
					open = false;
				}else{
					if(camera == null){
						camera = Camera.open();
					}
					btn.setText("close");
					turnLightOn(camera, true);
					open = true;
				}
				break; 
			default:
				break;
			}
		}
	}
	
	public static void turnLightOn(Camera camera, boolean open){
		if(camera == null){
			return;
		}
		Parameters params = camera.getParameters();
		if(params == null){
			return;
		}
		List<String> flashModes = params.getSupportedFlashModes();
		if(flashModes == null){
			return;
		}
		String flashMode = params.getFlashMode();
		
		String status = "";
		if(open){
			status = Parameters.FLASH_MODE_TORCH;
		}else{
			status = Parameters.FLASH_MODE_AUTO;
		}
		if(!status.equals(flashMode)){
			if(flashModes.contains(status)){
				params.setFlashMode(status);
				camera.setParameters(params);
			}
		}
	}
}
