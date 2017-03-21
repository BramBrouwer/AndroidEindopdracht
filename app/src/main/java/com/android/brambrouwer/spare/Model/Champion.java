package com.android.brambrouwer.spare.Model;

import android.os.Parcel;
import android.os.Parcelable;

/*
    Model class for each champion. Implements parcelable so we can it between intents as an extra
 */
public class Champion implements Parcelable{

    public static final Creator<Champion> CREATOR = new Creator<Champion>() {
        @Override
        public Champion createFromParcel(Parcel in) {
            return new Champion(in);
        }

        @Override
        public Champion[] newArray(int size) {
            return new Champion[size];
        }
    };
    public int id;
    public String name;
    public String title;
    public int attack;
    public int defense;
    public int magic;
    public int difficulty;

    public Champion(int id,String name, String title, String attack, String defense, String magic, String difficulty){
        this.id = id;
        this.name = name;
        this.title = title;
        this.attack = Integer.parseInt(attack);
        this.defense = Integer.parseInt(defense);
        this.magic = Integer.parseInt(magic);
        this.difficulty = Integer.parseInt(difficulty);

    }

    protected Champion(Parcel in) {
        id = in.readInt();
        name = in.readString();
        title = in.readString();
        attack = in.readInt();
        defense = in.readInt();
        magic = in.readInt();
        difficulty = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeInt(attack);
        dest.writeInt(defense);
        dest.writeInt(magic);
        dest.writeInt(difficulty);
    }
}
