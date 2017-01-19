package com.example.mbenben.studydemo.view.passwordedittext.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.example.mbenben.studydemo.R;

import java.util.HashMap;
import java.util.Iterator;


/**
 * 
 * ============================================================
 * 
 * project name : TiantianFangFu
 * 
 * copyright ZENG HUI (c) 2015
 * 
 * author : HUI
 * 
 * QQ ：240336124
 * 
 * version : 1.0
 * 
 * date created : On July, 2015
 * 
 * description :
 * 
 * revision history :
 * 
 * ============================================================
 * 
 */
public class AlertController {

	private Window mWindow;
	private CommonDialog mDialog;
	private DialogViewHolder mDialogVH;

	public AlertController(Window window, CommonDialog dialog) {
		this.mWindow = window;
		this.mDialog = dialog;
	}

	public Window getWindow() {
		return mWindow;
	}

	private CommonDialog getDialog() {
		return mDialog;
	}

	/**
	 * Look for a child view with the given id. If this view has the given id,
	 * return this view.
	 *
	 *            The id to search for.
	 * @return The view that has the given id in the hierarchy or null
	 */
	public <T extends View> T getView(int viewId) {
		return mDialogVH.getView(viewId);
	}

	public DialogViewHolder getDialogVH() {
		return mDialogVH;
	}

	public void setDialogVH(DialogViewHolder mDialogVH) {
		this.mDialogVH = mDialogVH;
	}

	/**
	 * Set the resource id of the {@link Drawable} to be used in the title.
	 * <p>
	 * 
	 * @return This Builder object to allow for chaining of calls to set methods
	 */
	public void setIcon(int viewId, int resouceId) {
		mDialogVH.setIcon(viewId, resouceId);
	}

	/**
	 * Set the {@link Drawable} to be used in the title.
	 * 
	 * @return This Builder object to allow for chaining of calls to set methods
	 */
	public void setIcon(int viewId, Drawable drawable) {
		mDialogVH.setIcon(viewId, drawable);
	}

	/**
	 * Creates a {@link AlertDialog} with the arguments supplied to this builder
	 * and {@link Dialog#show()}'s the dialog.
	 */
	public void setText(int viewId, CharSequence text) {
		mDialogVH.setText(viewId, text);
	}

	/**
	 * Creates a {@link AlertDialog} with the arguments supplied to this builder
	 * and {@link Dialog#show()}'s the dialog.
	 */
	public void setOnClickListener(int viewId, OnClickListener listener) {
		mDialogVH.setOnClick(viewId, listener);
	}

	public static class AlertParams {
		public Context mContext;
		public boolean mCancelable = true;
		public OnCancelListener mOnCancelListener;
		public OnDismissListener mOnDismissListener;
		public OnKeyListener mOnKeyListener;
		public View mView;
		public int mViewLayoutResId;
		private DialogViewHolder mDialogVh;
		private HashMap<Integer, DialogItem> mItems;
		private CommonDialog mDialog;
		// 显示动画
		private int mWindowAnimations;
		// 宽度
		private int mWidth = LayoutParams.WRAP_CONTENT;
		// 高度
		private int mHeight = LayoutParams.WRAP_CONTENT;
		private int mWindowFlags;
		private int mWindowGravity;

		public AlertParams(Context context) {
			this.mContext = context;
			mItems = new HashMap<>();
		}

		public void apply(AlertController mAlert) {
			final Window window = mAlert.getWindow();
			mDialog = mAlert.getDialog();

			if (mViewLayoutResId != 0) {
				mDialogVh = DialogViewHolder.get(mContext, mViewLayoutResId);
				window.setContentView(mDialogVh.getConvertView());
			}

			if (mView != null) {
				window.setContentView(mView);
				mDialogVh = new DialogViewHolder();
				mDialogVh.setConvertView(mView);
			}

			if (mDialogVh == null) {
				mDialogVh = new DialogViewHolder();
				mDialogVh.setConvertView(window.getDecorView());
			}

			mAlert.setDialogVH(mDialogVh);

			window.setWindowAnimations(mWindowAnimations);
			window.addFlags(mWindowFlags);
			window.setGravity(mWindowGravity);

			WindowManager.LayoutParams wl = window.getAttributes();
			wl.height = mHeight;
			wl.width = mWidth;
			mDialog.onWindowAttributesChanged(wl);

			if (mItems != null && mItems.size() > 0) {
				Iterator<Integer> iter = mItems.keySet().iterator();

				while (iter.hasNext()) {
					Integer viewId = iter.next();
					DialogItem dialogItem = mItems.get(viewId);
					// 设置文本
					mDialogVh.setText(viewId, dialogItem.text);
					// 设置点击
					mDialogVh.setOnClick(viewId, dialogItem.listener);
					// 设置图片
					mDialogVh.setIcon(viewId, dialogItem.drawable);
					mDialogVh.setIcon(viewId, dialogItem.resourceId);
				}
				mItems.clear();
				mItems = null;
			}
		}

