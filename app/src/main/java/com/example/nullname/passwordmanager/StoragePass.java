package com.example.nullname.passwordmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nullname.passwordmanager.data.PasswordDBHelper;
import com.example.nullname.passwordmanager.data.StoregeContract;

import java.util.ArrayList;
import java.util.HashMap;

public class StoragePass extends Activity {

    private static final String TAG = "MyLog";
    private PasswordDBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_pass);

        mDbHelper = new PasswordDBHelper(this);

        ArrayList <String> password = new ArrayList<String>();
        ArrayList <String> servise = new ArrayList<String>();

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;


        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String query = "SELECT " + StoregeContract.PasswordsEntry.COLUMN_PASSWORD + ", "
                + StoregeContract.PasswordsEntry.COLUMN_NAME_SERVISE
                + " FROM "
                + StoregeContract.PasswordsEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()) {
            map = new HashMap<String, String>();
            map.put("Pass", cursor.getString(cursor.getColumnIndex(StoregeContract.PasswordsEntry.COLUMN_PASSWORD)));
            map.put("Service", cursor.getString(cursor.getColumnIndex(StoregeContract.PasswordsEntry.COLUMN_NAME_SERVISE)));
            arrayList.add(map);
            Log.d(TAG, "кек");
        }

        cursor.close();
        mDbHelper.close();

        ListView listViewPass = findViewById(R.id.listView2);
        SimpleAdapter adapter = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                                                    new String[]{"Pass", "Service"},
                                                    new int[]{android.R.id.text1, android.R.id.text2});
        listViewPass.setAdapter(adapter);

        listViewPass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                TextView textViewPassord = view.findViewById(android.R.id.text1);
                TextView textViewService = view.findViewById(android.R.id.text2);

                showPopupMenu(textViewPassord);
            }
        });
    }

    private void showPopupMenu(View v){
        mDbHelper = new PasswordDBHelper(this);

        TextView tv = (TextView) v;
        final String item = tv.getText().toString();
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.btnCopyOne:
                        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", item);
                        clipboard.setPrimaryClip(clip);

                        //сообщение о копировании
                        Toast toast = Toast.makeText(getApplicationContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return true;
                    case R.id.btnChange:
                        AlertDialog.Builder builder = new AlertDialog.Builder(StoragePass.this);
                        LayoutInflater inflater = getLayoutInflater();
                        builder.setView(inflater.inflate(R.layout.dialog_layout, null))
                                .setPositiveButton(R.string.btnSave, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setNegativeButton(R.string.btnCancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                    case R.id.btnDelete:
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();

                        db.execSQL("DELETE FROM " + StoregeContract.PasswordsEntry.TABLE_NAME + " WHERE " + StoregeContract.PasswordsEntry.COLUMN_PASSWORD + "='" + item + "'");
                        mDbHelper.close();

                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
            }
        });
        popupMenu.show();
    }

}
