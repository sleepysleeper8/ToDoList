package com.example.todolist;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    public static App instance;
    public AppDatabase database;
    public TasksDao tasksDao;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database").allowMainThreadQueries()
                .build();
        tasksDao=database.tasksDao();

    }

    public static App getInstance() {
        return instance;
    }
    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public TasksDao getTasksDao() {
        return tasksDao;
    }

    public void setTasksDao(TasksDao tasksDao) {
        this.tasksDao = tasksDao;
    }
}
