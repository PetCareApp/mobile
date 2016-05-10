package cap7.com.br.petcare.Util;

/**
 * Created by Virginia on 02/03/2016.
 */
public class ScriptDB {

    // Database Name
    public static String DATABASE_NAME = "petcare_database";

   // Tabela Animal
   public static final String TAB_ANIMAL = "animal";

    public static final String ANIMAL_ID = "_id";
    public static final String ANIMAL_ID_CURSOR = "_id";
    public static final String ANIMAL_CODIGO = "codigo";
    public static final String ANIMAL_NOME = "nome";
    public static final String ANIMAL_NASCIMENTO = "nascimento";
    public static final String ANIMAL_ESPECIE = "especie";
    public static final String ANIMAL_SEXO = "sexo";
    public static final String ANIMAL_RACA = "raca";
    public static final String ANIMAL_COR = "cor";

    // Animal Table Create Query
    /**
      CREATE TABLE animal ( id INTEGER PRIMARY KEY AUTOINCREMENT, codigo TEXT, nome TEXT,
     nascimento TEXT, especie TEXT, sexo TEXT, raca TEXT, cor TEXT);
     */

    public static final String CREATE_TABLE_ANIMAL = "CREATE TABLE "
            + TAB_ANIMAL + "(" + ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ANIMAL_CODIGO + " TEXT, "
            + ANIMAL_NOME + " TEXT, " + ANIMAL_NASCIMENTO + " TEXT, " + ANIMAL_ESPECIE + " TEXT, "
            + ANIMAL_SEXO + " TEXT, " + ANIMAL_RACA + " TEXT, " + ANIMAL_COR + " TEXT)";


    //Tabela Proprietario
    public static final String TAB_PROPRIETARIO_NOVO = "proprietario";

    public static final String PROPRIETARIO_ID = "_id";
    public static final String PROPRIETARIO_ID_CURSOR = "_id";
    public static final String PROPRIETARIO_NOME = "nome";
    public static final String PROPRIETARIO_TELEFONE = "telefone";
    public static final String PROPRIETARIO_SENHA = "senha";
    public static final String PROPRIETARIO_EMAIL = "email";

    public static final String CREATE_TABLE_PROPRIETARIO = "CREATE TABLE " + TAB_PROPRIETARIO_NOVO +
            "( " + PROPRIETARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROPRIETARIO_TELEFONE + " TEXT, "
            + PROPRIETARIO_NOME + " TEXT, " + PROPRIETARIO_SENHA + " TEXT, " + PROPRIETARIO_EMAIL + " TEXT)";



}

