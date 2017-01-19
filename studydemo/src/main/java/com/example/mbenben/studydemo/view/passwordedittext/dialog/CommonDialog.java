package com.example.mbenben.studydemo.view.passwordedittext.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

import com.example.mbenben.studydemo.R;


public class CommonDialog extends Dialog {
	private AlertController mAlert;

	public CommonDialog(Context context, int theme) {
		super(context, theme);
		mAlert = new AlertController(getWindow(), this);
	}
	
	/**
	 * Look for a child view with the given id. If this view has the given id,
	 * return this view.
	 *
	 *            The id to search for.
	 * @return The view that has the given id in the hierarchy or null
	 */
	public <T extends View> T getView(int viewId) {
		return mAlert.getView(viewId);
	}
	
	/**
	 * Set the resource id of the {@link Drawable} to be used in the title.
	 * <p>
	 * 
	 * @return This Builder object to allow for chaining of calls to set methods
	 */
	public void setIcon(int viewId, int resouceId) {
		mAlert.setIcon(viewId, resouceId);
	}

	/**
	 * Set the {@link Drawable} to be used in the title.
	 * 
	 * @return This Builder object to allow for chaining of calls to set methods
	 */
	public void setIcon(int viewId, Drawable drawable) {
		mAlert.setIcon(viewId, drawable);
	}

	/**
	 * Creates a {@link AlertDialog} with the arguments supplied to this builder
	 * and {@link Dialog#show()}'s the dialog.
	 */
	public void setText(int viewId, CharSequence text) {
		mAlert.setText(viewId, text);
	}

	/**
	 * Creates a {@link AlertDialog} with the arguments supplied to this builder
	 * and {@link Dialog#show()}'s the dialog.
	 */
	public void setOnClickListener(int viewId, View.OnClickListener listener) {
		mAlert.setOnClickListener(viewId, listener);
	}


	public static class Builder {
		private int mTheme;
		private final AlertController.AlertParams P;

		public Builder(Context context, int theme) {
			this.mTheme = theme;
			P = new AlertController.AlertParams(context);
		}

		public Builder(Context context) {
			this(context, R.style.dialog);
		}

		public CommonDialog create() {
			final CommonDialog dialog = new CommonDialog(P.mContext, mTheme);

			P.apply(dialog.mAlert);

			dialog.setCancelable(P.mCancelable);

			if (P.mCancelable) {
				dialog.setCanceledOnTouchOutside(true);
			}

			dialog.setOnCancelListener(P.mOnCancelListener);
			dialog.setOnDismissListener(P.mOnDismissListener);
			if (P.mOnKeyListener != null) {
				dialog.setOnKeyListener(P.mOnKeyListener);
			}
			return dialog;
		}

		/**
		 * Returns a {@link Context} with the appropriate theme for dialogs
		 * created by this Builder. Applications should use this Context for
		 * obtaining LayoutInflaters for inflating views that will be used in
		 * the resulting dialogs, as it will cause views to be inflated with the
		 * correct theme.
		 * 
		 * @return A Context for built Dialogs.
		 */
		public Context getContext() {
			return P.mContext;
		}

