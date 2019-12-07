package com.example.nullname.passwordmanager;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nullname.passwordmanager.data.PasswordDBHelper;
import com.example.nullname.passwordmanager.data.StoregeContract;

public class GenPass extends Activity implements SeekBar.OnSeekBarChangeListener{

    public static TextView textViewLength;
    public static CheckBox cbRus, cbLat, cbNumber, cbUper, cbLower;
    public static SeekBar seekBar;
    public ListView listView;

    public static int tempSeekBar;

    private static final String TAG ="GenPass";

    private PasswordDBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_pass);

        cbRus = findViewById(R.id.cbRus);
        cbLat = findViewById(R.id.cbEng);
        cbNumber = findViewById(R.id.cbNumber);
        cbUper = findViewById(R.id.cbUp);
        cbLower = findViewById(R.id.cbLower);

        textViewLength = findViewById(R.id.textViewLength);
        textViewLength.setText("0");

        listView =findViewById(R.id.listView);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        mDbHelper = new PasswordDBHelper(this);


    }

    private static boolean checkBoxMetod(CheckBox checkBox){
        if(checkBox.isChecked())
            return true;
        else
            return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textViewLength.setText(String.valueOf(seekBar.getProgress()));
        tempSeekBar = Integer.valueOf(seekBar.getProgress());
    }
    //метод генерации пароля
    public static String generatePassword(){
        boolean rus, engl, uper, lower, number;

        String bigString;
        String password;

        rus = checkBoxMetod(cbRus);
        engl = checkBoxMetod(cbLat);
        uper = checkBoxMetod(cbUper);
        lower = checkBoxMetod(cbLower);
        number = checkBoxMetod(cbNumber);

        Randomizer ran = new Randomizer();

        bigString = ran.getBigString(rus, engl, uper, lower, number);

        password = ran.randomize(tempSeekBar,bigString);

        return password;
    }

    //метод обработки нажатия на кнопку
    public void onClick(View view) {

        //генерация 5 паролей
        String[] passwords = new String[5];
        for (int i = 0; i < 5; i++) {
            passwords[i] = generatePassword();
            if (passwords[0].length() == 0) {
                passwords[0] = "Выберете хотя бы один параметр";
                break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, passwords);

        //заполнение списка паролями
        listView.setAdapter(adapter);

        //действие, выполняемое при нажатии на элемент списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

                showPopupMenu(itemClicked);
            }
        });
    }

    private void showPopupMenu (View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenugenpass);

        TextView tempTextView = (TextView) v;
        final String item = tempTextView.getText().toString();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btnCopy:
                        //копирование в буфер обмена
                        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("", item);
                        clipboard.setPrimaryClip(clip);

                        //сообщение о копировании
                        Toast toast = Toast.makeText(getApplicationContext(), "Текст скопирован в буфер обмена", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        return true;
                    case R.id.btnAddToDataBase:
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();

                        contentValues.put(StoregeContract.PasswordsEntry.COLUMN_PASSWORD, item);
                        contentValues.put(StoregeContract.PasswordsEntry.COLUMN_NAME_SERVISE, "VK");
                        db.insert(StoregeContract.PasswordsEntry.TABLE_NAME, null, contentValues);

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
