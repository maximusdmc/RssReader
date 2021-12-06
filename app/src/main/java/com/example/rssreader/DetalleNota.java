package com.example.rssreader;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import java.io.IOException;
import java.net.URL;

public class DetalleNota extends AppCompatActivity {
    TextView txtdetalles, txt_titulo;

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.detalles_nota);
        txtdetalles = findViewById(R.id.muestra_detalles);
        txt_titulo = findViewById(R.id.txt_titulo);

        Bundle bundle = getIntent().getExtras();
        txt_titulo.setText(Html.fromHtml(bundle.getString("Title")));
        txtdetalles.setText(Html.fromHtml(bundle.getString("Content")));
        txtdetalles.setMovementMethod(new ScrollingMovementMethod());

/*
        String html = "http://hdpnoticias.com.mx/wp-content/uploads/2016/05/OBJETOS-ASEGURADOS-800x445.jpg";
        Spanned s = Html.fromHtml(html,getImageHTML(),null);
        TextView txt = (TextView)findViewById(R.id.muestra_detalles);
        txt.setText(s);
*/

    }

    public Html.ImageGetter getImageHTML(){
        return new Html.ImageGetter(){
            public Drawable getDrawable(String source) {
                try{
                    Drawable d = Drawable.createFromStream(new URL(source).openStream(), "src name");
                    d.setBounds(0, 0, 800,600);
                    return d;
                }catch(IOException e){
                    Log.v("IOException",e.getMessage());
                    return null;
                }
            }
        };
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem shareOpt = menu.findItem(R.id.menu_item_share);
        //Inicializamos nuestro ShareActionProvider
        ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareOpt);

        //Creamos nuestro sharer Intent

        Bundle bundle = getIntent().getExtras();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, bundle.getString("Link"));
        myShareActionProvider.setShareIntent(i);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

}
