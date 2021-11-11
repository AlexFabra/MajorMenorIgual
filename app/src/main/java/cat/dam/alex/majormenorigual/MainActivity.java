package cat.dam.alex.majormenorigual;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //numero màxim de imatges del mateix tipus:
    final int maxImg=10;
    //numero mínim de imatges del mateix tipus:
    final int minImg=1;
    Random aleatori = new Random();
    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num=aleatori.nextInt(maxImg);

        final LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        for (int i = 0; i < num; i++) {
            final TextView text = new TextView(this);
            text.setText(" je ");
            linearLayout1.addView(text);
        }

        num=aleatori.nextInt(maxImg);

        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.linear2);
        for (int i = 0; i < num; i++) {
            final TextView text2 = new TextView(this);
            text2.setText(" je ");
            linearLayout2.addView(text2);
        }
    }
}