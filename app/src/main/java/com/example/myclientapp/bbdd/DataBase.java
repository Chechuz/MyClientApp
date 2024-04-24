package com.example.myclientapp.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myclientapp.cliente.Cliente;
import com.example.myclientapp.notas.Notas;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public static final int DB_VERSION =2;
    public static String DB_NAME="MyClientTrakDB.db";
    public static String DB_TABLE_NOTAS="TablaNotas";
    public static String DB_TABLE_CLIENTES="TablaClientes";


    public static String COLUMN_ID="id";
    public static String COLUMN_CLIENT_NAME="nombre";
    public static String COLUMN_CLIENT_ADRESS="direccion";
    public static String COLUMN_CLIENT_PHONE="telefono";
    public static String COLUMN_CLIENT_EMAIL="email";
    public static String COLUMN_CLIENT_OTHER="otro";
    public static String COLUMN_FOREIGN_KEY="id_cliente";
    public static String COLUMN_TITLE="titulo";
    public static String COLUMN_DETAILS="detalle";
    public static String COLUMN_DATE="fecha";
    public static String COLUMN_TIME="hora";

    public DataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //CREACION  DE  LA  BASE  DE  DATOS  CON SUS DOS  TABLAS
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCliente= "CREATE TABLE "+ DB_TABLE_CLIENTES +
                " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLIENT_NAME + " TEXT, " +
                COLUMN_CLIENT_ADRESS + " TEXT, " +
                COLUMN_CLIENT_PHONE + " TEXT, " +
                COLUMN_CLIENT_EMAIL + " TEXT, " +
                COLUMN_CLIENT_OTHER + " TEXT); ";
        db.execSQL(queryCliente);
        String queryNotas= "CREATE TABLE "+ DB_TABLE_NOTAS +
                " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FOREIGN_KEY + " INTEGER, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DETAILS + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_FOREIGN_KEY +
                ") REFERENCES " + DB_TABLE_CLIENTES + "("+ COLUMN_ID +")); " ;
        db.execSQL(queryNotas);

    }
//   ACTUALIZACION DE LA BBDD
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i>=i1)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+ DB_NAME);
        onCreate(db);
    }

    //  -----^¨^¨^¨^¨  METODODS PARA  N O T A S   ¨^¨^¨^¨^¨---------

    //METODO QUE AÑADE  NOTAS   (metodo SQL .insert  )
    public long anadeNota(Notas notaModelo, int id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_TITLE, notaModelo.getTitulo());
        contentValues.put(COLUMN_DETAILS, notaModelo.getDetalle());
        contentValues.put(COLUMN_DATE, notaModelo.getFecha());
        contentValues.put(COLUMN_TIME, notaModelo.getHora());
        contentValues.put(COLUMN_FOREIGN_KEY, id);

        long ID= db.insert(DB_TABLE_NOTAS, null, contentValues);
        Log.d("Insertado", "id-->"+ID);
        return ID;
    }

    // creo el Array para almacenar los datos QUE SE MOSTRARÁ EN EL RECYCLER
    public List<Notas> getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        List <Notas> notas = new ArrayList<>();
        String querySt= "SELECT * FROM "+ DB_TABLE_NOTAS + " WHERE "+COLUMN_FOREIGN_KEY + " = "+ id;
        Cursor cursor= db.rawQuery(querySt, null);
        if(cursor.moveToFirst()){
            do{
                Notas notaModelo = new Notas();
                notaModelo.setId(cursor.getInt(0));
                notaModelo.setTitulo(cursor.getString(2));
                notaModelo.setDetalle(cursor.getString(3));
                notaModelo.setFecha(cursor.getString(4));
                notaModelo.setHora(cursor.getString(5));

                notas.add(notaModelo);
            } while (cursor.moveToNext());
        }
        return notas;
    }

    //   METODO QUE OBTIENE UNA NOTA DE LA TABLA SEGUN SU ID (para MOSTRARLA cuando le hago click)
    public Notas getNotas(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String [] query = new String[]{COLUMN_ID, COLUMN_FOREIGN_KEY, COLUMN_TITLE, COLUMN_DETAILS, COLUMN_DATE, COLUMN_TIME};
        Cursor cursor = db.query(DB_TABLE_NOTAS, query, COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        return new Notas(
                Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
    }
//    METODO QUE ELIMINA UNA NOTA PASANDO SU ID COMO PARÁMETRO
    public void eliminaNota(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_TABLE_NOTAS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //  -----^¨^¨^¨^¨  METODODS PARA  C L I E N T E   ¨^¨^¨^¨^¨---------

    //  CREO EL METODO que añade clientes a la database
    public long anadeCliente(Cliente cliente){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_CLIENT_NAME, cliente.getNombre());
        contentValues.put(COLUMN_CLIENT_ADRESS, cliente.getDireccion());
        contentValues.put(COLUMN_CLIENT_PHONE, cliente.getTelefono());
        contentValues.put(COLUMN_CLIENT_EMAIL, cliente.getEmail());
        contentValues.put(COLUMN_CLIENT_OTHER, cliente.getOtro());

        long ID= db.insert(DB_TABLE_CLIENTES, null, contentValues);
        Log.d("Insertado", "id-->"+ID);
        return ID;
    }

    // Obtiene la lista completa de clientes para poder ponerla en el Recycler
    public List<Cliente> getCliente(){
        SQLiteDatabase db = this.getReadableDatabase();
        List <Cliente> cliente_list = new ArrayList<>();
        String querySt= "SELECT * FROM "+ DB_TABLE_CLIENTES;
        Cursor cursor= db.rawQuery(querySt, null);
        if(cursor.moveToFirst()){
            do{
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(0));
                cliente.setNombre(cursor.getString(1));
                cliente.setDireccion(cursor.getString(2));
                cliente.setTelefono(cursor.getString(3));
                cliente.setEmail(cursor.getString(4));
                cliente.setOtro(cursor.getString(5));

                cliente_list.add(cliente);
            } while (cursor.moveToNext());
        }
        return cliente_list;
    }
    //Obtiene un cliente del Recycler para mostrar su vista completa en "Client Activity"
    public Cliente getClientes(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String [] query = new String[]{COLUMN_ID, COLUMN_CLIENT_NAME, COLUMN_CLIENT_ADRESS, COLUMN_CLIENT_PHONE, COLUMN_CLIENT_EMAIL, COLUMN_CLIENT_OTHER};
        Cursor cursor = db.query(DB_TABLE_CLIENTES, query, COLUMN_ID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        return new Cliente(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5));
    }
    public void eliminaCliente(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_TABLE_CLIENTES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void editaCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CLIENT_NAME, cliente.getNombre());
        contentValues.put(COLUMN_CLIENT_ADRESS, cliente.getDireccion());
        contentValues.put(COLUMN_CLIENT_PHONE, cliente.getTelefono());
        contentValues.put(COLUMN_CLIENT_EMAIL, cliente.getEmail());
        contentValues.put(COLUMN_CLIENT_OTHER, cliente.getOtro());

        db.update(DB_TABLE_CLIENTES, contentValues, COLUMN_ID + " = "+cliente.getId(), null);

        db.close();

    }
}
