package com.vzurauskas.accountstransfers.account;

import com.vzurauskas.accountstransfers.common.Amount;

public interface Transaction {
    Amount amountFor(Account account);
}
