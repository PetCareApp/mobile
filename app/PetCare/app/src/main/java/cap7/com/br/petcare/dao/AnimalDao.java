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
        cursor = database.rawQuery("SELECT id as _id, nome FROM " + ScriptDB.TAB_ANIMAL, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

}
