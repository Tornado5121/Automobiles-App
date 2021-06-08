package com.zhadko.automobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.zhadko.automobile.datalayer.DBHelper;
import com.zhadko.automobile.models.Car;
import com.zhadko.automobile.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String id;
    private String name;
    private String cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(this);
        db.addCar(new Car(1, "Toyota", "30000"));

        List<Car> cars =  db.getAllCars();

        for (Car car : cars) {
            Log.d("dsf", car.getId() + car.getName() + car.getCost());
        }


    }
}