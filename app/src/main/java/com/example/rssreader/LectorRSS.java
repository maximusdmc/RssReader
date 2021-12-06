package com.example.rssreader;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LectorRSS extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    String address = "http://hdpnoticias.com.mx/?feed=rss2";
    ProgressDialog progressDialog;
    ArrayList<GenVariables> genVariables;
    @SuppressLint("StaticFieldLeak")
    RecyclerView recyclerView;
    URL url;

    public LectorRSS(Context context, RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Descargando Noticias...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Adaptador adapter=new Adaptador(context, genVariables);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new EspacioVertical(50));
        recyclerView.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Getdata());

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void ProcessXml(Document data) {
        if (data != null) {
            genVariables =new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    GenVariables item=new GenVariables();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        if (cureent.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("content:encoded")){
                            item.setContent(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("media:content")){
                            //this will return us thumbnail url
                            String url=cureent.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);
                        }
                    }
                    genVariables.add(item);
                    }
            }
        }
    }

    public Document Getdata() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
