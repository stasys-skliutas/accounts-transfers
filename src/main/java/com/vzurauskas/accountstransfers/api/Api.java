package com.vzurauskas.accountstransfers.api;

import com.vzurauskas.accountstransfers.account.Accounts;
import com.vzurauskas.accountstransfers.account.GetAccount;
import com.vzurauskas.accountstransfers.account.PostAccount;
import com.vzurauskas.accountstransfers.transfer.PostTransfer;
import org.takes.facets.fallback.*;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtBasic;
import org.takes.misc.Opt;
import org.takes.rs.RsText;

import java.io.IOException;

public class Api {
    public static void start(Accounts accounts) throws IOException {
        new FtBasic(
                new TkFallback(
                        new TkFork(
                                new FkRegex("/", "Accounts and Transfers"),
                                new FkRegex("/accounts", new TkFork(new FkMethods("POST", new PostAccount(accounts)))),
                                new FkRegex("/accounts/.+", new TkFork(new FkMethods("GET", new GetAccount(accounts)))),
                                new FkRegex("/transfers", new TkFork(new FkMethods("POST", new PostTransfer(accounts))))
                        ),
                        fallback()
                ),
                8080
        ).start(Exit.NEVER);
    }

    private static Fallback fallback() {
        return new FbChain(
                new FbSlf4j(),
                new FbStatus(404, new RsText("Resource not found.")),
                new FbStatus(405, new RsText("This method is not allowed here.")),
                req -> new Opt.Single<>(new RsText("oops, something went terribly wrong!"))
        );
    }
}