		/**
		 * Set the resource id of the {@link Drawable} to be used in the title.
		 * <p>
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setIcon(int viewId, int resouceId) {
			P.setIcon(viewId, resouceId);
			return this;
		}

		/**
		 * Set the {@link Drawable} to be used in the title.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setIcon(int viewId, Drawable icon) {
			P.setIcon(viewId, icon);
			return this;
		}

		/**
		 * Sets whether the dialog is cancelable or not. Default is true.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setCancelable(boolean cancelable) {
			P.mCancelable = cancelable;
			return this;
		}

		/**
		 * Sets the callback that will be called if the dialog is canceled.
		 * 
		 * <p>
		 * Even in a cancelable dialog, the dialog may be dismissed for reasons
		 * other than being canceled or one of the supplied choices being
		 * selected. If you are interested in listening for all cases where the
		 * dialog is dismissed and not just when it is canceled, see
		 * {@link #setOnDismissListener(OnDismissListener)
		 * setOnDismissListener}.
		 * </p>
		 * 
		 * @see #setCancelable(boolean)
		 * @see #setOnDismissListener(OnDismissListener)
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setOnCancelListener(OnCancelListener onCancelListener) {
			P.mOnCancelListener = onCancelListener;
			return this;
		}

		/**
		 * Sets the callback that will be called when the dialog is dismissed
		 * for any reason.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setOnDismissListener(OnDismissListener onDismissListener) {
			P.mOnDismissListener = onDismissListener;
			return this;
		}

		/**
		 * Sets the callback that will be called if a key is dispatched to the
		 * dialog.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setOnKeyListener(OnKeyListener onKeyListener) {
			P.mOnKeyListener = onKeyListener;
			return this;
		}

		/**
		 * Set a custom view resource to be the contents of the Dialog. The
		 * resource will be inflated, adding all top-level views to the screen.
		 * 
		 * @param layoutResId
		 *            Resource ID to be inflated.
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setView(int layoutResId) {
			P.mView = null;
			P.mViewLayoutResId = layoutResId;
			return this;
		}

		/**
		 * Set a custom view to be the contents of the Dialog. If the supplied
		 * view is an instance of a {@link ListView} the light background will
		 * be used.
		 * 
		 * @param view
		 *            The view to use as the contents of the Dialog.
		 * 
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setView(View view) {
			P.mView = view;
			P.mViewLayoutResId = 0;
			return this;
		}

		/**
		 * Creates a {@link AlertDialog} with the arguments supplied to this
		 * builder and {@link Dialog#show()}'s the dialog.
		 */
		public Builder setText(int id, CharSequence text) {
			P.setText(id, text);
			return this;
		}

		/**
		 * Creates a {@link AlertDialog} with the arguments supplied to this
		 * builder and {@link Dialog#show()}'s the dialog.
		 */
		public Builder setOnClickListener(int id, View.OnClickListener listener) {
			P.setOnClickListener(id, listener);
			return this;
		}

		/**
		 * Dismiss this dialog, removing it from the screen. This method can be
		 * invoked safely from any thread. Note that you should not override
		 * this method to do cleanup when the dialog is dismissed, instead
		 * implement that in {@link #onStop}.
		 */
		public void dismiss() {
			P.dismiss();
		}

		/**
		 * Look for a child view with the given id. If this view has the given
		 * id, return this view.
		 *
		 *            The id to search for.
		 * @return The view that has the given id in the hierarchy or null
		 */
		public <T extends View> T getView(int viewId) {
			return P.getView(viewId);
		}

		/**
		 * Animation from the bottom of the pop-up to the middle all the time
		 */
		public Builder fromBottomToMiddle() {
			P.fromBottomToMiddle();
			return this;
		}

		/**
		 * Copy IOS pop-up effect
		 */
		public Builder fromBottom() {
			P.fromBottom();
			return this;
		}

		/**
		 * Special value for the height or width requested by a View.
		 * MATCH_PARENT means that the view wants to be as big as its parent,
		 * minus the parent's padding, if any. Introduced in API Level 8.
		 */
		public Builder fullWidth() {
			P.setWidth(LayoutParams.MATCH_PARENT);
			return this;
		}

		/**
		 * Loads a displacement of the default zoom animation
		 */
		public Builder loadAniamtion() {
			P.loadAniamtion();
			return this;
		}

		/**
		 * Information about how wide the view wants to be. Can be one of the
		 * constants FILL_PARENT (replaced by MATCH_PARENT , in API Level 8) or
		 * WRAP_CONTENT. or an exact size.
		 */
		public Builder setWidth(int width) {
			P.setWidth(width);
			return this;
		}

		/**
		 * Information about how tall the view wants to be. Can be one of the
		 * constants FILL_PARENT (replaced by MATCH_PARENT , in API Level 8) or
		 * WRAP_CONTENT. or an exact size.
		 */
		public Builder setHeight(int height) {
			P.setHeight(height);
			return this;
		}
	}

}
