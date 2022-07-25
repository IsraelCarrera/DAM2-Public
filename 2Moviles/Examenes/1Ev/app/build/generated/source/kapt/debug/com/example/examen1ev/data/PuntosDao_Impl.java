package com.example.examen1ev.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.collection.LongSparseArray;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.examen1ev.data.modelo.MensajesEntidad;
import com.example.examen1ev.data.modelo.PuntosConMensajes;
import com.example.examen1ev.data.modelo.PuntosEntidad;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PuntosDao_Impl implements PuntosDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MensajesEntidad> __insertionAdapterOfMensajesEntidad;

  private final EntityDeletionOrUpdateAdapter<MensajesEntidad> __deletionAdapterOfMensajesEntidad;

  public PuntosDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMensajesEntidad = new EntityInsertionAdapter<MensajesEntidad>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `mensaje` (`id`,`idPunto`,`autor`,`mensaje`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MensajesEntidad value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getIdPunto());
        if (value.getAutor() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAutor());
        }
        if (value.getMensaje() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMensaje());
        }
      }
    };
    this.__deletionAdapterOfMensajesEntidad = new EntityDeletionOrUpdateAdapter<MensajesEntidad>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `mensaje` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MensajesEntidad value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public Object addMensaje(final MensajesEntidad mensaje,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMensajesEntidad.insert(mensaje);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteMensaje(final MensajesEntidad mens,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMensajesEntidad.handle(mens);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllPuntos(final Continuation<? super List<PuntosConMensajes>> continuation) {
    final String _sql = "Select * from punto";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PuntosConMensajes>>() {
      @Override
      public List<PuntosConMensajes> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLatitud = CursorUtil.getColumnIndexOrThrow(_cursor, "latitud");
          final int _cursorIndexOfLongitud = CursorUtil.getColumnIndexOrThrow(_cursor, "longitud");
          final LongSparseArray<ArrayList<MensajesEntidad>> _collectionMensajes = new LongSparseArray<ArrayList<MensajesEntidad>>();
          while (_cursor.moveToNext()) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
            ArrayList<MensajesEntidad> _tmpMensajesCollection = _collectionMensajes.get(_tmpKey);
            if (_tmpMensajesCollection == null) {
              _tmpMensajesCollection = new ArrayList<MensajesEntidad>();
              _collectionMensajes.put(_tmpKey, _tmpMensajesCollection);
            }
          }
          _cursor.moveToPosition(-1);
          __fetchRelationshipmensajeAscomExampleExamen1evDataModeloMensajesEntidad(_collectionMensajes);
          final List<PuntosConMensajes> _result = new ArrayList<PuntosConMensajes>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PuntosConMensajes _item;
            final PuntosEntidad _tmpPuntos;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfLatitud) && _cursor.isNull(_cursorIndexOfLongitud))) {
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final double _tmpLatitud;
              _tmpLatitud = _cursor.getDouble(_cursorIndexOfLatitud);
              final double _tmpLongitud;
              _tmpLongitud = _cursor.getDouble(_cursorIndexOfLongitud);
              _tmpPuntos = new PuntosEntidad(_tmpId,_tmpLatitud,_tmpLongitud);
            }  else  {
              _tmpPuntos = null;
            }
            ArrayList<MensajesEntidad> _tmpMensajesCollection_1 = null;
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
            _tmpMensajesCollection_1 = _collectionMensajes.get(_tmpKey_1);
            if (_tmpMensajesCollection_1 == null) {
              _tmpMensajesCollection_1 = new ArrayList<MensajesEntidad>();
            }
            _item = new PuntosConMensajes(_tmpPuntos,_tmpMensajesCollection_1);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getOnePunto(final int id,
      final Continuation<? super PuntosConMensajes> continuation) {
    final String _sql = "select * from punto where id= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PuntosConMensajes>() {
      @Override
      public PuntosConMensajes call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfLatitud = CursorUtil.getColumnIndexOrThrow(_cursor, "latitud");
          final int _cursorIndexOfLongitud = CursorUtil.getColumnIndexOrThrow(_cursor, "longitud");
          final LongSparseArray<ArrayList<MensajesEntidad>> _collectionMensajes = new LongSparseArray<ArrayList<MensajesEntidad>>();
          while (_cursor.moveToNext()) {
            final long _tmpKey = _cursor.getLong(_cursorIndexOfId);
            ArrayList<MensajesEntidad> _tmpMensajesCollection = _collectionMensajes.get(_tmpKey);
            if (_tmpMensajesCollection == null) {
              _tmpMensajesCollection = new ArrayList<MensajesEntidad>();
              _collectionMensajes.put(_tmpKey, _tmpMensajesCollection);
            }
          }
          _cursor.moveToPosition(-1);
          __fetchRelationshipmensajeAscomExampleExamen1evDataModeloMensajesEntidad(_collectionMensajes);
          final PuntosConMensajes _result;
          if(_cursor.moveToFirst()) {
            final PuntosEntidad _tmpPuntos;
            if (! (_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfLatitud) && _cursor.isNull(_cursorIndexOfLongitud))) {
              final int _tmpId;
              _tmpId = _cursor.getInt(_cursorIndexOfId);
              final double _tmpLatitud;
              _tmpLatitud = _cursor.getDouble(_cursorIndexOfLatitud);
              final double _tmpLongitud;
              _tmpLongitud = _cursor.getDouble(_cursorIndexOfLongitud);
              _tmpPuntos = new PuntosEntidad(_tmpId,_tmpLatitud,_tmpLongitud);
            }  else  {
              _tmpPuntos = null;
            }
            ArrayList<MensajesEntidad> _tmpMensajesCollection_1 = null;
            final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
            _tmpMensajesCollection_1 = _collectionMensajes.get(_tmpKey_1);
            if (_tmpMensajesCollection_1 == null) {
              _tmpMensajesCollection_1 = new ArrayList<MensajesEntidad>();
            }
            _result = new PuntosConMensajes(_tmpPuntos,_tmpMensajesCollection_1);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllMensajesByIdPunto(final int id,
      final Continuation<? super List<MensajesEntidad>> continuation) {
    final String _sql = "select * from mensaje where idPunto=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<MensajesEntidad>>() {
      @Override
      public List<MensajesEntidad> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIdPunto = CursorUtil.getColumnIndexOrThrow(_cursor, "idPunto");
          final int _cursorIndexOfAutor = CursorUtil.getColumnIndexOrThrow(_cursor, "autor");
          final int _cursorIndexOfMensaje = CursorUtil.getColumnIndexOrThrow(_cursor, "mensaje");
          final List<MensajesEntidad> _result = new ArrayList<MensajesEntidad>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MensajesEntidad _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpIdPunto;
            _tmpIdPunto = _cursor.getInt(_cursorIndexOfIdPunto);
            final String _tmpAutor;
            if (_cursor.isNull(_cursorIndexOfAutor)) {
              _tmpAutor = null;
            } else {
              _tmpAutor = _cursor.getString(_cursorIndexOfAutor);
            }
            final String _tmpMensaje;
            if (_cursor.isNull(_cursorIndexOfMensaje)) {
              _tmpMensaje = null;
            } else {
              _tmpMensaje = _cursor.getString(_cursorIndexOfMensaje);
            }
            _item = new MensajesEntidad(_tmpId,_tmpIdPunto,_tmpAutor,_tmpMensaje);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipmensajeAscomExampleExamen1evDataModeloMensajesEntidad(
      final LongSparseArray<ArrayList<MensajesEntidad>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<MensajesEntidad>> _tmpInnerMap = new LongSparseArray<ArrayList<MensajesEntidad>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipmensajeAscomExampleExamen1evDataModeloMensajesEntidad(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<MensajesEntidad>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipmensajeAscomExampleExamen1evDataModeloMensajesEntidad(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`idPunto`,`autor`,`mensaje` FROM `mensaje` WHERE `idPunto` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "idPunto");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfIdPunto = CursorUtil.getColumnIndexOrThrow(_cursor, "idPunto");
      final int _cursorIndexOfAutor = CursorUtil.getColumnIndexOrThrow(_cursor, "autor");
      final int _cursorIndexOfMensaje = CursorUtil.getColumnIndexOrThrow(_cursor, "mensaje");
      while(_cursor.moveToNext()) {
        final long _tmpKey = _cursor.getLong(_itemKeyIndex);
        ArrayList<MensajesEntidad> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final MensajesEntidad _item_1;
          final int _tmpId;
          _tmpId = _cursor.getInt(_cursorIndexOfId);
          final int _tmpIdPunto;
          _tmpIdPunto = _cursor.getInt(_cursorIndexOfIdPunto);
          final String _tmpAutor;
          if (_cursor.isNull(_cursorIndexOfAutor)) {
            _tmpAutor = null;
          } else {
            _tmpAutor = _cursor.getString(_cursorIndexOfAutor);
          }
          final String _tmpMensaje;
          if (_cursor.isNull(_cursorIndexOfMensaje)) {
            _tmpMensaje = null;
          } else {
            _tmpMensaje = _cursor.getString(_cursorIndexOfMensaje);
          }
          _item_1 = new MensajesEntidad(_tmpId,_tmpIdPunto,_tmpAutor,_tmpMensaje);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
