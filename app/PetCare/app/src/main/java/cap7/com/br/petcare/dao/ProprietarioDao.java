package cap7.com.br.petcare.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import cap7.com.br.petcare.model.Proprietario;
import cap7.com.br.petcare.Util.ScriptDB;

/**
 * Created by antonio on 08/03/2016.
 */
public class ProprietarioDao extends DBDao {



    public ProprietarioDao(Context context) {
        super(context);
        //TODO talvez precise instanciar o databasehelper
    }
    //TODO refatorar o token quando adicionar login com facebook
    public String save(Proprietario proprietario) {
        long resultado;

        ContentValues values = new ContentValues();
        values.put(ScriptDB.PROPRIETARIO_NOME, proprietario.getNome());
        values.put(ScriptDB.PROPRIETARIO_TELEFONE, proprietario.getTelefone());
        values.put(ScriptDB.PROPRIETARIO_EMAIL, proprietario.getEmail());

        resultado = database.insert(ScriptDB.TAB_PROPRIETARIO_NOVO, null, values);
        database.close();

        if (resultado ==-1)
            return "Erro ao salvar usuário";
        else
            return "Usuário cadastrado com sucesso!";
    }

    public Cursor carregarPerfil(){
        Cursor cursor;
        String[] campos = {ScriptDB.PROPRIETARIO_NOME, ScriptDB.PROPRIETARIO_TELEFONE};
        database = this.readableDB();
        cursor = database.rawQuery("SELECT _id, nome, telefone FROM " + ScriptDB.TAB_PROPRIETARIO_NOVO, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        database.close();
        return cursor;
    }

    public Cursor retornarProprietario(int id){
        Cursor cursor;
        database = this.readableDB();
        cursor = database.rawQuery("SELECT _id, nome, email, telefone FROM "+ScriptDB.TAB_PROPRIETARIO_NOVO +
                " WHERE _id = ?", new String[]{String.valueOf(id)});
        if(cursor!=null){
            cursor.moveToFirst();
        }
        database.close();
        return cursor;

    }

    public void update(int id, String nome, String email, String telefone){
        ContentValues valores;
        String where;
        this.open();

        where = ScriptDB.PROPRIETARIO_ID + "=" +id;
        valores = new ContentValues();
        valores.put(ScriptDB.PROPRIETARIO_NOME,nome);
        valores.put(ScriptDB.PROPRIETARIO_EMAIL,email);
        valores.put(ScriptDB.PROPRIETARIO_TELEFONE, telefone);

        database.update(ScriptDB.TAB_PROPRIETARIO_NOVO,valores,where,null);
        database.close();


    }

}
