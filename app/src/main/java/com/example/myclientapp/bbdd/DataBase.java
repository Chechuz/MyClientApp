package com.example.myclientapp.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myclientapp.notas.Notas_modelo;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public static final int DB_VERSION =1;
    public static String DB_NAME="MyClientTrakDB.db";
    public static String DB_TABLE_NOTAS="TablaNotas";
    public static String DB_TABLE_CLIENTES="TablaClientes";


    public static String COLUMN_ID="id";
    public static String COLUMN_CLIENT_NAME="nombre";
    public static String COLUMN_CLIENT_ADRESS="direccion";
    public static String COLUMN_CLIENT_PHONE="telefono";
    public static String COLUMN_CLIENT_EMAIL="email";
    public static String COLUMN_CLIENT_OTHER="otro";
    public static String COLUMN_TITLE="titulo";
    public static String COLUMN_DETAILS="detalle";
    public static String COLUMN_DATE="fecha";
    public static String COLUMN_TIME="hora";
    public DataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE "+ DB_TABLE_NOTAS +
                " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DETAILS + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i>=i1)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+ DB_NAME);
        onCreate(db);
    }
    public long anadeNota(Notas_modelo notaModelo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_TITLE, notaModelo.getTitulo());
        contentValues.put(COLUMN_DETAILS, notaModelo.getDetalle());
        contentValues.put(COLUMN_DATE, notaModelo.getFecha());
        contentValues.put(COLUMN_TIME, notaModelo.getHora());

        long ID= db.insert(DB_TABLE_NOTAS, null, contentValues);
        Log.d("Insertado", "id-->"+ID);
        return ID;
    }
    // creo el Array para almacenar los datos
    public List<Notas_modelo> getNote(){
        SQLiteDatabase db = this.getReadableDatabase();
        List <Notas_modelo> notas = new ArrayList<>();
        String querySt= "SELECT * FROM "+ DB_TABLE_NOTAS;
        Cursor cursor= db.rawQuery(querySt, null);
        if(cursor.moveToFirst()){
            do{
                Notas_modelo notaModelo = new Notas_modelo();
                notaModelo.setId(cursor.getInt(0));
                notaModelo.setTitulo(cursor.getString(1));
                notaModelo.setDetalle(cursor.getString(2));
                notaModelo.setFecha(cursor.getString(3));
                notaModelo.setHora(cursor.getString(4));

                notas.add(notaModelo);
            } while (cursor.moveToNext());
        }
        return notas;
    }

    public Notas_modelo getNotas(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String [] query = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_DETAILS, COLUMN_DATE, COLUMN_TIME};
        Cursor cursor = db.query(DB_TABLE_NOTAS, query, COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        return new Notas_modelo(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    void eliminaNota(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_TABLE_NOTAS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

}
