package org.wooglies.blockchainchain;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.renderscript.Element;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;


public class MainActivity extends Activity {

    WebView webView;
    Button button;
    //TextView textView;
    ListView listView;

    String webPageString = "";

    BlockItemAdapter blockItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.activity_main_button);

        initializeListView();

        readWebpage();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                blockItemAdapter.clear();
                readWebpage();
                //try {
                //webView.loadUrl("https://blockchain.info/blocks");

                //new DownloadWebPageTask().execute("https://blockchain.info/blocks");


                //Log.d("DATA", buffer.toString());
                //}catch (Exception e){
                //    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                //}
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent itemAboutI = new Intent(MainActivity.this, BlockItemAbout.class);
                itemAboutI.putExtra("blockNumber", blockItemAdapter.getItem(position).getBlockNumber());
                itemAboutI.putExtra("blockMinedBy", blockItemAdapter.getItem(position).getBlockMinedBy());
                itemAboutI.putExtra("blockMinedAt", blockItemAdapter.getItem(position).getBlockMinedAt());
                itemAboutI.putExtra("blockHash", blockItemAdapter.getItem(position).getBlockHash());

                startActivity(itemAboutI);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ;
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    private void initializeListView(){

        listView = (ListView) findViewById(R.id.activity_main_listView);
        blockItemAdapter = new BlockItemAdapter(this, generateData("", "", "", ""));
        listView.setAdapter(blockItemAdapter);
        blockItemAdapter.remove(blockItemAdapter.getItem(blockItemAdapter.getCount() - 1));

    }

    private ArrayList<BlockItem> generateData(String blockNumber, String blockMinedBy, String blockMinedAt, String blockHash){
        ArrayList<BlockItem> arrayList = new ArrayList<>();
        arrayList.add(new BlockItem(blockNumber, blockMinedBy, blockMinedAt, blockHash));
        return arrayList;
    }



    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //textView.setText(Html.fromHtml(result));
            webPageString = result;

            String startsWith = "</table>";

            Integer id = 0;
            Integer blocks = 0;

            boolean run = true;

            String result1 = result.substring(result.indexOf("<table class=\"table table-striped\""), result.indexOf("</table>"));
            String result2 = result1.substring(result1.indexOf("</tr>") + 5);
            String result3 = result2.substring(result2.indexOf("<tr>"));


            String temp = result3;

            while( run ){

                try{
                    //Log.d("Degug", temp);

                    String rBlockNum = temp.substring(temp.indexOf("<a href=\"/block-height/"));
                    rBlockNum = rBlockNum.substring(23, rBlockNum.indexOf("\">"));

                    String rDiggedAt = temp.substring((temp.indexOf("<td>") + 4));
                    rDiggedAt = rDiggedAt.substring(rDiggedAt.indexOf("<td>") + 4);
                    rDiggedAt = rDiggedAt.substring(0, rDiggedAt.indexOf("</td>"));

                    String rDiggedBy = temp.substring(temp.indexOf("<a href=\"/blocks/") + 17);
                    rDiggedBy = rDiggedBy.substring(0, rDiggedBy.indexOf("\">"));

                    String rBlockHash = temp.substring(temp.indexOf("<a href=\"/block/") + 16);
                    rBlockHash = rBlockHash.substring(0, rBlockHash.indexOf("\">"));

                    temp = temp.substring(temp.indexOf("</tr>") + 5);

                    blocks++;

                    blockItemAdapter.add(new BlockItem(rBlockNum, rDiggedBy, rDiggedAt, rBlockHash));



                }catch (Exception e){
                    Log.d("Degug Exception", e.toString());
                    run = false;
                }


                //if(blocks >= 10){
                //
                //}

            }


            Log.d("Degug Block Count", blocks.toString());


            //String res = "";
            //res += "Block num: " + rBlockNum + "\n";
            //res += "Time: " + rDiggedAt + "\n";
            //res += "Relayed by: " + rDiggedBy + "\n";
            //res += "Block Hash: " + rBlockHash + "\n";




//            textView.setText(res);
            //Log.d("DATA", result);
        }
    }

    public void readWebpage() {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"https://blockchain.info/blocks"});

    }
}
