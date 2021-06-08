package com.zhadko.automobile.datalayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhadko.automobile.models.Car;
import com.zhadko.automobile.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Utils.DBName, null, Utils.version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CAR_TABLE = "CREATE TABLE " + Utils.TableName + "(" +
                Utils.Auto_ID + " INTEGER PRIMARY KEY," +
                Utils.Auto_name + " TEXT," +
                Utils.Auto_cost + " TEXT" + ")";
        db.execSQL(CREATE_CAR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TableName);
        onCreate(db);
    }

    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.Auto_ID, car.getId());
        contentValues.put(Utils.Auto_name, car.getName());
        contentValues.put(Utils.Auto_cost, car.getCost());

        db.insert(Utils.TableName, null, contentValues);
        db.close();
    }

    public Car getCar(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Utils.TableName, new String[] {Utils.Auto_ID, Utils.Auto_name, Utils.Auto_cost},
                Utils.Auto_ID + "=?",new String[] {String.valueOf(id)},
                null, null,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Car car = new Car(Integer.parseInt(cursor.getString((0))), cursor.getString(1), cursor.getString(2));
        return car;
    }

    public List<Car> getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Car> cars = new ArrayList<>();
        String getAllCars = "SELECT * FROM " + Utils.TableName;
        Cursor cursor = db.rawQuery(getAllCars, null);

        if (cursor.moveToFirst()) {

            do {
                Car car = new Car();
                car.setId(Integer.parseInt(Utils.Auto_ID));
                car.setName(Utils.TableName);
                car.setCost(Utils.Auto_cost);

                cars.add(car);

            } while (cursor.moveToNext());
        }
            return cars;
    }

    public int update(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.Auto_name, car.getName());
        contentValues.put(Utils.Auto_cost, car.getCost());
        return db.update(Utils.TableName, contentValues, Utils.Auto_ID + "=?", new String[] {String.valueOf(car.getId())});
    }

    public void deleteAllCars(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TableName, Utils.Auto_ID + "=?", new String[] {String.valueOf(car.getId())});
        db.close();
    }

    public int countCars() {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE_CARS = "SELECT * FROM " + Utils.TableName;
        Cursor cursor = db.rawQuery(DELETE_CARS, null);
        return cursor.getCount();
    }

}
