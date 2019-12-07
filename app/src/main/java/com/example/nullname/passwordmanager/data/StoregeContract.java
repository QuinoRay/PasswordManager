package com.example.nullname.passwordmanager.data;

import android.provider.BaseColumns;

public final class StoregeContract {
    private StoregeContract() {
    };
    public static final class PasswordsEntry implements BaseColumns {
        public final static String TABLE_NAME = "passwords";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PASSWORD = "password";
        public final static String COLUMN_NAME_SERVISE = "nameServise";
    }
}
