package org.wooglies.blockchainchain;

/**
 * Created by Wooglie on 2.10.2017..
 */
public class BlockItem {

    private String blockNumber, blockMinedBy, blockMinedAt, blockHash;

    public BlockItem(String blockNumber, String blockMinedBy, String blockMinedAt, String blockHash){

        this.blockHash = blockHash;
        this.blockMinedAt = blockMinedAt;
        this.blockMinedBy = blockMinedBy;
        this.blockNumber = blockNumber;
    }

    public String getBlockNumber() { return blockNumber; }

    public String getBlockMinedBy() { return blockMinedBy; }

    public String getBlockMinedAt() { return blockMinedAt; }

    public String getBlockHash() { return blockHash; }


}
