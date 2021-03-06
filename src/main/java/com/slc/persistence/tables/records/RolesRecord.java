/**
 * This class is generated by jOOQ
 */
package com.slc.persistence.tables.records;


import com.slc.persistence.tables.Roles;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class RolesRecord extends UpdatableRecordImpl<RolesRecord> implements Record1<String> {

    private static final long serialVersionUID = 1763519903;

    /**
     * Setter for <code>public.roles.role</code>.
     */
    public void setRole(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.roles.role</code>.
     */
    public String getRole() {
        return (String) get(0);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row1<String> valuesRow() {
        return (Row1) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Roles.ROLES.ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolesRecord value1(String value) {
        setRole(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RolesRecord values(String value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RolesRecord
     */
    public RolesRecord() {
        super(Roles.ROLES);
    }

    /**
     * Create a detached, initialised RolesRecord
     */
    public RolesRecord(String role) {
        super(Roles.ROLES);

        set(0, role);
    }
}
