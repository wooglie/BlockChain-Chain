package org.wooglies.blockchainchain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wooglie on 2.10.2017..
 */
public class BlockItemAdapter extends ArrayAdapter<BlockItem> {

    private final Context context;
    private final ArrayList<BlockItem> itemsArrayList;

    private String currency = "Kn";

    public BlockItemAdapter(Context context, ArrayList<BlockItem> itemsArrayList) {
        super(context, R.layout.pool_item, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = layoutInflater.inflate(R.layout.pool_item, parent, false);

        TextView blockNumber = (TextView) itemView.findViewById(R.id.pool_item_txtBlockNum);
        TextView blockMinedBy = (TextView) itemView.findViewById(R.id.pool_item_txtBlockMinedBy);
        TextView blockMinedAt = (TextView) itemView.findViewById(R.id.pool_item_txtBlockMinedAt);

        blockNumber.setText(itemsArrayList.get(position).getBlockNumber());
        blockMinedBy.setText(itemsArrayList.get(position).getBlockMinedBy());
        blockMinedAt.setText(itemsArrayList.get(position).getBlockMinedAt().substring(11));

        return itemView;
    }

}
