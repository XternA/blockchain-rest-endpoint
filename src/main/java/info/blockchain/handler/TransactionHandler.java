package info.blockchain.handler;

import info.blockchain.client.BlockchainExplorerClient;
import info.blockchain.list.TransactionList;
import info.blockchain.transaction.Transaction;
import info.blockchain.transaction.Transaction.TransactionBuilder;
import info.blockchain.transaction.UnspentTransaction;
import lombok.AllArgsConstructor;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@AllArgsConstructor
public class TransactionHandler implements ITransactionHandler {

    private final BlockchainExplorerClient blockchainExplorerClient;

    @Override
    public TransactionList getUnspentTransactions(String walletAddress) throws Exception {
        return TransactionList.builder()
                .outputs(mapTransactions(blockchainExplorerClient.getUnspentTransactions(walletAddress)))
                .build();
    }

    private List<Transaction> mapTransactions(List<UnspentTransaction> unspentTransactions) {
        TransactionBuilder transactionBuilder = Transaction.builder();

        return unspentTransactions
                .stream()
                .map(unspentTransaction -> transactionBuilder
                        .setValue(unspentTransaction.getValue())
                        .setTxHash(unspentTransaction.getTx_hash())
                        .setOutputIdx(unspentTransaction.getTx_output_n())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
