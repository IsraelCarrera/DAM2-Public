package com.example.examen1ev.data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PuntosDatabaseRoom_Impl extends PuntosDatabaseRoom {
  private volatile PuntosDao _puntosDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `punto` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `latitud` REAL NOT NULL, `longitud` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mensaje` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idPunto` INTEGER NOT NULL, `autor` TEXT NOT NULL, `mensaje` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7eaf144527f7412a1f4ffede5c604d8c')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `punto`");
        _db.execSQL("DROP TABLE IF EXISTS `mensaje`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPunto = new HashMap<String, TableInfo.Column>(3);
        _columnsPunto.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunto.put("latitud", new TableInfo.Column("latitud", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunto.put("longitud", new TableInfo.Column("longitud", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPunto = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPunto = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPunto = new TableInfo("punto", _columnsPunto, _foreignKeysPunto, _indicesPunto);
        final TableInfo _existingPunto = TableInfo.read(_db, "punto");
        if (! _infoPunto.equals(_existingPunto)) {
          return new RoomOpenHelper.ValidationResult(false, "punto(com.example.examen1ev.data.modelo.PuntosEntidad).\n"
                  + " Expected:\n" + _infoPunto + "\n"
                  + " Found:\n" + _existingPunto);
        }
        final HashMap<String, TableInfo.Column> _columnsMensaje = new HashMap<String, TableInfo.Column>(4);
        _columnsMensaje.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMensaje.put("idPunto", new TableInfo.Column("idPunto", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMensaje.put("autor", new TableInfo.Column("autor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMensaje.put("mensaje", new TableInfo.Column("mensaje", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMensaje = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMensaje = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMensaje = new TableInfo("mensaje", _columnsMensaje, _foreignKeysMensaje, _indicesMensaje);
        final TableInfo _existingMensaje = TableInfo.read(_db, "mensaje");
        if (! _infoMensaje.equals(_existingMensaje)) {
          return new RoomOpenHelper.ValidationResult(false, "mensaje(com.example.examen1ev.data.modelo.MensajesEntidad).\n"
                  + " Expected:\n" + _infoMensaje + "\n"
                  + " Found:\n" + _existingMensaje);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7eaf144527f7412a1f4ffede5c604d8c", "ffec912489cfd96a28a8ed5b9e4735f5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "punto","mensaje");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `punto`");
      _db.execSQL("DELETE FROM `mensaje`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PuntosDao.class, PuntosDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public PuntosDao puntosDao() {
    if (_puntosDao != null) {
      return _puntosDao;
    } else {
      synchronized(this) {
        if(_puntosDao == null) {
          _puntosDao = new PuntosDao_Impl(this);
        }
        return _puntosDao;
      }
    }
  }
}
