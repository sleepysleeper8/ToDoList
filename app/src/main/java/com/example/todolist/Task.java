package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String text;


    @ColumnInfo(name = "done")
    public boolean done;

    @ColumnInfo(name = "time")
    public long time;


    public Task() {
    }

    public Task(Parcel parcel) {
        id = parcel.readInt();
        text = parcel.readString();
        done = parcel.readByte() != 0;
        time = parcel.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeByte((byte) (done ? 1 : 0));
        dest.writeLong(time);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                time == task.time &&
                done == task.done &&
                Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, time, done);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}