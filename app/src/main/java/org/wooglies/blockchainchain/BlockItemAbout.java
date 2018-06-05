package org.wooglies.blockchainchain;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BlockItemAbout extends Activity {

    TextView tv_blockNum, tv_NumTrans, tv_outTotal, tv_estTranVol, tv_transFee, tv_height, tv_timestamp, tv_recived_time, tv_relayd_by, tv_difficulty, tv_bits, tv_size, tv_weight, tv_version, tv_nonce, tv_block_reward, tv_block_hash;

    String bNum, bNumTrans, bOutTotal, bEstTranVol, bHeight, bTimestamp, bRecived_time, bRelayd_by, bDifficulty, bBits, bDize, bWeight, bVersion, bNonce, bBlock_reward, bHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_item_about);

        tv_blockNum = (TextView) findViewById(R.id.activity_block_item_about_txt_block_num);
        tv_NumTrans = (TextView) findViewById(R.id.activity_block_item_about_txt_no_transactions);
        tv_outTotal = (TextView) findViewById(R.id.activity_block_item_about_txt_output_total);
        tv_estTranVol = (TextView) findViewById(R.id.activity_block_item_about_txt_etv);
        tv_transFee = (TextView) findViewById(R.id.activity_block_item_about_txt_trans_fee);
        tv_height = (TextView) findViewById(R.id.activity_block_item_about_txt_height);
        tv_recived_time = (TextView) findViewById(R.id.activity_block_item_about_txt_recived_at);
        tv_relayd_by = (TextView) findViewById(R.id.activity_block_item_about_txt_relayed_by);
        tv_difficulty = (TextView) findViewById(R.id.activity_block_item_about_txt_difficulty);
        tv_bits = (TextView) findViewById(R.id.activity_block_item_about_txt_bits);
        tv_size = (TextView) findViewById(R.id.activity_block_item_about_txt_size);
        tv_weight = (TextView) findViewById(R.id.activity_block_item_about_txt_weight);
        tv_version = (TextView) findViewById(R.id.activity_block_item_about_txt_version);
        tv_nonce = (TextView) findViewById(R.id.activity_block_item_about_txt_nonce);
        tv_block_reward = (TextView) findViewById(R.id.activity_block_item_about_txt_block_reward);
        tv_block_hash = (TextView) findViewById(R.id.activity_block_item_about_txt_block_hash);

        bNum = getIntent().getStringExtra("blockNumber");
        bRelayd_by = getIntent().getStringExtra("blockMinedBy");
        bRecived_time = getIntent().getStringExtra("blockMinedAt");
        bHash = getIntent().getStringExtra("blockHash");

        tv_blockNum.setText(bNum);
        tv_recived_time.setText(bRecived_time);
        tv_relayd_by.setText(bRelayd_by);
        tv_block_hash.setText(bHash);

        //readWebpage(tv_bits);

    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
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

            Log.d("DATA", result);

            Toast.makeText(BlockItemAbout.this, "ok", Toast.LENGTH_SHORT).show();
            /*
            String startsWith = "</table>";

            Integer id = 0;

            String result1 = result.substring(result.indexOf("<div class=\"container\">"), result.indexOf("<div style=\"width:100%; clear:both;\">"));
            String result2 = result1.substring(result1.indexOf("</tr>"));

            String noTrans = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("<span"));

            result2 = result2.substring(result2.indexOf("\">") + 2);
            String outTotal = result2.substring(0, result2.indexOf("</span>"));

            result2 = result2.substring(result2.indexOf("\">") + 2);
            String estTransVal = result2.substring(0, result2.indexOf("</span>"));

            result2 = result2.substring(result2.indexOf("\">") + 2);
            String transFee = result2.substring(0, result2.indexOf("</span>"));

            result2 = result2.substring(result2.indexOf("\">") + 2);
            String height = result2.substring(0, result2.indexOf("</a>"));

            result2 = result2.substring(result2.indexOf("<td>") + 4);
            result2 = result2.substring(result2.indexOf("<td>") + 4);
            String receivedTime = result2.substring(0, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("href=\"/blocks/") + 14);
            String relayedBy = result2.substring(0, result2.indexOf("\">"));

            result2 = result2.substring(result2.indexOf("Difficulty"));
            String difficulty = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("Bits"));
            String bits = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("Size"));
            String size = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("Weight"));
            String weight = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("Version"));
            String version = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("Nonce"));
            String nonce = result2.substring(result2.indexOf("<td>") + 4, result2.indexOf("</td>"));

            result2 = result2.substring(result2.indexOf("Block Reward"));
            String blockReward = result2.substring(result2.indexOf("\">") + 2, result2.indexOf("</span>"));

            result2 = result2.substring(result2.indexOf("Hash"));
            String hash = result2.substring(result2.indexOf("\">") + 2, result2.indexOf("</a>"));

            result2 = result2.substring(result2.indexOf("Previous Block"));
            String previousHash = result2.substring(result2.indexOf("\">") + 2, result2.indexOf("</a>"));


            String res = "Trans No: " + noTrans + "\n";
            res += "Output total: " + outTotal + "\n";
            res += "Est Trans Fee: " + estTransVal + "\n";
            res += "Trans Fee: " + transFee + "\n";
            res += "Relayed by: " + height + "\n";
            res += "Time: " + receivedTime + "\n";
            res += "Relayed by: " + relayedBy + "\n";
            res += "Difficulty: " + difficulty + "\n";
            res += "Bits: " + bits + "\n";
            res += "Size: " + size + "\n";
            res += "Weight:" + weight + "\n";
            res += "Version: " + version + "\n";
            res += "Nonce: " + nonce + "\n";
            res += "BlockReward: " + blockReward + "\n";
            res += "Hash: " + hash + "\n";
            res += "Previous Hash: " + previousHash + "\n";

            Log.d("DATA", res);
            */
        }
    }

    public void readWebpage(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        String web = "https://blockchain.info/block/" + bHash;
        task.execute(web);

    }

}
