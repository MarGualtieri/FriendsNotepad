package app.note.friendsnotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    View view;
    private EditText et1;
    private EditText bitacora;
    Button botonCopiar, botonPegar;
    TextView textView;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.blue);

        bitacora = (EditText) findViewById(R.id.bitacora);
        et1 = (EditText) findViewById(R.id.bitacora);
        //TextView = (TextView) findViewById(R.id.textview);
        botonCopiar = (Button) findViewById(R.id.botonCopiar);
        botonPegar = (Button) findViewById(R.id.botonPegar);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        botonPegar.setEnabled(true);

       /* if (!clipboardManager.hasPrimaryClip()) {
            botonPegar.setEnabled(false);
        } else {
            botonPegar.setEnabled(true);
        }*/


        String archivo[] = fileList();

        if (ArchivoExiste(archivo, "bitacora.txt")) {
            try {
                InputStreamReader Archivo = new InputStreamReader(openFileInput("bitacora.txt"));
                BufferedReader br = new BufferedReader(Archivo);
                String linea = br.readLine();
                String bitacoraCompleta = "";

                while (linea != null) {
                    bitacoraCompleta = bitacoraCompleta + linea + "\n";
                    linea = br.readLine();
                }

                br.close();
                Archivo.close();
                et1.setText(bitacoraCompleta);

            } catch (IOException e) {

            }

        }

    }

    private boolean ArchivoExiste(String archivo[], String NombreArchivo) {
        for (int i = 0; i < archivo.length; i++)
            if (NombreArchivo.equals(archivo[i]))
                return true;
        return false;
    }


    public void nueva(View view) {

        bitacora.setText("");
        Toast.makeText(this, "bitacora borrada", Toast.LENGTH_SHORT).show();
    }

    public void cerrar(View view) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Activity.MODE_PRIVATE));
            archivo.write(et1.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
        }
        finish();
    }

    public void copiar(View view) { // COPIAR A PORTAPAPELES

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("bitacora", bitacora.getText().toString());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, "Texto copiado", Toast.LENGTH_SHORT).show();
    }

    public void pegar(View view) {

        if (clipboardManager == null) {
            Toast.makeText(this, "Texto pegado", Toast.LENGTH_SHORT).show();
        } else {

            String clipboardText;
            clipboardText = clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(getApplicationContext()).toString();
            bitacora.setText(bitacora.getText().toString() + clipboardText);
            Toast.makeText(this, "Texto pegado", Toast.LENGTH_SHORT).show();
        }
    }

    public void onResume() {
        super.onResume();


}
}