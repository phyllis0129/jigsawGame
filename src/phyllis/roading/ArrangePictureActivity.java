package phyllis.roading;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ArrangePictureActivity extends Activity {
	
	private MyView myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myView=new MyView(this);//通过THIS将active my view联系起来
		setContentView(myView);
	}

	@Override//安卓自带的，可以删去
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arrange_picture, menu);
		return true;
	}

}
