package com.sv.gauge.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sv.gauge.R;
import com.sv.gauge.model.DomainObject;
import com.sv.gauge.model.DomainStepObject;
import com.sv.gauge.model.TransactionDetailObject;
import com.sv.gauge.model.TransactionObject;
import com.sv.gauge.model.UserObject;
import com.sv.gauge.server_model.Transaction;
import com.sv.gauge.server_model.TransactionDetail;
import com.sv.gauge.server_model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohan on 3/3/2016.
 */
public class GaugeDatabaseHelper extends SQLiteOpenHelper {
    //database
    public static final String DATABASE_NAME = "gauge.db";
    public static final int DATABASE_VERSION = 12;
    //
    private Context context;

    public GaugeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
//        onUpgrade(getReadableDatabase(), 1, 1);
    }


    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB CREATE", "Creating new db......................");

        InputStream inputStream = context.getResources().openRawResource(R.raw.database);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        StringBuilder builder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String rawSql = builder.toString();

            String[] sqls = rawSql.split(";");
            for (String sql : sqls) {
                db.execSQL(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //user
        db.execSQL("DROP TABLE IF EXISTS `user`");

        //domain
        db.execSQL("DROP TABLE IF EXISTS `domain`");

        //domain step
        db.execSQL("DROP TABLE IF EXISTS `domain_step`");

        //transaction
        db.execSQL("DROP TABLE IF EXISTS `transaction`");

        //transaction detail
        db.execSQL("DROP TABLE IF EXISTS `transaction_detail`");

        //recreate database
        onCreate(db);
    }

    //login
    public synchronized UserObject getUserObjectLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM `user` WHERE `username` = ? AND `password` = ?", new String[]{username, password});
        cursor.moveToFirst();

        if (cursor.getCount() == 1) {
            UserObject userObject = new UserObject();
            userObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            userObject.setName(cursor.getString(cursor.getColumnIndex("name")));
            userObject.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userObject.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            return userObject;
        }

        cursor.close();

        return null;
    }

    public synchronized void syncUsers(List<User> users){
        SQLiteDatabase db = getWritableDatabase();
        String deleteSql = "DELETE FROM `user`";
        db.execSQL(deleteSql);

        for (User user : users) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("id",user.getId());
            contentValues.put("name",user.getName());
            contentValues.put("username",user.getUsername());
            contentValues.put("password",user.getPassword());

            db.insert("`user`",null,contentValues);
        }
    }

    //domain
    public List<DomainObject> getDomains() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM `domain`", null);
        cursor.moveToFirst();

        List<DomainObject> domainObjects = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            DomainObject domainObject = new DomainObject();
            domainObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            domainObject.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            domainObject.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            domainObject.setFirstStep(cursor.getInt(cursor.getColumnIndex("first_step")));
            domainObject.setReportDescription(cursor.getString(cursor.getColumnIndex("report_description")));

            domainObjects.add(domainObject);
            cursor.moveToNext();
        }

        cursor.close();

        return domainObjects;
    }

    //domain step activity
    public int insertTransaction(TransactionObject transactionObject) {
        Log.d("DOMAIN_NAME", transactionObject.getDomainName());

        SQLiteDatabase db = getWritableDatabase();

        ContentValues transactionValues = new ContentValues();
        transactionValues.put("`domain_id`", transactionObject.getDomainId());
        transactionValues.put("`domain_name`", transactionObject.getDomainName());
        transactionValues.put("`report_description`", transactionObject.getReportDescription());
        transactionValues.put("`date`", transactionObject.getDate());
        transactionValues.put("`start_time`", transactionObject.getStartTime());
        transactionValues.put("`end_time`", transactionObject.getEndTime());
        transactionValues.put("`user_name`", transactionObject.getUserName());
        transactionValues.put("`user_id`", transactionObject.getUserId());
        transactionValues.put("`status`", transactionObject.getStatus());

        return (int) db.insert("`transaction`", null, transactionValues);
    }

    public int updateTransactionStatus(int transactionId, String endTime, String status) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues transactionValues = new ContentValues();
        transactionValues.put("`end_time`", endTime);
        transactionValues.put("`status`", status);

        return (int) db.update("`transaction`", transactionValues, "`id` = ?", new String[]{String.valueOf(transactionId)});
    }

    public int updateTransactionCurrentStep(int transactionId, int currentStep) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues transactionValues = new ContentValues();
        transactionValues.put("`current_step_id`", currentStep);

        return (int) db.update("`transaction`", transactionValues, "`id` = ?", new String[]{String.valueOf(transactionId)});
    }

    public int insertTransactionDetail(TransactionDetailObject transactionDetailObject) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues transactionDetailValues = new ContentValues();
        transactionDetailValues.put("`transaction`", transactionDetailObject.getTransaction());
        transactionDetailValues.put("`main_group`", transactionDetailObject.getMainGroup());
        transactionDetailValues.put("`sub_group`", transactionDetailObject.getSubGroup());
        transactionDetailValues.put("`domain_step`", transactionDetailObject.getDomainStep());
        transactionDetailValues.put("`number`", transactionDetailObject.getNumber());
        transactionDetailValues.put("`description`", transactionDetailObject.getDescription());
        transactionDetailValues.put("`value`", transactionDetailObject.getValue());
        transactionDetailValues.put("`unit`", transactionDetailObject.getUnit());
        transactionDetailValues.put("`default_value`", transactionDetailObject.getDefaultValue());
        transactionDetailValues.put("`comment`", transactionDetailObject.getComment());
        transactionDetailValues.put("`warning`", transactionDetailObject.isWarning() ? 1 : 0);
        transactionDetailValues.put("`time`", transactionDetailObject.getTime());

        return (int) db.insert("`transaction_detail`", null, transactionDetailValues);
    }

    public DomainStepObject getDomainStep(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `domain_step` WHERE `id` = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        if (cursor.getCount() == 1) {
            DomainStepObject object = new DomainStepObject();
            object.setId(cursor.getInt(cursor.getColumnIndex("id")));
            object.setDomainId(cursor.getInt(cursor.getColumnIndex("domain_id")));
            object.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            object.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            object.setMainGroup(cursor.getString(cursor.getColumnIndex("main_group")));
            object.setSubGroup(cursor.getString(cursor.getColumnIndex("sub_group")));
            //
            object.setVisualType(cursor.getString(cursor.getColumnIndex("visual_type")));
            object.setVisualLink(cursor.getString(cursor.getColumnIndex("visual_link")));
            object.setVisualDescription(cursor.getString(cursor.getColumnIndex("visual_description")));
            //
            object.setNextStep(cursor.getInt(cursor.getColumnIndex("next_step")));
            object.setShowMap(cursor.getInt(cursor.getColumnIndex("show_map")) != 0);
            object.setLastStep(cursor.getInt(cursor.getColumnIndex("last_step")) != 0);
            object.setMapFromPreviousLink(cursor.getString(cursor.getColumnIndex("map_from_previous_link")));
            //
            object.setEnableSkip(cursor.getInt(cursor.getColumnIndex("enable_skip")) != 0);
            object.setNextStep(cursor.getInt(cursor.getColumnIndex("next_step")));
            object.setSkipToStep(cursor.getInt(cursor.getColumnIndex("skip_to_step")));
            object.setMapFromBeginningLink(cursor.getString(cursor.getColumnIndex("map_from_beginning_link")));
            //
            String valueType = cursor.getString(cursor.getColumnIndex("value_type"));
            if (valueType==null || valueType.isEmpty()){
                valueType = "NONE";
            }
            object.setValueType(valueType);
            object.setValueHint(cursor.getString(cursor.getColumnIndex("value_hint")));
            object.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
            object.setDefaultValue(cursor.getString(cursor.getColumnIndex("default_value")));
            object.setDefaultFromValue(cursor.getString(cursor.getColumnIndex("default_from_value")));
            object.setDefaultToValue(cursor.getString(cursor.getColumnIndex("default_to_value")));
            //
            object.setReportDescription(cursor.getString(cursor.getColumnIndex("report_description")));

            return object;
        }

        cursor.close();

        return null;
    }

    //report list activity
    public List<TransactionObject> getTransactionObjects(int domaion) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `transaction` WHERE `domain_id` = ? ORDER BY id DESC", new String[]{String.valueOf(domaion)});
        cursor.moveToFirst();

        List<TransactionObject> transactionObjects = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            TransactionObject transactionObject = new TransactionObject();
            transactionObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            transactionObject.setDomainId(cursor.getInt(cursor.getColumnIndex("domain_id")));
            transactionObject.setDomainName(cursor.getString(cursor.getColumnIndex("domain_name")));
            transactionObject.setReportDescription(cursor.getString(cursor.getColumnIndex("report_description")));
            transactionObject.setDate(cursor.getString(cursor.getColumnIndex("date")));
            transactionObject.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            transactionObject.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            transactionObject.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            transactionObject.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            transactionObject.setStatus(cursor.getString(cursor.getColumnIndex("status")));

            transactionObjects.add(transactionObject);

            cursor.moveToNext();
        }

        cursor.close();

        return transactionObjects;
    }


    public TransactionObject getTransactionObject(int transactionId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `transaction` WHERE `id` = ?", new String[]{String.valueOf(transactionId)});
        cursor.moveToFirst();

        if (cursor.getCount() == 1) {
            TransactionObject transactionObject = new TransactionObject();
            transactionObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            transactionObject.setDomainId(cursor.getInt(cursor.getColumnIndex("domain_id")));
            transactionObject.setDomainName(cursor.getString(cursor.getColumnIndex("domain_name")));
            transactionObject.setReportDescription(cursor.getString(cursor.getColumnIndex("report_description")));
            transactionObject.setCurrentStepId(cursor.getInt(cursor.getColumnIndex("current_step_id")));
            transactionObject.setDate(cursor.getString(cursor.getColumnIndex("date")));
            transactionObject.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            transactionObject.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            transactionObject.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            transactionObject.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            transactionObject.setStatus(cursor.getString(cursor.getColumnIndex("status")));


            cursor.moveToNext();
            return transactionObject;
        }

        cursor.close();

        return null;
    }

    public TransactionObject getIncompleteTransactionObject(int domainId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `transaction` WHERE `status` = ? AND domain_id = ? AND `current_step_id` <> 0", new String[]{TransactionObject.TRANSACTION_STATUS_PENDING, String.valueOf(domainId)});
        cursor.moveToFirst();

        if (cursor.getCount() == 1) {
            TransactionObject transactionObject = new TransactionObject();
            transactionObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            transactionObject.setDomainId(cursor.getInt(cursor.getColumnIndex("domain_id")));
            transactionObject.setDomainName(cursor.getString(cursor.getColumnIndex("domain_name")));
            transactionObject.setReportDescription(cursor.getString(cursor.getColumnIndex("report_description")));
            transactionObject.setCurrentStepId(cursor.getInt(cursor.getColumnIndex("current_step_id")));
            transactionObject.setDate(cursor.getString(cursor.getColumnIndex("date")));
            transactionObject.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            transactionObject.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            transactionObject.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            transactionObject.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            transactionObject.setStatus(cursor.getString(cursor.getColumnIndex("status")));


            cursor.moveToNext();
            return transactionObject;
        }

        cursor.close();

        return null;
    }

    public List<TransactionDetailObject> getTransactionDetails(int transactionId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `transaction_detail` WHERE `transaction` = ?", new String[]{String.valueOf(transactionId)});
        cursor.moveToFirst();

        List<TransactionDetailObject> transactionDetailObjects = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            TransactionDetailObject transactionDetailObject = new TransactionDetailObject();
            transactionDetailObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            transactionDetailObject.setTransaction(cursor.getInt(cursor.getColumnIndex("transaction")));
            transactionDetailObject.setMainGroup(cursor.getString(cursor.getColumnIndex("main_group")));
            transactionDetailObject.setSubGroup(cursor.getString(cursor.getColumnIndex("sub_group")));
            transactionDetailObject.setDomainStep(cursor.getInt(cursor.getColumnIndex("domain_step")));
            transactionDetailObject.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            transactionDetailObject.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            transactionDetailObject.setValue(cursor.getString(cursor.getColumnIndex("value")));
            transactionDetailObject.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
            transactionDetailObject.setDefaultValue(cursor.getString(cursor.getColumnIndex("default_value")));
            transactionDetailObject.setComment(cursor.getString(cursor.getColumnIndex("comment")));
            transactionDetailObject.setWarning(cursor.getInt(cursor.getColumnIndex("warning")) != 0);
            transactionDetailObject.setTime(cursor.getString(cursor.getColumnIndex("time")));

            transactionDetailObjects.add(transactionDetailObject);

            cursor.moveToNext();
        }

        cursor.close();

        return transactionDetailObjects;
    }

    //sync
    public List<Transaction> getTransactionsSync() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `transaction` WHERE `status` = ?", new String[]{String.valueOf(TransactionObject.TRANSACTION_STATUS_COMPLETE)});
        cursor.moveToFirst();

        List<Transaction> transactionObjects = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            Transaction transactionObject = new Transaction();
            transactionObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            transactionObject.setDomainId(cursor.getInt(cursor.getColumnIndex("domain_id")));
            transactionObject.setDomainName(cursor.getString(cursor.getColumnIndex("domain_name")));
            transactionObject.setReportDescription(cursor.getString(cursor.getColumnIndex("report_description")));
            transactionObject.setDate(cursor.getString(cursor.getColumnIndex("date")));
            transactionObject.setStartTime(cursor.getString(cursor.getColumnIndex("start_time")));
            transactionObject.setEndTime(cursor.getString(cursor.getColumnIndex("end_time")));
            transactionObject.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
            transactionObject.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            transactionObject.setStatus(cursor.getString(cursor.getColumnIndex("status")));

            transactionObjects.add(transactionObject);

            cursor.moveToNext();
        }

        cursor.close();

        return transactionObjects;
    }

    public List<TransactionDetail> getTransactionDetailsSync(int transactionId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM `transaction_detail` WHERE `transaction` = ?", new String[]{String.valueOf(transactionId)});
        cursor.moveToFirst();

        List<TransactionDetail> transactionDetailObjects = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            TransactionDetail transactionDetailObject = new TransactionDetail();
            transactionDetailObject.setId(cursor.getInt(cursor.getColumnIndex("id")));
            transactionDetailObject.setTransaction(cursor.getInt(cursor.getColumnIndex("transaction")));
            transactionDetailObject.setMainGroup(cursor.getString(cursor.getColumnIndex("main_group")));
            transactionDetailObject.setSubGroup(cursor.getString(cursor.getColumnIndex("sub_group")));
            transactionDetailObject.setDomainStep(cursor.getInt(cursor.getColumnIndex("domain_step")));
            transactionDetailObject.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            transactionDetailObject.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            transactionDetailObject.setValue(cursor.getString(cursor.getColumnIndex("value")));
            transactionDetailObject.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
            transactionDetailObject.setDefaultValue(cursor.getString(cursor.getColumnIndex("default_value")));
            transactionDetailObject.setComment(cursor.getString(cursor.getColumnIndex("comment")));
            transactionDetailObject.setWarning(cursor.getInt(cursor.getColumnIndex("warning")));
            transactionDetailObject.setTime(cursor.getString(cursor.getColumnIndex("time")));

            transactionDetailObjects.add(transactionDetailObject);

            cursor.moveToNext();
        }

        cursor.close();

        return transactionDetailObjects;
    }

}
