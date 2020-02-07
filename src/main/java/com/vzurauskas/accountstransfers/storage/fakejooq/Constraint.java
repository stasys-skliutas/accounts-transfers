package com.vzurauskas.accountstransfers.storage.fakejooq;

import java.util.Collection;

import org.jooq.Record;

public interface Constraint {

    boolean satisfies(Collection<Record> table, Record inserted);
}
