package cap7.com.br.petcare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import cap7.com.br.petcare.model.Animal;
import cap7.com.br.petcare.Util.ScriptDB;

/**
 * Created by Admin on 02/03/2016.
 */
public class AnimalDao extends DBDao {

    public AnimalDao(Context context) {
        super(context);
    }

    public long save(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(ScriptDB.ANIMAL_CODIGO, animal.getCodigo());
        values.put(ScriptDB.ANIMAL_NOME, animal.getNome());
        values.put(ScriptDB.ANIMAL_NASCIMENTO, animal.getNascimento());
        values.put(ScriptDB.ANIMAL_ESPECIE, animal.getEspecie());
        values.put(ScriptDB.ANIMAL_SEXO, animal.getSexo());
        values.put(ScriptDB.ANIMAL_RACA, animal.getRaca());
        values.put(ScriptDB.ANIMAL_COR, animal.getCor());

        return database.insert(ScriptDB.TAB_ANIMAL, null, values);
    }

    public Cursor carregarAnimais(){
        Cursor cursor;
        String[] campos = {ScriptDB.ANIMAL_NOME};
        database = this.readableDB();
        cursor = database.rawQuery("SELECT _id, nome FROM " + ScriptDB.TAB_ANIMAL, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public Animal recuperarAnimal(int idAnimal){
        Cursor cursor;
        String id;
        String nome;
        String cor;
        String raca;
        Animal animal = new Animal();
        database = this.readableDB();
        String[] columns = {"_id", "nome", "cor", "raca"};
        cursor = database.query(ScriptDB.TAB_ANIMAL, columns, "_id =?", new String[] {String.valueOf(idAnimal)},null,null,null);
        if (cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
            do {
                id = cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_ID));
                nome = cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_NOME));
                cor = cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_COR));
                raca = cursor.getString(cursor.getColumnIndexOrThrow(ScriptDB.ANIMAL_RACA));
                animal.setId(Integer.valueOf(id));
                animal.setNome(nome);
                animal.setCor(cor);
                animal.setRaca(raca);
            } while (cursor.moveToNext());
        }
        return animal;
    }

    public void update(int id, String nome, String raca, String cor){
        ContentValues valores;
        String where;
        this.open();

        where = ScriptDB.ANIMAL_ID + "=" +id;
        valores = new ContentValues();
        valores.put(ScriptDB.ANIMAL_NOME, nome);
        valores.put(ScriptDB.ANIMAL_COR, cor);
        valores.put(ScriptDB.ANIMAL_RACA, raca);

        database.update(ScriptDB.TAB_ANIMAL,valores,where,null);
        database.close();


    }


    public void delete(int id){
        ContentValues valores;
        String where;
        this.open();
        database = this.readableDB();
        where = ScriptDB.ANIMAL_ID + "=" +id;

        database.delete(ScriptDB.TAB_ANIMAL, where, null);
        database.close();

    }
}
