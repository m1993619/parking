package com.graduation.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil
{
	
	public static final int PROGRESS_DIALOG = 0;

	public static Dialog showDialog(Context context, int id)
	{

		switch (id)
		{
		case 0:
			ProgressDialog dialog = new ProgressDialog(context);
			dialog.setCancelable(false);
			dialog.setMessage("更新数据中……");
			dialog.setIndeterminate(false);
			dialog.show();

			return dialog;
		default:
			return null;
		}
	}

}
