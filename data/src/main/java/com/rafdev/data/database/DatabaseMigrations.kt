package com.rafdev.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `creditCard` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `type` INTEGER NOT NULL,
                `title` TEXT NOT NULL,
                `number` TEXT NOT NULL,
                `total` REAL NOT NULL,
                `color` INTEGER NOT NULL
            )
        """.trimIndent())
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Tabla events
        db.execSQL(
            """
            CREATE TABLE events (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                budget REAL NOT NULL,
                totalSpent REAL NOT NULL,
                createdAt TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Tabla event_expenses
        db.execSQL(
            """
            CREATE TABLE event_expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                eventId INTEGER NOT NULL,
                name TEXT NOT NULL,
                amount REAL NOT NULL,
                createdAt TEXT NOT NULL,
                FOREIGN KEY(eventId) REFERENCES events(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            "CREATE INDEX index_event_expenses_eventId ON event_expenses(eventId)"
        )
    }
}