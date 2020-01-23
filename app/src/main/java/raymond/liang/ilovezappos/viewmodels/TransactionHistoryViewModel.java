package raymond.liang.ilovezappos.viewmodels;

import java.util.List;

import androidx.lifecycle.ViewModel;
import raymond.liang.ilovezappos.models.Transaction;

public class TransactionHistoryViewModel extends ViewModel {

    private List<Transaction> transaction;

    public TransactionHistoryViewModel(){
    }

    public List<Transaction> getTransaction(){
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction){
        this.transaction = transaction;
    }

}
