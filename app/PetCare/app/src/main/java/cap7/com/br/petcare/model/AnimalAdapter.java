package cap7.com.br.petcare.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cap7.com.br.petcare.R;

/**
 * Created by antonio on 02/05/2016.
 */
public class AnimalAdapter extends ArrayAdapter<Animal> {

    private Context context;
    private ArrayList<Animal> animais;

    public AnimalAdapter(Context context, List<Animal> animais){
        super(context,0,animais);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_animal, null);
        }
        Animal animal = getItem(position);

        TextView txtNomeAnimal = (TextView) convertView.findViewById(R.id.txtItemAnimal);
        txtNomeAnimal.setText(animal.getNome());
        return convertView;
    }
}
