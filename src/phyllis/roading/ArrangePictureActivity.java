package phyllis.roading;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ArrangePictureActivity extends Activity {
	
	private MyView myView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myView=new MyView(this);//ͨ��THIS��active my view��ϵ����
		setContentView(myView);
	}

	@Override//��׿�Դ��ģ�����ɾȥ
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arrange_picture, menu);
		return true;
	}

}
