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