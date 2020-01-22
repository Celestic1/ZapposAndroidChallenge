package raymond.liang.ilovezappos.viewmodels;

import androidx.lifecycle.ViewModel;
import raymond.liang.ilovezappos.models.Transaction;

public class TransactionHistoryViewModel extends ViewModel {

    private Transaction transaction;

    public TransactionHistoryViewModel(){
    }

    public Transaction getTransaction(){
        return transaction;
    }

    public void setTransaction(Transaction transaction){
        this.transaction = transaction;
    }

}
