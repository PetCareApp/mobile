package cap7.com.br.petcare.model;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cap7.com.br.petcare.R;
import cap7.com.br.petcare.Util.ScriptDB;

/**
 * Created by antonio on 03/05/2016.
 */
public class AnimalCursorAdapter extends CursorAdapter {

    public AnimalCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_animal, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtNomeAnimal = (TextView) view.findViewById(R.id.txtItemAnimal);

        txtNomeAnimal.setText(cursor.getString(cursor.getColumnIndex(ScriptDB.ANIMAL_NOME)));
    }
}
