/**
 * This class is generated by jOOQ
 */
package com.performancecarerx.persistence.tables;


import com.performancecarerx.persistence.Keys;
import com.performancecarerx.persistence.Public;
import com.performancecarerx.persistence.tables.records.RolesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Roles extends TableImpl<RolesRecord> {

    private static final long serialVersionUID = 42394646;

    /**
     * The reference instance of <code>public.roles</code>
     */
    public static final Roles ROLES = new Roles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RolesRecord> getRecordType() {
        return RolesRecord.class;
    }

    /**
     * The column <code>public.roles.role</code>.
     */
    public final TableField<RolesRecord, String> ROLE = createField("role", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>public.roles</code> table reference
     */
    public Roles() {
        this("roles", null);
    }

    /**
     * Create an aliased <code>public.roles</code> table reference
     */
    public Roles(String alias) {
        this(alias, ROLES);
    }

    private Roles(String alias, Table<RolesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Roles(String alias, Table<RolesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<RolesRecord> getPrimaryKey() {
        return Keys.ROLES_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<RolesRecord>> getKeys() {
        return Arrays.<UniqueKey<RolesRecord>>asList(Keys.ROLES_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Roles as(String alias) {
        return new Roles(alias, this);
    }

    /**
     * Rename this table
     */
    public Roles rename(String name) {
        return new Roles(name, null);
    }
}
