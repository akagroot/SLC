/**
 * This class is generated by jOOQ
 */
package com.performancecarerx.persistence.tables;


import com.performancecarerx.persistence.Keys;
import com.performancecarerx.persistence.PerformancecarerxDb;
import com.performancecarerx.persistence.tables.records.ExercisesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
public class Exercises extends TableImpl<ExercisesRecord> {

    private static final long serialVersionUID = -947058215;

    /**
     * The reference instance of <code>performancecarerx_db.exercises</code>
     */
    public static final Exercises EXERCISES = new Exercises();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExercisesRecord> getRecordType() {
        return ExercisesRecord.class;
    }

    /**
     * The column <code>performancecarerx_db.exercises.id</code>.
     */
    public final TableField<ExercisesRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>performancecarerx_db.exercises.name</code>.
     */
    public final TableField<ExercisesRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

    /**
     * The column <code>performancecarerx_db.exercises.exercise_group_key_name</code>.
     */
    public final TableField<ExercisesRecord, String> EXERCISE_GROUP_KEY_NAME = createField("exercise_group_key_name", org.jooq.impl.SQLDataType.VARCHAR.length(45).nullable(false), this, "");

    /**
     * The column <code>performancecarerx_db.exercises.is_deleted</code>.
     */
    public final TableField<ExercisesRecord, Boolean> IS_DELETED = createField("is_deleted", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * Create a <code>performancecarerx_db.exercises</code> table reference
     */
    public Exercises() {
        this("exercises", null);
    }

    /**
     * Create an aliased <code>performancecarerx_db.exercises</code> table reference
     */
    public Exercises(String alias) {
        this(alias, EXERCISES);
    }

    private Exercises(String alias, Table<ExercisesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Exercises(String alias, Table<ExercisesRecord> aliased, Field<?>[] parameters) {
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
    public Identity<ExercisesRecord, Integer> getIdentity() {
        return Keys.IDENTITY_EXERCISES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ExercisesRecord> getPrimaryKey() {
        return Keys.KEY_EXERCISES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ExercisesRecord>> getKeys() {
        return Arrays.<UniqueKey<ExercisesRecord>>asList(Keys.KEY_EXERCISES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ExercisesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ExercisesRecord, ?>>asList(Keys.FK_EXERCISE_EXERCISE_GROUP_KEY_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exercises as(String alias) {
        return new Exercises(alias, this);
    }

    /**
     * Rename this table
     */
    public Exercises rename(String name) {
        return new Exercises(name, null);
    }
}
