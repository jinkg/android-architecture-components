package com.kinglloy.android.persistence.migrations;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static com.kinglloy.android.persistence.migrations.UsersDatabase.MIGRATION_1_2;
import static com.kinglloy.android.persistence.migrations.UsersDatabase.MIGRATION_1_4;
import static com.kinglloy.android.persistence.migrations.UsersDatabase.MIGRATION_2_3;
import static com.kinglloy.android.persistence.migrations.UsersDatabase.MIGRATION_3_4;
import static junit.framework.Assert.assertEquals;

/**
 * Test the migration from different database schema versions to version 4.
 *
 * @author jinyalin
 * @since 2018/2/2.
 */
@RunWith(AndroidJUnit4.class)
public class MigrationTest {
    private static final String TEST_DB_NAME = "test-db";

    private static final User USER = new User("id", "username",
            new Date(System.currentTimeMillis()));

    // Helper for creating Room databases and migrations
    @Rule
    public MigrationTestHelper mMigrationTestHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                    UsersDatabase.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());

    // Helper for creating SQLite database in version 1
    private SqliteTestDbOpenHelper mSqliteTestDbHelper;

    @Before
    public void setUp() throws Exception {
        // To test migrations from version 1 of the database, we need to create the database
        // with version 1 using SQLite API
        mSqliteTestDbHelper = new SqliteTestDbOpenHelper(InstrumentationRegistry.getTargetContext(),
                TEST_DB_NAME);
        // We're creating the table for every test, to ensure that the table is in the correct state
        SqliteDatabaseTestHelper.createTable(mSqliteTestDbHelper);
    }

    @After
    public void tearDown() throws Exception {
        // Clear the database after every test
        SqliteDatabaseTestHelper.clearDatabase(mSqliteTestDbHelper);
    }

    @Test
    public void migrationFrom1To4_containsCorrectData() throws IOException {
        // Create the database with the initial version 1 schema and insert a user
        SqliteDatabaseTestHelper.insertUser(1, USER.getUserName(), mSqliteTestDbHelper);

        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 4, true,
                MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4);

        User dbUser = getMigratedRoomDatabase().userDao().getUser();
        assertEquals(dbUser.getId(), "1");
        assertEquals(dbUser.getUserName(), USER.getUserName());
    }

    @Test
    public void migrationFrom2To4_containsCorrectData() throws IOException {
        // Create the database with version 2
        SupportSQLiteDatabase db = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 2);
        // Insert some data
        insertUser(1, USER.getUserName(), db);
        //Prepare for the next version
        db.close();

        // Re-open the database with version 4 and provide
        // MIGRATION_2_3 and MIGRATION_3_4 as the migration process.
        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 4, true,
                MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4);

        // MigrationTestHelper automatically verifies the schema changes, but not the data validity
        // Validate that the data was migrated properly.
        User dbUser = getMigratedRoomDatabase().userDao().getUser();
        assertEquals(dbUser.getId(), "1");
        assertEquals(dbUser.getUserName(), USER.getUserName());
        // The date was missing in version 2, so it should be null in version 4
        assertEquals(dbUser.getDate(), null);
    }

    @Test
    public void migrationFrom3To4_containsCorrectData() throws IOException {
        // Create the database with version 3
        SupportSQLiteDatabase db = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 3);
        // db has schema version 3. Insert some data
        insertUser(1, USER.getUserName(), DateConverter.toTimestamp(USER.getDate()), db);
        //Prepare for the next version
        db.close();

        // Re-open the database with version 4 and provide
        // MIGRATION_2_3 and MIGRATION_3_4 as the migration process.
        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 4, true,
                MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4);

        // MigrationTestHelper automatically verifies the schema changes, but not the data validity
        // Validate that the data was migrated properly.
        User dbUser = getMigratedRoomDatabase().userDao().getUser();
        assertEquals(dbUser.getId(), "1");
        assertEquals(dbUser.getUserName(), USER.getUserName());
        assertEquals(dbUser.getDate(), USER.getDate());
    }

    @Test
    public void startInVersion4_containsCorrectData() throws IOException {
        // Create the database with version 4
        SupportSQLiteDatabase db = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 4);
        // insert some data
        insertUser(USER.getId(), USER.getUserName(), DateConverter.toTimestamp(USER.getDate()), db);
        db.close();

        // verify that the data is correct
        User dbUser = getMigratedRoomDatabase().userDao().getUser();
        assertEquals(dbUser.getId(), USER.getId());
        assertEquals(dbUser.getUserName(), USER.getUserName());
        assertEquals(dbUser.getDate(), USER.getDate());
    }

    private UsersDatabase getMigratedRoomDatabase() {
        UsersDatabase database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                UsersDatabase.class, TEST_DB_NAME)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_4)
                .build();
        // close the database and release any stream resources when the test finishes
        mMigrationTestHelper.closeWhenFinished(database);
        return database;
    }

    private void insertUser(int id, String userName, SupportSQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("userid", id);
        values.put("username", userName);

        db.insert("users", SQLiteDatabase.CONFLICT_REPLACE, values);
    }

    private void insertUser(int id, String userName, long date, SupportSQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("userid", id);
        values.put("username", userName);
        values.put("last_update", date);

        db.insert("users", SQLiteDatabase.CONFLICT_REPLACE, values);
    }

    private void insertUser(String id, String userName, long date, SupportSQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("userid", id);
        values.put("username", userName);
        values.put("last_update", date);

        db.insert("users", SQLiteDatabase.CONFLICT_REPLACE, values);
    }
}