		public void setText(int id, CharSequence text) {
			if (mDialogVh != null) {
				mDialogVh.setText(id, text);
				return;
			}

			DialogItem item = null;
			if (mItems.containsKey(id)) {
				item = mItems.get(id);
			} else {
				item = new DialogItem();
			}
			item.text = text;
			mItems.put(id, item);
		}

		public void setOnClickListener(int id, OnClickListener listener) {
			if (mDialogVh != null) {
				mDialogVh.setOnClick(id, listener);
				return;
			}

			DialogItem item = null;
			if (mItems.containsKey(id)) {
				item = mItems.get(id);
			} else {
				item = new DialogItem();
			}
			item.listener = listener;
			mItems.put(id, item);
		}

		/**
		 * Set the resource id of the {@link Drawable} to be used in the title.
		 * <p>
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public void setIcon(int id, int resouceId) {
			if (mDialogVh != null) {
				mDialogVh.setIcon(id, resouceId);
				return;
			}

			DialogItem item = null;
			if (mItems.containsKey(id)) {
				item = mItems.get(id);
			} else {
				item = new DialogItem();
			}
			item.resourceId = resouceId;
			mItems.put(id, item);
		}

		/**
		 * Set the {@link Drawable} to be used in the title.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public void setIcon(int id, Drawable drawable) {
			if (mDialogVh != null) {
				mDialogVh.setIcon(id, drawable);
				return;
			}

			DialogItem item = null;
			if (mItems.containsKey(id)) {
				item = mItems.get(id);
			} else {
				item = new DialogItem();
			}
			item.drawable = drawable;
			mItems.put(id, item);
		}

		/**
		 * Dismiss this dialog, removing it from the screen. This method can be
		 * invoked safely from any thread. Note that you should not override
		 * this method to do cleanup when the dialog is dismissed, instead
		 */
		public void dismiss() {
			if (mDialog != null) {
				mDialog.dismiss();
			}
		}

		/**
		 * Look for a child view with the given id. If this view has the given
		 * id, return this view.
		 *
		 *            The id to search for.
		 * @return The view that has the given id in the hierarchy or null
		 */
		public <T extends View> T getView(int viewId) {
			if (mDialogVh != null) {
				return mDialogVh.getView(viewId);
			}
			return null;
		}

		/**
		 * Animation from the bottom of the pop-up to the middle all the time
		 */
		public void fromBottomToMiddle() {
			mWindowAnimations = R.style.main_menu_animstyle;
			mWindowFlags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		}

		/**
		 * Copy IOS pop-up effect
		 */
		public void fromBottom() {
			fromBottomToMiddle();
			mWindowGravity = Gravity.CENTER | Gravity.BOTTOM;
		}

		/**
		 * Loads a displacement of the default zoom animation
		 */
		public void loadAniamtion() {
			mWindowAnimations = R.style.dialog_scale_animstyle;
		}

		/**
		 * Information about how wide the view wants to be. Can be one of the
		 * constants FILL_PARENT (replaced by MATCH_PARENT , in API Level 8) or
		 * WRAP_CONTENT. or an exact size.
		 */
		public void setWidth(int width) {
			this.mWidth = width;
		}

		/**
		 * Information about how tall the view wants to be. Can be one of the
		 * constants FILL_PARENT (replaced by MATCH_PARENT , in API Level 8) or
		 * WRAP_CONTENT. or an exact size.
		 */
		public void setHeight(int width) {
			this.mWidth = width;
		}
	}

}
