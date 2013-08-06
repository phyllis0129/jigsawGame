package phyllis.roading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	private Bitmap bitmap;//获取图片
	private AlertDialog.Builder succeed;
	private Random random;
	private int eventPosition;//点击的位置
	private int blankPosition;//空白的位置
	private int gameSize;
	private List<Integer> positionList;

	public MyView(Context context) {
		super(context);
		random = new Random();
		positionList=new ArrayList<Integer>();
		succeed = new AlertDialog.Builder(this.getContext());
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.large);
		setGameSize(3);
		setPositionList();
		setBlankPosition(getGameTotalNum() - 1);
		this.setBackgroundColor(Color.BLACK);
	}

	private int getGameTotalNum() {
		return (int) Math.pow(getGameSize(), 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = null;
		for (int i = 0; i < getGameTotalNum() - 1; i++) {
			canvas.drawBitmap(bitmap, getBitmapSrc(i),
					getBitmapDst(getRandomPosition(i)), paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 点击图片，实现图片的移动
		setBitmapEventPosition(event.getX(), event.getY());
		if (getPositionList().contains(getEventPosition())) {
			moveBitmap();
		}
		// 完成拼图时的提示
		finishiTip();

		return super.onTouchEvent(event);
	}

	private void finishiTip() {
		int flog = 1;
		for (int n = 0; n < getGameTotalNum() - 1; n++) {
			if (getPositionList().get(n) != n) {
				flog = 0;
				break;
			}
		}
		if (flog == 1) {
			succeed.setMessage("You succeed!");
			succeed.setTitle("Congratulate");
			succeed.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			succeed.setPositiveButton("Play again",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							setPositionList();
							MyView.this.invalidate();
						}
					});
			succeed.show();
		}
	}

	private void moveBitmap() {
		int index = getPositionList().indexOf(getEventPosition());
		//mb是eventPosition和blankPosition的差
		int mb = getBlankPosition() - getEventPosition();
		if (Math.abs(mb) == 1 || Math.abs(mb) == getGameSize()) {
			boolean flag = true;
			for (int i = 1; i < getGameSize(); i++) {
				if ((getEventPosition() == i * getGameSize() - 1 && getBlankPosition() == i
						* getGameSize())
						|| (getEventPosition() == i * getGameSize() && getBlankPosition() == i
								* getGameSize() - 1)) {
					flag = false;
					break;
				}
			}
			if (flag) {
				getPositionList().remove(index);
				getPositionList().add(index, getBlankPosition());
				setBlankPosition(getEventPosition());
				this.invalidate();
			}
		}
	}

	/**
	 * 获取被触摸图片的编号
	 * 
	 * @param x
	 *            触摸图片时获得触摸点x坐标
	 * @param y
	 *            触摸图片时获得触摸点y坐标
	 * @return
	 * @return 被触摸图片的编号
	 */
	private void setBitmapEventPosition(float x, float y) {

		int row = (int) y / (getSingleDstHeight() + 2);
		int col = (int) x / (getSingleDstWidth() + 2);
		eventPosition = row * getGameSize() + col;
	}

	/**
	 * 获取随即摆放的位置
	 * 
	 * @param num
	 *            int类型
	 * @return 图片被随机放在的位置
	 */
	public int getRandomPosition(int num) {
		return getPositionList().get(num);
	}

	/**
	 * 获得从原位图截取的区域
	 * 
	 * @param num
	 *            int类型
	 * @return Rect类型
	 */
	public Rect getBitmapSrc(int num) {
		int row = num / getGameSize();
		int col = num % getGameSize();
		return getSrcRect(row, col);
	}

	public Rect getSrcRect(int row, int col) {
		Rect rect = new Rect();
		rect.left = col * getSingleBitmapWidth();
		rect.top = row * getSingBitmapHeight();
		rect.bottom = (row + 1) * getSingBitmapHeight();
		rect.right = (col + 1) * getSingleBitmapWidth();
		return rect;
	}

	/**
	 * 获取随即摆放位置
	 * 
	 * @param num
	 *            int
	 * @return Rect
	 */
	public Rect getBitmapDst(int num) {
		int row = num / getGameSize();
		int col = num % getGameSize();
		return getDstRect(row, col);
	}

	public Rect getDstRect(int row, int col) {
		Rect rect = new Rect();
		rect.left = col * (getSingleDstWidth() + 2);
		rect.top = row * (getSingleDstHeight() + 2);
		rect.right = rect.left + getSingleDstWidth();
		rect.bottom = rect.top + getSingleDstHeight();
		return rect;
	}

	public List<Integer> getPositionList() {
		return positionList;
	}

	public void setPositionList() {
		while (getPositionList().size() < getGameTotalNum() - 1) {
			int dst = random.nextInt(getGameTotalNum() - 1);
			if (!getPositionList().contains(dst)) {
				getPositionList().add(dst);
			}
		}
	}

	public int getSingleBitmapWidth() {
		return bitmap.getWidth() / getGameSize();
	}

	public int getSingBitmapHeight() {
		return bitmap.getHeight() / getGameSize();
	}

	public int getSingleDstWidth() {
		return this.getWidth() / getGameSize();
	}

	public int getSingleDstHeight() {
		int temp = getSingleBitmapWidth() / getSingBitmapHeight();
		return getSingleDstWidth() / temp;
	}

	public int getEventPosition() {
		return eventPosition;
	}

	public void setEventPosition(int eventPosition) {
		this.eventPosition = eventPosition;
	}

	public int getBlankPosition() {
		return blankPosition;
	}

	public void setBlankPosition(int blankPosition) {
		this.blankPosition = blankPosition;
	}

	public int getGameSize() {
		return gameSize;
	}

	public void setGameSize(int imageTotalnum) {
		this.gameSize = imageTotalnum;
	}

}
