package com.energy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.energy.application.MyApplication;

public class BaseActivity extends SherlockActivity  {
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("退出")
        	.setIcon(R.drawable.quit)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("退出")) {
			new AlertDialog.Builder(BaseActivity.this)   
		    .setTitle("提示")  
		    .setMessage("确定退出吗？")  
		    .setPositiveButton("是", new android.content.DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					MyApplication.getInstance().exit();
				}
		    })  
		    .setNegativeButton("否",null)  
		    .show();  
		}
        return true;
    }

}
