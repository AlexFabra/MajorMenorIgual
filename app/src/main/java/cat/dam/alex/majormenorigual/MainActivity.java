package cat.dam.alex.majormenorigual;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //numero de ranas:
    final int numRanas = 12;
    //declaramos los nombres de las imagenes que se utilizarán:
    final String[] imgRanas ={"rana1","rana2","rana3","rana4","rana5"};
    //declaración random:
    private Random aleatorio = new Random();
    //numRandom guardara los números random:
    private int numRandom=0;
    private boolean resultado;
    private String eleccionUsuario ="";
    private int puntuacion;
    private static final String PUNTUACION = "playerScore";
    private TextView tv_puntuacion;
    private ImageButton btnMthan, btnLthan,btnEqual,btnEleccion;
    private Button btnNuevaPartida;
    private ImageView[] ranas1, ranas2;
    private LinearLayout linearLayout1_1,linearLayout1_2,linearLayout1_3,linearLayout1_4,
    linearLayout2_1,linearLayout2_2,linearLayout2_3,linearLayout2_4;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Desa l’estat actual del joc de l’usuari
        savedInstanceState.putInt(PUNTUACION, puntuacion);
        // Sempre cridem a la superclasse perquè desi també la jerarquia de vistes actual
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onResume(){
        super.onResume();
        //para guardar la puntuación:
        //creem una variable per guardar la dada al layout:
        TextView tv_puntuacion = (TextView) findViewById(R.id.puntuacion);
        //guardamos el casting String de puntuación:
        String puntuacionS = String.valueOf(puntuacion);
        //lo guardamos en el elemento del layout:
        tv_puntuacion.setText(puntuacionS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            // Restaura els valors que s’han desat de l’estat
            puntuacion = savedInstanceState.getInt(PUNTUACION);
            onResume();
        }

        //declaramos los arrays que guardarán ranas de dos tipos:
        //creamos dos arrays para luego contar el número de ranas visibles en cada uno y compararlos.
        ranas1 = new ImageView[numRanas];
        ranas2= new ImageView[numRanas];

        //sincronizamos los linearLayout:
        linearLayout1_1 = (LinearLayout) findViewById(R.id.linear1_1);
        linearLayout1_2 = (LinearLayout) findViewById(R.id.linear1_2);
        linearLayout1_3 = (LinearLayout) findViewById(R.id.linear1_3);
        linearLayout1_4 = (LinearLayout) findViewById(R.id.linear1_4);

        linearLayout2_1 = (LinearLayout) findViewById(R.id.linear2_1);
        linearLayout2_2 = (LinearLayout) findViewById(R.id.linear2_2);
        linearLayout2_3 = (LinearLayout) findViewById(R.id.linear2_3);
        linearLayout2_4 = (LinearLayout) findViewById(R.id.linear2_4);

        //sincronizamos los botones:
        btnMthan = (ImageButton) findViewById(R.id.mthan);
        btnLthan = (ImageButton) findViewById(R.id.lthan);
        btnEqual = (ImageButton) findViewById(R.id.equal);
        btnEleccion = (ImageButton) findViewById(R.id.eleccion);
        btnNuevaPartida = (Button) findViewById(R.id.nuevaPartida);

        //sincronizamos la puntuación:
        tv_puntuacion = (TextView) findViewById(R.id.puntuacion);

        //creamos las imagenes:
        creaImagenesEnLayouts(ranas1, linearLayout1_1, linearLayout1_2, linearLayout1_3, linearLayout1_4, imgRanas);
        creaImagenesEnLayouts(ranas2, linearLayout2_1, linearLayout2_2, linearLayout2_3, linearLayout2_4, imgRanas);

        //determinamos las imagenes visibles y las invisibles:
        RandomizarVisivilidadArrayImageView(ranas1);
        RandomizarVisivilidadArrayImageView(ranas2);

        btnMthan.setOnClickListener(view -> {
            eleccionUsuario = "mthan";
            puntuacion=gestionaRespuesta(eleccionUsuario,btnEleccion,ranas1,ranas2,tv_puntuacion,puntuacion);
        });

        btnLthan.setOnClickListener(view -> {
            eleccionUsuario = "lthan";
            puntuacion=gestionaRespuesta(eleccionUsuario,btnEleccion,ranas1,ranas2,tv_puntuacion,puntuacion);
        });

        btnEqual.setOnClickListener(view -> {
            eleccionUsuario = "equal";
            puntuacion=gestionaRespuesta(eleccionUsuario,btnEleccion,ranas1,ranas2,tv_puntuacion,puntuacion);

        });
        btnNuevaPartida.setOnClickListener(view -> {
            Drawable imagen =  getDrawable(R.drawable.question);
            btnEleccion.setImageDrawable(imagen);
            RandomizarVisivilidadArrayImageView(ranas1);
            RandomizarVisivilidadArrayImageView(ranas2);
        });
    }

    /** Gestiona respuesta modifica el botón de elección según el string pasado,
     *  llama a una función que comprueba el resultado de la partida,
     *  a otra que muestra el resultado
     *  y si el usuario ha ganado, reinicia la partida.
     *
     * @param eleccion String
     * @param btnEleccion ImageButton
     * @param ranas1 Array ImageView
     * @param ranas2 Array ImageView
     */
    public int gestionaRespuesta(String eleccion, ImageButton btnEleccion, ImageView[] ranas1, ImageView[] ranas2,TextView tv_puntuacion, int puntuacion){
        Drawable imagenEleccion =  StringToDrawable(eleccion);
        btnEleccion.setImageDrawable(imagenEleccion);
        resultado = compruebaResultado(ranas1,ranas2,eleccion);
        muestraResultado(resultado);
        //si la elección es correcta, se reinicia la partida:
        if(resultado){
            puntuacion=actualizaPuntuacion(tv_puntuacion,puntuacion);
            Drawable imagenCuestion =  getDrawable(R.drawable.question);
            btnEleccion.setImageDrawable(imagenCuestion);
            RandomizarVisivilidadArrayImageView(ranas1);
            RandomizarVisivilidadArrayImageView(ranas2);
        }
        return puntuacion;
    }

    /** muestraResultado crea un toast de feedback para el usuario.
     *
     * @param resultado boolean
     */
    public void muestraResultado(boolean resultado){
        if(resultado) {
            Toast.makeText(MainActivity.this, "Has acertado!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Estas segur@?", Toast.LENGTH_SHORT).show();
        }
    }

    /** compruebaResultado retorna true si la elección del usuario ha sido correcta
     *  compara el numero de imagenes visibles de cada array y se analiza la coincidencia del resultado
     *  con la elección del usuario.
     * @param img1 array de ImageView
     * @param img2 array de ImageView
     * @param eleccion String cuyo valor representa la elección del usuario
     * @return boolean true si la respuesta del usuario es correcta.
     */
    public boolean compruebaResultado(ImageView[] img1, ImageView[] img2, String eleccion) {
        int contador1 = 0;
        int contador2 = 0;

        for (int i = 0; i < img1.length; i++) {
            //las imagenes visibles incrementan los contadores:
            if (img1[i].getImageAlpha() == 255) {
                contador1++;
            }
            if (img2[i].getImageAlpha() == 255) {
                contador2++;
            }
        }

        switch (eleccion) {
            case "equal":
                if (contador1 == contador2) {
                    return true;
                }
                break;
            case "mthan":
                if (contador1 > contador2) {
                    return true;
                }
                break;
            case "lthan":
                if (contador1 < contador2) {
                    return true;
                }
                break;
        }
        return false;
    }


    /** RandomizarVisivilidadArrayImageView determina el Alpha de las imagenes del array en 0 o en 255 aleatoriamente.
     *
     * @param imgs array ImageView
     */
    public void RandomizarVisivilidadArrayImageView (ImageView[] imgs){
        for (ImageView img : imgs) {
            numRandom = aleatorio.nextInt(2);
            if (numRandom == 0) {
                img.setImageAlpha(255);
            } else {
                img.setImageAlpha(0);
            }
        }
    }

    /** creaImagenesEnLayouts crea inyecta en un ImageView[] la imagen pasada por parámetro con el tamaño pasado por parámetro.
     *  organiza las imagenes de tres en tres en cada layout. El tamaño límite son 12 imagenes, es decir, si el array es mayor a 11,
     *  la función generará un error.
     *
     * @param img array de ImageView
     * @param ll1 LinearLayout
     * @param ll2 LinearLayout
     * @param ll3 LinearLayout
     * @param ll4 LinearLayout
     * @param imgs array de Strings (nombres de las imagenes)
     *
     */
    public void creaImagenesEnLayouts(ImageView[] img, LinearLayout ll1, LinearLayout ll2, LinearLayout ll3, LinearLayout ll4, String[] imgs) {
        String imgAleatoria;
        for (int i = 0; i < img.length; i++) {
            //debemos pasarle this para que no nos lea null en la referencia del array donde apunta el contador:
            img[i] = new ImageView(this);
            imgAleatoria = randomizarImagen(imgs);
            creaImagen(img[i],imgAleatoria);
            adaptaImagenALayout(img[i]);

            //cada layout solo contendrá 3 ranas, este if-else se encargará de organizarlas:
            if (i <= 2) {
                ll1.addView(img[i]);
            } else if (i <= 5) {
                ll2.addView(img[i]);
            } else if (i <= 8) {
                ll3.addView(img[i]);
            } else {
                ll4.addView(img[i]);
            }

            //la imagen se mostrará visible en un principio:
            img[i].setImageAlpha(255);
        }
    }

    /** adapta imagen a layout crea un LayoutParams para asignarle un peso a la imagen y así
     * adaptarla al layout
     *
     * @param img ImageView
     */
    public void adaptaImagenALayout(ImageView img){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        img.setLayoutParams(params);
    }

    /** creaImagen inyecta una imagen a un ImageView.
     *
     * @param iv ImageView
     * @param nombreImagen String nombre de la imagen
     */
    public void creaImagen(ImageView iv, String nombreImagen){
        Drawable imagen = StringToDrawable(nombreImagen);
        iv.setImageDrawable(imagen);
    }

    /** String to Drawable crea un Drawable de un String.
     *
     * @param nombreImagen String
     * @return Drawable
     */
    public Drawable StringToDrawable(String nombreImagen){
        //guardamos los resources en esta variable para poder acceder a ellos:
        Resources res = this.getResources();
        //encontramos el que nos interesa:
        int resId = res.getIdentifier(nombreImagen, "drawable", this.getPackageName());
        //Declaramos la variable de tipo drawable y le pasamos el identificador:
        return getResources().getDrawable(resId);
    }

    /** Dado un array de Strings, randomizarImagenes retorna un String aleatorio del array.
     *
     * @param imagenes array con Strings de los nombres de las imagenes
     * @return String nombre aleatorio del array
     */
    public String randomizarImagen(String[] imagenes){
        String imgAleatoria;
        //tomamos una imagen aleatoria entre las declaradas en el array imgRanas:
        imgAleatoria=imagenes[aleatorio.nextInt(imagenes.length)];
        return imgAleatoria;
    }
    public int actualizaPuntuacion(TextView tv_puntuacion,int puntuacion){
        puntuacion+=10;
        tv_puntuacion.setText(String.valueOf(puntuacion));
        return puntuacion;
    }
}