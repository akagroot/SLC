/**
 * This class is generated by jOOQ
 */
package com.performancecarerx.persistence.tables;


import com.performancecarerx.persistence.Keys;
import com.performancecarerx.persistence.PerformancecarerxDb;
import com.performancecarerx.persistence.tables.records.ExerciseGroupsRecord;

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
public class ExerciseGroups extends TableImpl<ExerciseGroupsRecord> {

    private static final long serialVersionUID = 987811798;

    /**
     * The reference instance of <code>performancecarerx_db.exercise_groups</code>
     */
    public static final ExerciseGroups EXERCISE_GROUPS = new ExerciseGroups();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExerciseGroupsRecord> getRecordType() {
        return ExerciseGroupsRecord.class;
    }

    /**
     * The column <code>performancecarerx_db.exercise_groups.key_name</code>.
     */
    public final TableField<ExerciseGroupsRecord, String> KEY_NAME = createField("key_name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

    /**
     * The column <code>performancecarerx_db.exercise_groups.display_name</code>.
     */
    public final TableField<ExerciseGroupsRecord, String> DISPLAY_NAME = createField("display_name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

    /**
     * Create a <code>performancecarerx_db.exercise_groups</code> table reference
     */
    public ExerciseGroups() {
        this("exercise_groups", null);
    }

    /**
     * Create an aliased <code>performancecarerx_db.exercise_groups</code> table reference
     */
    public ExerciseGroups(String alias) {
        this(alias, EXERCISE_GROUPS);
    }

    private ExerciseGroups(String alias, Table<ExerciseGroupsRecord> aliased) {
        this(alias, aliased, null);
    }

    private ExerciseGroups(String alias, Table<ExerciseGroupsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return PerformancecarerxDb.PERFORMANCECARERX_DB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ExerciseGroupsRecord> getPrimaryKey() {
        return Keys.KEY_EXERCISE_GROUPS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ExerciseGroupsRecord>> getKeys() {
        return Arrays.<UniqueKey<ExerciseGroupsRecord>>asList(Keys.KEY_EXERCISE_GROUPS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExerciseGroups as(String alias) {
        return new ExerciseGroups(alias, this);
    }

    /**
     * Rename this table
     */
    public ExerciseGroups rename(String name) {
        return new ExerciseGroups(name, null);
    }
}