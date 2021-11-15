package cat.dam.alex.majormenorigual;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //numero de ranas:
    final int numRanas = 12;
    //declaramos los nombres de las imagenes que se utilizarán:
    final String imgRana1= "rana1";
    final String imgRana2= "rana2";
    //tamaño de las imagenes:
    int tamañoImagen = 110;
    //declaración random:
    Random aleatorio = new Random();
    //numRandom guardara los números random:
    int numRandom=0;
    boolean resultado;
    String eleccionUsuario ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declaramos los arrays que guardarán ranas de dos tipos:
        //creamos dos arrays para luego contar el número de ranas visibles en cada uno y compararlos.
        ImageView[] ranas1 = new ImageView[numRanas];
        ImageView[]  ranas2= new ImageView[numRanas];

        //sincronizamos los linearLayout:
        final LinearLayout linearLayout1_1 = (LinearLayout) findViewById(R.id.linear1_1);
        final LinearLayout linearLayout1_2 = (LinearLayout) findViewById(R.id.linear1_2);
        final LinearLayout linearLayout1_3 = (LinearLayout) findViewById(R.id.linear1_3);
        final LinearLayout linearLayout1_4 = (LinearLayout) findViewById(R.id.linear1_4);

        final LinearLayout linearLayout2_1 = (LinearLayout) findViewById(R.id.linear2_1);
        final LinearLayout linearLayout2_2 = (LinearLayout) findViewById(R.id.linear2_2);
        final LinearLayout linearLayout2_3 = (LinearLayout) findViewById(R.id.linear2_3);
        final LinearLayout linearLayout2_4 = (LinearLayout) findViewById(R.id.linear2_4);

        //sincronizamos los botones:
        final ImageButton btnMthan = (ImageButton) findViewById(R.id.mthan);
        final ImageButton btnLthan = (ImageButton) findViewById(R.id.lthan);
        final ImageButton btnEqual = (ImageButton) findViewById(R.id.equal);
        final ImageButton btnEleccion = (ImageButton) findViewById(R.id.eleccion);
        final Button btnNuevaPartida = (Button) findViewById(R.id.nuevaPartida);

        //creamos las imagenes:
        creaImagenesEnLayouts(ranas1, linearLayout1_1, linearLayout1_2, linearLayout1_3, linearLayout1_4, imgRana1, tamañoImagen);
        creaImagenesEnLayouts(ranas2, linearLayout2_1, linearLayout2_2, linearLayout2_3, linearLayout2_4, imgRana2, tamañoImagen);

        //determinamos las imagenes visibles y las invisibles:
        RandomizarVisivilidadArrayImageView(ranas1);
        RandomizarVisivilidadArrayImageView(ranas2);

        btnMthan.setOnClickListener(view -> {
            eleccionUsuario = "mthan";
            gestionaRespuesta(eleccionUsuario,btnEleccion,ranas1,ranas2);
        });

        btnLthan.setOnClickListener(view -> {
            eleccionUsuario = "lthan";
            gestionaRespuesta(eleccionUsuario,btnEleccion,ranas1,ranas2);
        });

        btnEqual.setOnClickListener(view -> {
            eleccionUsuario = "equal";
            gestionaRespuesta(eleccionUsuario,btnEleccion,ranas1,ranas2);
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
    public void gestionaRespuesta(String eleccion, ImageButton btnEleccion, ImageView ranas1[], ImageView ranas2[]){
        Drawable imagenEleccion =  StringToDrawable(eleccion);
        btnEleccion.setImageDrawable(imagenEleccion);
        resultado = compruebaResultado(ranas1,ranas2,eleccion);
        muestraResultado(resultado);
        //si la elección es correcta, se reinicia la partida:
        if(resultado){
            Drawable imagenCuestion =  getDrawable(R.drawable.question);
            btnEleccion.setImageDrawable(imagenCuestion);
            RandomizarVisivilidadArrayImageView(ranas1);
            RandomizarVisivilidadArrayImageView(ranas2);
        }
    }

    /** muestraResultado crea un toast de feedback para el usuario.
     *
     * @param resultado boolean
     */
    public void muestraResultado(boolean resultado){
        if(resultado==true) {
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
    public boolean compruebaResultado(ImageView img1[], ImageView img2[], String eleccion) {
        int contador1 = 0;
        int contador2 = 0;
        int cont = 0;

        for (int i = 0; i < img1.length; i++) {
            //las imagenes visibles incrementan los contadores:
            if (img1[i].getImageAlpha() == 255) {
                contador1++;
            }
            if (img2[i].getImageAlpha() == 255) {
                contador2++;
            }
        }

        if (eleccion.equals("equal")){
            if (contador1 == contador2) {
                return true;

            }
        } else if(eleccion.equals("mthan")){
            if (contador1 > contador2) {
                return true;
            }
        }else if(eleccion.equals("lthan")){
            if (contador1 < contador2) {
                return true;
            }
        }
        return false;
    }


    /** RandomizarVisivilidadArrayImageView determina el Alpha de las imagenes del array en 0 o en 255 aleatoriamente.
     *
     * @param imgs array ImageView
     */
    public void RandomizarVisivilidadArrayImageView (ImageView[] imgs){
        for(int i=0;i<imgs.length;i++) {
            numRandom = aleatorio.nextInt(2);
            if (numRandom == 0) {
                imgs[i].setImageAlpha(255);
            } else {
                imgs[i].setImageAlpha(0);
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
     * @param nombreImagen String que contiene el nombre de la imagen
     * @param tamañoImagen int que contiene el tamaño de la imagen
     *
     */
    public void creaImagenesEnLayouts(ImageView[] img, LinearLayout ll1,LinearLayout ll2,LinearLayout ll3,LinearLayout ll4, String nombreImagen, int tamañoImagen) {
        for (int i = 0; i < img.length; i++) {
            //debemos pasarle this para que no nos lea null en la referencia del array donde apunta el contador:
            img[i] = new ImageView(this);

           creaImagen(img[i],nombreImagen);

            adaptaImagenALayout(img[i]);

            //cada layout solo contendrá 3 ranas, este if-else se encargará de organizarlas:
            if (i <= 2) {
                ll1.addView(img[i]);
            } else if (i <= 5 && i > 2) {
                ll2.addView(img[i]);
            } else if (i <= 8 && i > 5) {
                ll3.addView(img[i]);
            } else if (i > 8) {
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
         /*para crear la imagen podemos hacer lo siguiente:
                Drawable imagen = getDrawable(R.drawable.rana1);
            el problema es que 'rana1' esta escrito sin referenciar a una variable, por lo que no es flexible.
            así que intentamos hacer lo siguiente:
                Drawable imagen = getDrawable(R.drawable.nombreImagen);
            no funciona, porque no podemos introducir una variable aquí. Por lo que lo haremos de otra forma:
            crearemos una variable de tipo resources para poder pasarle el nombre de la imagen con una variable.
             */

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
        Drawable imagen = getResources().getDrawable(resId);
        return imagen;
    }
}